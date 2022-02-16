package dev.wirezbungee.utils.string;

import net.md_5.bungee.api.ChatColor;

public class FormatUtils {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
