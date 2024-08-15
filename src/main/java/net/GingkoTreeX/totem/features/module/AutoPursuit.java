package net.GingkoTreeX.totem.features.module;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.utils.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class AutoPursuit extends FeatureModule {
   public AutoPursuit(){
       super("AutoPursuit", Category.ATTACK, "Auto fly to your target(Only HvH)",null,null,0);
   }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()){
            Entity target= null;
            if (MinecraftClient.getInstance().player != null) {
                target = WorldUtils.getNearestEntityFromReach(MinecraftClient.getInstance().player, 60);
            }
            if (target != null && target.isLiving() && target.isAlive()) {
            pursuit(MinecraftClient.getInstance().player, (PlayerEntity) target);
            }
        }
    }

    @Override
    public void onRender() {

    }
   private void pursuit(PlayerEntity player,PlayerEntity target) {
       if (target != null) {
           double distance = player.distanceTo(target);
           if (player.getHealth() > 6) {
               if (distance <= 60) {
                   // 制裁飞行
                   if (distance > 30) {
                       player.setVelocity(
                               (target.getX() - player.getX()) / 6,
                               (target.getY() - player.getY()) / 6,
                               (target.getZ() - player.getZ()) / 6
                       );
                   } else if (distance > 5) { // distance <= 30
                       player.setVelocity(
                               (target.getX() - player.getX()) / 3,
                               (target.getY() - player.getY()) / 6,
                               (target.getZ() - player.getZ()) / 3
                       );
                   } else {
                       player.setVelocity(
                               (target.getX() - player.getX()) * 2,
                               (target.getY() - player.getY()) * 2,
                               (target.getZ() - player.getZ()) * 2);
                   }
               }

           } else {
               player.setVelocity(3, 3, 3);
           }
       }
   }
}
