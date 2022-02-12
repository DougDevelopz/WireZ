package dev.wirezbukkit.commands.database;

import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezcommon.core.mysql.hikari.MultiDataPoolSetup;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.files.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
            getDatabaseCommandAccessorInstance().grabListOfDatabases(source, messages, (commandAction) -> {
                for (String databases : multiDataPoolSetup.getPlayersCurrentDbs().get(source.grabName()).keySet()) {
                    source.sendMessage(prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_SET).replace("%database%", databases));
                }
            });

        } else if (args.length == 2) {
            if (!source.hasPermission("wirez.dbadmin")) {
                source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                source.sendMessage(prefix + LangAccessor.toConfigString(Lang.PLAYER_NULL));
                return;
            }

            final String[] messages = new String[]{
                    prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_TARGET_EMPTY),
                    prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_TARGET_INTRO).replace("%player%", target.getName())
            };
            getDatabaseCommandAccessorInstance().grabListOfTargetsDatabase(source, args, messages, (commandAction) -> {
                for (String databases : multiDataPoolSetup.getPlayersCurrentDbs().get(target.getName()).keySet()) {
                    source.sendMessage(prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_SET).replace("%database%", databases));
                }
            });
        }
    }
}
