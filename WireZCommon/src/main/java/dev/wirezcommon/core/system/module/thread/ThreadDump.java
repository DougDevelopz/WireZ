package dev.wirezcommon.core.system.module.thread;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.core.system.SystemsWrapper;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

@ModuleLoaderInfo(name = "Thread Dump System", description = "Thread Dump System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class ThreadDump extends AbstractModuleLoader implements SystemsWrapper<ThreadInfo[]> {

    @Override
    public boolean isThread() {
        return true;
    }

    @Override
    public boolean isOperating() {
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
    public ThreadInfo[] getElement() {
        ThreadMXBean threadMXBean = initThreadMXBean();
        return threadMXBean.dumpAllThreads(true, true);
    }
}