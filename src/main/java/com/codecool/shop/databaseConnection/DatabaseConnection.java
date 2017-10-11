package com.codecool.shop.databaseConnection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database Connection object.
 *
 * <P>Setting up connection to the database.
 *
 * <P>Note that {@link Logger} is used to log
 * See {@link #getConnection()} for more information.
 *
 * @author Tom
 * @version 1.0
 */

public abstract class  DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static Map<String,String> connectionData = readConnectionFileData();
    private static final String DATABASE = "jdbc:postgresql://"+ connectionData.get("url").trim() +"/"
                                            + connectionData.get("database").trim();
    private static final String DB_USER = connectionData.get("user");
    private static final String DB_PASSWORD = connectionData.get("password");


    /** Setup database connection through DriverManager
     * @throws SQLException If database connection details are wrong.
     * @return the setup connection */
    public static Connection getConnection() throws SQLException {
        logger.info("Database name was:{}, DB Username was: {}", DATABASE, DB_USER);
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    /**
     * Reading connection details from file.
     *
     * This method using {@link FileReader} and {@link java.io.BufferedReader} libraries.
     * The file opened with the FileReader and read line by line with BufferedReader.
     * @throws FileNotFoundException If file route is wrong or file does not exists.
     * @throws IOException if somethings goes wrong while reading file's lines.
     * @return A map with the connection details (key: detail's title, value: detail's data) as strings.
     */
    private static Map<String,String> readConnectionFileData() {
        Map<String, String> connectionData = new HashMap<>();

        FileReader readFile = null;

        try {
            readFile = new FileReader("connection_properties.txt");
        } catch (FileNotFoundException e) {
            logger.warn("File error: {}", e.fillInStackTrace());

        }

        BufferedReader buffReader = new BufferedReader(readFile);

        try {
            String line = null;
            while ((line = buffReader.readLine()) != null) {
                String temporaryArray[] = line.split(" ");
                connectionData.put(temporaryArray[0], temporaryArray[1]);
            }
            buffReader.close();
        } catch (IOException e) {
            logger.error("File reader error: {}", e.fillInStackTrace());
        }

        return connectionData;
    }


    /**
     * Connecting to database, run query, closing connection
     * @throws SQLException If the query was written wrong.
     */
    public void process() throws SQLException {
        Connection connectToDB = getConnection();
        logger.info("Connected to database successfully!");
        action();
        logger.info("Query executed successfully!");
        closeConnection(connectToDB);
        logger.info("Closed connection successfully!");
    }
    public abstract void action();

    /**
     * Close the actual connection after query executed in process method.
     * @param dbConnection Get's the db connection to close.
     * @throws SQLException If something wrong with the database connection.
     */
    public void closeConnection(Connection dbConnection) throws SQLException {
        dbConnection.close();

    }
}
