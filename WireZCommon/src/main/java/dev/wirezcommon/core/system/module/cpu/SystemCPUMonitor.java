package dev.wirezcommon.core.system.module.cpu;

import com.sun.management.OperatingSystemMXBean;
import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.core.system.SystemsWrapper;

@ModuleLoaderInfo(name = "System CPU Load Monitor", description = "System CPU Load Monitor System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class SystemCPUMonitor extends AbstractModuleLoader implements SystemsWrapper<Double> {

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
        return false;
    }

    @Override
    public Double getElement() {
        OperatingSystemMXBean systemMXBean = initOperatingSystemMXBean();
        return systemMXBean.getSystemCpuLoad() * 100;
    }
}

