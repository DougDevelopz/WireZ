package dev.wirezbukkit.commands;

import dev.wirezbukkit.utils.string.MessageUtils;
import dev.wirezmc.commands.ICommandSender;
import org.bukkit.command.CommandSender;
import java.util.Optional;

public class CMDSenderImpl implements ICommandSender {

    private CommandSender source;

    public CMDSenderImpl(CommandSender source) {
        this.source = source;
    }

    @Override
    public Optional<String> getPlayerName() {
        return Optional.of(source.getName());
    }

    @Override
    public Optional<String> getPlayerUUID() {
        return Optional.empty();
    }

    @Override
    public boolean hasPermission(String permission) {
        return source.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        MessageUtils.sendMessage(source, message);
    }
}
