package dev.wirezbukkit.commands.systems;

import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezcommon.core.promise.Promise;
import dev.wirezcommon.core.promise.PromiseGlobalExecutor;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.files.Lang;
import dev.wirezcommon.minecraft.util.ByteBinClient;

public class ServerThreadDump extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "threaddump";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.THREAD_DUMP_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.THREAD_DUMP_SYN);
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
        Promise.createNew().fulfillInAsync(() -> {
            getSystemCommandAccessorInstance().sendThreadDump(() -> {
                source.sendMessage(prefix + LangAccessor.toConfigString(Lang.THREAD_INFO_COMPLETE).replace("%key%", ByteBinClient.getKey()));
            });
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }
}
