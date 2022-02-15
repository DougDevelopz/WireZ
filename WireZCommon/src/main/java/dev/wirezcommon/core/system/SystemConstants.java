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

    private static final SystemMovingAverage DB_ACTIVE_CONNECTIONS_10_SEC = new SystemMovingAverage(10);
    private static final SystemMovingAverage DB_ACTIVE_CONNECTIONS_1_MIN = new SystemMovingAverage(60);
    private static final SystemMovingAverage DB_ACTIVE_CONNECTIONS_15_MIN = new SystemMovingAverage(60 * 15);

    private static final SystemMovingAverage DB_IDLE_CONNECTIONS_10_SEC = new SystemMovingAverage(10);
    private static final SystemMovingAverage DB_IDLE_CONNECTIONS_1_MIN = new SystemMovingAverage(60);
    private static final SystemMovingAverage DB_IDLE_CONNECTIONS_15_MIN = new SystemMovingAverage(60 * 15);

    private static List<SystemMovingAverage> systemCPUAvgs = Collections.synchronizedList(Arrays.asList(SYSTEM_CPU_10_SEC, SYSTEM_CPU_1_MIN, SYSTEM_CPU_15_MIN));

    private static List<SystemMovingAverage> processCPUAvgs = Collections.synchronizedList(Arrays.asList(PROCESS_CPU_10_SEC, PROCESS_CPU_1_MIN, PROCESS_CPU_15_MIN));

    private static List<SystemMovingAverage> activeConnectionAvgs = Collections.synchronizedList(Arrays.asList(DB_ACTIVE_CONNECTIONS_10_SEC,
            DB_ACTIVE_CONNECTIONS_1_MIN, DB_ACTIVE_CONNECTIONS_15_MIN));

    private static List<SystemMovingAverage> idleConnections = Collections.synchronizedList(Arrays.asList(DB_IDLE_CONNECTIONS_10_SEC,
            DB_IDLE_CONNECTIONS_1_MIN, DB_IDLE_CONNECTIONS_15_MIN));

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

    public static double getActiveConnection10Sec(){
        return DB_ACTIVE_CONNECTIONS_10_SEC.getAverage();
    }

    public static double getActiveConnection1Min(){
        return DB_ACTIVE_CONNECTIONS_1_MIN.getAverage();
    }

    public static double getActiveConnection15Min(){
        return DB_ACTIVE_CONNECTIONS_15_MIN.getAverage();
    }

    public static double getIdleConnection1Sec(){
        return DB_IDLE_CONNECTIONS_10_SEC.getAverage();
    }

    public static double getIdleConnection1Min(){
        return DB_ACTIVE_CONNECTIONS_1_MIN.getAverage();
    }

    public static double getIdleConnection15Min(){
        return DB_IDLE_CONNECTIONS_15_MIN.getAverage();
    }


    public static List<SystemMovingAverage> getSystemCPUAvgs() {
        return systemCPUAvgs;
    }

    public static List<SystemMovingAverage> getProcessCPUAvgs() {
        return processCPUAvgs;
    }

    public static List<SystemMovingAverage> getActiveConnectionAvgs() {
        return activeConnectionAvgs;
    }

    public static List<SystemMovingAverage> getIdleConnections() {
        return idleConnections;
    }
}
