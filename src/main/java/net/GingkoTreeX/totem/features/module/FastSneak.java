package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class FastSneak extends FeatureModule {

    private static final int MINIMUM_DELAY_MS = 150;
    private long lastActionTime;

    public FastSneak() {
        super("FastSneak", Category.MOVEMENT, "when you sneak your speed will as Sprinting", null, null, 0);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (System.currentTimeMillis() - lastActionTime > MINIMUM_DELAY_MS) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null && player.isSneaking()) {
                    double x = player.getVelocity().x * 3;
                    double y = player.getVelocity().y;
                    double z = player.getVelocity().z * 3;
                    player.setVelocity(x, y, z);
                    lastActionTime = System.currentTimeMillis();
                }
            }
        }
    }

    @Override
    public void onEnable() {
        lastActionTime = System.currentTimeMillis();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onRender() {

    }
}
