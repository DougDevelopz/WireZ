package dev.wirezcommon.core.system.task;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.system.SystemConstants;
import dev.wirezcommon.core.system.SystemMovingAverage;
import dev.wirezcommon.core.system.module.cpu.ProcessCPUMonitor;
import dev.wirezcommon.core.system.module.cpu.SystemCPUMonitor;

import java.math.BigDecimal;

public class SystemsTask implements Runnable {


    public void run() {

        BigDecimal systemCpuLoad = BigDecimal.valueOf(getSystemCPULoad());
        BigDecimal processCpuLoad = BigDecimal.valueOf(getProcessCPULoad());

        if (systemCpuLoad.signum() != -1) { // if value is not negative
            System.out.println("Averages");
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
