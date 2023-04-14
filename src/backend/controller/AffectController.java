package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Affect;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AffectController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public AffectController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Affect[] list() {
        ArrayList<Affect> result = new ArrayList<Affect>();

        try {
            String query = "SELECT * FROM AFFECT";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Affect model = new Affect(rs.getInt("threatid"),
                        rs.getString("environmentname"),
                        rs.getString("biome"),
                        rs.getDate("datelastaffected"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Affect[result.size()]);}

    public void insert(Affect o) {
        try {
            String query = "INSERT INTO AFFECT VALUES (?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, o.getThreatID());
            ps.setString(2, o.getEnvironmentName());
            ps.setString(3, o.getBiome());
            ps.setDate(4, o.getDateLastAffected());
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

            String query = "SELECT * FROM Affect WHERE ThreatID = ?";
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
            String query = "DELETE FROM Affect WHERE ThreatID = ?";
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
