package dev.wirezbukkit.commands.systems;

import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezmc.commands.SubCommand;
import dev.wirezmc.files.Lang;
import dev.wirezmc.util.ByteBinClient;

public class HeapDumpSummary extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "heapdump";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.HEAP_DUMP_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.HEAP_DUMP_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);

        if (!source.hasPermission("wirez.sysadmin")) {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return;
        }

        source.sendMessage(prefix + LangAccessor.toConfigString(Lang.PASTE_LOADING));

        getSystemCommandAccessorInstance().printHeapSummary(() -> {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.HEAP_DUMP_COMPLETE).replace("%key%", ByteBinClient.getKey()));
        });
    }
}
