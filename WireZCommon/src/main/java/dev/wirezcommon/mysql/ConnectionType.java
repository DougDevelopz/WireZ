package dev.wirezcommon.mysql;

import java.sql.Connection;

public interface ConnectionType {

    Connection getConnection(String player, String db);

    Connection getConnection();

}
