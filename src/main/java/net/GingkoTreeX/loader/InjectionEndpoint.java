package net.GingkoTreeX.loader;

import net.GingkoTreeX.totem.Totem;

public class InjectionEndpoint {
    public static void Load() {
        (Totem.INSTANCE.Instance = new Totem()).init();
    }

}
