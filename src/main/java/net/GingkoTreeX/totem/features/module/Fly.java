package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;


public class Fly extends FeatureModule {
    public Fly() {
        super("Fly", Category.MOVEMENT , "我操高雅啊", null,"Speed m/tick",0.3);
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {



    }
 /*
     X轴：正值向右，负值向左
     Y轴：正值向上，负值向下
     Z轴：正值向前，负值向后
    */

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            PlayerEntity player = mc.player;
            KeyBinding forwardKey = mc.options.keyForward;
            KeyBinding backwardKey = mc.options.keyBack;
            KeyBinding leftKey = mc.options.keyLeft;
            KeyBinding rightKey = mc.options.keyRight;
            KeyBinding jumpKey = mc.options.keyJump;
            KeyBinding sneakKey = mc.options.keySneak;
            double config = getConfig();
            if (player != null) {
                double x = player.getPos().x;
                double y = player.getPos().y;
                double z = player.getPos().z;

                if (forwardKey.isPressed()) {
                    z += config;
                } else if (backwardKey.isPressed()) {
                    z -= config;
                }

                if (leftKey.isPressed()) {
                    x += config;
                } else if (rightKey.isPressed()) {
                    x -= config;
                }

                if (jumpKey.isPressed()) {
                    y += config;
                } else if (sneakKey.isPressed()) {
                    y -= config;
                }
                player.updatePosition(x, y, z);
                player.setPos(x,y,z);
            }
        }
    }

    @Override
    public void onRender() {

    }
}