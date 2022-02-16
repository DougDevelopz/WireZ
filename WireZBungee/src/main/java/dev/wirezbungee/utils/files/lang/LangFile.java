package dev.wirezbungee.utils.files.lang;

import dev.wirezbungee.utils.files.AbstractFile;
import dev.wirezcommon.module.ModuleLoaderInfo;
import dev.wirezcommon.module.ModuleLoaderType;
import dev.wirezmc.files.Lang;
import net.md_5.bungee.config.Configuration;

@ModuleLoaderInfo(name = "Lang File", description = "Lang File allows you to edit added configurable messages", type = ModuleLoaderType.ADDON)
public class LangFile extends AbstractFile {

    @Override
    public void registerFile() {
        createFile("messages.yml");
        setData();
        saveFile();
    }

    @Override
    public void setData() {
        try {
            if (isFileEmpty()) {
                final Configuration configuration = getConfiguration();
                for (Lang item : Lang.CACHE) {
                    configuration.set(item.getPath(), item.getValue());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

