package dev.wirezbukkit.commands.systems;

import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.commands.types.SystemCommands;
import dev.wirezmc.files.Lang;

public class CPUInfo extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "cpu";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.CPU_INFO_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.CPU_INFO_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);

        if (!source.hasPermission("wirez.sysadmin")) {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return;
        }
        source.sendMessage(prefix + LangAccessor.toConfigString(Lang.CPU_INFO_HEADER));
        SystemCommands systemCommands = getSystemCommandAccessorInstance();
        systemCommands.sendSystemsCPUInformation(source, LangAccessor.toConfigString(Lang.CPU_INFO_SYSTEMS));
        systemCommands.sendProcessedCPUInformation(source, LangAccessor.toConfigString(Lang.CPU_INFO_PROCESSED));
    }
}
