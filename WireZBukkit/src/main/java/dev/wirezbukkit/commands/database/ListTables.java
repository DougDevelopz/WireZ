package dev.wirezbukkit.commands.database;

import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezcommon.minecraft.mysql.MultiDataPoolSetup;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.commands.types.DatabaseCommands;
import dev.wirezcommon.minecraft.files.Lang;
import dev.wirezcommon.minecraft.util.ByteBinClient;

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
