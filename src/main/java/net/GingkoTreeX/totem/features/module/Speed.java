package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.entity.player.PlayerEntity;

public class Speed extends FeatureModule {

    private static final int MINIMUM_DELAY_MS = 150;
    private long lastActionTime;
    public Speed() {
        super("Speed", Category.MOVEMENT,"加速", null,"Speed(倍率)", 1.3);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (System.currentTimeMillis() - lastActionTime > MINIMUM_DELAY_MS) {
                PlayerEntity player = mc.player;
                if (player != null && !player.isOnGround()) {
                    player.setVelocity(
                            player.getVelocity().getX() * getConfig(),
                            player.getVelocity().getY(),
                            player.getVelocity().getZ() * getConfig());
                    lastActionTime = System.currentTimeMillis();
                }
            }
        }
    }

    @Override
    public void onRender() {

    }
}
