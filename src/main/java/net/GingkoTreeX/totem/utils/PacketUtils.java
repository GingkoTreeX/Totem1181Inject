package net.GingkoTreeX.totem.utils;


import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PacketUtils{
    public static void sendPacket(Packet<?> packet) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
        }
    }

    public static void sendUsePacket(){
        PlayerActionC2SPacket packet = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.UP);
        if (MinecraftClient.getInstance().getNetworkHandler()!=null) {
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
        }
    }
}

