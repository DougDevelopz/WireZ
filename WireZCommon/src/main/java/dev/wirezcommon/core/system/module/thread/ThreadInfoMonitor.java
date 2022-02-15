package dev.wirezcommon.core.system.module.thread;


import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.core.system.SystemsWrapper;
import dev.wirezcommon.core.system.module.disk.DiskMonitor;
import dev.wirezcommon.core.system.module.memory.MemoryMonitor;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@ModuleLoaderInfo(name = "Thread Info Monitor", description = "Thread Info Monitor System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class ThreadInfoMonitor extends AbstractModuleLoader implements SystemsWrapper<ThreadInfo[]> {

    @Override
    public boolean isOperating() {
        return false;
    }

    @Override
    public boolean isThread() {
        return true;
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
    public ThreadInfo[] getElement() {
        ThreadMXBean threadMXBean = initThreadMXBean();
        return threadMXBean.getThreadInfo(Arrays.stream(threadMXBean.getAllThreadIds()).toArray());
    }
}
