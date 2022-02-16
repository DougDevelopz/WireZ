package dev.wirezbungee.commands.database;

import dev.wirezbungee.WireZ;
import dev.wirezbungee.commands.CMDSenderImpl;
import dev.wirezbungee.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.files.Lang;

public class DumpTable extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "dumptable";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.DUMP_TABLE_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.DUMP_TABLE_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        final String noPerms = LangAccessor.toConfigString(Lang.NO_PERMISSION);
        if (args.length != 4) {
            source.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        if (!source.hasPermission("wirez.dbadmin")) {
            source.sendMessage(prefix + noPerms);
            return;
        }

        final String[] messages = new String[]{
                prefix + LangAccessor.toConfigString(Lang.DATABASE_NOT_CONNECTED),
                prefix + LangAccessor.toConfigString(Lang.DUMP_TABLE_SUCCESS)
        };


        getDatabaseCommandAccessorInstance().createDumpOfTable(source, args, messages, WireZ.getInstance().getDataFolder());
    }
}

