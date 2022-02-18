package dev.wirezmc.commands.types;

import dev.wirezcommon.module.AbstractModuleLoader;
import dev.wirezcommon.mysql.HikariAuthentication;
import dev.wirezmc.mysql.MultiDataPoolSetup;
import dev.wirezcommon.mysql.SQLTypes;
import dev.wirezmc.mysql.StatementAPI;
import dev.wirezcommon.promise.Promise;
import dev.wirezcommon.promise.PromiseGlobalExecutor;
import dev.wirezmc.util.ByteBinClient;
import dev.wirezmc.util.IAction;
import dev.wirezmc.commands.ICommandSender;

import java.io.*;
import java.sql.ResultSetMetaData;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseCommands {

    private final MultiDataPoolSetup multiDataPoolSetup = MultiDataPoolSetup.grabInstance();

    /**
     * Initiates a connection to the specified database with provided arg data
     */
    public void initConnection(ICommandSender source, String[] args, String[] messages) {

        final String host = args[1];
        final AtomicInteger port = new AtomicInteger();
        try {
            port.set(Integer.parseInt(args[2]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        final String database = args[3];
        final String username = args[4];
        final String password = args[5];
        final AtomicInteger timeout = new AtomicInteger();
        final AtomicInteger poolSize = new AtomicInteger();
        try {
            timeout.set(Integer.parseInt(args[6]));
            poolSize.set(Integer.parseInt(args[7]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        multiDataPoolSetup.init(source, source.grabName(), messages[0], messages[1], SQLTypes.MYSQL,
                new HikariAuthentication(host, port.get(), database, username, password), timeout.get(), poolSize.get());
    }

    /**
     * Initiates a disconnection to the specified database.
     */
    public void initDisconnection(ICommandSender source, String[] args, String[] messages) {
        final String name = source.grabName();
        final String database = args[1];
        if (multiDataPoolSetup.isNotEstablished(name, database)) {
            source.sendMessage(messages[0]);
            return;
        }

        multiDataPoolSetup.close(name, database);
        source.sendMessage(messages[1]);

    }


    /**
     * Initiates a list of active databases you are currently connected to
     *
     * @param action is a command action used to retrieve a list of active databases
     */
    public void grabListOfDatabases(ICommandSender source, String[] messages, IAction action) {
        if (multiDataPoolSetup.getPlayersCurrentDbs().isEmpty() || multiDataPoolSetup.getPlayersCurrentDbs().get(source.grabName()).isEmpty()) {
            source.sendMessage(messages[0]);
            return;
        }

        source.sendMessage(messages[1]);
        //To retrieve a list of active databases
        action.run();
    }

    /**
     * Initiates a list of active databases a specified user is currently connected to
     *
     * @param action is a command action used to retrieve a list of active databases a target uses.
     */
    public void grabListOfTargetsDatabase(ICommandSender source, String[] args, String[] messages, IAction action) {
        if (multiDataPoolSetup.getPlayersCurrentDbs().isEmpty() || multiDataPoolSetup.getPlayersCurrentDbs().get(args[1]).isEmpty()) {
            source.sendMessage(messages[0]);
            return;
        }

        source.sendMessage(messages[1]);
        //To retrieve a list of active databases a target is connected to
        action.run();
    }


    /**
     * Creates a detailed csv file containing of the specified table through the specified database
     *
     * @param dataFolder location of where to store this file dump
     */
    public synchronized void createDumpOfTable(ICommandSender source, String[] args, String[] messages, File dataFolder) {
        final String name = source.grabName();
        final String database = args[1];
        final String table = args[2];
        final String fileName = args[3];
        Promise.createNew().fulfillInAsync(() -> {
            if (multiDataPoolSetup.isNotEstablished(source.grabName(), database)) {
                source.sendMessage(messages[0]);
                return true;
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
                    source.sendMessage(messages[1]);
                    return rs;
                });
            });
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace);
    }

    /**
     * Generates a paste of listed tables from the specified database
     */
    public void showTables(ICommandSender source, String[] args, IAction requestAction) {
        final String name = source.grabName();
        final String database = args[1];
        final List<String> formatList = Collections.synchronizedList(new LinkedList<>());
        CompletableFuture.runAsync(() -> AbstractModuleLoader.getModule(StatementAPI.class).ifPresent(statementAPI -> {
            statementAPI.executeQuery(multiDataPoolSetup.getConnection(name, database), "SHOW TABLES", rs -> {
                while (rs.next()) {
                    formatList.add(rs.getRow() + ":" + rs.getString(1) + "\n");
                }
                return rs;
            });
        })).whenComplete((unused, throwable) -> Promise.createNew().fulfillInAsync(() -> {
            ByteBinClient.postRequest(formatList, requestAction);
            return true;
        }, PromiseGlobalExecutor.getGlobalExecutor()).onError(Throwable::printStackTrace));
    }
}

