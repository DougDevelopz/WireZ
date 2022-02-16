package dev.wirezcommon.system.module.thread;


import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.module.ModuleLoaderInfo;
import dev.wirezcommon.module.ModuleLoaderType;
import dev.wirezcommon.system.SystemsWrapper;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

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
