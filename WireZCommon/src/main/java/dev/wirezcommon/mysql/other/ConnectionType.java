package dev.wirezcommon.mysql.other;

import java.sql.Connection;

public interface ConnectionType {

    Connection getConnection(String player, String db);
}
