package net.GingkoTreeX.totem;

import net.GingkoTreeX.totem.command.CommandManager;
import net.GingkoTreeX.totem.config.ConfigManager;
import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.ModuleFramework;
import net.GingkoTreeX.totem.features.module.Esp;
import net.GingkoTreeX.totem.ui.gui.ClickGui;
import net.GingkoTreeX.totem.ui.hud.Hud;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.swing.*;

@Mod("totem")
public class Totem {
    public static Totem INSTANCE;
    public Totem Instance;
    public static String mode; 
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private static final String PATH = "Totem_Module_Setting.txt";
    private static boolean i = true;

    public Totem() {
        // Register 《事件巴士》
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClickGui());
        MinecraftForge.EVENT_BUS.register(new Hud());
        MinecraftForge.EVENT_BUS.register(new Esp());
    }
    public void init(){
        // Register 《事件巴士》
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClickGui());
        MinecraftForge.EVENT_BUS.register(new Hud());
        MinecraftForge.EVENT_BUS.register(new Esp());
    }

    //处理框架逻辑实现
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        //初始化
        if (i) {
            System.setProperty("java.awt.headless", "false");
            setConfig();
            setTitle();
            i = false;
        }

        //模块状态更新
        try {
            FeatureModule.onUpdateAll();
            setTitle();
        }catch (Exception e){
            StackTraceElement[] s = e.getStackTrace();
            JOptionPane.showMessageDialog(null,s,"功能异常,已关闭所有功能,以下为异常椎栈:\n"
                                            ,JOptionPane.ERROR_MESSAGE);
            for (FeatureModule module : ModuleFramework.getInstance().getModules()) {
                module.setEnabled(false);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        //处理命令
        String message= event.getMessage();
        CommandManager.onChat(message,event);
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        //处理按键绑定
        if (mc.player==null){return;}
        for (FeatureModule module : ModuleFramework.getInstance().getModules()) {
            if (module.isModuleKeyDown(module)) {
                module.setEnabled(!module.isEnabled());
            }
        }
    }

    static {
        //JVM关闭时保存配置
        Runtime.getRuntime().addShutdownHook(new Thread(() -> new ConfigManager(PATH).saveConfigs()));
    }

    @OnlyIn(Dist.CLIENT)
    private void setConfig(){
        //获取配置信息
        ConfigManager configManager = new ConfigManager(PATH);
        configManager.loadConfigs();
        //自动启动hud（可删除）
        if(mc.player!=null){
            if (!ModuleFramework.getInstance().getModule(Hud.class).isEnabled())
            {
                ModuleFramework.getInstance().getModule(Hud.class).setEnabled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void setTitle(){
        mc.getWindow().setTitle(" [TotemMod] " + mc.getGameVersion());
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void setupC(final FMLCommonSetupEvent event) {
        System.out.println(event.getIMCStream());
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void setupS(final FMLCommonSetupEvent event){
        try {
            Thread.sleep(1000);
            System.out.println("咋滴你要用服务器暴打玩家啊？？");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
