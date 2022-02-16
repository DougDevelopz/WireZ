package dev.wirezbungee.commands;

import dev.wirezbungee.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.files.Lang;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class WireZCommand extends Command {

    public WireZCommand() {
        super("wirezb");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        CMDSenderImpl source = new CMDSenderImpl(sender);
        if (!source.hasPermission("wirez.help")) {
            source.sendMessage(LangAccessor.toConfigString(Lang.PREFIX) + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return;
        }

        if (args.length > 0) {
            for (SubCommand subCommand : SubCommandRegistry.getInstance().getSubCommandList()) {
                if (args[0].equalsIgnoreCase(subCommand.getSubCommandName())) {
                    subCommand.perform(source, args);
                }
            }
        } else {
            for (SubCommand subCommand : SubCommandRegistry.getInstance().getSubCommandList()) {
                source.sendMessage(LangAccessor.toConfigString(Lang.PREFIX)
                        + subCommand.getSubCommandSyntax() + " - " + subCommand.getSubCommandDescription());
            }
        }
    }
}

