package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Contains;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContainsController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public ContainsController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Contains[] list() {
        ArrayList<Contains> result = new ArrayList<Contains>();

        try {
            String query = "SELECT * FROM CONTAINS";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Contains model = new Contains(rs.getString("COUNTRIESNAME"),
                        rs.getString("ENVIRONMENTNAME"),
                        rs.getString("BIOME"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Contains[result.size()]);}

    public void insert(Contains o) {
        try {
            String query = "INSERT INTO CONTAINS VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getCountriesName());
            ps.setString(2, o.getEnvironmentName());
            ps.setString(3, o.getBiome());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(String countriesName, String environmentName, String biome) {
        try {
            String query = "SELECT * FROM CONTAINS WHERE COUNTRIESNAME = ? and ENVIRONMENTNAME =? and BIOME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, countriesName);
            ps.setString(2, environmentName);
            ps.setString(3, biome);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String countriesName, String environmentName, String biome) {
        try {
            String query = "SELECT * FROM CONTAINS WHERE COUNTRIESNAME = ? and ENVIRONMENTNAME =? and BIOME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, countriesName);
            ps.setString(2, environmentName);
            ps.setString(3, biome);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(Contains o) {
//        try {
//            String query = "UPDATE CONTAINS SET EnvironmentName = ?, Biome = ?, DateLastAffected = ? WHERE ThreatID = ?";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.setString(1, o.getEnvironmentName());
//            ps.setString(2,o.getBiome());
//            ps.setDate(3,o.getDateLastAffected());
//            ps.setInt(4,o.getThreatID());
//            ps.executeUpdate();
//            connection.commit();
//            ps.close();
            // only has keys update is = to delete and add?
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            dbHandler.rollbackConnection();
//        }

    }
}
