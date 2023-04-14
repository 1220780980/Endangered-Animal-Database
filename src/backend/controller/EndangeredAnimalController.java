package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Affect;
import src.backend.model.EndangeredAnimal;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EndangeredAnimalController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public EndangeredAnimalController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public EndangeredAnimal[] list() {
        ArrayList<EndangeredAnimal> result = new ArrayList<EndangeredAnimal>();

        try {
            String query = "SELECT * FROM ENDANGEREDANIMAL";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                EndangeredAnimal model = new EndangeredAnimal(rs.getString("SCIENTIFICNAME"),
                        rs.getString("COMMONNAME"),
                        rs.getString("TYPE"),
                        rs.getString("HABITAT"),
                        rs.getString("APPEARANCE"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new EndangeredAnimal[result.size()]);}

    public void insert(EndangeredAnimal o) {
        try {
            String query = "INSERT INTO ENDANGEREDANIMAL VALUES (?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getScientificName());
            ps.setString(2, o.getCommonName());
            ps.setString(3, o.getType());
            ps.setString(4, o.getHabitat());
            ps.setString(5, o.getAppearance());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(String scientificName) {
        try {
            String query = "SELECT * FROM ENDANGEREDANIMAL WHERE SCIENTIFICNAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, scientificName);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String scientificName) {
        try {
            String query = "DELETE FROM ENDANGEREDANIMAL WHERE SCIENTIFICNAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, scientificName);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(EndangeredAnimal o) {
        try {
            String query = "UPDATE ENDANGEREDANIMAL SET COMMONNAME = ?, TYPE = ?, HABITAT = ?, APPEARANCE = ? WHERE SCIENTIFICNAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getCommonName());
            ps.setString(2,o.getType());
            ps.setString(3,o.getHabitat());
            ps.setString(4,o.getAppearance());
            ps.setString(5,o.getScientificName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
