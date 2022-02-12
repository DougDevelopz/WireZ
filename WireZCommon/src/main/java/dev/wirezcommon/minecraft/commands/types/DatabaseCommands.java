package dev.wirezcommon.minecraft.commands.types;

import com.zaxxer.hikari.metrics.PoolStats;
import dev.wirezcommon.core.module.AbstractModuleLoader;
import dev.wirezcommon.core.mysql.hikari.HikariAuthentication;
import dev.wirezcommon.core.mysql.hikari.MultiDataPoolSetup;
import dev.wirezcommon.core.mysql.other.SQLTypes;
import dev.wirezcommon.core.mysql.other.StatementAPI;
import dev.wirezcommon.minecraft.commands.CommandAction;
import dev.wirezcommon.minecraft.commands.ICommandSender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSetMetaData;

public class DatabaseCommands {

    private final MultiDataPoolSetup multiDataPoolSetup = MultiDataPoolSetup.grabInstance();

    /**
     * Initiates a connection to the specified database with provided arg data
     *
     * @param source   is the command source
     * @param args     represents arguments of the command
     * @param messages messages to be sent
     */
    public void initConnection(ICommandSender source, String[] args, String[] messages) {

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
        int timeout = 0;
        int pool = 0;
        try {
            timeout = Integer.parseInt(args[6]);
            pool = Integer.parseInt(args[7]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        multiDataPoolSetup.init(source, source.grabName(), message -> {
            source.sendMessage(messages[0]);
        }, messages[1], SQLTypes.MYSQL, new HikariAuthentication(host, port, database, username, password), timeout, pool);
    }

    /**
     * Initiates a disconnection to the specified database with the provided database
     *
     * @param source   is the command source
     * @param args     represents arguments of the command
     * @param messages messages to be sent
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
     * @param source   is the command source
     * @param messages messages to be sent
     * @param action   used to retrieve a list of active databases
     */
    public void grabListOfDatabases(ICommandSender source, String[] messages, CommandAction action) {
        if (multiDataPoolSetup.getPlayersCurrentDbs().isEmpty() || multiDataPoolSetup.getPlayersCurrentDbs().get(source.grabName()).isEmpty()) {
            source.sendMessage(messages[0]);
            return;
        }

        source.sendMessage(messages[1]);
        //To retrieve a list of active databases
        action.run(source);
    }

    /**
     * Initiates a list of active databases a specified user is currently connected to
     *
     * @param source   is the command source
     * @param args     represents arguments of the command
     * @param messages messages to be sent
     * @param action   used to retrieve a list of active databases
     */
    public void grabListOfTargetsDatabase(ICommandSender source, String[] args, String[] messages, CommandAction action) {
        if (multiDataPoolSetup.getPlayersCurrentDbs().isEmpty() || multiDataPoolSetup.getPlayersCurrentDbs().get(args[1]).isEmpty()) {
            source.sendMessage(messages[0]);
            return;
        }

        source.sendMessage(messages[1]);
        //To retrieve a list of active databases of a target
        action.run(source);
    }


    /**
     * Creates a detailed csv file containing of the specified table through the specified database
     *
     * @param source     is the command source
     * @param args       represents arguments of the command
     * @param messages   messages to be sent
     * @param dataFolder location of where to store this file dump
     */
    public synchronized void createDumpOfTable(ICommandSender source, String[] args, String[] messages, File dataFolder) {
        String name = source.grabName();
        String database = args[1];
        String table = args[2];
        String fileName = args[3];
        if (multiDataPoolSetup.isNotEstablished(source.grabName(), database)) {
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
                source.sendMessage(messages[1]);
                return rs;
            });
        });
    }
}

