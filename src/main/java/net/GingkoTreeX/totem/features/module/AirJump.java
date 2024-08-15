package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class AirJump extends FeatureModule {

    public AirJump() {
        super("AirJump", Category.MOVEMENT , "看名字就知道是干啥的了", null,null,0);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled() && mc.player != null ) {
            Item item = mc.player.getInventory().getArmorStack(2).getItem();
            Item item2 = mc.player.getInventory().getArmorStack(1).getItem();
            if (!(item2.equals(Items.ELYTRA)||item.equals(Items.ELYTRA))) {
                mc.player.setOnGround(true);
            }
        }
    }

    @Override
    public void onRender() {

    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}
