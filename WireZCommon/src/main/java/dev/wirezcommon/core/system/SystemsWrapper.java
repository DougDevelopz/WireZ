package dev.wirezcommon.core.system;

import com.sun.management.OperatingSystemMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicReference;

public interface SystemsWrapper<T> {

    boolean isThread();

    boolean isOperating();

    boolean isMemory();

    boolean isDatabase();


    default ThreadMXBean initThreadMXBean() {
        AtomicReference<ThreadMXBean> threadMXBean = new AtomicReference<>();
        if (isThread()) {
            threadMXBean.set(ManagementFactory.getThreadMXBean());
        }

        return threadMXBean.get();
    }


    default OperatingSystemMXBean initOperatingSystemMXBean() {
        AtomicReference<OperatingSystemMXBean> operatingSystemMXBeanAtomicReference = new AtomicReference<>();
        if (isOperating()) {
            MBeanServerConnection connection = ManagementFactory.getPlatformMBeanServer();
            try {
                operatingSystemMXBeanAtomicReference.set(ManagementFactory.newPlatformMXBeanProxy(connection, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                        com.sun.management.OperatingSystemMXBean.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return operatingSystemMXBeanAtomicReference.get();
    }

    default MemoryMXBean initMemoryMXBean() {
        AtomicReference<MemoryMXBean> memoryMXBean = new AtomicReference<>();
        if (isMemory()) {
            memoryMXBean.set(ManagementFactory.getMemoryMXBean());
        }

        return memoryMXBean.get();
    }


    default HikariPoolMXBean initHikariPool(HikariDataSource dataSource) {
        AtomicReference<HikariPoolMXBean> hikariPoolMXBean = new AtomicReference<>();
        if (isDatabase()) {
            hikariPoolMXBean.set(dataSource.getHikariPoolMXBean());
        }
        return hikariPoolMXBean.get();
    }


    T getElement();
}
