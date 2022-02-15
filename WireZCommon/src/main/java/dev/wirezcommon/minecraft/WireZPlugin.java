package dev.wirezcommon.minecraft;

import dev.wirezcommon.core.module.ModuleLocatorLoader;

public abstract class WireZPlugin extends ModuleLocatorLoader {

    protected void registerRegistries() {
        callOnLoadUp();
        registerFiles();
        registerCommands();
    }

    protected abstract void registerFiles();

    protected abstract void registerCommands();

}
