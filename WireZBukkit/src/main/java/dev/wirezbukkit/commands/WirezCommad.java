package dev.wirezbukkit.commands;

import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezbukkit.utils.string.MessageUtils;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.files.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WirezCommad implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!s.hasPermission("wirez.help")) {
            MessageUtils.sendMessage(s, LangAccessor.toConfigString(Lang.PREFIX) + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return true;
        }

        if (args.length > 0) {
            for (SubCommand subCommand : SubCommandRegistry.getInstance().getSubCommandList()) {
                if (args[0].equalsIgnoreCase(subCommand.getSubCommandName())) {
                    subCommand.perform(s, args);
                }
            }
        } else {
            for (SubCommand subCommand : SubCommandRegistry.getInstance().getSubCommandList()) {
                MessageUtils.sendMessage(s, LangAccessor.toConfigString(Lang.PREFIX)
                        + subCommand.getSubCommandSyntax() + " - " + subCommand.getSubCommandDescription());
            }
        }
        return true;
    }
}
