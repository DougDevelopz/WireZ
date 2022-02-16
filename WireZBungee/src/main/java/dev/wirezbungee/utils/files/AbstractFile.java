package dev.wirezbungee.utils.files;

import dev.wirezcommon.module.AbstractModuleLoader;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractFile extends AbstractModuleLoader {

    private File file;
    private Configuration configuration;


    public void createFile(String fileName) {
        //To export a new folder by force
        try {
            Files.createDirectories(Paths.get(ProxyServer.getInstance().getPluginsFolder() + File.separator + "wirez"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        file = new File(ProxyServer.getInstance().getPluginsFolder() + File.separator + "wirez", fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveFile() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFileEmpty() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader.readLine() == null;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public abstract void registerFile();

    public abstract void setData();

}
