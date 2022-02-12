package dev.wirezbukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class WireZ extends JavaPlugin {

    public void onEnable() {
        new WireZRegistries();
    }

    public void onDisable() {

    }

    public static WireZ getInstance() {
        return WireZ.getPlugin(WireZ.class);
    }
}
