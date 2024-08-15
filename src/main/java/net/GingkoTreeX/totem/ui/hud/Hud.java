package net.GingkoTreeX.totem.ui.hud;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.Category;
import net.GingkoTreeX.totem.features.ModuleFramework;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "totem",bus = Mod.EventBusSubscriber.Bus.MOD)
public class Hud extends FeatureModule {
    private static boolean isOpen;
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public Hud() {
        super("Hud", Category.RENDER, "Hud", null,null,0);
    }

    @Override
    public void onEnable() {

        isOpen = true;
    }
    @Override
    public void onDisable() {

        isOpen = false;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && isOpen) {
            //AddChatMessage.addPinkChatMessage("[DEBUG]ClickGUI Step-0");
            MatrixStack matrices = event.getMatrixStack();
            renderModuleList(matrices);
        }

    }
    private void renderModuleList(MatrixStack matrices){
        int xPosition = MinecraftClient.getInstance().getWindow().getScaledWidth()-200; // 列表起始X位置
        int yPosition = 20; // 列表起始Y位置
        int elementHeight = 15; // 单个元素的高度

        mc.textRenderer.drawWithShadow(matrices, "[tips]" + "使用G键打开ClickGui", xPosition, yPosition + 2, 0XFF0000);
        yPosition += elementHeight;
        mc.textRenderer.drawWithShadow(matrices, "[tips]" + "输入.bind <key> <module>绑定按键", xPosition, yPosition + 2, 0XFF0000);
        List<FeatureModule> modules = ModuleFramework.getInstance().getAllModule();

        for (int i = 0; i < modules.size() && yPosition <= mc.getWindow().getScaledHeight() - 15; i-=-1) {
            FeatureModule module = modules.get(i);
            if (module.isEnabled()) {
                   // 添加矩形边框
                yPosition += elementHeight;
                // 绘制模块名称
                int color = -1;
                String config = module.getConfigName()==null ? " " : " "+module.getConfigName()+":"+module.getConfig();
                mc.textRenderer.drawWithShadow(matrices, "[+]" + module.getName()+config , xPosition, yPosition + 2, color);

            }
        }
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onRender() {
    }


}
