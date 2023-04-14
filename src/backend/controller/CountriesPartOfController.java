package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.CountriesPartOf;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountriesPartOfController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public CountriesPartOfController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public CountriesPartOf[] list() {
        ArrayList<CountriesPartOf> result = new ArrayList<CountriesPartOf>();

        try {
            String query = "SELECT * FROM CountriesPartOf";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                CountriesPartOf model = new CountriesPartOf(
                        rs.getString("COUNTRIESNAME"),
                        rs.getString("CAPITAL"),
                        rs.getInt("POPULATION"),
                        rs.getString("OFFICIALLANGUGAGE"),
                        rs.getString("CONTINENT"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new CountriesPartOf[result.size()]);}

    public void insert(CountriesPartOf o) {
        try {
            String query = "INSERT INTO COUNTRIESPARTOF VALUES (?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getCountryName());
            ps.setString(2, o.getCapital());
            ps.setInt(3, o.getPopulation());
            ps.setString(4, o.getOfficialLanguage());
            ps.setString(5,o.getContinent());
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

            String query = "SELECT * FROM COUNTRIESPARTOF WHERE COUNTRYNAME = ?";
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
            String query = "DELETE FROM COUNTRIESPARTOF WHERE COUNTRYNAME = ?";
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

    public void update(CountriesPartOf o) {
        try {
            String query = "UPDATE COUNTRIESPARTOF SET CAPITAL = ?, POPULATION = ?, OFFICIALLANGUAGE = ? WHERE COUNTRYNAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getCountryName());
            ps.setString(2,o.getCapital());
            ps.setInt(3,o.getPopulation());
            ps.setString(4,o.getOfficialLanguage());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
