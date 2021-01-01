package com.rs.custom.data_structures;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;
import com.rs.tools.DebugLine;
import com.rs.utils.Utils;




public class SquealOfFortune implements Serializable {
	private static final long serialVersionUID = -2063410653116131907L;

	public static int SOF_INTERFACE_ID = 1253;
	public static int TAB_INTERFACE_ID = 0;
	private Player player;
	private int prizeId;
	private boolean isDiscarded = false;

	private static List<Item> UNCOMMON = Arrays.asList(new Item[] { new Item(995, 250), new Item(23718), new Item(23722), new Item(23726), new Item(23730),
			new Item(23734),	new Item(23738), new Item(23742), new Item(23746), new Item(23750), new Item(23754),
			new Item(23758), new Item(23762), new Item(23766), new Item(23770), new Item(23775), new Item(23779),
			new Item(23783), new Item(23787), new Item(23791), new Item(23795), new Item(23799), new Item(23803),
			new Item(23807),	new Item(23811), new Item(23815) });
	private static List<Item> RARE = Arrays.asList(new Item[] { new Item(995, 500), new Item(23719), new Item(23723), new Item(23727), new Item(23731),
			new Item(23735), new Item(23739), new Item(23743), new Item(23747), new Item(23751), new Item(23755),
			new Item(23759), new Item(23763), new Item(23767), new Item(23771), new Item(23776), new Item(23780),
			new Item(23784), new Item(23788), new Item(23792), new Item(23796), new Item(23800), new Item(23804),
			new Item(23808), new Item(23812), new Item(23816) });
	private static List<Item> COMMON = Arrays.asList(new Item[] { new Item(995, 100), new Item(23717), new Item(23721), new Item(23725), new Item(23729),
			new Item(23733), new Item(23737), new Item(23741), new Item(23745), new Item(23749), new Item(23753),
			new Item(23757), new Item(23761), new Item(23765), new Item(23769), new Item(23774), new Item(23778),
			new Item(23782), new Item(23786), new Item(23790), new Item(23794), new Item(23798), new Item(23802),
			new Item(23806), new Item(23810), new Item(23814) });
	private static List<Item> SUPER_RARE = Arrays.asList(new Item[] { new Item(995, 1000), new Item(23720), new Item(23724), new Item(23728), new Item(23732),
			new Item(23736), new Item(23740), new Item(23744), new Item(23748), new Item(23752), new Item(23756),
			new Item(23760), new Item(23764), new Item(23768), new Item(23773), new Item(23777), new Item(23781),
			new Item(23785), new Item(23789), new Item(23793), new Item(23797), new Item(23801), new Item(23805),
			new Item(23809), new Item(23812) });


	public void one() {
		player.getPackets().sendGameMessage("made it past the beginning()");
	}



	public static Item getSuperRareItem() {
		return SUPER_RARE.get((int) (Math.random() * SUPER_RARE.size()));
	}

	public static Item getRareItem() {
		return RARE.get((int) (Math.random() * RARE.size()));
	}

	public static Item getCommonItem() {
		return COMMON.get((int) (Math.random() * COMMON.size()));
	}

	public static Item getUncommonItem() {
		return UNCOMMON.get((int) (Math.random() * UNCOMMON.size()));
	}

	private ItemsContainer<Item> items;
	private int[] superRare;
	private int[] rares;
	private int[] common;
	private int[] uncommon;

	public SquealOfFortune(Player player) {
		items = new ItemsContainer<Item>(13, false);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Starts the fortune. RARE - 0, 4, 8 COMMON - 1, 5, 7, 10, 12 UNCOMMON - 2,
	 * 6, 9, 11
	 */
	public void start() {
		items.clear();
		player.getPackets().sendConfigByFile(11026, player.getSpins());
		player.getPackets().sendConfigByFile(11155, Utils.random(1, 5));
		player.getPackets().sendGlobalConfig(1928, 1);
		for (int slotLocation = 0; slotLocation < 14; slotLocation++) {
			if (slotLocation == 8 || slotLocation == 4) {
				items.add(getRareItem());
			} else if (slotLocation == 0) {
				items.add(getSuperRareItem());
			} else if (slotLocation == 2 || slotLocation == 6 || slotLocation == 9 || slotLocation == 11) {
				items.add(getUncommonItem());
			} else if (slotLocation == 1 || slotLocation == 5 || slotLocation == 7 || slotLocation == 10 || slotLocation == 12) {
				items.add(getCommonItem());
			} else {
				items.add(getCommonItem());
			}
		}
		setupSOFSpinInterface();
	}

	public Item setupTotalLevelScaling(Item item) {
		if(item.getAmount() > 1) {
			int totalLevel = 0;
			int totalXP = 0;
			for(short level : player.getSkills().getLevels())
				totalLevel += level;
			for(double xp : player.getSkills().getXp())
				totalXP += (int)xp;

			int levelModifier = totalLevel/100 + 1;
			int xpModifier = totalXP/(1_000_000 * Settings.XP_RATE) + 1;

			item.setAmount(item.getAmount()*(levelModifier+xpModifier));
		}
		return item;
	}

	private void setupSOFSpinInterface() {
		player.getPackets().sendWindowsPane(SOF_INTERFACE_ID, 0);

		//Hide button top right
		player.getPackets().sendIComponentText(SOF_INTERFACE_ID, 108, "Exit");

		//set spin amount
		player.getPackets().sendIComponentText(SOF_INTERFACE_ID, 96, ""+ player.getSpins());

		//Hide Buy Spins1
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 0, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 1, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 2, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 3, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 4, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 5, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 6, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 12, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 14, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 15, true);

		sendInterItems();
	}

	/**
	 * Sends the Items in the reward container.
	 */
	private void sendInterItems() {
		player.getPackets().sendItems(665, items);
		int random = Utils.random(15000);
		if (random < 10) {
			superRare = new int[] { 0, 4, 8 };
			prizeId = (int) superRare[(int) (Math.random() * superRare.length)];
			// super rare
		} else if (random < 50) {
			rares = new int[] { 0, 4, 8 };
			prizeId = (int) rares[(int) (Math.random() * rares.length)];
			// rare
		} else if (random < 5000) {
			// uncommon
			uncommon = new int[] { 2, 6, 9, 11 };
			prizeId = (int) uncommon[(int) (Math.random() * uncommon.length)];
		} else {
			// common
			common = new int[] { 1, 3, 5, 7, 10, 12 };
			prizeId = (int) common[(int) (Math.random() * common.length)];
		}
	}

	private void setVisibleExitButton(boolean isVisible) {
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 246, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 247, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 248, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 249, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 250, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 255, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 256, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 257, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 258, !isVisible);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 260, !isVisible);
	}

	private void setupSOFRewardInterface() {
		player.getPackets().sendIComponentText(SOF_INTERFACE_ID, 162, "Spins remaining: "+ player.getSpins());
		player.getPackets().sendIComponentText(SOF_INTERFACE_ID, 43, "For every 12 hours of play time, you get a spin ticket!");
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 320, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 247, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 248, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 249, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 250, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 255, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 256, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 257, true);
		player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 260, true);
	}

	public void handleSOFButtons(Player player, int buttonId) {
		long currentTime = Utils.currentTimeMillis();

		DebugLine.print("buttonId: " + buttonId);
		if (buttonId == 93) {//red button next screen
			if (player.getSpins() == 0) {
				items.clear();
				player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746	: 548, 0);
				player.getPackets().sendGlobalConfig(1790, 0);
				player.getPackets().sendRunScript(5906);
				isDiscarded = false;
				return;
			}  //damx
			if (player.getLockDelay() >= currentTime) {
				return;
			}
			player.lock(11);
			player.getPackets().sendGlobalConfig(1781, Utils.getRandom(13));
			player.getPackets().sendConfigByFile(10860, prizeId);
			player.getPackets().sendGlobalConfig(1790, 1);
			player.getPackets().sendConfigByFile(10861, prizeId);
			player.setSpins(player.getSpins() - 1); //damx

			final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
			executor.schedule(new Runnable() {
				@Override
				public void run() {
					setupSOFRewardInterface();
				}
			}, 7, TimeUnit.SECONDS);
		}
		else if (buttonId == 106) {
			player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746	: 548, 0);
			items.clear();
		} else if (buttonId == 273) {
			start();
		} else if (buttonId == 192) {//claim item
			if(!isDiscarded) {
				setupSOFRewardInterface();
				setVisibleExitButton(true);
				if (player.getInventory().hasFreeSlots())
					player.getInventory().addItem(new Item(items.get(prizeId).getId()));
				else {
					Item[] itemsToBank = new Item[]{new Item(items.get(prizeId).getId())};
					player.getBank().addItems(itemsToBank, true);
				}
			}
			player.getPackets().sendConfigByFile(10861, 0);
			player.getPackets().sendGlobalConfig(1790, 0);
			player.getPackets().sendHideIComponent(1253, 240, false);
			player.getPackets().sendHideIComponent(1253, 178, false);
			player.getPackets().sendHideIComponent(1253, 225, false);
			player.getPackets().sendRunScript(5906);
			items.clear();
			player.getPackets().sendIComponentText(SOF_INTERFACE_ID, 162, "Spins remaining: "+ player.getSpins());
		} else if (buttonId == 258) {//done
			items.clear();
			player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, 0);
			player.getPackets().sendGlobalConfig(1790, 0);
			player.getPackets().sendRunScript(5906);
		} else if(buttonId == 239) {//discard
			isDiscarded = true;
			setupSOFRewardInterface();
			setVisibleExitButton(true);

			player.getPackets().sendConfigByFile(10861, 0);
			player.getPackets().sendGlobalConfig(1790, 0);
			player.getPackets().sendHideIComponent(1253, 240, false);
			player.getPackets().sendHideIComponent(1253, 178, false);
			player.getPackets().sendHideIComponent(1253, 225, false);
			player.getPackets().sendRunScript(5906);
			items.clear();
			player.getPackets().sendIComponentText(SOF_INTERFACE_ID, 162, "Spins remaining: "+ player.getSpins());
		}
	}

}