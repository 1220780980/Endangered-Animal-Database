package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.CountriesPartOf;
import src.backend.model.Eat;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EatController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public EatController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Eat[] list() {
        ArrayList<Eat> result = new ArrayList<Eat>();

        try {
            String query = "SELECT * FROM EAT";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Eat model = new Eat(
                        rs.getString("FOODNAME"),
                        rs.getString("SCIENTIFICNAME"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Eat[result.size()]);}

    public void insert(Eat o) {
        try {
            String query = "INSERT INTO EAT VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getFoodName());
            ps.setString(2, o.getScientificName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(String foodName, String sciName) {
        try {

            String query = "SELECT * FROM EAT WHERE FOODNAME = ? and SCIENTIFICNAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, foodName);
            ps.setString(2,sciName);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(String foodName, String sciName) {
        try {
            String query = "DELETE FROM EAT WHERE FOODNAME = ? and SCIENTIFICNAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, foodName);
            ps.setString(2, sciName);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(CountriesPartOf o) {
//        try {
//            String query = "UPDATE COUNTRIESPARTOF SET CAPITAL = ?, POPULATION = ?, OFFICIALLANGUAGE = ? WHERE COUNTRYNAME = ?";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.setString(1, o.getCountryName());
//            ps.setString(2,o.getCapital());
//            ps.setInt(3,o.getPopulation());
//            ps.setString(4,o.getOfficialLanguage());
//            ps.executeUpdate();
//            connection.commit();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            dbHandler.rollbackConnection();
//        }

    }
}
