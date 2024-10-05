package net.GingkoTreeX.totem.command.commands;

import net.GingkoTreeX.totem.features.controller.TargetSelector;
import net.GingkoTreeX.totem.utils.MessageUtils;

public class Help {
    public static boolean help(String message){
        String[] parts = message.substring(1).split(" ");
        if ("help".equalsIgnoreCase(parts[0])) {
            MessageUtils.addPinkChatMessage("Totem指令帮助列表");
            MessageUtils.addPinkChatMessage(".bind <module> <key> -绑定按键");
            MessageUtils.addPinkChatMessage(".saveConfig -保存配置");
            MessageUtils.addPinkChatMessage(".loadConfig -加载配置");
            MessageUtils.addPinkChatMessage(".addFriend -添加朋友(不会攻击朋友)");
            MessageUtils.addPinkChatMessage(".removeFriend -移除朋友");
            return true;
        }
        return false;
    }
}
