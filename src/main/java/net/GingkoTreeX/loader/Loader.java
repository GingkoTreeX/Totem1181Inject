package net.GingkoTreeX.loader;

import sun.misc.Unsafe;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;




public class Loader extends Thread {


    private final byte[][] classes;


    public Loader(final byte[][] classes){
        this.classes = classes;
    }

    /**
     * 调用建构元
     *
     * @return 状态码 目前为0
     */

    public static int a(final byte[][] array) {
        try {
            new Loader(array).start();
        } catch (Exception ignored) {
        }
        return 0;
    }


    public static byte[][] a(final int n) {
        return new byte[n][];
    }

    @Override
    public void run() {
        System.setProperty("java.awt.headless", "false");
        String PATH = System.getProperty("java.classpath");
        try {
            String className = "net.GingkoTreeX.totem.Totem.InjectionEndpoint";
            ClassLoader contextClassLoader = null;
            for (final Thread thread : Thread.getAllStackTraces().keySet()) {
                if (thread.getName().equalsIgnoreCase("Render thread")) {
                    contextClassLoader = thread.getContextClassLoader(); // 客户端线程的CL
                }
            }
            if (contextClassLoader == null) {
                return;
            }

            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            Module baseModule = Object.class.getModule();
            Class<?> currentClass = Loader.class;
            long addr = unsafe.objectFieldOffset(Class.class.getDeclaredField("module"));
            unsafe.getAndSetObject(currentClass, addr, baseModule);

            this.setContextClassLoader(contextClassLoader);
            final Method declaredMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
            declaredMethod.setAccessible(true);
            // 逐个加载class
            Class<?> clazz = null;
            Class<?> clazz2 = null;
            for (final byte[] array : this.classes) {
                JOptionPane.showMessageDialog(null,array);
                try {
                   clazz2 = (Class<?>) declaredMethod.invoke(contextClassLoader, null, array, 0, array.length, contextClassLoader.getClass().getProtectionDomain());
                }catch (Exception ignored){}
                if (clazz2 != null && clazz2.getName().contains(className)) {
                    clazz = clazz2;
                }
            }

            // 调用初始化方法
         if (clazz != null) {
             clazz.getDeclaredMethod("Load").invoke(null);
             //InjectionEndpoint.Load();
         }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
        }
    }
}
