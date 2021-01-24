package com.rs.custom;

import com.rs.Server;
import com.rs.Settings;
import com.rs.cache.loaders.ClientScriptMap;
import com.rs.custom.data_structures.SpinsManager;
import com.rs.custom.interfaces.CustomInterfaces;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.others.FireSpirit;
import com.rs.game.player.Player;
import com.rs.custom.interfaces.PlayerDesign;
import com.rs.game.player.content.PlayerLook;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static com.rs.custom.interfaces.CustomInterfaces.debugCommandsListScreen;

public class CustomCommands {
    private static String[] allCustomCommands = new String[] {
            "interh", "interfacecid", "finishquests", "starter", "resetbank", "loadjson", "savejson", "getcontroller", "addspins", "interface", "firespirit",
            "xprate", "timeplayed", "datecreated", "appearance", "welcome", "coordinate", "item", "spawnnpc", "faceanim", "npc", "test",  "serialsave",
            "data", "commandlist", "coordinaterepeater", "transformid", "debug", "emptyinventory", "hideinterbetween", "facingtile", "spawnobject", "shop",
            "showicompbetween", "hideicompbetween", "tester1"
    };
    public static boolean isCustom(String command) {
        return Arrays.asList(allCustomCommands).contains(command);
    }
    public static void customCommand(Player player, String[] command) {
        switch(command[0]) {
            case "tester1":
                for(Item item : ShopsHandler.getShop(1).getPlayerStock())
                    if(item != null)
                        System.out.print(item.getName() + " ");
                break;
            case "shop":
                ShopsHandler.openShop(player, Integer.parseInt(command[1]));
                break;
            case "spawnobject":
                WorldObject worldObject = new WorldObject(Integer.parseInt(command[1]), 10, 0, player.getTileInFacingDirection());
                World.spawnObject(worldObject, true);
                break;
            case "facingtile":
                WorldTile tilePlayerIsLookingAt = player.getTileInFacingDirection();
                player.getPackets().sendGameMessage("Tile player is looking at; " + tilePlayerIsLookingAt);
                break;
            case "showicompbetween":
                if (command.length < 3) {
                    player.getPackets().sendPanelBoxMessage("Use: ;;showicompbetween [Interface ID] [Starting Component ID] [Ending Component ID]");
                    break;
                }
                int componentLen = Utils.getInterfaceDefinitionsComponentsSize(Integer.parseInt(command[1]));
                int interfaceID = Integer.parseInt(command[1]);
                int startingComponentID = Integer.parseInt(command[2]);
                int endingComponentID = Integer.parseInt(command[3]);

                System.out.println("length: " + componentLen);
                for(int nextComponentId = startingComponentID; nextComponentId <= endingComponentID; nextComponentId++)
                    player.getPackets().sendHideIComponent(interfaceID, nextComponentId, false);

                break;
            case "hideicomp":
                if (command.length < 3) {
                    player.getPackets().sendGameMessage("Use: ;;hideicomp [Interface ID] [Component ID]");
                    break;
                }
                player.getPackets().sendHideIComponent(Integer.parseInt(command[1]), Integer.parseInt(command[2]), true);
                System.out.println("length: " + Utils.getInterfaceDefinitionsComponentsSize(Integer.parseInt(command[1])));
                break;
            case "interh":
                if (command.length < 2) {
                    player.getPackets().sendGameMessage("Use: ;;hideicomp [Interface ID] [Component ID]");
                    break;
                }

                try {
                    int interId = Integer.valueOf(command[1]);
                    for (int componentId = 0; componentId < Utils.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
                        player.getPackets().sendIComponentModel(interId, componentId, 66);
                    }
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ;;inter interfaceId");
                }
                break;

            case "interfacecid":
                if (command.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ;;interfacecid [interfaceId]");
                    break;
                }

                try {
                    int interId = Integer.valueOf(command[1]);
                    for (int componentId = 0; componentId < Utils.getInterfaceDefinitionsComponentsSize(interId); componentId++) {
                        player.getPackets().sendIComponentText(interId,	componentId, "cid: " + componentId);
                    }
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ;;interfacecid [interfaceId]");
                }
                break;

            case "finishquests":
                player.getQuestManager().checkCompleted();
                break;
            case "starter":
                player.giveStartingItems();
                break;
            case "resetbank":
                player.getBank().reset();
                break;
            case "loadjson":
                player.getSaveJSONManager().loadJSON();
                break;
            case "savejson":
                player.getSaveJSONManager().saveJSON();
                break;
            case "getcontroller":
                try {
                    String controler_name = player.getControlerManager().getController().getClass().getName();
                    player.getPackets().sendGameMessage(controler_name);
                } catch(NullPointerException e) {
                    player.getPackets().sendGameMessage("none");
                }
                break;
            case "addspins":
                if(command.length != 2 || !command[1].matches("\\d+")) {
                    player.getPackets().sendGameMessage("The format is \";;addspins [amt]\"");
                    break;
                }
                SpinsManager.addSpins(player, Integer.parseInt(command[1]));
                break;
            case "interface":
                if(command.length != 2 || !command[1].matches("\\d+")) {
                    player.getPackets().sendGameMessage("The format is \";;interface [id]\"");
                    break;
                }
                player.getInterfaceManager().sendInterface(Integer.parseInt(command[1]));
                break;
            case "firespirit":
                new FireSpirit(player.getLocation(), player);
                player.getPackets().sendGameMessage("<col=ff0000>A fire spirit emerges from the bonfire.");
                break;
            case "xprate":
                player.getPackets().sendGameMessage("XP Rate: " + Settings.XP_RATE + "x");
                break;
            case "timeplayed":
                player.getPackets().sendGameMessage("Time Played: " + player.getTotalMinutesPlayed() + " Minutes.");
                break;
            case "datecreated":
                Date creationDate = new Date(player.getCreationDate());
                String date = (new SimpleDateFormat("MMM dd,yyyy HH:mm")).format(creationDate);
                player.getPackets().sendGameMessage("Your account was created on " + date);
                break;
            case "appearance":
                PlayerDesign.open(player);
                break;
            case "welcome":
                CustomInterfaces.welcomeScreen(player);
                break;
            case "coordinate":
                player.getPackets().sendGameMessage("X: " + Integer.toString(player.getLocation().getX()) + " Y: " + Integer.toString(player.getLocation().getY())
                        + " Plane: " + Integer.toString(player.getLocation().getPlane()));
                break;
            case "item":
                if(command.length != 3 || !command[1].matches("\\d+") || !command[2].matches("\\d+")) {
                    player.getPackets().sendGameMessage("The format is \";;item [id] [amt]\"");
                    break;
                }
                player.getInventory().addItem(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                break;
            case "spawnnpc":
                try {
                    World.spawnNPC(Integer.parseInt(command[1]), player, -1, true,true);
                    break;
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::npc id(Integer)");
                }
                break;
            case "faceanim":
                player.getDialogueManager().testFaceAnimations("DukeHoracio", Integer.parseInt(command[1]));
                break;
            case "npc":
                int id = Integer.parseInt(command[1]);
                WorldTile loc = player.getLocation();
                World.spawnNPC(id, new WorldTile(loc.getX()+1, loc.getY(), loc.getPlane()), -1, true, true);

                break;
            case "test":
                PlayerLook.openCharacterCustomizing(player);
                break;
            case "serialsave":
                Server.saveFiles();
                player.getPackets().sendGameMessage("Saved as serial");
                break;
            case "data":
                if(command.length == 1) {
                    try (PrintWriter out = new PrintWriter("filename.txt")) {
                        for (int scriptId = 0; scriptId < 5920; scriptId++) {
                            Map<Long, Object> map = ClientScriptMap.getMap(scriptId).getValues();
                            try {
                                map.entrySet().forEach(entry -> {
                                    out.print(entry.getKey() + " " + entry.getValue() + " ");
                                });
                                out.println("");
                            } catch (Exception e) {
                                continue;
                            }
                            System.out.println("\n^Script " + scriptId);
                        }
                    } catch (Exception e) {
                        ;
                    }
                }
                if(command.length == 2) {
                    int scriptId = Integer.parseInt(command[1]);
                    Map<Long, Object> map = ClientScriptMap.getMap(scriptId).getValues();
                    try {
                        map.entrySet().forEach(entry -> {
                            System.out.print(entry.getKey() + " " + entry.getValue() + " ");
                        });
                    } catch(Exception e) {
                        System.out.println("Script error");
                    }
                    System.out.println("\n^Script " + scriptId);
                }
                break;
            case "commandlist":
                debugCommandsListScreen(player);
                break;
            case "coordinaterepeater":
                player.getPackets().sendGameMessage("--You must logout to kill the repeater--");
                long timeInterval = 2000;//2 seconds
                Runnable repeater = new Runnable() {
                    public void run() {
                        while (true) {
                            player.getPackets().sendGameMessage("X: " + Integer.toString(player.getLocation().getX()) + " Y: "
                                    + Integer.toString(player.getLocation().getY()) + " Plane: " + Integer.toString(player.getLocation().getPlane()));
                            try {
                                Thread.sleep(timeInterval);
                            } catch(InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                Thread thread = new Thread(repeater);
                thread.start();
                break;
            case "transformid":
                player.getPackets().sendGameMessage("ID " + player.getAppearence().getTransformedNPCId());
                break;
            case "debug":
                player.setDebugMode(!player.getDebugMode());
                player.getPackets().sendGameMessage("Debug mode has been set to " + player.getDebugMode());
                break;
            case "emptyinventory":
                player.getInventory().reset();
                break;
            case "hideicompbetween":
                if (command.length < 3) {
                    player.getPackets().sendPanelBoxMessage("Use: ;;hideicompbetween [Interface ID] [Starting Component ID] [Ending Component ID]");
                    break;
                }
                int componentSize = Utils.getInterfaceDefinitionsComponentsSize(Integer.parseInt(command[1]));
                int interfaceId = Integer.parseInt(command[1]);
                int startingComponentId = Integer.parseInt(command[2]);
                int endingComponentId = Integer.parseInt(command[3]);

                System.out.println("length: " + componentSize);
                for(int nextComponentId = startingComponentId; nextComponentId <= endingComponentId; nextComponentId++)
                    player.getPackets().sendHideIComponent(interfaceId, nextComponentId, true);

                break;
            default:
                return;
        }
    }
}
