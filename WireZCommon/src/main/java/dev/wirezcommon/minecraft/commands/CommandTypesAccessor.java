package dev.wirezcommon.minecraft.commands;

import dev.wirezcommon.minecraft.commands.types.DatabaseCommands;

public class CommandTypesAccessor {

    private static DatabaseCommands databaseCommandsInstance = null;

    public static DatabaseCommands getDatabaseCommandsInstance() {
        if (databaseCommandsInstance == null) databaseCommandsInstance = new DatabaseCommands();
        return databaseCommandsInstance;
    }
}
