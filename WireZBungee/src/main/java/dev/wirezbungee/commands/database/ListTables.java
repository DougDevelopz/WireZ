package dev.wirezbungee.commands.database;

import dev.wirezbungee.commands.CMDSenderImpl;
import dev.wirezbungee.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.commands.types.DatabaseCommands;
import dev.wirezmc.files.Lang;
import dev.wirezmc.mysql.MultiDataPoolSetup;
import dev.wirezmc.util.ByteBinClient;

public class ListTables extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "listtables";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.LIST_TABLES_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.LIST_TABLES_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final MultiDataPoolSetup multiDataPoolSetup = MultiDataPoolSetup.grabInstance();
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        final String noPerms = LangAccessor.toConfigString(Lang.NO_PERMISSION);
        if (args.length != 3) {
            source.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        if (!source.hasPermission("wirez.dbadmin")) {
            source.sendMessage(prefix + noPerms);
            return;
        }

        if (multiDataPoolSetup.isNotEstablished(source.grabName(), args[1])) {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.DATABASE_NOT_CONNECTED));
            return;
        }

        DatabaseCommands databaseCommands = getDatabaseCommandAccessorInstance();
        source.sendMessage(prefix + LangAccessor.toConfigString(Lang.PASTE_LOADING));
        databaseCommands.showTables(source, args, () -> {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.LIST_TABLES_COMPLETE).replace("%key%", ByteBinClient.getKey()));
        });
    }
}

