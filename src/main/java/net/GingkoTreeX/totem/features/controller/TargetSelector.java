package net.GingkoTreeX.totem.features.controller;

import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;

import static net.GingkoTreeX.totem.utils.IMinecraft.mc;


public class TargetSelector {

    private static final List<String> friends =new ArrayList<>();
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
    public static Entity getNearbyTarget(PlayerEntity player, double reach) {
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
                //判断是否可以攻击
                if (!(target.isAttackable() && target.isAlive() && target.getType().equals(EntityType.PLAYER))) {
                    continue;
                }
                // 如果目标实体是自己或朋友，则跳过
                if (target == player || isFriend(target.getName().getString())) {
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
    public static void addFriend(String entity){
        friends.add(entity);
    }
    public static void removeFriend(String entity){
        friends.remove(entity);
    }
     private static boolean isFriend(String entity) {
         for (String friend : friends) {
             if (entity.equalsIgnoreCase(friend)) {
                 return true;
             }
         }
        return false;
    }

}
