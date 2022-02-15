package dev.wirezmc.commands.types;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.promise.Promise;
import dev.wirezcommon.core.promise.PromiseGlobalExecutor;
import dev.wirezcommon.core.system.SystemConstants;
import dev.wirezcommon.core.system.module.cpu.ProcessCPUMonitor;
import dev.wirezcommon.core.system.module.cpu.SystemCPUMonitor;
import dev.wirezcommon.core.system.module.disk.DiskMonitor;
import dev.wirezcommon.core.system.module.memory.MemoryMonitor;
import dev.wirezcommon.core.system.module.thread.ThreadDump;
import dev.wirezcommon.core.system.module.thread.ThreadInfoMonitor;
import dev.wirezmc.commands.ICommandSender;
import dev.wirezmc.health.HealthFormat;
import dev.wirezmc.util.ByteBinClient;
import dev.wirezmc.util.IAction;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SystemCommands {

    public void sendSystemsCPUInformation(ICommandSender source, String systemsCPU) {
        source.sendMessage(systemsCPU.replace("%10s%", String.valueOf(Math.round(SystemConstants.getSystemCPU10Sec())))
                .replace("%1m%", String.valueOf(Math.round(SystemConstants.getSystemCPU1Min())))
                .replace("%15m%", String.valueOf(Math.round(SystemConstants.getSystemCPU15Min()))));
        AbstractModuleLoader.getModule(SystemCPUMonitor.class).ifPresent(systemCPUMonitor -> {
            source.sendMessage(HealthFormat.format(systemCPUMonitor.getElement()));
        });
    }

    public void sendProcessedCPUInformation(ICommandSender source, String processedCPU) {
        source.sendMessage(processedCPU.replace("%10s%", String.valueOf(Math.round(SystemConstants.getProcessCPU10Sec())))
                .replace("%1m%", String.valueOf(Math.round(SystemConstants.getProcessCPU1Min())))
                .replace("%15m%", String.valueOf(Math.round(SystemConstants.getProcessCPU15Min()))));
        AbstractModuleLoader.getModule(ProcessCPUMonitor.class).ifPresent(processedCPUMonitor -> {
            source.sendMessage(HealthFormat.format(processedCPUMonitor.getElement()));
        });
    }

    public void sendDiskInformation(ICommandSender source, String disk) {
        AbstractModuleLoader.getModule(DiskMonitor.class).ifPresent(diskMonitor -> {
            source.sendMessage(disk.replace("%used%", String.valueOf(Math.round(diskMonitor.getElement()[2])))
                    .replace("%total%", String.valueOf(Math.round(diskMonitor.getElement()[0]))));
            double div = (diskMonitor.getElement()[2] / diskMonitor.getElement()[0]) * 100;
            source.sendMessage(HealthFormat.format(Math.round(div)));
        });
    }

    public void sendRAMInformation(ICommandSender source, String ram) {
        AbstractModuleLoader.getModule(MemoryMonitor.class).ifPresent(memoryMonitor -> {
            source.sendMessage(ram.replace("%used%", String.valueOf(Math.round(memoryMonitor.getElement()[1])))
                    .replace("%total%", String.valueOf(Math.round(memoryMonitor.getElement()[0]))));
            double div = (memoryMonitor.getElement()[1] / memoryMonitor.getElement()[0]) * 100;
            source.sendMessage(HealthFormat.format(Math.round(div)));
        });
    }

    public void sendThreadInformation(IAction action) {
        Promise.createNew().fulfillInAsync(() -> {
            AbstractModuleLoader.getModule(ThreadInfoMonitor.class).ifPresent((threadInfoMonitor) -> {
                ByteBinClient.postRequest(List.of(Arrays.toString(threadInfoMonitor.getElement())), action);
            });
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    public void sendThreadDump(IAction action) {
        Promise.createNew().fulfillInAsync(() -> {
            AbstractModuleLoader.getModule(ThreadDump.class).ifPresent((threadDump) -> {
                ByteBinClient.postRequest(List.of(Arrays.toString(threadDump.getElement())), action);
            });
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    public void printHeapSummary(IAction action) {
        Promise.createNew().fulfillInAsync(() -> {
            try {
                MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
                ObjectName diagnosticName = ObjectName.getInstance("com.sun.management:type=DiagnosticCommand");
                GCHistogram diagnosticCommandMBean = JMX.newMXBeanProxy(beanServer, diagnosticName, GCHistogram.class);
                String histogramVisual = diagnosticCommandMBean.gcClassHistogram(new String[0]);
                ByteBinClient.postRequest(Collections.singletonList(histogramVisual), action);
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            }
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    public interface GCHistogram {
        String gcClassHistogram(String[] args);
    }
}
