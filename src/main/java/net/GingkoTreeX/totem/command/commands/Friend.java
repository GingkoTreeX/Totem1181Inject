package net.GingkoTreeX.totem.command.commands;

import net.GingkoTreeX.totem.features.controller.TargetSelector;
import net.GingkoTreeX.totem.utils.MessageUtils;


public class Friend {
    public static boolean add(String message){
        String[] parts = message.substring(1).split(" ");
            if ("addfriend".equalsIgnoreCase(parts[0])) {
                if (parts.length == 2) {
                    TargetSelector.addFriend(parts[1]);
                    MessageUtils.addPinkChatMessage("添加成功");
                }else {
                    MessageUtils.addPinkChatMessage("请使用/addfriend <name>");
                }
                return true;
            }
        return false;
    }
    public static boolean remove(String message){
        String[] parts = message.substring(1).split(" ");
        if ("removefriend".equalsIgnoreCase(parts[0])) {
            if (parts.length == 2) {
                TargetSelector.removeFriend(parts[1]);
                MessageUtils.addPinkChatMessage("移除成功");
            }else {
                MessageUtils.addPinkChatMessage("请使用/removefriend <name>");
            }
            return true;
        }
        return false;
    }
}
