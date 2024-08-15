package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class GhostHand extends FeatureModule {
    private static int MINIMUM_DELAY_MS = 1000;
    private long lastActionTime;
    public GhostHand() {
        super("GhostHand", Category.ATTACK, "", null, "High", 500);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

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
                    if (target.isLiving()) {
                        player.setVelocity(0, this.getConfig(), 0);
                        player.setVelocity(0, -this.getConfig(), 0);
                        MinecraftClient.getInstance().interactionManager.attackEntity(player, target);
                        player.swingHand(Hand.MAIN_HAND); // 模拟玩家挥动手臂
                        lastActionTime = System.currentTimeMillis();
                    /*  if (System.currentTimeMillis() - player.getLastAttackedTime() > 1000L && AuraUtil.getNearestEntityFromReach(player,reach) != null){
                        MINIMUM_DELAY_MS += 10;
                    }else {
                        MINIMUM_DELAY_MS -= 10;
                    }
                    */
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
}
