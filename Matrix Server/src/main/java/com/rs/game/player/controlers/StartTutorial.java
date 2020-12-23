package com.rs.game.player.controlers;

import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class StartTutorial extends Controler {


	private static final int QUEST_GUIDE_NPC = 949;

	@Override
	public void start() {
	}

	public NPC findNPC(int id) {
		// as it may be far away
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

	@Override
	public void process() {
		if (getStage() == 1 && player.getPrayer().isAncientCurses())
			updateProgress();
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		return false;
	}

	@Override
	public boolean processObjectClick2(WorldObject object) {
		return false;
	}

	@Override
	public boolean processObjectClick3(WorldObject object) {
		return false;
	}

	public void refreshStage() {
	}

	@Override
	public void sendInterfaces() {
	}

	public void updateProgress() {
		setStage(getStage() + 1);
		if (getStage() == 2) {
			player.getDialogueManager().startDialogue("QuestGuide",
					QUEST_GUIDE_NPC, this);
		}
		refreshStage();
	}

	@Override
	public boolean processNPCClick1(NPC npc) {
		if (npc.getId() == QUEST_GUIDE_NPC) {
			player.getDialogueManager().startDialogue("QuestGuide",
					QUEST_GUIDE_NPC, this);
		}
		return false;
	}

	public void setStage(int stage) {
		getArguments()[0] = stage;
	}

	public int getStage() {
		if (getArguments() == null)
			setArguments(new Object[] { 0 }); // index 0 = stage
		return (Integer) getArguments()[0];
	}

	/*
	 * return remove controler
	 */
	@Override
	public boolean login() {
		start();
		return false;
	}

	/*
	 * return remove controler
	 */
	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		return false;
	}

	@Override
	public boolean keepCombating(Entity target) {
		return false;
	}

	@Override
	public boolean canAttack(Entity target) {
		return false;
	}

	@Override
	public boolean canHit(Entity target) {
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		return false;
	}

	@Override
	public void forceClose() {
		player.getSkills().addXp(Skills.ATTACK, 13034431);
		player.getSkills().addXp(Skills.STRENGTH, 13034431);
		player.getSkills().addXp(Skills.DEFENCE, 13034431);
		player.getSkills().addXp(Skills.HITPOINTS, 13034431);
		player.getSkills().addXp(Skills.RANGE, 13034431);
		player.getSkills().addXp(Skills.MAGIC, 13034431);
		player.getSkills().addXp(Skills.PRAYER, 13034431);
		player.getSkills().addXp(Skills.SUMMONING, 13034431);
		player.getHintIconsManager().removeUnsavedHintIcon();
		player.getMusicsManager().reset();
		player.setYellOff(false);
		player.getInventory().addItem(1856, 1);
		player.getPackets().sendGameMessage(
				"Congratulations! You finished the start tutorial.");
		player.getPackets()
				.sendGameMessage(
						"You've received a guide book. Use it if you have questions or talk with other players.");
		player.getPackets().sendGameMessage("or talk with other players.");

		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getInterfaceManager().sendInterfaces();
				player.getInterfaceManager()
						.closeReplacedRealChatBoxInterface();
				player.getDialogueManager().startDialogue("QuestGuide",
						QUEST_GUIDE_NPC, null);
			}
		});
	}
}
