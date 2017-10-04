package com.codecool.shop.databaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExecuteQuery extends DatabaseConnection {

    private HashMap<String, ArrayList<String>> queryData = new HashMap<>();
    private PreparedStatement statement;

    public ExecuteQuery(PreparedStatement statement) throws IOException, SQLException {
        this.statement = statement;
    }

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
            e.printStackTrace();
        }
    }
    public HashMap<String, ArrayList<String>> getDatabaseData() {
        return queryData;
    }
}
