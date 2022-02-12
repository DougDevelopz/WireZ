package dev.wirezbukkit;

import dev.wirezcommon.core.promise.PromiseGlobalExecutor;
import dev.wirezcommon.core.system.task.SystemsThreadExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class WireZ extends JavaPlugin {

    public void onEnable() {

        new WireZRegistries();
        SystemsThreadExecutor.call();
    }

    public void onDisable() {
        SystemsThreadExecutor.close();
        PromiseGlobalExecutor.terminate();
    }

    public static WireZ getInstance() {
        return WireZ.getPlugin(WireZ.class);
    }
}
