package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class FastUse extends FeatureModule {
    MinecraftClient mc = MinecraftClient.getInstance();

    public FastUse() {
        super("FastUse", Category.MISC, "0.5s to eat(Only HvH)", GLFW.GLFW_KEY_Z, null, 0);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (mc.player != null) {
// 获取当前玩家的位置信息
                Vec3d pos = mc.player.getPos();
// 创建一个数据包刷新状态
                PlayerMoveC2SPacket packet = new PlayerMoveC2SPacket.PositionAndOnGround(
                        pos.x, pos.y + 0f, pos.z, true);
                pos.add(pos.x, pos.y + 0f, pos.z);
// 发送数据包至服务器
                if (mc.player.networkHandler != null) {
                    mc.player.networkHandler.sendPacket(packet);
                }
            }

        }


    }

    @Override
    public void onRender() {
        // ...
    }

    @Override
    public void onDisable() {

    }
}