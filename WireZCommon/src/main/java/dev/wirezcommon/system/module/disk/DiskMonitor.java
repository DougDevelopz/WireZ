package dev.wirezcommon.system.module.disk;

import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.module.ModuleLoaderInfo;
import dev.wirezcommon.module.ModuleLoaderType;
import dev.wirezcommon.system.SystemsWrapper;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

@ModuleLoaderInfo(name = "Disk Space Monitor", description = "Disk Space Monitor System for system aspects of the plugin", type = ModuleLoaderType.ADDON)
public class DiskMonitor extends AbstractModuleLoader implements SystemsWrapper<Double[]> {

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
    public boolean isDatabase() {
        return false;
    }

    @Override
    public Double[] getElement() {
        final int mb = 1024 * 1024;
        try {
            return new Double[]{(double) fileStore.getTotalSpace() / mb, (double) fileStore.getUsableSpace() / mb,
                    (double) (fileStore.getTotalSpace() - fileStore.getUsableSpace()) / mb};
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}