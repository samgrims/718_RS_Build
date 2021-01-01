package com.rs.game.player.controlers;

import java.util.HashMap;

import com.rs.game.minigames.BrimhavenAgility;
import com.rs.game.minigames.RefugeOfFear;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.RequestController;
import com.rs.game.minigames.clanwars.WarController;
import com.rs.game.minigames.creations.StealingCreationGame;
import com.rs.game.minigames.creations.StealingCreationLobby;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelController;
import com.rs.game.player.controlers.castlewars.CastleWarsPlaying;
import com.rs.game.player.controlers.castlewars.CastleWarsWaiting;
import com.rs.game.player.controlers.events.DeathEvent;
import com.rs.game.player.controlers.fightpits.FightPitsArena;
import com.rs.game.player.controlers.fightpits.FightPitsLobby;
import com.rs.game.player.controlers.pestcontrol.PestControlGame;
import com.rs.game.player.controlers.pestcontrol.PestControlLobby;
import com.rs.utils.Logger;

public class ControlerHandler {

	private static final HashMap<Object, Class<Controller>> handledControlers = new HashMap<Object, Class<Controller>>();

	@SuppressWarnings("unchecked")
	public static final void init() {
		try {
			Class<Controller> value1 = (Class<Controller>) Class
					.forName(Wilderness.class.getCanonicalName());
			handledControlers.put("Wilderness", value1);
			Class<Controller> value2 = (Class<Controller>) Class
					.forName(Kalaboss.class.getCanonicalName());
			handledControlers.put("Kalaboss", value2);
			Class<Controller> value4 = (Class<Controller>) Class
					.forName(GodWars.class.getCanonicalName());
			handledControlers.put("GodWars", value4);
			Class<Controller> value5 = (Class<Controller>) Class
					.forName(ZGDController.class.getCanonicalName());
			handledControlers.put("ZGDControler", value5);
			Class<Controller> value6 = (Class<Controller>) Class
					.forName(TutorialIsland.class.getCanonicalName());
			handledControlers.put("TutorialIsland", value6);
			Class<Controller> value7 = (Class<Controller>) Class
					.forName(StartTutorial.class.getCanonicalName());
			handledControlers.put("StartTutorial", value7);
			Class<Controller> value9 = (Class<Controller>) Class
					.forName(DuelArena.class.getCanonicalName());
			handledControlers.put("DuelArena", value9);
			Class<Controller> value10 = (Class<Controller>) Class
					.forName(DuelController.class.getCanonicalName());
			handledControlers.put("DuelControler", value10);
			Class<Controller> value11 = (Class<Controller>) Class
					.forName(CorpBeastController.class.getCanonicalName());
			handledControlers.put("CorpBeastControler", value11);
			Class<Controller> value14 = (Class<Controller>) Class
					.forName(DTController.class.getCanonicalName());
			handledControlers.put("DTControler", value14);
			Class<Controller> value15 = (Class<Controller>) Class
					.forName(JailController.class.getCanonicalName());
			handledControlers.put("JailControler", value15);
			Class<Controller> value17 = (Class<Controller>) Class
					.forName(CastleWarsPlaying.class.getCanonicalName());
			handledControlers.put("CastleWarsPlaying", value17);
			Class<Controller> value18 = (Class<Controller>) Class
					.forName(CastleWarsWaiting.class.getCanonicalName());
			handledControlers.put("CastleWarsWaiting", value18);
//			Class<Controler> value99 = (Class<Controler>) Class
//					.forName(DungeonControler.class.getCanonicalName());
//			handledControlers.put("DungeonControler", value99);
			Class<Controller> value20 = (Class<Controller>) Class
					.forName(NewHomeController.class.getCanonicalName());
			handledControlers.put("NewHomeControler", value20);
			handledControlers.put("clan_wars_request", (Class<Controller>) Class.forName(RequestController.class.getCanonicalName()));
			handledControlers.put("clan_war", (Class<Controller>) Class.forName(WarController.class.getCanonicalName()));
			handledControlers.put("clan_wars_ffa", (Class<Controller>) Class.forName(FfaZone.class.getCanonicalName()));
			handledControlers.put("NomadsRequiem", (Class<Controller>) Class.forName(NomadsRequiem.class.getCanonicalName()));
			handledControlers.put("BorkControler", (Class<Controller>) Class.forName(BorkController.class.getCanonicalName()));
			handledControlers.put("BrimhavenAgility", (Class<Controller>) Class.forName(BrimhavenAgility.class.getCanonicalName()));
			handledControlers.put("FightCavesControler", (Class<Controller>) Class.forName(FightCaves.class.getCanonicalName()));
			handledControlers.put("FightKilnControler", (Class<Controller>) Class.forName(FightKiln.class.getCanonicalName()));
			handledControlers.put("FightPitsLobby", (Class<Controller>) Class.forName(FightPitsLobby.class.getCanonicalName()));
			handledControlers.put("FightPitsArena", (Class<Controller>) Class.forName(FightPitsArena.class.getCanonicalName()));
			handledControlers.put("PestControlGame", (Class<Controller>) Class.forName(PestControlGame.class.getCanonicalName()));
			handledControlers.put("PestControlLobby", (Class<Controller>) Class.forName(PestControlLobby.class.getCanonicalName()));
			handledControlers.put("Barrows", (Class<Controller>) Class.forName(Barrows.class.getCanonicalName()));
			handledControlers.put("RefugeOfFear", (Class<Controller>) Class.forName(RefugeOfFear.class.getCanonicalName()));
			handledControlers.put("Falconry", (Class<Controller>) Class.forName(Falconry.class.getCanonicalName()));
			handledControlers.put("QueenBlackDragonControler", (Class<Controller>) Class.forName(QueenBlackDragonController.class.getCanonicalName()));
			handledControlers.put("HouseControler", (Class<Controller>) Class.forName(HouseController.class.getCanonicalName()));
			handledControlers.put("RuneSpanControler", (Class<Controller>) Class.forName(RunespanController.class.getCanonicalName()));
			handledControlers.put("DeathEvent", (Class<Controller>) Class.forName(DeathEvent.class.getCanonicalName()));
			handledControlers.put("SorceressGarden", (Class<Controller>) Class.forName(SorceressGarden.class.getCanonicalName()));
			handledControlers.put("CrucibleControler", (Class<Controller>) Class.forName(CrucibleController.class.getCanonicalName()));
			handledControlers.put("StealingCreationsGame", (Class<Controller>) Class.forName(StealingCreationGame.class.getCanonicalName()));
			handledControlers.put("StealingCreationsLobby", (Class<Controller>) Class.forName(StealingCreationLobby.class.getCanonicalName()));
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static final void reload() {
		handledControlers.clear();
		init();
	}

	public static final Controller getControler(Object key) {
		if (key instanceof Controller)
			return (Controller) key;
		Class<Controller> classC = handledControlers.get(key);
		if (classC == null)
			return null;
		try {
			return classC.newInstance();
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}
}
