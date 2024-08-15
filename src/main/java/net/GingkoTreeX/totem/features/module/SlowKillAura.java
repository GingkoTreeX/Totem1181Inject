package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class SlowKillAura extends FeatureModule {
    private static final int MINIMUM_DELAY_MS = 700;
    private long lastActionTime;
    public SlowKillAura() {
        super("SlowKillAura", Category.ATTACK, "Automatically attacks nearby enemies.", null,"reach",3.4);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {
        double reach = this.getConfig();
        if (this.isEnabled()) {
            if ((System.currentTimeMillis() - lastActionTime) < MINIMUM_DELAY_MS) {
                return;
            }
            PlayerEntity player= MinecraftClient.getInstance().player;
            if (player != null) {
                Entity target = WorldUtils.getNearestEntityFromReach(MinecraftClient.getInstance().player, reach);
                if (MinecraftClient.getInstance().interactionManager != null && target != null) {
                    if (target.isAttackable()) {
                        MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                        player.swingHand(Hand.MAIN_HAND); // 模拟玩家挥动手臂
                        lastActionTime = System.currentTimeMillis();
                    }
                }
                player.setHeadYaw(player.headYaw + 30L);//扭头，看起来很吊
                MinecraftClient.getInstance().player.setSprinting(true);
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
