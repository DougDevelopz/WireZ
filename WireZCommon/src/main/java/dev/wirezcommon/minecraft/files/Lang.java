package dev.wirezcommon.minecraft.files;

public enum Lang {

    PREFIX("prefix", "&7[&6&lWireZ&7] "),
    NO_PERMISSION("no-permission", "&cYou do not have permission to perform this action!"),
    PLAYER_NULL("player-null", "&cThat player does not exist!"),


    PASTE_LOADING("paste-loading", "&7Paste is loading!"),

    //Database Command Messgaes
    CONNECT_TO_DB_DESC("connect-to-db-desc", "&7Allows you to connect to a database!"),
    CONNECT_TO_DB_SYN("connect-to-db-syn", "&f/wirez connect <host> <port> <database> <user> <password> <timeout> <poolSize>"),
    DATABASE_ALREADY_CONNECTED("db-already-connected", "&cThis database is already in use and connected from someone else!"),
    CONNECTED_TO_DB_SUCCESSFULLY("connected-to-db-successfully", "&7You have successfully made a connection!"),

    DISCONNECT_FROM_DB_DESC("disconnect-from-db-desc", "&7Allows you to disconnect from a database!"),
    DISCONNECT_FROM_DB_SYN("disconnect-from-db-syn", "&f/wirez disconnect <database>"),
    DATABASE_NOT_CONNECTED("db-not-connected", "&cThis database does not even have an established connection!"),
    DISCONNECTED_FROM_DB_SUCCESSFULLY("disconnected-from-db-successfully", "&7You have successfully disconnected from the specified datase!"),

    LISTED_DATABASES_DESC("listed-databases-desc", "&7Allows you to view the connected databases you're connected to!"),
    LISTED_DATABASES_SYN("listed-databases-syn", "&f/wirez dblist"),
    LISTED_DATABASES_EMPTY("listed-databases-empty", "&cYou are currently not connected to any databases at this time"),
    LISTED_DATABASES_TARGET_EMPTY("listed-databases-target-empty", "&cThis player is currently not connected to any databases!"),
    LISTED_DATABASES_INTRO("listed-connected-databases-intro", "&7Here is a list of databases you are currently connected to"),
    LISTED_DATABASES_TARGET_INTRO("listed-connected-databases-target-intro", "&7Here is a list of databases &f%player% &7is connected to:"),
    LISTED_DATABASES_SET("listed-connected-databases", "&f%database%"),

    DUMP_TABLE_DESC("dump-table-desc", "&7Allows you to print a specified table from a database to a csv file"),
    DUMP_TABLE_SYN("dump-table-syn", "&f/wirez dumptable <database> <table> <filename>"),
    DUMP_TABLE_SUCCESS("dump-table-success", "&aYou have successfully created a CSV file! Check the folder dblogs!"),

    LIST_TABLES_DESC("list-tables-desc", "&7Allows you to display an iteration of tables of a database!"),
    LIST_TABLES_SYN("list-tables-syn", "&f/wirez listtables <database> <paste_type>"),
    LIST_TABLES_COMPLETE("list-tables-complete", "&7Here is the link with the list of tables: &f%key%"),

    //Systems
    CPU_INFO_DESC("cpu-info-desc", "&7Allows you to view both processed, and system CPU information"),
    CPU_INFO_SYN("cpu-info-syn", "&f/wirez cpu"),
    CPU_INFO_HEADER("cpu-info-header", "&7CPU usage from last 10s, 1m, 15m"),
    CPU_INFO_SYSTEMS("cpu-info-systems", "&f%10s%%&7, &f%1m%%&7, &f%15m%% &8(systems)"),
    CPU_INFO_PROCESSED("cpu-info-processed", "&f%10s%%&7, &f%1m%%&7, &f%15m%% &8(processed)"),

    MEMORY_INFO_DESC("memory-info-desc", "&7Allows you to view both disk, and RAM information"),
    MEMORY_INFO_SYN("memory-info-syn", "&f/wirez memory"),
    MEMORY_INFO_HEADER("memory-info-header", "&7Memory for Disk and RAM"),
    DISK_INFO("disk-info", "&f%used% &7/ &f%total% &8(disk)"),
    RAM_INFO("ram-info", "&f%used% &7/ &f%total% &8(RAM)"),

    THREAD_INFO_DESC("thread-info-desc", "&7Allows you to view thread information"),
    THREAD_INFO_SYN("thread-info-syn", "&f/wirez thread"),
    THREAD_INFO_COMPLETE("thread-info-complete", "&7Here is the link with the thread information: &f%key%"),

    THREAD_DUMP_DESC("thread-dump-desc", "&7Allows you to perform thread dumps"),
    THREAD_DUMP_SYN("thread-dump-syn", "&f/wirez threaddump"),
    THREAD_DUMP_COMPLETE("thread-info-complete", "&7Here is the link with the thread information: &f%key%"),

    HEAP_DUMP_DESC("heap-dump-desc", "&7Allows you to perform heap dumps"),
    HEAP_DUMP_SYN("heap-dump-syn", "&f/wirez heapdump"),
    HEAP_DUMP_COMPLETE("heap-dump-complete", "&7Here is the link for the heap dump: &f%key%");

    public static final Lang[] CACHE = values();
    private final String path;
    private final String value;

    Lang(String path, String value) {
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

}
