package dev.wirezcommon.core.system.task;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.system.SystemConstants;
import dev.wirezcommon.core.system.SystemMovingAverage;
import dev.wirezcommon.core.system.module.cpu.ProcessCPUMonitor;
import dev.wirezcommon.core.system.module.cpu.SystemCPUMonitor;
import dev.wirezcommon.core.system.module.database.ActiveConnections;
import dev.wirezcommon.core.system.module.database.IdleConnections;

import java.math.BigDecimal;

public class SystemsTask implements Runnable {


    public void run() {

        BigDecimal systemCpuLoad = BigDecimal.valueOf(getSystemCPULoad());
        BigDecimal processCpuLoad = BigDecimal.valueOf(getProcessCPULoad());
        BigDecimal activeConnectionLoad = BigDecimal.valueOf(getActiveConnections());
        BigDecimal idleConnectionLoad = BigDecimal.valueOf(getIdleConnections());

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

        if (activeConnectionLoad.signum() != -1) {
            for (SystemMovingAverage average : SystemConstants.getActiveConnectionAvgs()) {
                average.add(activeConnectionLoad);
               //System.out.println(average.getAverage());
            }
        }

        if (idleConnectionLoad.signum() != -1) {
            for (SystemMovingAverage average : SystemConstants.getIdleConnections()) {
                average.add(idleConnectionLoad);
               // System.out.println(average.getAverage());
            }
        }
    }

    private double getSystemCPULoad() {
        return AbstractModuleLoader.getModule(SystemCPUMonitor.class).map(SystemCPUMonitor::getElement).orElse(0.0);
    }

    private double getProcessCPULoad() {
        return AbstractModuleLoader.getModule(ProcessCPUMonitor.class).map(ProcessCPUMonitor::getElement).orElse(0.0);
    }

    private int getActiveConnections() {
        return AbstractModuleLoader.getModule(ActiveConnections.class).map(ActiveConnections::getElement).orElse(0);
    }

    private int getIdleConnections() {
        return AbstractModuleLoader.getModule(IdleConnections.class).map(IdleConnections::getElement).orElse(0);
    }
}
