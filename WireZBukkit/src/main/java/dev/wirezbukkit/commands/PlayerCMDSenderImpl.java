package dev.wirezbukkit.commands;

import dev.wirezbukkit.utils.string.MessageUtils;
import dev.wirezcommon.minecraft.commands.ICommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PlayerCMDSenderImpl implements ICommandSender {

    private Player source;

    public PlayerCMDSenderImpl(Player source) {
        this.source = source;
    }

    @Override
    public Optional<String> getPlayerName() {
        return Optional.of(source.getName());
    }

    @Override
    public Optional<String> getPlayerUUID() {
        return Optional.of(source.getUniqueId().toString());
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
