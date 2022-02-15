package dev.wirezbukkit.commands.systems;

import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.commands.types.SystemCommands;
import dev.wirezcommon.minecraft.files.Lang;

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
