package dev.wirezbukkit.utils.files;

import dev.wirezbukkit.WireZ;
import dev.wirezcommon.core.module.AbstractModuleLoader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractFile extends AbstractModuleLoader {

    private final WireZ plugin = WireZ.getInstance();
    private File file;
    private FileConfiguration fileConfiguration;


    public void createFile(String fileName) {
        file = new File(plugin.getDataFolder(), fileName);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void createFile(String folderName, String fileName) {
        file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }


    public void saveFile() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void reloadFile(String fileName) {
        if (file == null) {
            file = new File(plugin.getDataFolder(), fileName);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        InputStream configStream = plugin.getResource(fileName);
        if (configStream != null) {
            YamlConfiguration newFile = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream));
            getFileConfiguration().setDefaults(newFile);
        }
    }

    public void reloadFile(String folderName, String fileName) {
        if (file == null) {
            file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        InputStream configStream = plugin.getResource(fileName);
        if (configStream != null) {
            YamlConfiguration newFile = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream));
            getFileConfiguration().setDefaults(newFile);
        }
    }


    public boolean isFileNotEmpty() {
        return getFileConfiguration().getKeys(false).size() != 0;
    }

    public boolean containsPath(String path) {
        return getFileConfiguration().contains(path);
    }

    public abstract void registerFile();

    public abstract void setData();

}
