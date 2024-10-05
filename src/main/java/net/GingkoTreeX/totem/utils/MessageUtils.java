package net.GingkoTreeX.totem.utils;


import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class MessageUtils {
    public static void addGreenChatMessage(String message) {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText(message).styled(style ->
                    style.withColor(Formatting.GREEN)
                            .withBold(true)),false);
        }
    }

    public static void addRedChatMessage(String message) {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText(message).styled(style ->
                    style.withColor(Formatting.RED)
                            .withBold(true)),false);
        }
    }

    public static void addPinkChatMessage(String message) {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText(message).styled(style ->
                    style.withColor(Formatting.LIGHT_PURPLE)
                            .withBold(true)),false);
        }
    }


}
