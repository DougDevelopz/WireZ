package dev.wirezcommon.minecraft.commands;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface CommandSender {

    Optional<String> getPlayerName();

    Optional<String> getPlayerUUID();

    boolean hasPermission(String permission);

    void sendMessage(String message);

    default boolean isPlayer() {
        return getPlayerName().isPresent();
    }

    default String grabName() {
        AtomicReference<String> grabbedName = new AtomicReference<>();
        getPlayerName().ifPresent(grabbedName::set);
        return grabbedName.get();
    }
}
