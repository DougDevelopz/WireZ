package dev.wirezbungee.commands;

import com.google.common.collect.Lists;
import dev.wirezmc.commands.SubCommand;

import java.util.List;

public class SubCommandRegistry {

    private static SubCommandRegistry instance = null;
    private final List<SubCommand> subCommandList = Lists.newArrayList();

    public static SubCommandRegistry getInstance() {
        if (instance == null) {
            instance = new SubCommandRegistry();
        }

        return instance;
    }

    public void registerCommands() {
        for (SubCommandTypes all : SubCommandTypes.CACHE) {
            subCommandList.add(all.getSubCommand());
        }
    }

    public List<SubCommand> getSubCommandList() {
        return subCommandList;
    }
}
