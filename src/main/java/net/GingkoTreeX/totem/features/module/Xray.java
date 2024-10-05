package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.controller.DiamondClusterDetector;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.GingkoTreeX.totem.utils.RenderUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "totem", bus = Mod.EventBusSubscriber.Bus.MOD)
public class Xray extends FeatureModule {

    private static final int MINIMUM_DELAY_MS = 2000;
    private long lastActionTime;
    private static boolean open;

    static List<BlockPos> blocks = new ArrayList<>();

    public Xray() {
        super("Xray", Category.RENDER, "",null, null, 0);
    }



    @Override
    public void onEnable() {
        blocks = new ArrayList<>();
        open = true;
        MessageUtils.addPinkChatMessage("这个渲染有点歪 如果想恢复正常需要按住C键放大");
    }

    @Override
    public void onDisable() {
        open = false;
    }

    @SubscribeEvent
    public void onRenderLevelLast(RenderLevelLastEvent event) {
        if (open) {
            if (mc.player != null) {
                PlayerEntity player = mc.player;
                double playerX = player.getX();
                double playerY = player.getY();
                double playerZ = player.getZ();
                if (System.currentTimeMillis()-lastActionTime > MINIMUM_DELAY_MS){
                // 遍历玩家周围的8×16个方块
                for (int x = -(8 * 16); x <= (8 * 16); x++) {
                    for (int z = -(8 * 16); z <= (8 * 16); z++) {
                        // 设置高度范围限制
                        int yMin = Math.max(-10, (int) playerY - 8 * 16);
                        int yMax = Math.min(10, (int) playerY + 8 * 16);
                        // 遍历高度范围
                        for (int y = yMin; y <= yMax; y++) {
                            BlockPos position = new BlockPos(x + playerX, y, z + playerZ);
                            BlockState blockState = player.world.getBlockState(position);
                            // 判断方块是否为钻石矿石
                            if (blockState.getBlock().getDefaultState().isOf(Blocks.DIAMOND_ORE) || blockState.getBlock().getDefaultState().isOf(Blocks.DEEPSLATE_DIAMOND_ORE)) {
                                if (!blocks.contains(position)) {
                                    if (!DiamondClusterDetector.hasAdjacentDiamondCluster(position)) {
                                        blocks.add(position);
                                    }
                                }
                            }else {blocks.remove(position);}
                        }
                    }
                    lastActionTime=System.currentTimeMillis();
                }
                }
                for (BlockPos block: blocks){
                    VertexConsumer vertex = mc.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.LINES);
                    RenderUtil.renderBlockEsp(event.getPoseStack(),vertex,block);
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
