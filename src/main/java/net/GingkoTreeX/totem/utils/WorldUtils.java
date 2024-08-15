package net.GingkoTreeX.totem.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

import static net.GingkoTreeX.totem.utils.IMinecraft.mc;


public class WorldUtils {
    // 玩家 搜索半径和搜索范围
    public static Entity getNearestEntityFromReach(PlayerEntity player, double reach) {
        List<Entity> playerList = null ;
        if (mc.world != null) {
            Box searchArea = new Box(
                    player.getX() - reach,
                    player.getY() - reach,
                    player.getZ() - reach,
                    player.getX() + reach,
                    player.getY() + reach,
                    player.getZ() + reach
            );
            playerList = mc.world.getOtherEntities(player,searchArea);
        }
        if (playerList != null) {
            for (Entity target : playerList) {
                // 如果目标实体是自己，则跳过
                if (target == player) {
                    continue;
                }
                if (!target.isAttackable()) {
                    continue;
                }
                // 计算目标实体与自己的距离
                double distance = player.distanceTo(target);
                // 如果目标实体在搜索半径内，则返回
                if (distance <= reach) {
                    return target;
                }
            }
        }

        return null;
    }
    public static Entity getPlayerNearestEntityFromReach(PlayerEntity player, double reach) {
        List<Entity> playerList = null;
        if (mc.world != null) {
            Box searchArea = new Box(
                    player.getX() - reach,
                    player.getY() - reach,
                    player.getZ() - reach,
                    player.getX() + reach,
                    player.getY() + reach,
                    player.getZ() + reach
            );
            playerList = mc.world.getOtherEntities(player, searchArea);
        }
        if (playerList != null) {
            for (Entity target : playerList) {
                // 如果目标实体是自己，则跳过
                if (target == player) {
                    continue;
                }
                if (!(target.isAttackable() && target.isAlive() && target.getType().equals(EntityType.PLAYER))) {
                    continue;
                }
                // 计算目标实体与自己的距离
                double distance = player.distanceTo(target);
                // 如果目标实体在搜索半径内，则返回
                if (distance <= reach) {
                    return target;
                }
            }
        }

        return null;
    }

    public static List<Entity> getEntityFromWorld(PlayerEntity player) {
        List<Entity> playerList = null ;
        if (mc.world != null) {
            Box searchArea = new Box(
                    player.getX() - 300,
                    player.getY() - 300,
                    player.getZ() - 300,
                    player.getX() + 300,
                    player.getY() + 300,
                    player.getZ() + 300
            );
            playerList = mc.world.getOtherEntities(player, searchArea);
        }
        return playerList;
    }

        public boolean getBlockIsAir(BlockPos pos) {
        if (mc.world == null) {
            return false;
        }
        return mc.world.getBlockState(pos).isAir();
    }
}
