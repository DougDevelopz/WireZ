package dev.wirezmc.mysql;

import com.zaxxer.hikari.HikariDataSource;
import dev.wirezcommon.core.mysql.hikari.HikariAuthentication;
import dev.wirezcommon.core.mysql.hikari.HikariSetupTool;
import dev.wirezcommon.core.mysql.other.SQLTypes;
import dev.wirezcommon.core.mysql.other.StatementAPI;
import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.module.ModuleLoaderInfo;
import dev.wirezcommon.core.module.ModuleLoaderType;
import dev.wirezmc.commands.ICommandSender;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@ModuleLoaderInfo(name = "Multi Data Pool Setup", description = "Multi Data Pool allows the connection of multiple data sources in an easy way", type = ModuleLoaderType.INSTANCE)
public class MultiDataPoolSetup extends AbstractModuleLoader implements HikariSetupTool {

    private final Map<String, Map<String, HikariDataSource>> playersCurrentDbs = Collections.synchronizedMap(new LinkedHashMap<>());

    public void init(ICommandSender player, String playerName, String errorMessage, String connectMessage, SQLTypes types, HikariAuthentication authentication, int timeOut, int poolSize) {
        if (playersCurrentDbs.values().stream().map(Map::keySet).anyMatch(c -> c.contains(authentication.database()))) {
            player.sendMessage(errorMessage);

        } else {
            Map<String, HikariDataSource> playerDataSource = playersCurrentDbs.getOrDefault(playerName, Collections.synchronizedMap(new LinkedHashMap<>()));

            playerDataSource.put(authentication.database(), new HikariDataSource(getDataProperties(types, authentication, timeOut, poolSize)));

            playersCurrentDbs.put(playerName, playerDataSource);
            player.sendMessage(connectMessage);
        }

        AbstractModuleLoader.getModule(StatementAPI.class).ifPresent(statementAPI -> statementAPI.setType(this));
    }

    public boolean isNotEstablished(String name, String database) {
        return getPlayersCurrentDbs().isEmpty() || getDataSource(name, database) == null || isClosed(name, database);
    }


    public HikariDataSource getDataSource(String player, String database) {
        return playersCurrentDbs.get(player).get(database);
    }

    public void close(String name, String db) {
        getDataSource(name, db).close();
        playersCurrentDbs.get(name).remove(db);
        System.out.println("Disconnected");
    }

    public void closeForAll() {
        for (Collection<HikariDataSource> dataSources : playersCurrentDbs.values().stream().map(Map::values).toList()) {
            if (dataSources.isEmpty()) return;
            dataSources.forEach(HikariDataSource::close);
        }
    }

    public boolean isClosed(String player, String db) {
        return getDataSource(player, db).isClosed();
    }

    @Override
    public Connection getConnection(String player, String database) {
        try {
            return playersCurrentDbs.get(player).get(database).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, Map<String, HikariDataSource>> getPlayersCurrentDbs() {
        return playersCurrentDbs;
    }

    public static MultiDataPoolSetup grabInstance() {
        final AtomicReference<MultiDataPoolSetup> instance = new AtomicReference<>();
        AbstractModuleLoader.getModule(MultiDataPoolSetup.class).ifPresent(instance::set);
        return instance.get();
    }
}
