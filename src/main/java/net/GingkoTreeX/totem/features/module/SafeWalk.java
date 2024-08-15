package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import net.GingkoTreeX.totem.features.Category;


public class SafeWalk extends FeatureModule {

    public SafeWalk() {
        super("SafeWalk", Category.MOVEMENT, "", null, null, 0);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            if (mc.player != null) {
            if (isPlayerNearEdge(mc.player) && !mc.options.keyJump.isPressed()) {
                mc.options.keySneak.setPressed(true);
            }else mc.options.keySneak.setPressed(false);
        }
    }
}

    @Override
    public void onEnable() {
     
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onRender() {

    }
    private boolean isPlayerNearEdge(PlayerEntity player) {
        // 获取玩家当前位置的方块
        double playerX =  player.getX();
        double playerY =  player.getY() - 1;
        double playerZ =  player.getZ();
        BlockPos pos = new BlockPos(playerX, playerY, playerZ);
        // 检查玩家下方是否有方块
       // if (getBlockIsAir(pos)) {
         //   return true;
        //}

        // 检查玩家周围是否有方块
        for (int x = 1; x <= 2; x++) {
            for (int z = 1; z <= 2; z++) {
                if (getBlockIsAir(new BlockPos( (double)playerX+z/10,  (double)playerY,  (double)playerZ+z/10))||getBlockIsAir(new BlockPos(playerX-z/10, playerY, playerZ-z/10))) {
                    return true;
                }
            }
        }

        return false;
    }
    private boolean getBlockIsAir(BlockPos pos) {
        if (mc.world == null) {
            return false;
        }
        return mc.world.getBlockState(pos).isAir();
    }
}
