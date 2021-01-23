package com.rs.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.Shop;

public class ShopsHandler {
	private static final HashMap<Integer, Shop> handledShops = new HashMap<Integer, Shop>();

	private static final String UNPACKED_PATH = Settings.SERVER_DIR + "data/items/Shops.txt";

	public static void init() {
		loadShops();
	}

	private static void loadShops() {
		Logger.log("ShopsHandler", "Loading shops...");
		try {
			BufferedReader in = new BufferedReader(new FileReader(UNPACKED_PATH));
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
				addShop(key, new Shop(metaSections[1], money, mainStock, playerStock, generalStore));
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

	public static String readAlexString(ByteBuffer buffer) {
		int count = buffer.get() & 0xfff;
		byte[] bytes = new byte[count];
		buffer.get(bytes, 0, count);
		return new String(bytes);
	}

	public static void writeAlexString(DataOutputStream out, String string)	throws IOException {
		byte[] bytes = string.getBytes();
		out.writeByte(bytes.length);
		out.write(bytes);
	}

	public static void restoreShops() {
		for (Shop shop : handledShops.values())
			shop.restoreItems();
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
