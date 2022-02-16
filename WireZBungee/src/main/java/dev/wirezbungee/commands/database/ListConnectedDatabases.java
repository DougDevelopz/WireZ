package dev.wirezbungee.commands.database;

import dev.wirezbungee.commands.CMDSenderImpl;
import dev.wirezbungee.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.files.Lang;
import dev.wirezmc.mysql.MultiDataPoolSetup;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ListConnectedDatabases extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "dblist";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.LISTED_DATABASES_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.LISTED_DATABASES_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        final MultiDataPoolSetup multiDataPoolSetup = MultiDataPoolSetup.grabInstance();

        if (args.length == 1) {
            if (!source.hasPermission("wirez.dbadmin")) {
                source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
                return;
            }

            final String[] messages = new String[]{
                    prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_EMPTY),
                    prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_INTRO)
            };
            getDatabaseCommandAccessorInstance().grabListOfDatabases(source, messages, () -> {
                for (String databases : multiDataPoolSetup.getPlayersCurrentDbs().get(source.grabName()).keySet()) {
                    source.sendMessage(prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_SET).replace("%database%", databases));
                }
            });

        } else if (args.length == 2) {
            if (!source.hasPermission("wirez.dbadmin")) {
                source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
                return;
            }
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
            if (target == null) {
                source.sendMessage(prefix + LangAccessor.toConfigString(Lang.PLAYER_NULL));
                return;
            }

            final String[] messages = new String[]{
                    prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_TARGET_EMPTY),
                    prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_TARGET_INTRO).replace("%player%", target.getName())
            };


            getDatabaseCommandAccessorInstance().grabListOfTargetsDatabase(source, args, messages, () -> {
                for (String databases : multiDataPoolSetup.getPlayersCurrentDbs().get(target.getName()).keySet()) {
                    source.sendMessage(prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_SET).replace("%database%", databases));
                }
            });
        }
    }
}

