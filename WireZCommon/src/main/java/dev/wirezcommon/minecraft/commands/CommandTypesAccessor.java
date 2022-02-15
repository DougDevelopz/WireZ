package dev.wirezcommon.minecraft.commands;

import dev.wirezcommon.minecraft.commands.types.DatabaseCommands;
import dev.wirezcommon.minecraft.commands.types.SystemCommands;

public class CommandTypesAccessor {

    private static DatabaseCommands databaseCommandsInstance = null;
    private static SystemCommands systemCommandsInstance = null;

    public static DatabaseCommands getDatabaseCommandsInstance() {
        if (databaseCommandsInstance == null) databaseCommandsInstance = new DatabaseCommands();
        return databaseCommandsInstance;
    }

    public static SystemCommands getSystemCommandsInstance() {
        if (systemCommandsInstance == null) systemCommandsInstance = new SystemCommands();
        return systemCommandsInstance;
    }
}
