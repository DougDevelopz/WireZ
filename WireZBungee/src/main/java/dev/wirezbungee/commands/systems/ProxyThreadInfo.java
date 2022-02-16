package dev.wirezbungee.commands.systems;

import dev.wirezbungee.commands.CMDSenderImpl;
import dev.wirezbungee.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.files.Lang;
import dev.wirezmc.util.ByteBinClient;

public class ProxyThreadInfo extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "threadinfo";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.THREAD_INFO_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.THREAD_INFO_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);

        if (!source.hasPermission("wirez.sysadmin")) {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return;
        }

        source.sendMessage(prefix + LangAccessor.toConfigString(Lang.PASTE_LOADING));
        getSystemCommandAccessorInstance().sendThreadInformation(() -> {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.THREAD_INFO_COMPLETE).replace("%key%", ByteBinClient.getKey()));
        });
    }
}

