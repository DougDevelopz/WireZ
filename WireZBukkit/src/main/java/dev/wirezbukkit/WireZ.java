package dev.wirezbukkit;

import dev.wirezcommon.promise.PromiseGlobalExecutor;
import dev.wirezcommon.system.task.SystemsThreadExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class WireZ extends JavaPlugin {

    public void onEnable() {
        new WireZRegistries();
    }

    public void onDisable() {
        SystemsThreadExecutor.close();
        PromiseGlobalExecutor.close();
    }

    public static WireZ getInstance() {
        return WireZ.getPlugin(WireZ.class);
    }
}
