package net.GingkoTreeX.totem.command.commands;

import net.GingkoTreeX.totem.config.ConfigManager;

public class Config {
    public static boolean load(String message){
        String[] parts = message.substring(1).split(" ");
        if ("LoadConfig".equals(parts[0])) {
            new ConfigManager("Totem_Module_Setting.txt").loadConfigs();
            return true;
        }
        return false;
    }

    public static boolean save(String message){
        String[] parts = message.substring(1).split(" ");
        if ("LoadConfig".equals(parts[0])) {
            new ConfigManager("Totem_Module_Setting.txt").saveConfigs();
            return true;
        }
        return false;
    }
}
