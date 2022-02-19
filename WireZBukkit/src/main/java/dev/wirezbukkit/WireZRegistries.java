package dev.wirezbukkit;

import dev.wirezbukkit.commands.SubCommandRegistry;
import dev.wirezbukkit.commands.WirezCommad;
import dev.wirezbukkit.utils.files.lang.LangFile;
import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.socket.WireZServerSocketController;
import dev.wirezmc.platform.PlatformInfo;
import dev.wirezmc.platform.PlatformType;
import dev.wirezmc.WireZPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class WireZRegistries extends WireZPlugin implements PlatformInfo {

    public WireZRegistries() {
        Bukkit.getLogger().log(Level.INFO, convertToString());
        registerRegistries();
        AbstractModuleLoader.getModuleMap().values().forEach(module -> {
            String message = module.getName() + " {" + module.getDescription() + " | " + module.getModuleType() + "} has loaded!";
            Bukkit.getLogger().log(Level.INFO, message);
        });
        final WireZ plugin = WireZ.getInstance();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        if (plugin.getConfig().getBoolean("web-server-socket-enabled")) {
            WireZServerSocketController serverSocketController = new WireZServerSocketController(plugin.getConfig().getInt("web-server-socket-port"));
            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, serverSocketController::reportStats, 20, 20 * 15);
        }
    }

    @Override
    public String getName() {
        return "WireZ Bukkit";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public PlatformType getType() {
        return PlatformType.SERVER;
    }

    @Override
    public void addToInstances() {
        //No instances are needed
    }

    @Override
    public void addToAddons() {
        List<Class<? extends AbstractModuleLoader>> connectList = getAddonModuleList();
        connectList.add(LangFile.class);
    }

    @Override
    protected void registerFiles() {
        AbstractModuleLoader.getModule(LangFile.class).ifPresent(LangFile::registerFile);
        //This is just a dummy to allow me to make the folder "dblogs"
        File file = new File(WireZ.getInstance().getDataFolder() + File.separator + "dblogs", "empty.txt");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void registerCommands() {
        WireZ.getInstance().getCommand("wirez").setExecutor(new WirezCommad());
        SubCommandRegistry.getInstance().registerCommands();
    }
}
