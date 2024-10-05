package net.GingkoTreeX.totem.command;

import net.GingkoTreeX.totem.command.commands.Bind;
import net.GingkoTreeX.totem.command.commands.Config;
import net.GingkoTreeX.totem.command.commands.Friend;
import net.GingkoTreeX.totem.command.commands.Help;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraftforge.client.event.ClientChatEvent;

public final class CommandManager {
    public static void onChat(String message, ClientChatEvent event) {
        if (message.startsWith(".")) {
            Help.help(message);
            boolean bind = false;
            boolean loadConfig = Config.load(message);
            boolean saveConfig = Config.save(message);
            boolean addFriend = Friend.add(message);
            boolean removeFriend = Friend.remove(message);
            if (!loadConfig && !saveConfig && !addFriend && removeFriend) {
                bind = Bind.bind(message);
            }
            if (!bind && !loadConfig && !saveConfig && !addFriend && !removeFriend){
                MessageUtils.addPinkChatMessage("未知指令 请输入.help查看指令列表");
            }
            event.setCanceled(true);
        }
    }
}
