package dev.wirezbungee.utils.files;

import dev.wirezcommon.module.ModuleLoaderInfo;
import dev.wirezcommon.module.ModuleLoaderType;
import net.md_5.bungee.config.Configuration;

@ModuleLoaderInfo(name = "Config File", description = "Config File allows you to edit settings", type = ModuleLoaderType.ADDON)
public class Config extends AbstractFile {

    @Override
    public void registerFile() {
        createFile("config.yml");
        setData();
        saveFile();
    }

    @Override
    public void setData() {
        try {
            if (isFileEmpty()) {
                final Configuration configuration = getConfiguration();
                configuration.set("web-server-socket-enabled", false);
                configuration.set("web-server-socket-port", 30044);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
