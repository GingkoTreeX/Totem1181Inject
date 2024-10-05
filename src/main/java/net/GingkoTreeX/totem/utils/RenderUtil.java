package net.GingkoTreeX.totem.utils;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static net.GingkoTreeX.totem.utils.IMinecraft.mc;

public class RenderUtil {
    public static List<Vec3d> boxOutlineVertexPositions(Box box) {
        List<Vec3d> vertices = new ArrayList<>();
        vertices.add(new Vec3d(box.minX, box.minY, box.minZ));
        vertices.add(new Vec3d(box.maxX, box.minY, box.minZ));
        vertices.add(new Vec3d(box.maxX, box.minY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.minY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.minY, box.minZ));
        vertices.add(new Vec3d(box.minX, box.maxY, box.minZ));
        vertices.add(new Vec3d(box.maxX, box.minY, box.minZ));
        vertices.add(new Vec3d(box.maxX, box.maxY, box.minZ));
        vertices.add(new Vec3d(box.maxX, box.minY, box.maxZ));
        vertices.add(new Vec3d(box.maxX, box.maxY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.minY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.maxY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.maxY, box.minZ));
        vertices.add(new Vec3d(box.maxX, box.maxY, box.minZ));
        vertices.add(new Vec3d(box.maxX, box.maxY, box.maxZ));
        vertices.add(new Vec3d(box.maxX, box.maxY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.maxY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.maxY, box.maxZ));
        vertices.add(new Vec3d(box.minX, box.minY, box.minZ));

        return vertices;
    }



    public static void renderEsp(MatrixStack matrices, VertexConsumer vertices, Entity entity) {
        if (mc.player != null) {
            Box aabb = new Box(entity.getX()+0.3,
                    entity.getY()-1.1,
                    entity.getZ()+0.3,
                    entity.getX()-0.3,
                    entity.getY()-2,
                    entity.getZ()-0.3);
            matrices.push();
            // 平移到正确的相对位置
            matrices.translate(-mc.player.getX(),-mc.player.getY()+1,-mc.player.getZ());

            WorldRenderer.drawBox(matrices, vertices, aabb, 1.0F, 0.0F, 0.0F, 1.0F); // 红色边框
            matrices.pop();
        }
    }
    public static void renderBlockEsp(MatrixStack matrices, VertexConsumer vertices, BlockPos blockPos) {
        if (mc.player != null) {
            Box aabb = new Box(blockPos.getX()+1,
                    blockPos.getY()+1,
                    blockPos.getZ()+1,
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ());
            matrices.push();
            // 平移到正确的相对位置
            matrices.translate(-mc.player.getX(),-mc.player.getY()-2,-mc.player.getZ());
            WorldRenderer.drawBox(matrices, vertices, aabb, 0.0F, 2.0F, 19.0F, 90.0F); // 红色边框
            matrices.pop();
        }
    }

    public static void generateHalfCircleParticles(PlayerEntity player, ParticleEffect particleEffect, double radius) {
        double centerX = player.getX();
        double centerY = player.getY()+0.5;
        double centerZ = player.getZ();

        for(double angle = 0; angle <= 360; angle += 3) { // 每隔5度放置一个粒子
            double radians = Math.toRadians(angle);
            double offsetX = Math.cos(radians) * radius;
            double offsetZ = Math.sin(radians) * radius;

            // 生成粒子
            double particleX = centerX + offsetX;
            double particleZ = centerZ + offsetZ;
            mc.worldRenderer.addParticle(particleEffect, false,true, particleX, centerY, particleZ, 0, 0, 0);
        }
    }

    public static void setColor(int color) {
        float a = (float) (color >> 24 & 0xFF) / 255.0f;
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }
    public void giveGlowingEffectToNearbyItems(ServerPlayerEntity player) {
        // 获取玩家当前位置
        BlockPos playerPosition = player.getBlockPos();

        int radius = 100;
        Box areaBox = new Box(playerPosition.getX() - radius, playerPosition.getY() - radius, playerPosition.getZ() - radius,
                playerPosition.getX() + radius, playerPosition.getY() + radius, playerPosition.getZ() + radius);

        // 获取玩家周围的掉落物列表
        World world = player.getWorld();
        List<ItemEntity> nearbyItems = world.getEntitiesByClass(ItemEntity.class, areaBox, itemEntity -> true);

        // 给每个掉落物添加发光效果
        for (ItemEntity itemEntity : nearbyItems) {

        }
    }

    public static void prepareBoxRender(float lineWidth, double red, double green, double blue, double alpha) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(lineWidth);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(false);
        GL11.glColor4d(red, green, blue, alpha);
    }


    public static void stopBoxRender() {
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
    }


    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 0xFF) / 255.0f;
        float red = (float) (hex >> 16 & 0xFF) / 255.0f;
        float green = (float) (hex >> 8 & 0xFF) / 255.0f;
        float blue = (float) (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glHint(3154, 4354);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private static float[] convertToListOfVec3d(List<Vec3d> vec3dList) {
        int totalVertices = vec3dList.size() * 3; // 因为每个Vec3d有x,y,z三个值
        float[] vertexData = new float[totalVertices];

        int index = 0;
        for(Vec3d vec : vec3dList) {
            vertexData[index++] = (float) vec.x;
            vertexData[index++] = (float) vec.y;
            vertexData[index++] = (float) vec.z;
        }

        return vertexData;
    }
}

