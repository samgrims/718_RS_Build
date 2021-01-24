package com.rs.custom.interfaces;

import com.rs.game.player.Player;
import com.rs.game.player.content.Shop;
import com.rs.net.decoders.WorldPacketsDecoder;

public class CustomButtonHandlers {
    public static void handleCustom1265Shops(Player player, int interfaceId, int componentId, int slotId, int packetId) {
        if (interfaceId == 1265) {
            Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
            if (shop == null)
                return;
            Integer slot = (Integer) player.getTemporaryAttributtes().get("ShopSelectedSlot");
            boolean isBuying = player.getTemporaryAttributtes().get("shop_buying") != null;
            if (componentId == 20) {
                player.getTemporaryAttributtes().put("ShopSelectedSlot", slotId);
                if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
                    shop.sendInfo(player, slotId, isBuying);
                else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
                    shop.handleShop(player, slotId, 1);
                else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
                    shop.handleShop(player, slotId, 5);
                else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
                    shop.handleShop(player, slotId, 10);
                else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
                    shop.handleShop(player, slotId, 50);
                else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
                    shop.handleShop(player, slotId, 500);
                else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
                    shop.sendExamine(player, slotId);
            } else if (componentId == 201) {
                if (slot == null)
                    return;
                if (isBuying)
                    shop.buy(player, slot, shop.getAmount());
                else {
                    shop.sell(player, slot, shop.getAmount());
                    player.getPackets().sendConfig(2563, 0);
                    player.getPackets().sendConfig(2565, 1); // this is to update the tab.
                }
            } else if (componentId == 208) {
                shop.setAmount(player, shop.getAmount() + 5);
            } else if (componentId == 15) {
                shop.setAmount(player, shop.getAmount() + 1);
            } else if (componentId == 214) {
                if (shop.getAmount() > 1)
                    shop.setAmount(player, shop.getAmount() - 1);
            } else if (componentId == 217) {
                if (shop.getAmount() > 1)
                    shop.setAmount(player, shop.getAmount() - 5);
            } else if (componentId == 220) {
                shop.setAmount(player, 1);
            } else if (componentId == 211) {
                if (slot == null)
                    return;
                shop.setAmount(player, isBuying ? shop.getMainStock()[slot].getAmount() : player.getInventory().getItems().getItems()[slot].getAmount());
            } else if (componentId == 29) {
                player.getPackets().sendConfig(2561, 93);
                player.getTemporaryAttributtes().remove("shop_buying");
                shop.setAmount(player, 1);
            } else if (componentId == 28) {
                player.getTemporaryAttributtes().put("shop_buying", true);
                shop.setAmount(player, 1);
            }
        } else if (interfaceId == 1266) {
            if (componentId == 0) {
                if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
                    player.getInventory().sendExamine(slotId);
                else {
                    Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
                    if (shop == null)
                        return;
                    player.getPackets().sendConfig(2563, slotId);
                    if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
                        shop.sendValue(player, slotId);
                    else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
                        shop.sell(player, slotId, 1);
                    else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
                        shop.sell(player, slotId, 5);
                    else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
                        shop.sell(player, slotId, 10);
                    else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
                        shop.sell(player, slotId, 50);
                }
            }
        }
    }
}
