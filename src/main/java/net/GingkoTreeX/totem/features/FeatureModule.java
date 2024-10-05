package net.GingkoTreeX.totem.features;


import net.GingkoTreeX.totem.utils.IMinecraft;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public abstract class FeatureModule implements IMinecraft {
    private static boolean canRender = true;
    private final String name;
    private final Category category;
    private final String description;
    private double config;
    private String configName;
    private boolean enabled;
    private Integer keyBind;

    public FeatureModule(String name,
                         Category category,
                         String description,
                         @Nullable Integer defaultKeyBind /*如果没有绑定按键 则此处填null即可*/,
                         @Nullable String configName ,
                         double defConfig) {
                    this.name = name;
                    this.category = category;
                    this.description = description;
                    if (defaultKeyBind!=null) {
                        this.keyBind = defaultKeyBind;
                    }
                    if (configName!=null){
                        this.configName = configName;
                    }
                    this.config=defConfig;
    }
    public FeatureModule(String name,
                         Category category,
                         String description,
                         @Nullable Integer defaultKeyBind /*如果没有绑定按键 则此处填null即可*/
                         ) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.keyBind = defaultKeyBind;
        this.config = 0;
        this.configName = null;
    }

    public static void onUpdateAll() {
        for (FeatureModule module : ModuleFramework.getInstance().getModules()) {
            try {
                module.onUpdate();
                if (canRender) {
                    module.onRender();
                }
            }catch (NullPointerException ignored){

            }
        }
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void onUpdate();

    public abstract void onRender();

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getConfigName(){
        return configName;
    }

    public void setConfig(double config){
        this.config=config;
    }

    public double getConfig(){
        return config;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (mc.player != null) {
            if (enabled) {
                onEnable();
                MessageUtils.addGreenChatMessage("[+]" + this.getName() + " has been Enabled!");
            } else {
                onDisable();
                MessageUtils.addRedChatMessage("[-]" + this.getName() + " has been Disabled!");
            }
        }
    }

    public Integer getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(Integer keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isModuleKeyDown(FeatureModule module) {
        // 如果 keyBind 为 0，则表示未绑定按键，直接返回 false
            keyBind = module.getKeyBind();
            if (keyBind==null||keyBind==0) {
                return false;
            }
            long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
            return GLFW.glfwGetKey(windowHandle, keyBind) == GLFW.GLFW_PRESS;
    }

    public void setRender(boolean render){
        canRender = render;
    }

    public boolean getRender(){
        return canRender;
    }
}
