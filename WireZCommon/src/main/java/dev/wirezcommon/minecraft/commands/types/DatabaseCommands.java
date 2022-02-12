package dev.wirezcommon.minecraft.commands.types;

import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.mysql.hikari.HikariAuthentication;
import dev.wirezcommon.core.mysql.hikari.MultiDataPoolSetup;
import dev.wirezcommon.core.mysql.other.SQLTypes;
import dev.wirezcommon.core.mysql.other.StatementAPI;
import dev.wirezcommon.minecraft.commands.CommandAction;
import dev.wirezcommon.minecraft.commands.CommandSender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSetMetaData;

public class DatabaseCommands {

    private final MultiDataPoolSetup multiDataPoolSetup = MultiDataPoolSetup.grabInstance();

    public void initConnection(CommandSender source, String[] args, String connectionMessage) {

        final String host = args[1];
        int port = 0;
        try {
            port = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        final String database = args[3];
        final String username = args[4];
        final String password = args[5];
        multiDataPoolSetup.init(source, source.grabName(), message -> {
            source.sendMessage(connectionMessage);
        }, SQLTypes.MYSQL, new HikariAuthentication(host, port, database, username, password), 5000, 10);
    }

    public void initDisconnection(CommandSender source, String[] args, String[] messages) {
        final String name = source.grabName();
        if (multiDataPoolSetup.getDataSource(name, args[1]) == null || multiDataPoolSetup.isClosed(name, args[1])) {
            source.sendMessage(messages[0]);
            return;
        }

        multiDataPoolSetup.close(name, args[1]);
        source.sendMessage(messages[1]);
    }

    public void grabListOfDatabases(CommandSender source, String[] messages, CommandAction action) {
        if (multiDataPoolSetup.getPlayersCurrentDbs().get(source.grabName()).isEmpty()) {
            source.sendMessage(messages[0]);
            return;
        }

        source.sendMessage(messages[1]);
        //To retrieve a list of active databases
        action.run(source);
    }

    public void grabListOfTargetsDatabase(CommandSender source, String[] args, String[] messages, CommandAction action) {
        if (multiDataPoolSetup.getPlayersCurrentDbs().get(args[1]).isEmpty()) {
            source.sendMessage(messages[0]);
            return;
        }

        source.sendMessage(messages[1]);
        //To retrieve a list of active databases for a target
        action.run(source);
    }


    public synchronized void createDumpOfTable(CommandSender source, String[] args, String[] messages, String dataFolder) {
        String name = source.grabName();
        String database = args[1];
        String table = args[2];
        String fileName = args[3];
        if (multiDataPoolSetup.getDataSource(name, database) == null || multiDataPoolSetup.isClosed(name, database)) {
            source.sendMessage(messages[0]);
            return;
        }

        File file = new File(dataFolder + File.separator + "dblogs", fileName + ".csv");
        try {
            if (!file.createNewFile()) {
                throw new RuntimeException("Could not create CSV");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractModuleLoader.getModule(StatementAPI.class).ifPresent(statementAPI -> {
            statementAPI.executeQuery(multiDataPoolSetup.getConnection(name, database), "SELECT * FROM " + table, rs -> {
                try (FileWriter fw = new FileWriter(file)) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int cols = rs.getMetaData().getColumnCount();

                    for (int i = 1; i <= cols; i++) {
                        fw.append(rsmd.getColumnLabel(i));
                        if (i < cols) fw.append(',');
                        else fw.append('\n');
                    }

                    while (rs.next()) {

                        for (int i = 1; i <= cols; i++) {
                            fw.append(rs.getString(i));
                            if (i < cols) fw.append(',');
                        }
                        fw.append('\n');
                    }
                    fw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return rs;
            });
        });
    }
}

