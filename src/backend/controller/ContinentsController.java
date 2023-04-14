package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Continents;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContinentsController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public ContinentsController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Continents[] list() {
        ArrayList<Continents> result = new ArrayList<Continents>();

        try {
            String query = "SELECT * FROM CONTINENTS";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Continents model = new Continents(
                        rs.getString("NAME"),
                        rs.getInt("NUMBEROFCOUNTRIES"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Continents[result.size()]);}

    public void insert(Continents o) {
        try {
            String query = "INSERT INTO CONTINENTS VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getName());
            ps.setInt(2, o.getNumberOfCountries());
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

            String query = "SELECT * FROM CONTINENTS WHERE NAME = ?";
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
            String query = "DELETE FROM CONTINENTS WHERE NAME = ?";
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

    public void update(Continents o) {
        try {
            String query = "UPDATE CONTINENTS SET NAME = ?, NUMBEROFCOUNTRIES = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getName());
            ps.setInt(2,o.getNumberOfCountries());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
