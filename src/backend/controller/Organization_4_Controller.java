package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Organizations;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Organization_4_Controller {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public Organization_4_Controller() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Organizations[] list() {
        ArrayList<Organizations> result = new ArrayList<Organizations>();

        try {
            String query = "SELECT * FROM ORGANIZATIONS_4";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Organizations model = new Organizations(rs.getString("ADDRESS"), rs.getString("NAME"),
                        null,null);
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Organizations[result.size()]);}

    public void insert(Organizations o) {
        try {
            String query = "INSERT INTO Organizations_4 VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getAddress());
            ps.setString(2, o.getName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(String address) {
        try {

            String query = "SELECT * FROM Organizations_4 WHERE NAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, address);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String address) {
        try {
            String query = "DELETE FROM Organizations_4 WHERE NAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            String name = "1";
            ps.setString(1, name);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(Organizations o) {
        try {
            String query = "UPDATE Organizations_4 SET NAME = ? WHERE ADDRESS = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getName());
            ps.setString(2,o.getAddress());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
