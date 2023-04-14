package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Affect;
import src.backend.model.EndangeredIn;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EndangeredInController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public EndangeredInController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public EndangeredIn[] list() {
        ArrayList<EndangeredIn> result = new ArrayList<EndangeredIn>();

        try {
            String query = "SELECT * FROM ENDANGEREDIN";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                EndangeredIn model = new EndangeredIn(
                        rs.getString("threatid"),
                        rs.getString("environmentname"),
                        rs.getString("biome"),
                        rs.getInt("population"),
                        rs.getDate("date"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new EndangeredIn[result.size()]);}

    public void insert(EndangeredIn o) {
        try {
            String query = "INSERT INTO ENDANGEREDIN VALUES (?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getScientificName());
            ps.setString(2, o.getCountries());
            ps.setString(3, o.getStatus());
            ps.setInt(4,o.getPopulation());
            ps.setDate(5, o.getDate());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(String name, String cname) {
        try {

            String query = "SELECT * FROM ENDANGEREDIN WHERE SCIENTIFICNAME = ? and COUNTRIES = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, name);
            ps.setString(2,cname);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String name, String cname) {
        try {
            String query = "DELETE FROM ENDANGEREDIN WHERE SCIENTIFICNAME = ? and COUNTRIES = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, name);
            ps.setString(2,cname);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(EndangeredIn o) {
        try {
            String query = "UPDATE ENDANGEREDIN SET STATUS = ?, POPULATION = ?, DATE = ? WHERE SCIENTIFICNAME = ? and COUNTRIES = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getStatus());
            ps.setInt(2,o.getPopulation());
            ps.setDate(3,o.getDate());
            ps.setString(4,o.getScientificName());
            ps.setString(5,o.getCountries());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
