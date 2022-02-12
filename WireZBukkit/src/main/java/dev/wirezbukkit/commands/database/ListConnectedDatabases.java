package dev.wirezbukkit.commands.database;

import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezbukkit.utils.string.MessageUtils;
import dev.wirezcommon.core.mysql.hikari.MultiDataPoolSetup;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.files.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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
        final CommandSender source = (CommandSender) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        final MultiDataPoolSetup multiDataPoolSetup = MultiDataPoolSetup.grabInstance();

        if (args.length == 1) {
            if (!source.hasPermission("wirez.dbadmin")) {
                MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
                return;
            }
            if (multiDataPoolSetup.getPlayersCurrentDbs().get(source.getName()).isEmpty()) {
                MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_EMPTY));
                return;
            }

            MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_INTRO));
            for (String databases : multiDataPoolSetup.getPlayersCurrentDbs().get(source.getName()).keySet()) {
                MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_SET).replace("%database%",
                        databases));
            }
        } else if (args.length == 2) {
            if (!source.hasPermission("wirez.dbadmin")) {
                MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.PLAYER_NULL));
                return;
            }

            MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_TARGET_INTRO)
                    .replace("%player%", target.getName()));
            for (String databases : multiDataPoolSetup.getPlayersCurrentDbs().get(target.getName()).keySet()) {
                MessageUtils.sendMessage(source, prefix + LangAccessor.toConfigString(Lang.LISTED_DATABASES_SET)
                        .replace("%database%", databases));
            }
        }
    }
}
