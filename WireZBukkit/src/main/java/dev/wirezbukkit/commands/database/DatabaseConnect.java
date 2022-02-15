package dev.wirezbukkit.commands.database;

import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.files.Lang;

import static dev.wirezcommon.minecraft.commands.CommandTypesAccessor.getDatabaseCommandsInstance;

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
