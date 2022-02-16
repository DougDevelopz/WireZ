package dev.wirezcommon.system.module.thread;

import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.module.ModuleLoaderInfo;
import dev.wirezcommon.module.ModuleLoaderType;
import dev.wirezcommon.system.SystemsWrapper;

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