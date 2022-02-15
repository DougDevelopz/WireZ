package dev.wirezcommon.core.system.module.memory;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.core.system.SystemsWrapper;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

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

