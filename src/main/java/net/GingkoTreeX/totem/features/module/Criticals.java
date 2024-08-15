package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class Criticals extends FeatureModule {
    public Criticals(){
        super("Criticals", Category.ATTACK, "Causes you to hit a critical hit with every sword", null,"jumpHeight",0.22);
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
            PlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null && player.isOnGround() && player.handSwinging) {
                    player.setVelocity(0,getConfig(),0);
                }
        }
    }

    @Override
    public void onRender() {
    }
}
