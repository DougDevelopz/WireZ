package dev.wirezmc;

import dev.wirezcommon.system.task.SystemsThreadExecutor;
import dev.wirezmc.platform.ModuleLocatorLoader;

public abstract class WireZPlugin extends ModuleLocatorLoader {

    protected void registerRegistries() {
        callOnLoadUp();
        SystemsThreadExecutor.call();
        registerFiles();
        registerCommands();
    }

    protected abstract void registerFiles();

    protected abstract void registerCommands();

}
