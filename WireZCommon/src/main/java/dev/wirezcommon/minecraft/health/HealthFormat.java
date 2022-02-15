package dev.wirezcommon.minecraft.health;

import java.util.concurrent.atomic.AtomicReference;

public class HealthFormat {

    public static String format(/*Allows for any value type (double,long,etc)*/Number valueType) {
        AtomicReference<StringBuilder> dataText = new AtomicReference<>();

        int dataPoint = valueType.intValue();
        if (dataPoint >= 0 && dataPoint <= 10) {
            dataText.set(new StringBuilder().append("&a⚫").append("&7").append("⚫".repeat(9)));
        } else if (dataPoint >= 11 && dataPoint <= 20) {
            dataText.set(new StringBuilder().append("&a").append("⚫".repeat(2)).append("&7").append("⚫".repeat(8)));
        } else if (dataPoint >= 21 && dataPoint <= 30) {
            dataText.set(new StringBuilder().append("&a").append("⚫".repeat(3)).append("&7").append("⚫".repeat(7)));
        } else if (dataPoint >= 31 && dataPoint <= 40) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(4)).append("&7").append("⚫".repeat(6)));
        } else if (dataPoint >= 41 && dataPoint <= 50) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(5)).append("&7").append("⚫".repeat(5)));
        } else if (dataPoint >= 51 && dataPoint <= 60) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(6)).append("&7").append("⚫".repeat(4)));
        } else if (dataPoint >= 61 && dataPoint <= 70) {
            dataText.set(new StringBuilder().append("&e").append("⚫".repeat(7)).append("&7").append("⚫".repeat(3)));
        } else if (dataPoint >= 71 && dataPoint <= 80) {
            dataText.set(new StringBuilder().append("&c").append("⚫".repeat(8)).append("&7").append("⚫".repeat(2)));
        } else if (dataPoint >= 81 && dataPoint <= 90) {
            dataText.set(new StringBuilder().append("&4").append("⚫".repeat(9)).append("&7").append("⚫"));
        } else if (dataPoint >= 91 && dataPoint <= 100) {
            dataText.set(new StringBuilder().append("&4").append("⚫".repeat(10)));
        }

        return "&8&l|" + dataText + "&8&l|";
    }
}
