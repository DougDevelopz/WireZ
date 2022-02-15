package dev.wirezmc.commands;

import dev.wirezmc.commands.types.DatabaseCommands;
import dev.wirezmc.commands.types.SystemCommands;

public abstract class SubCommand {

    public abstract String getSubCommandName();


    public abstract String getSubCommandDescription();

    public abstract String getSubCommandSyntax();

    public abstract void perform(Object sender, String[] args);

    protected DatabaseCommands getDatabaseCommandAccessorInstance() {
        return CommandTypesAccessor.getDatabaseCommandsInstance();
    }

    protected SystemCommands getSystemCommandAccessorInstance() {
        return CommandTypesAccessor.getSystemCommandsInstance();
    }
}
