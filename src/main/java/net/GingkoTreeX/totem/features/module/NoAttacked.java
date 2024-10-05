package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraft.advancement.criterion.EntityHurtPlayerCriterion;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;



public class NoAttacked extends FeatureModule {
    boolean open = false;
    public NoAttacked() {
        super("NoAttack", Category.MISC,"",null, null, 0);
    }

    @SubscribeEvent
    public void onAttacked(AttackEntityEvent event){
        if (open) {
            MessageUtils.addPinkChatMessage("1");
            if (event.getTarget() == mc.player) {
                MessageUtils.addPinkChatMessage("2");
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public void onAttacked2(LivingHurtEvent event){
        if (open) {
            MessageUtils.addPinkChatMessage("3");
            if (event.getEntityLiving() == mc.player) {
                MessageUtils.addPinkChatMessage("4");
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void onEnable() {
        open = true;
    }

    @Override
    public void onDisable() {
        open = false;
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled())
            if (!open)
                open = true;

    }

    @Override
    public void onRender() {

    }
}
