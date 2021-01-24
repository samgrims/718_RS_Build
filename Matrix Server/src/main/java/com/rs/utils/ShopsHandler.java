package com.rs.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.Shop;

public class ShopsHandler {
	private static final HashMap<Integer, Shop> handledShops = new HashMap<Integer, Shop>();

	private static final String SHOPS_FILE_PATH = Settings.SERVER_DIR + "data/items/Shops.txt";

	public static void init() {
		loadShops();
	}

	private static void loadShops() {
		Logger.log("ShopsHandler", "Loading shops...");
		try {
			BufferedReader in = new BufferedReader(new FileReader(SHOPS_FILE_PATH));
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				if (line.startsWith("//"))
					continue;
				String[] metaSections = line.split(" - ", 4);
				if (metaSections.length != 3 && metaSections.length != 4)
					throw new RuntimeException("Invalid list for shop line: " + line);
				String[] metaShopHeader = metaSections[0].split(" ", 3);
				if (metaShopHeader.length != 3)
					throw new RuntimeException("Invalid list for shop line: " + line);

				String[] mainItemsMeta = metaSections[2].split(" ");
				Item[] mainStock = interpretMetaItems(mainItemsMeta);

				Item[] playerStock = new Item[0];
				if(metaSections.length == 4) {
					String[] playerItemsMeta = metaSections[3].split(" ");
					playerStock = interpretMetaItems(playerItemsMeta);
				}

				int key = Integer.valueOf(metaShopHeader[0]);
				int money = Integer.valueOf(metaShopHeader[1]);
				boolean generalStore = Boolean.valueOf(metaShopHeader[2]);
				addShop(key, new Shop(metaSections[1], key, money, mainStock, playerStock, generalStore));
			}
			in.close();
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static Item[] interpretMetaItems(String[] itemsMeta) {
		Item[] stock = new Item[itemsMeta.length / 2];
		int amountIndex = 0;
		for (int i = 0; i < stock.length; i++)
			stock[i] = new Item(Integer.valueOf(itemsMeta[amountIndex++]),	Integer.valueOf(itemsMeta[amountIndex++]), true);
		return stock;
	}

	public static void restoreShops() {
		for (Shop shop : handledShops.values())
			shop.restoreItems();
	}

	/**
	 * Does not save mainStock
	 * @param shop
	 */
	public static void savePlayerStockToFile(Shop shop) {
		try {
			String textOutput = getCompleteShopStockMeta(shop);
			BufferedWriter out = new BufferedWriter(new FileWriter(SHOPS_FILE_PATH));

			out.write(textOutput);
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static String getCompleteShopStockMeta(Shop shop) {
		String textOutput = "";
		int shopKey = shop.getKey();
		Item[] playerStock = shop.getFilePlayerStock();

		Path path = Paths.get(SHOPS_FILE_PATH);
		String playerStockMeta = "";
		for(Item item : playerStock)
			playerStockMeta = playerStockMeta + item.getId() + " " + item.getAmount() + " ";

		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			for(String line : lines) {
				if (line == null)
					break;
				if (line.startsWith("//")) {
					textOutput = textOutput + line + "\n";
					continue;
				}

				String[] metaSections = line.split(" - ", 4);
				String[] metaShopHeader = metaSections[0].split(" ", 3);
				if(metaSections.length != 3 && metaSections.length != 4)
					throw new RuntimeException("Invalid list for shop line: " + line);
				if (metaShopHeader.length != 3)
					throw new RuntimeException("Invalid list for shop line: " + line);

				int lineKey = Integer.valueOf(metaShopHeader[0]);
				if(lineKey == shopKey) {
					String resultLine = String.join(" - ", metaSections[0], metaSections[1], metaSections[2], playerStockMeta);
					textOutput = textOutput + resultLine + "\n";
				} else {
					textOutput = textOutput + line + "\n";
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return textOutput;
	}

	public static boolean openShop(Player player, int key) {
		Shop shop = getShop(key);
		if (shop == null)
			return false;
		shop.addPlayer(player);
		return true;
	}

	public static Shop getShop(int key) {
		return handledShops.get(key);
	}

	public static void addShop(int key, Shop shop) {
		handledShops.put(key, shop);
	}

}
