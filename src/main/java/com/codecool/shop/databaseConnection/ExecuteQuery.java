package com.codecool.shop.databaseConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/** Execute queries which after connection setup in DatabaseConnection abstract class.*/
public class ExecuteQuery extends DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteQuery.class);

    /** Value - {@value}, HashMap to store data from database */
    private HashMap<String, ArrayList<String>> queryData = new HashMap<>();
    private PreparedStatement statement;

    /**
     * Constructor.
     * @param statement required which will be executed.
     * @throws IOException If output wrong.
     * @throws SQLException If statement is wrong.
     */
    public ExecuteQuery(PreparedStatement statement) throws IOException, SQLException {
        this.statement = statement;
    }

    /**This method is overridden (from DatabaseConnection abstract class).*/
    @Override
    public void action(){
        try {
                PreparedStatement statement = this.statement;
                ResultSet rs = statement.executeQuery();
            int numberOfColumns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                ArrayList<String> temporaryData = new ArrayList<>();
                for (int i = 1; i <= numberOfColumns; i++) {
                    temporaryData.add(rs.getString(i));
                }
                queryData.put(rs.getString(1), temporaryData);
            }
        } catch (SQLException e) {
            logger.error("SQL Error, something went wrong with the query: {}", e.fillInStackTrace());
            e.printStackTrace();
        }
    }

    /**
     * After running action we could get the data we asked through our query
     * @return a HashMap which is a class value.
     * Note: Process from {@link DatabaseConnection} should be ran first to fill this HashMap with data.
     */
    public HashMap<String, ArrayList<String>> getDatabaseData() {
        return queryData;
    }
}
