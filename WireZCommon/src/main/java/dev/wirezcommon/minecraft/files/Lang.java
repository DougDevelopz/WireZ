package dev.wirezcommon.minecraft.files;

public enum Lang {

    PREFIX("prefix", "&7[&6&lWireZ&7] "),
    NO_PERMISSION("no-permission", "&cYou do not have permission to perform this action!"),
    PLAYER_NULL("player-null", "&cThat player does not exist!"),

    //Database Command Messgaes
    CONNECT_TO_DB_DESC("connect-to-db-desc", "&7Allows you to connect to a database!"),
    CONNECT_TO_DB_SYN("connect-to-db-syn", "&f/wirez connect <host> <port> <database> <user> <password>"),
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
    LISTED_DATABASES_SET("listed-connected-databases", "&f%database%");

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
