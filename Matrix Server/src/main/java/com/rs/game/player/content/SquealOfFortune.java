package com.rs.game.player.content;

import java.io.Serializable;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;
import com.rs.utils.Utils;




public class SquealOfFortune implements Serializable {

	/**
	 * Generated SerialUID.
	 */
	private static final long serialVersionUID = -2063410653116131907L;

	public static int INTERFACE_ID = 1253;
	public static int TAB_INTERFACE_ID = 0;
	private Player player;
	private int prizeId;
	private boolean needPrize;

	private static int[] UN_COMMON = { 23718, 23722, 23726, 23730, 23734,
			23738, 23742, 23746, 23750, 23754, 23758, 23762, 23766, 23770,
			23775, 23779, 23783, 23787, 23791, 23795, 23799, 23803, 23807,
			23811, 23815 };
	private static int[] RARE = { 23719, 23723, 23727, 23731, 23735, 23739,
			23743, 23747, 23751, 23755, 23759, 23763, 23767, 23771, 23776,
			23780, 23784, 23788, 23792, 23796, 23800, 23804, 23808, 23812,
			23816 };
	private static int[] COMMON = { 23717, 23721, 23725, 23729, 23733, 23737,
			23741, 23745, 23749, 23753, 23757, 23761, 23765, 23769, 23774,
			23778, 23782, 23786, 23790, 23794, 23798, 23802, 23806, 23810,
			23814 };
	private static int[] SUPER_RARE = { 23720, 23724, 23728, 23732, 23736,
			23740, 23744, 23748, 23752, 23756, 23760, 23764, 23768, 23773,
			23777, 23781, 23785, 23789, 23793, 23797, 23801, 23805, 23809,
			23812 };


	public void one() {
		player.getPackets().sendGameMessage("made it past the beginning()");
	}



	public static int superRare() {
		return SUPER_RARE[(int) (Math.random() * SUPER_RARE.length)];
	}

	public static int rare() {
		return RARE[(int) (Math.random() * RARE.length)];
	}

	public static int common() {
		return COMMON[(int) (Math.random() * COMMON.length)];
	}

	public static int uncommon() {
		return UN_COMMON[(int) (Math.random() * UN_COMMON.length)];
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
		player.getPackets().sendConfigByFile(11026, player.getSpins());  //was commented out damx
		player.getPackets().sendConfigByFile(11155, Utils.random(1, 5));
		player.getPackets().sendGlobalConfig(1928, 1);
		for (int i = 0; i < 14; i++) {
			if (i == 8 || i == 4) {
				items.add(new Item(rare()));
			} else if (i == 0) {
				items.add(new Item(superRare()));
			} else if (i == 2 || i == 6 || i == 9 || i == 11) {
				items.add(new Item(uncommon()));
			} else if (i == 1 || i == 5 || i == 7 || i == 10 || i == 12) {
				items.add(new Item(common()));
			} else {
				items.add(new Item(common()));
			}
		}
		player.getPackets().sendWindowsPane(INTERFACE_ID, 0);
		sendInterItems();



	}

	/**
	 * Sends the Items in the reward container.
	 */
	public void sendInterItems() {
		player.getPackets().sendItems(665, items);
		int random = Utils.random(15000);
		if (random < 10) {
			superRare = new int[] { 0, 4, 8 };
			prizeId = (int) superRare[(int) (Math.random() * superRare.length)];
			System.out.println("You just won super rare!");
			// super rare
		} else if (random < 50) {
			rares = new int[] { 0, 4, 8 };
			prizeId = (int) rares[(int) (Math.random() * rares.length)];
			System.out.println("You just won rare!");
			// rare
		} else if (random < 5000) {
			// uncommon
			uncommon = new int[] { 2, 6, 9, 11 };
			prizeId = (int) uncommon[(int) (Math.random() * uncommon.length)];
			System.out.println("uncommon");
		} else {
			// common
			common = new int[] { 1, 3, 5, 7, 10, 12 };
			prizeId = (int) common[(int) (Math.random() * common.length)];
			System.out.println("common");
		}
	}

	public void handleButtons(Player player, int buttonId) {
		long currentTime = Utils.currentTimeMillis();
		if (buttonId == 93) {
			if (player.getSpins() == 0) {
				items.clear();
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, 0);
				player.getPackets().sendGlobalConfig(1790, 0);
				player.getPackets().sendRunScript(5906);
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
		}



		else if (buttonId == 106) {
			player.getPackets().sendWindowsPane(
					player.getInterfaceManager().hasRezizableScreen() ? 746
							: 548, 0);
			items.clear();
		} else if (buttonId == 273) {
			start();
		} else if (buttonId == 192) {
			player.getInventory().addItem(new Item(items.get(prizeId).getId()));
			player.getPackets().sendConfigByFile(10861, 0);
			player.getPackets().sendGlobalConfig(1790, 0);
			player.getPackets().sendHideIComponent(1253, 240, false);
			player.getPackets().sendHideIComponent(1253, 178, false);
			player.getPackets().sendHideIComponent(1253, 225, false);
			player.getPackets().sendRunScript(5906);
			items.clear();
		} else if (buttonId == 258) {
			items.clear();
			player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, 0);
			player.getPackets().sendGlobalConfig(1790, 0);
			player.getPackets().sendRunScript(5906);
		}
	}
}