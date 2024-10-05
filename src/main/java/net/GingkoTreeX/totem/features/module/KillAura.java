package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.GingkoTreeX.totem.utils.RenderUtil;
import net.GingkoTreeX.totem.features.controller.TargetSelector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.Random;


public class KillAura extends FeatureModule {
    final static int MAX = 180;
    final static int MIN = 90;
    private long lastActionTime;
    private long lastMessageTime;
    private Entity lastTarget;

    private double lastView = 0.2;
    private long randomMs = new Random().nextInt(MAX - MIN + 1) + MIN;
    private final ParticleEffect particleEffect = ParticleTypes.BUBBLE;
    public KillAura() {
        super("KillAura", Category.ATTACK, "Automatically attacks nearby enemies.",GLFW.GLFW_KEY_R,"reach",3.0);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onUpdate() {
        double reach = this.getConfig();
        if (this.isEnabled()) {
            if (mc.player!=null){
                RenderUtil.generateHalfCircleParticles(mc.player,particleEffect,reach);
            }
            if ((System.currentTimeMillis() - lastActionTime) < randomMs) {
                return;
            }
            ClientPlayerEntity player= MinecraftClient.getInstance().player;
            if (player != null) {
                Entity target;
                if (lastTarget == null || !lastTarget.isAlive() || lastTarget.distanceTo(mc.player) > reach) {
                    target = TargetSelector.getNearbyTarget(MinecraftClient.getInstance().player, reach + 1);
                } else {
                    target = lastTarget;
                }
                if (lastView < 1){
                    lastView+=0.2;
                }
                if (target != null && target.isAlive()) {
                    forcedBypass(target,lastView,player.renderYaw);
                }else if (target != lastTarget||lastView == 1){lastView = 0.2;}
                target = TargetSelector.getNearbyTarget(MinecraftClient.getInstance().player, reach);
                if (MinecraftClient.getInstance().interactionManager != null && target != null) {
                    if (target instanceof PlayerEntity) {
                        if (System.currentTimeMillis() - lastMessageTime > 3000L)
                            if (!canBeat((PlayerEntity) target, player) && player.getHealth() < 10) {
                                MessageUtils.addPinkChatMessage("主播你可能对不过他啊 , 关掉杀戮跑路!!");
                                lastMessageTime = System.currentTimeMillis();
                            }
                    }
                    if (lastView == 1) {
                        MinecraftClient.getInstance().interactionManager.attackEntity(player, target); //可能会叠vl
                        player.swingHand(Hand.MAIN_HAND); // 模拟玩家挥动手臂
                        fakeCriticals(target);
                    }
                    lastTarget = target;
                    lastActionTime = System.currentTimeMillis();
                    randomMs = new Random().nextInt(MAX - MIN + 1) + MIN;
                }
            }
        }
    }
    @Override
    public void onRender() {
    }



    @Override
    public void onDisable() {
    }

    private void fakeCriticals(Entity target) {
        mc.worldRenderer.addParticle(ParticleTypes.CRIT, false,true, target.getX(), target.getY(), target.getZ(), 0, 0, 0);
    }

    private void forcedBypass(Entity target ,double view,float renderYaw){

        double targetX = target.getX();
        double targetY = target.getY()+1.6;
        double targetZ = target.getZ();

        double deltaX = targetX - mc.player.getX();
        double deltaY = targetY - (mc.player.getY() + mc.player.getEyeHeight(target.getPose()));
        double deltaZ = targetZ - mc.player.getZ();

        float distance = MathHelper.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ)); // 水平距离
        float yaw = (float) (Math.atan2(deltaZ, deltaX) * (180 / Math.PI));
        float pitch = (float) (-Math.atan2(deltaY, distance) * (180 / Math.PI));

        yaw = MathHelper.wrapDegrees(yaw-90L)/4;
        pitch = MathHelper.wrapDegrees(pitch);

        mc.player.setYaw((float) (view * 3 * yaw + yaw));
        mc.player.setPitch(pitch);

        mc.player.renderYaw = renderYaw;
    }

    private int getArmorLevel(ItemStack itemStack) {
        if (itemStack.isEmpty()) return 0;
        int level = 0;
        if (itemStack.isOf(Items.IRON_BOOTS))    level += 1; // 铁靴
        else if (itemStack.isOf(Items.IRON_HELMET)) level += 1; // 铁头盔
        else if (itemStack.isOf(Items.IRON_CHESTPLATE)) level += 1; // 铁胸甲
        else if (itemStack.isOf(Items.IRON_LEGGINGS)) level += 1; // 铁腿甲
        else if (itemStack.isOf(Items.DIAMOND_BOOTS))   level += 2; // 钻石靴
        else if (itemStack.isOf(Items.DIAMOND_HELMET)) level += 2; // 钻石头盔
        else if (itemStack.isOf(Items.DIAMOND_CHESTPLATE)) level += 2; // 钻石胸甲
        else if (itemStack.isOf(Items.DIAMOND_LEGGINGS)) level += 2; // 钻石腿甲
        int enchantmentLevel = EnchantmentHelper.getLevel(Enchantments.PROTECTION, itemStack);
        // 根据附魔等级增加额外的护甲级别
        level += enchantmentLevel;
        return level;
    }

    private int getSwordLevel(ItemStack itemStack) {
        if (itemStack.isEmpty()) return 0;
        int level = 0;
        if (itemStack.isOf(Items.IRON_SWORD))  level += 12; // 铁剑
        else if (itemStack.isOf(Items.IRON_SWORD)) level += 14; // 钻石剑
        int enchantmentLevel = EnchantmentHelper.getLevel(Enchantments.SHARPNESS, itemStack);
        // 根据附魔等级增加额外的护甲级别
        level += enchantmentLevel;
        return level;
    }

    private boolean canBeat(PlayerEntity target , PlayerEntity player){
        double targetHit = getSwordLevel(target.getInventory().getMainHandStack());
        double playerHit = getSwordLevel(target.getInventory().getMainHandStack()) + 2.0;
        int defense = 0 ;
        int targetDefense = 0 ;
        Iterable<ItemStack> targetArmorItems = target.getArmorItems();
        Iterable<ItemStack> playerArmorItems = player.getArmorItems();
        for (ItemStack itemStack :playerArmorItems) {
            defense += getArmorLevel(itemStack);
        }
        for (ItemStack itemStack :targetArmorItems) {
            targetDefense += getArmorLevel(itemStack);
        }
        double p = player.getHealth()/Math.abs(targetHit-defense);
        double t = target.getHealth()/Math.abs(playerHit-targetDefense);

        return p > t && (playerHit-targetDefense) > 0;
    }

}
