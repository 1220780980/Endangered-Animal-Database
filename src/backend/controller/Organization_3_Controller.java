package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Organizations;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Organization_3_Controller {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public Organization_3_Controller() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Organizations[] list() {
        ArrayList<Organizations> result = new ArrayList<Organizations>();

        try {
            String query = "SELECT * FROM ORGANIZATIONS_3";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Organizations model = new Organizations(null, rs.getString("NAME"),
                        null,rs.getString("FUNDEDBY"));
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
            String query = "INSERT INTO Organizations_3 VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getName());
            ps.setString(2, o.getFundedBy());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(String name) {
        try {

            String query = "SELECT * FROM Organizations_3 WHERE NAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, name);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String name) {
        try {
            String query = "DELETE FROM Organizations_3 WHERE NAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
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
            String query = "UPDATE Organizations_3 SET FUNDEDBY = ? WHERE NAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getFundedBy());
            ps.setString(2,o.getName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
