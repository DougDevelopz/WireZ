package dev.wirezbungee.commands.systems;

import dev.wirezbungee.commands.CMDSenderImpl;
import dev.wirezbungee.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.commands.types.SystemCommands;
import dev.wirezmc.files.Lang;

public class MemoryInfo extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "memory";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.MEMORY_INFO_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.MEMORY_INFO_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);

        if (!source.hasPermission("wirez.sysadmin")) {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return;
        }

        source.sendMessage(prefix + LangAccessor.toConfigString(Lang.MEMORY_INFO_HEADER));
        SystemCommands systemCommands = getSystemCommandAccessorInstance();
        systemCommands.sendDiskInformation(source, LangAccessor.toConfigString(Lang.DISK_INFO));
        systemCommands.sendRAMInformation(source, LangAccessor.toConfigString(Lang.RAM_INFO));
    }
}
