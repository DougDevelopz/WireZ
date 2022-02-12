package dev.wirezcommon.core.system.module.cpu;


import com.sun.management.OperatingSystemMXBean;


import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.core.system.SystemsWrapper;

@ModuleLoaderInfo(name = "Process CPU Load Monitor", description = "Process CPU Load Monitor System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class ProcessCPUMonitor extends AbstractModuleLoader implements SystemsWrapper<Double> {

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
    public boolean isDatabase() {
        return false;
    }

    @Override
    public Double getElement() {
        OperatingSystemMXBean systemMXBean = initOperatingSystemMXBean();
        return systemMXBean.getProcessCpuLoad() * 100;
    }
}
