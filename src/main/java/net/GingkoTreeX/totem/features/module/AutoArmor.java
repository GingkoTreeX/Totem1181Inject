package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AutoArmor extends FeatureModule {
    final double MAX = 1000 * this.getConfig();
    final double MIN = 50;
    private final long MAX_DELAY_MS = (long) (getConfig() * 100L);
    private long lastActionTime;


    public AutoArmor() {
        super("AutoArmor", Category.MISC, "", null, "穿甲速度(s)", 0.1);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            MessageUtils.addPinkChatMessage("[Totem] 整理完成");
        }
    }

    @Override
    public void onUpdate() {
        if (mc.player != null && this.isEnabled()) {
            if (mc.player != null) {
                mc.setScreen(new InventoryScreen(mc.player));
            }
            PlayerEntity player = MinecraftClient.getInstance().player;
            lastActionTime = System.currentTimeMillis();
                for (int i = 0; i <4 ;i++){
                if (player != null) {
                    ItemStack armor = player.getInventory().getArmorStack(i);
                    for (int s = 0; s < 45; s++) {
                        ItemStack item = player.getInventory().getStack(s);
                        int level = getArmorLevel(item);
                        int armorLevel = getArmorLevel(armor);
                        if (!item.isEmpty()) {
                            if (level > armorLevel) {
                                //TODO:替换装备
                                MessageUtils.addPinkChatMessage("[DEBUG] 发现可替换装备"+item.getItem().getClass());
                                if (player.getInventory().main.size() > s) {
                                    if (!armor.isEmpty()) {
                                        player.getInventory().main.set(i,ItemStack.EMPTY);
                                        for (int slot = 10;slot<45;slot++){
                                            if (player.getInventory().main.get(slot).isEmpty()) {
                                                player.getInventory().main.set(slot, armor);
                                            }
                                        }
                                    }
                                        player.getInventory().main.set(s, ItemStack.EMPTY);
                                        player.getInventory().main.set(i, item);
                                    }
                            }
                        }
                    }
                }
            }
             this.setEnabled(false);
        }
    }

    @Override
    public void onRender() {
    }
    private int getArmorLevel(ItemStack itemStack) {
        int level = 0;
        if (itemStack.isOf(Items.IRON_BOOTS))    level += 1; // 铁靴
        else if (itemStack.isOf(Items.IRON_HELMET)) level += 1; // 铁头盔
        else if (itemStack.isOf(Items.IRON_CHESTPLATE)) level += 1; // 铁胸甲
        else if (itemStack.isOf(Items.IRON_LEGGINGS)) level += 1; // 铁腿甲
        else if (itemStack.isOf(Items.DIAMOND_BOOTS))   level += 4; // 钻石靴
        else if (itemStack.isOf(Items.DIAMOND_HELMET)) level += 4; // 钻石头盔
        else if (itemStack.isOf(Items.DIAMOND_CHESTPLATE)) level += 4; // 钻石胸甲
        else if (itemStack.isOf(Items.DIAMOND_LEGGINGS)) level += 4; // 钻石腿甲
        if (!itemStack.isEmpty() && level == 0) level = -1;
        int enchantmentLevel = EnchantmentHelper.getLevel(Enchantments.PROTECTION, itemStack);
        // 根据附魔等级增加额外的护甲级别
        level += enchantmentLevel;
        return level;
    }
}
