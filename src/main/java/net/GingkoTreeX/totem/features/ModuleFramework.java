package net.GingkoTreeX.totem.features;

import net.GingkoTreeX.totem.HackFramework;
import net.GingkoTreeX.totem.features.module.*;
import net.GingkoTreeX.totem.ui.gui.ClickGui;
import net.GingkoTreeX.totem.ui.hud.Hud;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static net.minecraft.client.render.debug.DebugRenderer.drawBox;

public class ModuleFramework extends HackFramework implements EventListener {

    private static ModuleFramework INSTANCE;

    public static ModuleFramework getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleFramework();
        }

        return INSTANCE;
    }

    private ModuleFramework() {
        super("Module", true);
        // 在此处注册模块 未注册的模块不会被加载
        registerModule(new Hud());
        registerModule(new ClickGui());
        registerModule(new KillAura());
        registerModule(new FastUse());
        registerModule(new AutoEat());
        registerModule(new Criticals());
        registerModule(new AirJump());
        registerModule(new SafeWalk());
        registerModule(new Lighting());
        registerModule(new SlowKillAura());
        registerModule(new Speed());
        registerModule(new NoRepelled());
        registerModule(new Fly());
        registerModule(new Esp());
        registerModule(new HitBox());
        registerModule(new Xray());
        registerModule(new NoAttacked());
    }


    public FeatureModule getModuleByName(String name) {
        List<FeatureModule> modules=getModules();
        for (FeatureModule module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }


    public List<FeatureModule> getAllModule(){
       return super.getModules();
    }

    public List<FeatureModule> getCategoryModules(Category category) {
        List<FeatureModule> categoryModules = new ArrayList<>();
        for (FeatureModule module : getModules()) {
            if (module.getCategory() == category) {
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }
}
