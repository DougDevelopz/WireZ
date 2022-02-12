package dev.wirezcommon.core.mysql.hikari;

public record HikariAuthentication(String host, int port, String database, String user, String password) {
}
