package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import java.util.ArrayList;
import java.util.List;


public class HitBox extends FeatureModule {
    public HitBox() {
        super("HitBox", Category.ATTACK, " your target hitBox",null ,"Box", 0.2);
    }

    @Override
    public void onEnable() {
        MinecraftClient.getInstance().getEntityRenderDispatcher().setRenderHitboxes(true);
    }

    @Override
    public void onDisable() {
        MinecraftClient.getInstance().getEntityRenderDispatcher().setRenderHitboxes(false);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null) {
            // 创建一个新的列表来存储PlayerEntity实例
            List<Entity> entities = WorldUtils.getEntityFromWorld(mc.player);
            List<PlayerEntity> playerEntities = new ArrayList<>();
            for (Entity entity : entities) {
                // 检查当前实体是否是PlayerEntity类型
                if (entity instanceof PlayerEntity) {
                    // 强制转换并添加到playerEntities列表中
                    playerEntities.add((PlayerEntity) entity);
                }
            }
            for (PlayerEntity playerentity : playerEntities) {
                Box boudingBox = new Box(
                        playerentity.getX() - getConfig(),
                        playerentity.getY(),
                        playerentity.getZ() - getConfig(),
                        playerentity.getX() + getConfig(),
                        playerentity.getY() + 1.8 +getConfig(),
                        playerentity.getZ() + getConfig()
                );
                playerentity.setBoundingBox(boudingBox);
            }
        }
    }

    @Override
    public void onRender() {

    }
}
