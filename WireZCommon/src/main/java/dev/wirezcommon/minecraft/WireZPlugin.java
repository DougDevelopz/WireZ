package dev.wirezcommon.minecraft;

import dev.wirezcommon.core.module.ModuleLocatorLoader;

public abstract class WireZPlugin extends ModuleLocatorLoader {

    protected void registerRegistries() {
        callOnLoadUp();
        registerFiles();
        registerDatabase();
        registerCommands();
        registerListeners();
    }

    protected abstract void registerFiles();

    protected abstract void registerDatabase();

    protected abstract void registerListeners();

    protected abstract void registerCommands();



}
