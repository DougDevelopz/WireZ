package dev.wirezcommon.core.system.module.disk;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.core.system.SystemsWrapper;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@ModuleLoaderInfo(name = "Disk Space Monitor", description = "Disk Space Monitor System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class DiskMonitor extends AbstractModuleLoader implements SystemsWrapper<List<Long>> {

    private static FileStore fileStore = null;

    static {
        try {
            fileStore = Files.getFileStore(Paths.get("."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isThread() {
        return false;
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
    public List<Long> getElement() {
        AtomicLong total = new AtomicLong();
        AtomicLong free = new AtomicLong();
        AtomicLong used = new AtomicLong();
        try {
            total.set(fileStore.getTotalSpace());
            free.set(fileStore.getUsableSpace());
            used.set(total.get() - free.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.synchronizedList(Arrays.asList(total.get(), free.get(), used.get()));
    }
}