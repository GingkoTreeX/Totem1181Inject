//使用mixin是一个不好的习惯 一定要改掉

/*package net.GingkoTreeX.totem.mixin;

import net.GingkoTreeX.totem.features.ModuleFramework;
import net.GingkoTreeX.totem.features.module.Esp;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class MixinEsp {
    @Shadow
    private static double renderDistanceMultiplier;
    @Shadow
    private Box boundingBox;
    @Shadow
    public final Box getBoundingBox() {
        return this.boundingBox;
    }
    /**
     * @author GingkoTreeX
     * @reason Esp
     */

  /*  @Overwrite
    public boolean shouldRender(double distance) {
        if (ModuleFramework.getInstance().isModuleEnabled(Esp.class)){return true;}
        double d0 = this.getBoundingBox().getAverageSideLength();
        if (Double.isNaN(d0)) {
            d0 = 1.0;
        }

        d0 *= 64.0 * renderDistanceMultiplier;
        return distance < d0 * d0;
    }
}
*/