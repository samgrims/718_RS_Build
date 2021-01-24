package com.rs.game.player.content;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;
import com.rs.utils.ItemExamines;
import com.rs.utils.ItemSetsKeyGenerator;
import com.rs.utils.ShopsHandler;


public class Shop {

	private static final int MAIN_STOCK_ITEMS_KEY = ItemSetsKeyGenerator.generateKey();

	private static final int MAX_SHOP_SIZE = 40;
	public static final int COINS = 995;

	private String name;
	private int key;
	private HashMap<Integer, Item> stock;
	private boolean stockLoaded;
	private Item[] mainStock;
	private int[] defaultQuantity;
	private Item[] playerStock;
	private Item[] filePlayerStock;
	private int money;
	private int amount;

	private CopyOnWriteArrayList<Player> viewingPlayers;

	public Shop(String name, int key, int money, Item[] mainStock, Item[] playerStock, boolean isGeneralStore) {
		viewingPlayers = new CopyOnWriteArrayList<Player>();
		this.name = name;
		this.key = key;
		stockLoaded = false;
		this.money = money;
		this.mainStock = mainStock;
		defaultQuantity = new int[mainStock.length];
		for (int i = 0; i < defaultQuantity.length; i++)
			defaultQuantity[i] = mainStock[i].getAmount();
		if (isGeneralStore && mainStock.length < MAX_SHOP_SIZE)
			this.playerStock = new Item[MAX_SHOP_SIZE - mainStock.length];
		for(int i = 0; i < playerStock.length; i++) {
			if(i > this.playerStock.length)
				break;
			this.playerStock[i] = playerStock[i];
		}
	}

	public boolean isGeneralStore() {
		return playerStock != null;
	}

	public void addPlayer(final Player player) {
		viewingPlayers.add(player);
		player.getTemporaryAttributtes().put("Shop", this);
		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				viewingPlayers.remove(player);
				player.getTemporaryAttributtes().remove("Shop");
				player.getTemporaryAttributtes().remove("shop_buying");
				player.getTemporaryAttributtes().remove("amount_shop");
			}
		});
		player.getPackets().sendConfig(118, MAIN_STOCK_ITEMS_KEY);
		player.getPackets().sendConfig(1496, -1);
		player.getPackets().sendConfig(532, money);
		player.getPackets().sendConfig(2565, 0);
		loadStock(player);
		player.getPackets().sendGlobalConfig(199, -1);
		player.getInterfaceManager().sendInterface(1265);
		player.getPackets().sendIComponentText(1265, 85, name);
		for (int i = 0; i < MAX_SHOP_SIZE; i++)
			player.getPackets().sendGlobalConfig(946 + i, i < defaultQuantity.length ? defaultQuantity[i]: playerStock != null ? 0 : -1);// prices
		player.getPackets().sendGlobalConfig(1241, 16750848);
		player.getPackets().sendGlobalConfig(1242, 15439903);
		player.getPackets().sendGlobalConfig(741, -1);
		player.getPackets().sendGlobalConfig(743, -1);
		player.getPackets().sendGlobalConfig(744, 0);
		if (playerStock != null)
			player.getPackets().sendHideIComponent(1265, 19, false);
		player.getPackets().sendHideIComponent(1265, 45, true);//hide stats button
		player.getPackets().sendHideIComponent(1265, 60, true);//Hide transaction component
		player.getPackets().sendIComponentSettings(1265, 20, 0, getStoreSize() * 6, 1150);
		player.getPackets().sendIComponentSettings(1265, 26, 0, getStoreSize() * 6, 82903066);
		sendInventory(player);
		player.getTemporaryAttributtes().put("shop_buying", true);
		player.getTemporaryAttributtes().put("amount_shop", 1);
	}

	public void sendInventory(Player player) {
		player.getInterfaceManager().sendInventoryInterface(1266);
		ItemsContainer playerInventory = player.getInventory().getItems();
		player.getPackets().sendItems(93, playerInventory);
		player.getPackets().sendUnlockIComponentOptionSlots(1266, 0, 0, 27, 0, 1, 2, 3, 4, 5);
		player.getPackets().sendInterSetItemsOptionsScript(1266, 0, 93, 4, 7,"Value", "Sell 1", "Sell 5", "Sell 10",
				"Sell 50", "Examine");
	}
	//cid 67
	public void buy(Player player, int slotId, int quantity) {
		if (slotId >= getStoreSize())
			return;
		Item item = itemMapToArray(stock)[slotId];
		if (item == null)
			return;
		if (item.getAmount() == 0) {
			player.getPackets().sendGameMessage("There is no stock of that item at the moment.");
			return;
		}

		int price = getBuyPrice(item);
		int amountCoins = player.getInventory().getItems().getNumberOf(money);
		int maxQuantity = amountCoins / price;
		int buyQuantity = item.getAmount() > quantity ? quantity : item.getAmount();
		boolean enoughCoins = maxQuantity >= buyQuantity;
		if (!enoughCoins) {
			player.getPackets().sendGameMessage("You don't have enough coins.");
			buyQuantity = maxQuantity;
		} else if (quantity > buyQuantity)
			player.getPackets().sendGameMessage("The shop has run out of stock.");
		if (item.getDefinitions().isStackable()) {
			if (player.getInventory().getFreeSlots() < 1) {
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
				return;
			}
		} else {
			int freeSlots = player.getInventory().getFreeSlots();
			if (buyQuantity > freeSlots) {
				buyQuantity = freeSlots;
				player.getPackets().sendGameMessage("Not enough space in your inventory.");
			}
		}
		if (buyQuantity != 0) {
			int totalPrice = price * buyQuantity;
			player.getInventory().deleteItem(money, totalPrice);
			player.getInventory().addItem(item.getId(), buyQuantity);
			stock.get(item.getId()).setAmount(item.getAmount() - buyQuantity);
			if (item.getAmount() <= 0 && slotId >= mainStock.length)
				playerStock[slotId - mainStock.length] = null;
			refreshShop();
			sendInventory(player);
		}
	}

	public void restoreItems() {
		boolean needRefresh = false;
		if(!stockLoaded)
			return;

		for (int i = 0; i < mainStock.length; i++) {
			Item item = mainStock[i];
			if (stock.get(item.getId()).getAmount() < item.getAmount()) {
				stock.get(item.getId()).setAmount(stock.get(item.getId()).getAmount() + 1);
				needRefresh = true;
			}
		}
		if (needRefresh)
			refreshShop();
	}

	public void sell(Player player, int slotId, int sellAmount) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);

		if (item == null)
			return;
		int originalId = item.getId();
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isDestroyItem() || ItemConstants.getItemDefaultCharges(item.getId()) != -1 || !ItemConstants.isTradeable(item)
				|| item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		int defaultQuantity = getDefaultQuantity(item.getId());
		if (defaultQuantity == -1 && playerStock == null) {
			player.getPackets().sendGameMessage("Random items can only be sold at general stores");
			return;
		}
		int price = getSellPrice(item, defaultQuantity);
		int itemAmountInPlayerInventory = player.getInventory().getItems().getNumberOf(originalId);
		if (sellAmount > itemAmountInPlayerInventory)
			sellAmount = itemAmountInPlayerInventory;

		if(!isShopFull()) {
			addItem(item.getId(), sellAmount);
		} else {
			player.getPackets().sendGameMessage("Shop is currently full.");
			return;
		}

		player.getInventory().deleteItem(originalId, sellAmount);
		player.getInventory().addItem(money, price * sellAmount);
		refreshShop();
		renewShopInterfaceCompletely(player);
	}

	private boolean isShopFull() {
		return stock.size() == MAX_SHOP_SIZE;
	}

	private void addItem(int itemId, int quantity) {
		if(stock.containsKey(itemId)) {
			stock.get(itemId).setAmount(stock.get(itemId).getAmount() + quantity);
		}
		if(!stock.containsKey(itemId)) {
			stock.put(itemId, new Item(itemId, quantity));
		}
	}

	/**
	 * This had to be done every time a new item was put into the stock hashmap,
	 * or else there was no interface update.
	 * @param player
	 */
	private void renewShopInterfaceCompletely(Player player) {
		viewingPlayers.remove(player);
		player.getTemporaryAttributtes().remove("Shop");
		player.getTemporaryAttributtes().remove("shop_buying");
		player.getTemporaryAttributtes().remove("amount_shop");
		player.getInterfaceManager().closeScreenInterface();
		this.addPlayer(player);
	}

	public void sendValue(Player player, int slotId) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isNoted() || !ItemConstants.isTradeable(item)	|| item.getId() == money) {
			player.getPackets().sendGameMessage("You can't sell this item.");
			return;
		}
		int dq = getDefaultQuantity(item.getId());
		if (dq == -1 && playerStock == null) {
			player.getPackets().sendGameMessage("You can't sell this item to this shop.");
			return;
		}
		int price = getSellPrice(item, dq);
		player.getPackets().sendGameMessage(item.getDefinitions().getName() + ": shop will buy for: " + price + " " +
				ItemDefinitions.getItemDefinitions(money).getName().toLowerCase() + ". Right-click the item to sell.");
	}

	public int getDefaultQuantity(int itemId) {
		for (int i = 0; i < mainStock.length; i++)
			if (mainStock[i].getId() == itemId)
				return defaultQuantity[i];
		return -1;
	}

	public void sendInfo(Player player, int slotId, boolean isBuying) {
		if (slotId >= getStoreSize())
			return;
		Item[] stock = isBuying ? mainStock : player.getInventory().getItems().getItems();
		Item item = slotId >= stock.length ? playerStock[slotId - stock.length] : stock[slotId];
		if (item == null)
			return;
		int price = getBuyPrice(item);
		player.getPackets().sendGameMessage(item.getDefinitions().getName() + ": shop will " + (isBuying ? "sell" : "buy") +" for " + price + " " + ItemDefinitions.getItemDefinitions(money).getName().toLowerCase() + ".");
	}

	public int getBuyPrice(Item item) {
		int price = item.getDefinitions().getValue();
		if (price == 0)
			price = 1;
		return price;
	}

	public int getSellPrice(Item item, int dq) {
		return item.getDefinitions().getValue() / 2;
	}

	public void sendExamine(Player player, int slotId) {
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? playerStock[slotId - mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
	}

	public void refreshShop() {
		removeZeroStockItems();
		loadStockIntoPlayerStock();
		ShopsHandler.savePlayerStockToFile(this);

		for (Player player : viewingPlayers) {
			loadStock(player);
			if(isBuyingInterfaceOpen(player))
				renewShopInterfaceCompletely(player);
			player.getPackets().sendIComponentSettings(620, 25, 0,	getStoreSize() * 6, 1150);
		}
	}

	public boolean isBuyingInterfaceOpen(Player player) {
		boolean isBuyInterfaceOpen;
		try {
			isBuyInterfaceOpen = (boolean) player.getTemporaryAttributtes().get("shop_buying");
		} catch(NullPointerException e) {
			isBuyInterfaceOpen = false;
		}
		return isBuyInterfaceOpen;
	}

	private void removeZeroStockItems() {
		for(Object item : stock.values().toArray()) {
			Item stockItem = (Item)item;
			if(isMainStockItem(stockItem))
				continue;
			if(stockItem.getAmount() < 1) {
				stock.remove(stockItem.getId());
			}
		}
	}

	private boolean isMainStockItem(Item item) {
		for(Item mainItem : mainStock) {
			if(mainItem.getId() == item.getId())
				return true;
		}
		return false;
	}

	public int getStoreSize() {
		if(stockLoaded)
			return stock.size();
		else
			return mainStock.length	+ (playerStock != null ? playerStock.length : 0);
	}

	public void loadStock(Player player) {
		if(stockLoaded) {
			player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, itemMapToArray(stock));
		}
		else {
			player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, combineMainAndPlayerStock());
			stockLoaded = true;
		}
	}

	private void loadStockIntoPlayerStock() {
		HashMap<Integer, Item> temporaryStock = copyStockMap();

		for(int i = 0; i < mainStock.length; i++) {
			Item mainItem = mainStock[i].clone();
			int itemId = mainItem.getId();
			int mainItemAmount = mainItem.getAmount();
			if(temporaryStock.containsKey(itemId)) {
				int tempItemAmount = temporaryStock.get(itemId).getAmount();
				if(tempItemAmount - mainItemAmount <= 0)
					temporaryStock.remove(itemId);
				else
					temporaryStock.get(itemId).setAmount(tempItemAmount - mainItemAmount);
			}
		}
		filePlayerStock = itemMapToArray(temporaryStock);
	}

	private HashMap<Integer, Item> copyStockMap() {
		Gson gson = new Gson();
		String jsonString = gson.toJson(stock);

		Type type = new TypeToken<HashMap<Integer, Item>>(){}.getType();
		HashMap<Integer, Item> clonedMap = gson.fromJson(jsonString, type);
		return clonedMap;
	}

	public Item[] combineMainAndPlayerStock() {
		int storeSize = getStoreSize();
		stock = new HashMap<>();

		for(int i = 0; i < storeSize; i++) {
			if(i < mainStock.length) {
				stock.put(mainStock[i].getId(), mainStock[i].clone());
			} else if(i < mainStock.length + playerStock.length) {
				Item playerItem = playerStock[i - mainStock.length];
				if(playerItem != null) {
					if(!stock.containsKey(playerItem.getId()))
						stock.put(playerItem.getId(), playerItem.clone());
					else {
						Item existingItem = stock.get(playerItem.getId());
						existingItem.setAmount(existingItem.getAmount() + playerItem.getAmount());
					}
				}
			}
		}
		return itemMapToArray(stock);
	}

	public Item[] itemMapToArray(HashMap<Integer, Item> map) {
		return map.values().toArray(new Item[map.size()]);
	}

	public void sendSellStore(Player player, Item[] inventory) {
		Item[] stock = new Item[inventory.length + (playerStock != null ? playerStock.length : 0)];
		System.arraycopy(inventory, 0, stock, 0, inventory.length);
		if (playerStock != null)
			System.arraycopy(playerStock, 0, stock, inventory.length, playerStock.length);
		player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

	/**
	 * Checks if the player is buying an item or selling it.
	 * @param player The player
	 * @param slotId The slot id
	 * @param amount The amount
	 */
	public void handleShop(Player player, int slotId, int amount) {
		boolean isBuying = player.getTemporaryAttributtes().get("shop_buying") != null;
		if (isBuying)
			buy(player, slotId, amount);
		else
			sell(player, slotId, amount);
	}

	public Item[] getMainStock() {
		return this.mainStock;
	}

	public Item[] getPlayerStock() {
		return this.playerStock;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(Player player, int amount) {
		this.amount = amount;
		player.getPackets().sendIComponentText(1265, 67, String.valueOf(amount)); //just update it here
	}

	public int getKey() {
		return key;
	}

	public Item[] getFilePlayerStock() {
		return filePlayerStock;
	}
}