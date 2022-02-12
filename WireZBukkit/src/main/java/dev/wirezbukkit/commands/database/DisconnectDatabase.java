package dev.wirezbukkit.commands.database;

import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezbukkit.utils.string.MessageUtils;
import dev.wirezcommon.core.mysql.hikari.MultiDataPoolSetup;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.files.Lang;
import org.bukkit.command.CommandSender;

public class DisconnectDatabase extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "disconnect";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.DISCONNECT_FROM_DB_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.DISCONNECT_FROM_DB_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CommandSender source = (CommandSender) sender;
        final String name = source.getName();
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        if (args.length < 2) {
            MessageUtils.sendMessage(source, prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        if (!source.hasPermission("wirez.dbadmin")) {
            MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return;
        }


        final MultiDataPoolSetup multiDataPoolSetup = MultiDataPoolSetup.grabInstance();
        if (multiDataPoolSetup.getDataSource(name, args[1]) == null || multiDataPoolSetup.isClosed(name, args[1])) {
            MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.DATABASE_NOT_CONNECTED));
            return;
        }

        multiDataPoolSetup.close(((CommandSender) sender).getName(), args[1]);
        MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.DISCONNECTED_FROM_DB_SUCCESSFULLY));
    }
}
