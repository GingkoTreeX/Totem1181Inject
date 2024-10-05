package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.ModuleFramework;
import net.GingkoTreeX.totem.features.controller.TargetSelector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

public class Criticals extends FeatureModule {

    long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();

    private final static int SPACE =GLFW.GLFW_KEY_SPACE;
    public Criticals(){
        super("Criticals", Category.ATTACK, "Causes you to hit a critical hit with every sword", null,"jumpLong(1.5 is best)",1.5);
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
            if (mc.player!=null){
                Entity target = TargetSelector.getNearbyTarget(MinecraftClient.getInstance().player, ModuleFramework.getInstance().getModule(KillAura.class).getConfig() + getConfig()+0.5);
                if (target != null) {
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    if (player != null && player.isOnGround()) {
                        mc.options.keyJump.setPressed(true);
                    }
                }else if ( !(GLFW.glfwGetKey(windowHandle,mc.options.keyJump.getKey().getCode() ) == GLFW.GLFW_PRESS))mc.options.keyJump.setPressed(false);
            }
        }
    }

    @Override
    public void onRender() {
    }
}
