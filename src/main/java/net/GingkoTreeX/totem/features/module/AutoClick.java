package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class AutoClick extends FeatureModule {
    final static int MAX = 50;
    final static int MIN = 10;
    private final long MAX_DELAY_MS = (long) (getConfig() * 100L);
    private long lastActionTime;


    public AutoClick() {
        super("AutoClick", Category.ATTACK, "Fps= 1 / your config", null, "点击间隔(s)", 0.1);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled() && System.currentTimeMillis() - lastActionTime > MAX_DELAY_MS + new Random().nextInt(MAX - MIN + 1) + MIN) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (mc.currentScreen != null) {
                if (player != null && mc.mouse.wasLeftButtonClicked()) {

                    mc.currentScreen.mouseClicked(mc.mouse.getX(), mc.mouse.getY(), 1);

                }
                if (player != null && mc.mouse.wasRightButtonClicked() && !isHoldingFood(player)) {

                    mc.currentScreen.mouseClicked(mc.mouse.getX(), mc.mouse.getY(), 2);

                }
            }
            lastActionTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onRender() {
    }

    private boolean isHoldingFood(PlayerEntity player) {
        ItemStack mainHandItem = player.getMainHandStack();
        ItemStack offHandItem = player.getOffHandStack();

        return isFoodItem(mainHandItem) || isFoodItem(offHandItem);
    }

    private boolean isFoodItem(ItemStack itemStack) {
        return itemStack.isFood();
    }

}
