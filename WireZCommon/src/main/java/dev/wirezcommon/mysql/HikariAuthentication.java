package dev.wirezcommon.mysql;

public record HikariAuthentication(String host, int port, String database, String user, String password) {
}
