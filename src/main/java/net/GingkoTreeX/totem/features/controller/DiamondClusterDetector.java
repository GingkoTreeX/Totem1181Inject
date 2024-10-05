package net.GingkoTreeX.totem.features.controller;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.*;

import static net.GingkoTreeX.totem.utils.IMinecraft.mc;

public class DiamondClusterDetector {

    private static final int CONNECTED_DISTANCE = 1; // 定义“相连”为xy平面上距离1以内，z轴同层
    private static final int DIAMOND_REQUIRED_COUNT = 3;

    // 记录已访问的BlockPos
    private static final Set<BlockPos> visited = new HashSet<>();
    private static final World world = mc.world;

    public static boolean hasAdjacentDiamondCluster(BlockPos startBlockPos) {
        visited.clear(); // 每次检测前清空访问记录
        return checkAndExplore(startBlockPos);
    }

    private static boolean checkAndExplore(BlockPos current) {
        if (visited.contains(current) || !isDiamond(current)) {
            return false; // 已访问过或者不是钻石则返回false
        }

        visited.add(current); // 标记为已访问

        // 如果已找到2个钻石（即下次找到就是第3个）
        if (visited.size() >= DIAMOND_REQUIRED_COUNT - 1) {
            return true;
        }

        // 探索四周的方块
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = current.offset(direction);
            if (direction.getAxis().isHorizontal() && withinConnectDistance(current, neighbor) && !visited.contains(neighbor)) {
                if (checkAndExplore(neighbor)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isDiamond(BlockPos pos) {
        BlockState state = null;
        if (world != null) {
            state = world.getBlockState(pos);
        }
        if (state != null) {
            return state.getBlock().equals(Blocks.DIAMOND_ORE) || state.getBlock().equals(Blocks.DEEPSLATE_DIAMOND_ORE);
        }
        return false;
    }

    private static boolean withinConnectDistance(BlockPos posA, BlockPos posB) {
        return Math.abs(posA.getX() - posB.getX()) <= CONNECTED_DISTANCE && Math.abs(posA.getZ() - posB.getZ()) <= CONNECTED_DISTANCE;
    }
}