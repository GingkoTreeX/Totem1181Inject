package net.GingkoTreeX.totem.config;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.ModuleFramework;

import javax.swing.*;
import java.io.*;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class ConfigManager {
    private final File configFile;

    public ConfigManager(String configPath) {
        this.configFile = new File(configPath);
    }

    public void saveConfigs() {
        List<FeatureModule> modules = ModuleFramework.getInstance().getModules();
        try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
            for (FeatureModule module : modules) {
                writer.println(
                        module.getName() + "," +
                                module.isEnabled() + "," + // 添加启用状态
                                module.getKeyBind() + "," +
                                module.getConfig()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadConfigs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
        List<FeatureModule> modules = ModuleFramework.getInstance().getModules();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // 更新条件
                    String moduleName = "def";
                    boolean enabled = false;
                    Integer keyBind = null;
                    try {
                        moduleName = parts[0];
                    } catch (Exception e){StackTraceElement[] s = e.getStackTrace();
                        JOptionPane.showConfirmDialog(null,s,"配置载入失败,功能名称获取错误", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);}
                    try {
                        enabled = Boolean.parseBoolean(parts[1]);
                    } catch (Exception e){StackTraceElement[] s = e.getStackTrace();
                        JOptionPane.showConfirmDialog(null,s,"配置载入失败,功能启动状态获取错误", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);}
                    try {
                        keyBind = parts[2].equals("null") ? null : Integer.parseInt(parts[2]);
                    } catch (Exception e){StackTraceElement[] s = e.getStackTrace();
                        JOptionPane.showConfirmDialog(null,s,"配置载入失败,功能按键绑定获取错误", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);}
                    double configValue = Double.parseDouble(parts[3]);
                    for (FeatureModule module : modules) {
                        if (module.getName().equals(moduleName)) {
                            module.setEnabled(enabled); // 设置启用状态
                            module.setKeyBind(keyBind);
                            module.setConfig(configValue);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e){
            StackTraceElement[] s = e.getStackTrace();
            JOptionPane.showMessageDialog(null,s);
        }
    }
}
