package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.WorldUtils;
import net.minecraft.client.MinecraftClient;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import java.util.Random;


public class KillAura extends FeatureModule {
    final static int MAX = 180;
    final static int MIN = 90;
    private long lastActionTime;
    private long randomMs = new Random().nextInt(MAX - MIN + 1) + MIN;
    public KillAura() {
        super("KillAura", Category.ATTACK, "Automatically attacks nearby enemies.",GLFW.GLFW_KEY_R,"reach",3.0);
    }

    @Override
    public void onEnable() {

    }

    @SuppressWarnings({ "resource", "null" })
    @Override
    public void onUpdate() {
        double reach = this.getConfig();
        if (this.isEnabled()) {
            if ((System.currentTimeMillis() - lastActionTime) < randomMs) {
                return;
            }
            PlayerEntity player= MinecraftClient.getInstance().player;
            if (player != null) {
                Entity target = WorldUtils.getPlayerNearestEntityFromReach(MinecraftClient.getInstance().player, reach);
                if (MinecraftClient.getInstance().interactionManager != null && target != null) {
                        MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                        player.swingHand(Hand.MAIN_HAND); // 模拟玩家挥动手臂
                        lastActionTime = System.currentTimeMillis();
                        randomMs = new Random().nextInt(MAX - MIN + 1) + MIN;
                }
            }
        }
    }
    @Override
    public void onRender() {
    }



    @Override
    public void onDisable() {
    }

}
