package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.LivingEnvironment;
import src.backend.model.Organizations;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LivingEnvironmentController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public LivingEnvironmentController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Organizations[] list() {
        ArrayList<LivingEnvironment> result = new ArrayList<LivingEnvironment>();

        try {
            String query = "SELECT * FROM LivingEnvironment";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                LivingEnvironment model = new LivingEnvironment(rs.getString("NAME"),rs.getString("Biome"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Organizations[result.size()]);}

    public void insert(LivingEnvironment o) {
        try {
            String query = "INSERT INTO LivingEnvironment VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getName());
            ps.setString(2, o.getBiome());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(String name, String biome) {
        try {

            String query = "SELECT * FROM LivingEnvironment WHERE NAME = ? and Biome = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, name);
            ps.setString(2,biome);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String name, String biome) {
        try {
            String query = "DELETE FROM LivingEnvironment WHERE NAME = ? and BIOME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, name);
            ps.setString(2,biome);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(LivingEnvironmentController o) {
//        try {
//            String query = "UPDATE Organizations_4 SET NAME = ? WHERE ADDRESS = ?";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.setString(1, o.getName());
//            ps.setString(2,o.getAddress());
//            ps.executeUpdate();
//            connection.commit();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            dbHandler.rollbackConnection();
//        }
    }
}
