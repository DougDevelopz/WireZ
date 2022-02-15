package dev.wirezmc.platform;

public interface PlatformInfo {

    String getName();

    PlatformType getType();

    String getVersion();

    default String convertToString() {
        return String.valueOf(new PlatformRecord("Platform Name: " + getName() + "\n", "Platform Type: " + getType() + "\n",
                "Platform Version: " + getVersion()));
    }
}
