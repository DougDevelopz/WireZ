package dev.wirezcommon.system.task;

import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.system.SystemConstants;
import dev.wirezcommon.system.SystemMovingAverage;
import dev.wirezcommon.system.module.cpu.ProcessCPUMonitor;
import dev.wirezcommon.system.module.cpu.SystemCPUMonitor;

import java.math.BigDecimal;

public class SystemsTask implements Runnable {


    public void run() {

        BigDecimal systemCpuLoad = BigDecimal.valueOf(getSystemCPULoad());
        BigDecimal processCpuLoad = BigDecimal.valueOf(getProcessCPULoad());

        if (systemCpuLoad.signum() != -1) { // if value is not negative
            for (SystemMovingAverage average : SystemConstants.getSystemCPUAvgs()) {
                average.add(systemCpuLoad);
            }
        }

        if (processCpuLoad.signum() != -1) { // if value is not negative
            for (SystemMovingAverage average : SystemConstants.getProcessCPUAvgs()) {
                average.add(processCpuLoad);
            }
        }
    }

    private double getSystemCPULoad() {
        return AbstractModuleLoader.getModule(SystemCPUMonitor.class).map(SystemCPUMonitor::getElement).orElse(0.0);
    }

    private double getProcessCPULoad() {
        return AbstractModuleLoader.getModule(ProcessCPUMonitor.class).map(ProcessCPUMonitor::getElement).orElse(0.0);
    }
}
