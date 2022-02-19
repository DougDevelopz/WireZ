package dev.wirezbungee;

import dev.wirezbungee.commands.SubCommandRegistry;
import dev.wirezbungee.commands.WireZCommand;
import dev.wirezbungee.utils.files.Config;
import dev.wirezbungee.utils.files.lang.LangFile;
import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.socket.WireZServerSocketController;
import dev.wirezmc.WireZPlugin;
import dev.wirezmc.platform.PlatformInfo;
import dev.wirezmc.platform.PlatformType;
import net.md_5.bungee.api.ProxyServer;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class WireZRegistries extends WireZPlugin implements PlatformInfo {

    public WireZRegistries() {
        ProxyServer.getInstance().getLogger().log(Level.INFO, convertToString());
        registerRegistries();
        AbstractModuleLoader.getModuleMap().values().forEach(module -> {
            String message = module.getName() + " {" + module.getDescription() + " | " + module.getModuleType() + "} has loaded!";
            ProxyServer.getInstance().getLogger().log(Level.INFO, message);
        });

        AbstractModuleLoader.getModule(Config.class).ifPresent(config -> {
            if (config.getConfiguration().getBoolean("web-server-socket-enabled")) {
                WireZServerSocketController serverSocketController = new WireZServerSocketController(config.getConfiguration().getInt("web-server-socket-port"));
                ProxyServer.getInstance().getScheduler().schedule(WireZ.getInstance(), serverSocketController::reportStats, 20, TimeUnit.SECONDS);
            }
        });
    }

    @Override
    public String getName() {
        return "WireZ Bungee";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public PlatformType getType() {
        return PlatformType.PROXY;
    }

    @Override
    public void addToInstances() {
        //No instances are needed
    }

    @Override
    public void addToAddons() {
        List<Class<? extends AbstractModuleLoader>> connectList = getAddonModuleList();
        connectList.add(LangFile.class);
        connectList.add(Config.class);
    }

    @Override
    protected void registerFiles() {
        AbstractModuleLoader.getModule(LangFile.class).ifPresent(LangFile::registerFile);
        AbstractModuleLoader.getModule(Config.class).ifPresent(Config::registerFile);
    }

    @Override
    protected void registerCommands() {
        WireZ.getInstance().getProxy().getPluginManager().registerCommand(WireZ.getInstance(), new WireZCommand());
        SubCommandRegistry.getInstance().registerCommands();
    }
}
