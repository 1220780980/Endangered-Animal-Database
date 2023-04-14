package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Affect;
import src.backend.model.Predator;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PredatorController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public PredatorController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Predator[] list() {
        ArrayList<Predator> result = new ArrayList<Predator>();

        try {
            String query = "SELECT * FROM PREDATOR";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Predator model = new Predator(
                        rs.getInt("threatid"),
                        rs.getString("name")
                        );
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Predator[result.size()]);}

    public void insert(Predator o) {
        try {
            String query = "INSERT INTO PREDATOR VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, o.getThreatID());
            ps.setString(2, o.getName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(int id) {
        try {

            String query = "SELECT * FROM PREDATOR WHERE ThreatID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(int id) {
        try {
            String query = "DELETE FROM PREDATOR WHERE ThreatID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(Affect o) {
        try {
            String query = "UPDATE Affect SET EnvironmentName = ?, Biome = ?, DateLastAffected = ? WHERE ThreatID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getEnvironmentName());
            ps.setString(2,o.getBiome());
            ps.setDate(3,o.getDateLastAffected());
            ps.setInt(4,o.getThreatID());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
