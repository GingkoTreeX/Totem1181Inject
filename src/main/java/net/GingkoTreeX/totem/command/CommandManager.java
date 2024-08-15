package net.GingkoTreeX.totem.command;

import net.GingkoTreeX.totem.command.commands.Bind;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraftforge.client.event.ClientChatEvent;

public final class CommandManager {
    public static void onChat(String message, ClientChatEvent event) {
        if (message.startsWith(".")) {
            boolean bind = Bind.bind(message);
            if (!bind){
                MessageUtils.addPinkChatMessage("Command not found");
            }
            event.setCanceled(true);
        }
    }
}
