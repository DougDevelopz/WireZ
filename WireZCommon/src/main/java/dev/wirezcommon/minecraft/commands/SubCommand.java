package dev.wirezcommon.minecraft.commands;

import dev.wirezcommon.minecraft.files.Lang;

public abstract class SubCommand {

    public abstract String getSubCommandName();


    public abstract String getSubCommandDescription();

    public abstract String getSubCommandSyntax();

    public abstract void perform(Object sender, String[] args);
}
