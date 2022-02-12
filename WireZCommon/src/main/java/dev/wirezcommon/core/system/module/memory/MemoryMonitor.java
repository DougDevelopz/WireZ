package dev.wirezcommon.core.system.module.memory;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.core.system.SystemsWrapper;

import java.lang.management.MemoryMXBean;

@ModuleLoaderInfo(name = "Memory Monitor", description = "Memory Monitor System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class MemoryMonitor extends AbstractModuleLoader implements SystemsWrapper<Long[]> {

    @Override
    public boolean isOperating() {
        return false;
    }

    @Override
    public boolean isThread() {
        return false;
    }

    @Override
    public boolean isMemory() {
        return true;
    }

    @Override
    public boolean isDatabase() {
        return false;
    }

    @Override
    public Long[] getElement() {
        MemoryMXBean memoryMXBean = initMemoryMXBean();
        final long usedMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
        final long total = memoryMXBean.getHeapMemoryUsage().getMax();
        return new Long[]{usedMemory, total};
    }
}

