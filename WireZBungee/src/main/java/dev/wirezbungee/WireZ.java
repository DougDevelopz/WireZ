package dev.wirezbungee;

import dev.wirezcommon.promise.PromiseGlobalExecutor;
import dev.wirezcommon.system.task.SystemsThreadExecutor;
import net.md_5.bungee.api.plugin.Plugin;

public class WireZ extends Plugin {

    private static WireZ instance;

    public void onEnable() {
        instance = this;
        new WireZRegistries();
    }

    public void onDisable(){
        SystemsThreadExecutor.close();
        PromiseGlobalExecutor.close();
    }

    public static WireZ getInstance() {
        return instance;
    }
}
