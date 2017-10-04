package com.codecool.shop.databaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectQuery extends DatabaseConnection {
    private HashMap<String, ArrayList<String>> queryData = new HashMap<>();
    private String tableName;
    private String where;
    private String columns;
    public SelectQuery(String tableName, String where, String columns) throws IOException {
        this.tableName = tableName;
        this.where = where;
        this.columns = columns;
    }

    @Override
    public void action(Connection dbConnection){
        try {
            ResultSet rs;
            if (where == null) {
                String query = "SELECT "+ columns +" FROM " + tableName +";";
                PreparedStatement statement = dbConnection.prepareStatement(query);
                rs = statement.executeQuery();

            } else {
                String query = "SELECT "+ columns +" FROM " + tableName + " WHERE id=?;";
                PreparedStatement statement = dbConnection.prepareStatement(query);
                statement.setInt(1, Integer.valueOf(where));
                rs = statement.executeQuery();
            }
            int numberOfColumns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                ArrayList<String> temporaryData = new ArrayList<>();
                for (int i = 1; i <= numberOfColumns; i++) {
                    temporaryData.add(rs.getString(i));
                }
                queryData.put(rs.getString(1), temporaryData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public HashMap<String, ArrayList<String>> getDatabaseData() {
        return queryData;
    }
}
