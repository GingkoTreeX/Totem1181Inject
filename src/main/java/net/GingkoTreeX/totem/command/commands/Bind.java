package net.GingkoTreeX.totem.command.commands;

import net.GingkoTreeX.totem.features.FeatureModule;
import net.GingkoTreeX.totem.features.ModuleFramework;
import net.GingkoTreeX.totem.utils.MessageUtils;
import org.lwjgl.glfw.GLFW;

public class Bind {
    public static boolean bind(String message){
            String[] parts = message.substring(1).split(" ");

            if ("bind".equals(parts[0])) {
                if (parts.length >= 3) {
                    String moduleName = parts[1];
                    int key = mapCharacterToGLFWKey(parts[2].charAt(0));
                    FeatureModule moduleToBind = findModuleByName(moduleName);
                    if (moduleToBind != null) {
                        if (parts[2].equalsIgnoreCase("None")){
                            moduleToBind.setKeyBind(null);
                            MessageUtils.addPinkChatMessage(moduleName+" is bind to "+ parts[2]);
                            return true;
                        }
                        bind(moduleToBind, key);
                        MessageUtils.addPinkChatMessage(moduleName+" is bind to "+ parts[2]);
                        return true;
                    } else {
                        MessageUtils.addPinkChatMessage("Module Not Found");
                    }
                } else {
                    MessageUtils.addPinkChatMessage(".bind command requires at least two arguments: <Module> <key>");
                }
            }
        return false;
    }
    private static FeatureModule findModuleByName(String moduleName) {
        if (moduleName!=null){
            return ModuleFramework.getInstance().getModuleByName(moduleName);
        }
        return null;
    }

    private static void bind(FeatureModule module, int key){
        Class<? extends FeatureModule> moduleClass=module.getClass();
        ModuleFramework.getInstance().setModuleKeyBind(moduleClass,key);
    }

    private static int mapCharacterToGLFWKey(char character) {
        int key = 0;
        char mappedChar = ' ';
        for (int keyCode = GLFW.GLFW_KEY_SPACE; keyCode <= GLFW.GLFW_KEY_LAST; keyCode++) {
            String mappedStr = GLFW.glfwGetKeyName(keyCode, GLFW.glfwGetKeyScancode(keyCode));

            if (mappedStr != null && !mappedStr.isEmpty()) {
                mappedChar = mappedStr.charAt(0);
            }

            key = keyCode;
            if (Character.toUpperCase(mappedChar) == Character.toUpperCase(character)) {
                break;
            }
        }
        return key;
    }
}
