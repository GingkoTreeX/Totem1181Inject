package net.GingkoTreeX.totem.features.module;

import com.mojang.blaze3d.systems.RenderSystem;
import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.RenderUtil;
import net.GingkoTreeX.totem.utils.WorldUtils;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "totem", bus = Mod.EventBusSubscriber.Bus.MOD) 
public class Esp extends FeatureModule {
    private static boolean open;

    public Esp() {
        super("Esp", Category.RENDER, "",null, null, 0);
    }



    @Override
    public void onEnable() {
      open = true;
    }

    @Override
    public void onDisable() {
      open = false;
    }

	@SubscribeEvent
    public void onRenderLevelLast(RenderLevelLastEvent event) {
        if (open) {
        if (mc.player != null) {
            // 创建一个新的列表来存储PlayerEntity实例
            List<Entity> entities = WorldUtils.getEntityFromWorld(mc.player);
            List<PlayerEntity> playerEntities = new ArrayList<>();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            for (Entity entity : entities) {
                // 检查当前实体是否是PlayerEntity类型
                if (entity instanceof PlayerEntity) {
                    // 强制转换并添加到playerEntities列表中
                    playerEntities.add((PlayerEntity) entity);
                }
            }
            for (PlayerEntity playerentity: playerEntities){
                VertexConsumer vertex = mc.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.LINE_STRIP);
                RenderUtil.renderEsp(event.getPoseStack(),vertex,playerentity);
            }
    
            }
        }
    }

    @Override
    public void onRender() {

    }



    @Override
    public void onUpdate() {
    }


}
