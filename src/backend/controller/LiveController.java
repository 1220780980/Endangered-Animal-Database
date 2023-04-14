package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Affect;
import src.backend.model.Live;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LiveController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public LiveController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Live[] list() {
        ArrayList<Live> result = new ArrayList<Live>();

        try {
            String query = "SELECT * FROM LIVE";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Live model = new Live(rs.getString("SCIENTIFICNAME"),
                        rs.getString("ENVIRONMENTNAME"),
                        rs.getString("BIOME"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Live[result.size()]);}

    public void insert(Live o) {
        try {
            String query = "INSERT INTO LIVE VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getScientificName());
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

    public void get(String sName, String eName, String biome) {
        try {

            String query = "SELECT * FROM LIVE WHERE sName = ? and eName = ? and biome = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, sName);
            ps.setString(2,eName);
            ps.setString(3,biome);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String sName, String eName, String biome) {
        try {
            String query = "DELETE FROM Live WHERE sName = ? and eName = ? and biome = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, sName);
            ps.setString(2, eName);
            ps.setString(3, biome);            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(Live o) {
//        try {
//            String query = "UPDATE Affect SET EnvironmentName = ?, Biome = ?, DateLastAffected = ? WHERE ThreatID = ?";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.setString(1, o.getEnvironmentName());
//            ps.setString(2,o.getBiome());
//            ps.setDate(3,o.getDateLastAffected());
//            ps.setInt(4,o.getThreatID());
//            ps.executeUpdate();
//            connection.commit();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            dbHandler.rollbackConnection();
//        }
        // only table of primary keys

    }
}
