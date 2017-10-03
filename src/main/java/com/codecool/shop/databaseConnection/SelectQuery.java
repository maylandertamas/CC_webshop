package com.codecool.shop.databaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectQuery extends DatabaseConnection {
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
            if (where == null) {
                String query;
                PreparedStatement statement;
                statement = dbConnection.prepareStatement("SELECT ? FROM ?;");

                statement.setString(1,columns);
                statement.setString(2,tableName);
                System.out.println(statement);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                   System.out.println(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
