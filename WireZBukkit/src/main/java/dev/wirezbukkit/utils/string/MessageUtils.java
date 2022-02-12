package dev.wirezbukkit.utils.string;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void sendMessage(Player player, String message) {
        if (player != null) {
            player.sendMessage(FormatUtils.color(message));
        } else {
            Bukkit.broadcastMessage(FormatUtils.color(message));
        }
    }

    public static void sendMessage(String text) {
        sendMessage(null, text);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(FormatUtils.color(message));
    }
}
