package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.entity.player.PlayerEntity;

public class NoRepelled extends FeatureModule {
    public NoRepelled() {
        super("NoRepelled",Category.MISC,"反击退", null , "fallSpeed", 0.1);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()){
            PlayerEntity player = mc.player;
            if (player != null && !player.isOnGround() && !mc.options.keyJump.isPressed()){
                player.setVelocity(0,-getConfig(),0);
            }
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onRender() {}
}
