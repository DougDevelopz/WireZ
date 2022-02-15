package dev.wirezcommon.core.system;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SystemConstants {

    //System CPU
    private static final SystemMovingAverage SYSTEM_CPU_10_SEC = new SystemMovingAverage(10);
    private static final SystemMovingAverage SYSTEM_CPU_1_MIN = new SystemMovingAverage(60);
    private static final SystemMovingAverage SYSTEM_CPU_15_MIN = new SystemMovingAverage(60 * 15);

    //Process CPU
    private static final SystemMovingAverage PROCESS_CPU_10_SEC = new SystemMovingAverage(10);
    private static final SystemMovingAverage PROCESS_CPU_1_MIN = new SystemMovingAverage(60);
    private static final SystemMovingAverage PROCESS_CPU_15_MIN = new SystemMovingAverage(60 * 15);

    private static List<SystemMovingAverage> systemCPUAvgs = Collections.synchronizedList(Arrays.asList(SYSTEM_CPU_10_SEC, SYSTEM_CPU_1_MIN, SYSTEM_CPU_15_MIN));

    private static List<SystemMovingAverage> processCPUAvgs = Collections.synchronizedList(Arrays.asList(PROCESS_CPU_10_SEC, PROCESS_CPU_1_MIN, PROCESS_CPU_15_MIN));


    public static double getSystemCPU10Sec() {
        return SYSTEM_CPU_10_SEC.getAverage();
    }

    public static double getSystemCPU1Min() {
        return SYSTEM_CPU_1_MIN.getAverage();
    }

    public static double getSystemCPU15Min() {
        return SYSTEM_CPU_15_MIN.getAverage();
    }

    public static double getProcessCPU10Sec() {
        return PROCESS_CPU_10_SEC.getAverage();
    }

    public static double getProcessCPU1Min() {
        return PROCESS_CPU_1_MIN.getAverage();
    }

    public static double getProcessCPU15Min() {
        return PROCESS_CPU_15_MIN.getAverage();
    }

    public static List<SystemMovingAverage> getSystemCPUAvgs() {
        return systemCPUAvgs;
    }

    public static List<SystemMovingAverage> getProcessCPUAvgs() {
        return processCPUAvgs;
    }

}
