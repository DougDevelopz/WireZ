package dev.wirezbukkit.commands;

import dev.wirezbukkit.commands.database.*;
import dev.wirezbukkit.commands.systems.*;
import dev.wirezmc.commands.SubCommand;

public enum SubCommandTypes {

    CONNECT("connect", new DatabaseConnect()),
    DISCONNECT("disconnect", new DatabaseDisconnect()),
    DB_LIST("dblist", new ListConnectedDatabases()),
    DUMP_TABLE("dumptable", new DumpTable()),
    LIST_TABLES("listtables", new ListTables()),

    CPU("cpu", new CPUInfo()),
    MEMORY_INFO("memory", new MemoryInfo()),
    THREAD_INFO("threadinfo", new ServerThreadInfo()),
    THREAD_DUMP("threaddump", new ServerThreadDump()),
    HEAP_DUMP("heapdump", new HeapDumpSummary());

    private final String name;
    private final SubCommand subCommand;
    public static final SubCommandTypes[] CACHE = values();

    SubCommandTypes(String name, SubCommand subCommand) {
        this.name = name;
        this.subCommand = subCommand;
    }

    public static SubCommandTypes fromName(String name) {
        for (SubCommandTypes types : CACHE) {
            if (types.getName().equalsIgnoreCase(name)) {
                return types;
            }
        }
        return null;
    }

    public <T extends SubCommand> T getSubCommand(Class<T> clazz) {
        if (!clazz.isInstance(this.subCommand)) {
            try {
                throw new Exception(name() + " is not instance of " + clazz.getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clazz.cast(this.subCommand);
    }

    public String getName() {
        return name;
    }

    public SubCommand getSubCommand() {
        return subCommand;
    }
}

