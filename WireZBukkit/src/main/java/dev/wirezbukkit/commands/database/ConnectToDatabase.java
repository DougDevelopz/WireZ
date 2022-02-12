package dev.wirezbukkit.commands.database;

import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezbukkit.utils.string.MessageUtils;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.core.mysql.hikari.HikariAuthentication;
import dev.wirezcommon.core.mysql.hikari.MultiDataPoolSetup;
import dev.wirezcommon.core.mysql.other.SQLTypes;
import dev.wirezcommon.minecraft.files.Lang;
import org.bukkit.command.CommandSender;

public class ConnectToDatabase extends SubCommand {

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
        final CommandSender source = (CommandSender) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        final String noPerms = LangAccessor.toConfigString(Lang.NO_PERMISSION);
        if (args.length < 6) {
            MessageUtils.sendMessage(source, prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        if (!source.hasPermission("wirez.dbadmin")) {
            MessageUtils.sendMessage(source, prefix + noPerms);
            return;
        }

        final String host = args[1];
        int port = 0;
        try {
            port = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        final String database = args[3];
        final String username = args[4];
        final String password = args[5];
        final MultiDataPoolSetup grabInstance = MultiDataPoolSetup.grabInstance();
        grabInstance.init(source, source.getName(), message -> {
            MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.DATABASE_ALREADY_CONNECTED));
        }, SQLTypes.MYSQL, new HikariAuthentication(host, port, database, username, password), 5000, 10);
    }
}
