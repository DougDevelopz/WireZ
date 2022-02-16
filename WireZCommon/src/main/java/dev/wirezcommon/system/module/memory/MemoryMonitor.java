package dev.wirezcommon.system.module.memory;

import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.module.ModuleLoaderInfo;
import dev.wirezcommon.module.ModuleLoaderType;
import dev.wirezcommon.system.SystemsWrapper;

import java.lang.management.MemoryMXBean;

@ModuleLoaderInfo(name = "Memory Monitor", description = "Memory Monitor System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class MemoryMonitor extends AbstractModuleLoader implements SystemsWrapper<Double[]> {


    @Override
    public boolean isOperating() {
        return true;
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
    public Double[] getElement() {
        MemoryMXBean memoryMXBean = initMemoryXBean();
        final int mb = 1024 * 1024;
        return new Double[]{
                ((double) memoryMXBean.getHeapMemoryUsage().getMax() / mb),
                ((double) memoryMXBean.getHeapMemoryUsage().getUsed() / mb),
                ((double) (memoryMXBean.getHeapMemoryUsage().getMax() - memoryMXBean.getHeapMemoryUsage().getUsed()) / mb)
        };
    }
}

