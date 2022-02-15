package dev.wirezcommon.minecraft.commands;

import dev.wirezcommon.minecraft.commands.types.DatabaseCommands;
import dev.wirezcommon.minecraft.commands.types.SystemCommands;
import dev.wirezcommon.minecraft.files.Lang;

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
