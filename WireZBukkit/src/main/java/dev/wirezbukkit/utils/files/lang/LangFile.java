package dev.wirezbukkit.utils.files.lang;

import dev.wirezbukkit.utils.files.AbstractFile;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezcommon.minecraft.files.Lang;
import org.bukkit.configuration.Configuration;

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
        if (isFileNotEmpty()) return;
        final Configuration configuration = getFileConfiguration();
        for (Lang item : Lang.CACHE) {
            configuration.set(item.getPath(), item.getValue());
        }
    }
}
