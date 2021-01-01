package com.rs.custom.data_structures;

import java.io.Serializable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
		player.getPackets().sendConfigByFile(11026, player.getSpins());
		player.getPackets().sendConfigByFile(11155, Utils.random(1, 5));
		player.getPackets().sendGlobalConfig(1928, 1);
		for (int slotLocation = 0; slotLocation < 14; slotLocation++) {
			if (slotLocation == 8 || slotLocation == 4) {
				items.add(new Item(rare()));
			} else if (slotLocation == 0) {
				items.add(new Item(superRare()));
			} else if (slotLocation == 2 || slotLocation == 6 || slotLocation == 9 || slotLocation == 11) {
				items.add(new Item(uncommon()));
			} else if (slotLocation == 1 || slotLocation == 5 || slotLocation == 7 || slotLocation == 10 || slotLocation == 12) {
				items.add(new Item(common()));
			} else {
				items.add(new Item(common()));
			}
		}
		player.getPackets().sendWindowsPane(SOF_INTERFACE_ID, 0);

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
	public void sendInterItems() {
		player.getPackets().sendItems(665, items);
		int random = Utils.random(15000);
		if (random < 10) {
			superRare = new int[] { 0, 4, 8 };
			prizeId = (int) superRare[(int) (Math.random() * superRare.length)];
//			System.out.println("You just won super rare!");
			// super rare
		} else if (random < 50) {
			rares = new int[] { 0, 4, 8 };
			prizeId = (int) rares[(int) (Math.random() * rares.length)];
//			System.out.println("You just won rare!");
			// rare
		} else if (random < 5000) {
			// uncommon
			uncommon = new int[] { 2, 6, 9, 11 };
			prizeId = (int) uncommon[(int) (Math.random() * uncommon.length)];
//			System.out.println("uncommon");
		} else {
			// common
			common = new int[] { 1, 3, 5, 7, 10, 12 };
			prizeId = (int) common[(int) (Math.random() * common.length)];
//			System.out.println("common");
		}
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
					System.out.println("run happened");
					player.getPackets().sendIComponentText(SOF_INTERFACE_ID, 162, "Spins remaining: "+ player.getSpins());
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 320, true);//But spins sprite
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 247, true);//Claim later text
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 248, true);//Claim later text
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 249, true);//Claim later text
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 250, true);//Claim later text
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 255, true);//Claim later text
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 256, true);//Claim later text
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 257, true);//Claim later text
					player.getPackets().sendHideIComponent(SOF_INTERFACE_ID, 260, true);//Claim later text
				}
			}, 8, TimeUnit.SECONDS);
		}
		else if (buttonId == 106) {
			player.getPackets().sendWindowsPane(player.getInterfaceManager().hasRezizableScreen() ? 746	: 548, 0);
			items.clear();
		} else if (buttonId == 273) {
			start();
		} else if (buttonId == 192) {//claim item
			if(!isDiscarded)
				if(player.getInventory().hasFreeSlots())
					player.getInventory().addItem(new Item(items.get(prizeId).getId()));
				else {
					Item[] itemsToBank = new Item[]{new Item(items.get(prizeId).getId())};
					player.getBank().addItems(itemsToBank, true);
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