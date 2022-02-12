package dev.wirezcommon.core.mysql.other;

import java.sql.Connection;

public interface ConnectionType {

    Connection getConnection(String player, String db);
}
