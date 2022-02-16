package dev.wirezbungee.commands;

import dev.wirezbungee.utils.string.MessageUtils;
import dev.wirezmc.commands.ICommandSender;
import net.md_5.bungee.api.CommandSender;

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
