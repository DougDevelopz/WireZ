package dev.wirezcommon.minecraft.platform;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.minecraft.mysql.MultiDataPoolSetup;
import dev.wirezcommon.core.mysql.other.StatementAPI;
import dev.wirezcommon.core.system.module.cpu.ProcessCPUMonitor;
import dev.wirezcommon.core.system.module.cpu.SystemCPUMonitor;
import dev.wirezcommon.core.system.module.disk.DiskMonitor;
import dev.wirezcommon.core.system.module.memory.MemoryMonitor;
import dev.wirezcommon.core.system.module.thread.ThreadDump;
import dev.wirezcommon.core.system.module.thread.ThreadInfoMonitor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class ModuleLocatorLoader {

    private final List<Class<? extends AbstractModuleLoader>> instanceModuleList = new LinkedList<>(Arrays.asList(StatementAPI.class, MultiDataPoolSetup.class));

    private final List<Class<? extends AbstractModuleLoader>> addonModuleList = new LinkedList<>(Arrays.asList(ProcessCPUMonitor.class,
            SystemCPUMonitor.class, DiskMonitor.class, MemoryMonitor.class, ThreadDump.class,
            ThreadInfoMonitor.class));

    public void callOnLoadUp() {
        addToInstances();
        addToAddons();
        initInstances();
        initAddons();
    }

    public void initInstances() {
        for (Class<? extends AbstractModuleLoader> instanceModules : instanceModuleList) {
            AbstractModuleLoader.loadModule(instanceModules);
        }
    }

    public void initAddons() {
        for (Class<? extends AbstractModuleLoader> addonModules : addonModuleList) {
            AbstractModuleLoader.loadModule(addonModules);
        }
    }

    public abstract void addToInstances();

    public abstract void addToAddons();

    public List<Class<? extends AbstractModuleLoader>> getInstanceModuleList() {
        return instanceModuleList;
    }

    public List<Class<? extends AbstractModuleLoader>> getAddonModuleList() {
        return addonModuleList;
    }
}
