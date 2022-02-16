package dev.wirezbungee.commands.database;

import dev.wirezbungee.commands.CMDSenderImpl;
import dev.wirezbungee.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.files.Lang;

import static dev.wirezmc.commands.CommandTypesAccessor.getDatabaseCommandsInstance;

public class DatabaseConnect extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "connect";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.CONNECT_TO_DB_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.CONNECT_TO_DB_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        final String noPerms = LangAccessor.toConfigString(Lang.NO_PERMISSION);
        if (args.length < 8) {
            source.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        if (!source.hasPermission("wirez.dbadmin")) {
            source.sendMessage(prefix + noPerms);
            return;
        }

        final String[] messages = new String[]{
                prefix + LangAccessor.toConfigString(Lang.DATABASE_ALREADY_CONNECTED),
                prefix + LangAccessor.toConfigString(Lang.CONNECTED_TO_DB_SUCCESSFULLY)
        };

        getDatabaseCommandsInstance().initConnection(source, args, messages);
    }
}
