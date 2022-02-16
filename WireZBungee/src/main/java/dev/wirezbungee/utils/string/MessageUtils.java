package dev.wirezbungee.utils.string;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageUtils {

    public static void sendMessage(String text, ProxiedPlayer player) {
        if (player != null) {
            player.sendMessage(TextComponent.fromLegacyText(FormatUtils.color(text)));
        } else {
            ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(FormatUtils.color(text)));
        }
    }

    public static void sendMessage(String text) {
        sendMessage(text, null);
    }


    public static void sendConsoleMessage(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(FormatUtils.color(message)));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText(FormatUtils.color(message)));
    }
}
