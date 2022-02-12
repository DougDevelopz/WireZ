package dev.wirezbukkit.commands.database;

import dev.wirezbukkit.WireZ;
import dev.wirezbukkit.commands.CMDSenderImpl;
import dev.wirezbukkit.utils.files.lang.LangAccessor;
import dev.wirezcommon.core.promise.Promise;
import dev.wirezcommon.core.promise.PromiseGlobalExecutor;
import dev.wirezcommon.minecraft.commands.SubCommand;
import dev.wirezcommon.minecraft.files.Lang;

public class DisconnectDatabase extends SubCommand {

    @Override
    public String getSubCommandName() {
        return "disconnect";
    }

    @Override
    public String getSubCommandDescription() {
        return LangAccessor.toConfigString(Lang.DISCONNECT_FROM_DB_DESC);
    }

    @Override
    public String getSubCommandSyntax() {
        return LangAccessor.toConfigString(Lang.DISCONNECT_FROM_DB_SYN);
    }

    @Override
    public void perform(Object sender, String[] args) {
        final CMDSenderImpl source = (CMDSenderImpl) sender;
        final String prefix = LangAccessor.toConfigString(Lang.PREFIX);
        if (args.length < 2) {
            source.sendMessage(prefix + this.getSubCommandSyntax() + " - " + this.getSubCommandDescription());
            return;
        }

        if (!source.hasPermission("wirez.dbadmin")) {
            source.sendMessage(prefix + LangAccessor.toConfigString(Lang.NO_PERMISSION));
            return;
        }

        final String[] messages = new String[]{
                prefix + LangAccessor.toConfigString(Lang.DATABASE_NOT_CONNECTED),
                prefix + LangAccessor.toConfigString(Lang.DISCONNECTED_FROM_DB_SUCCESSFULLY)
        };

        Promise.createNew().fulfillInAsync(() -> {
            getDatabaseCommandAccessorInstance().initDisconnection(source, args, messages);
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);

    }
}
