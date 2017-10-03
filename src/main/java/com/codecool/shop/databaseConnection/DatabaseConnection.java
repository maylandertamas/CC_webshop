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

public abstract class  DatabaseConnection {

    private static Map<String,String> connectionData = readConnectonFileData();
    private static final String DATABASE = "jdbc:postgresql://"+ connectionData.get("url").trim() +"/"
                                            + connectionData.get("database").trim();
    private static final String DB_USER = connectionData.get("user");
    private static final String DB_PASSWORD = connectionData.get("password");

    public DatabaseConnection() throws IOException {
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private static Map<String,String> readConnectonFileData() {
        Map<String, String> connectionData = new HashMap<>();

        FileReader readFile = null;

        try {
            readFile = new FileReader("connection_properties.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return connectionData;
    }

    public void process() throws SQLException {
        Connection connectToDB = getConnection();
        action(connectToDB);
        closeConnection(connectToDB);
    }
    public abstract void action(Connection dbConnection);

    public void closeConnection(Connection dbConnection) throws SQLException {
        dbConnection.close();

    }
}
