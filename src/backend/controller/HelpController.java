package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Affect;
import src.backend.model.Help;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelpController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public HelpController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public Help[] list() {
        ArrayList<Help> result = new ArrayList<Help>();

        try {
            String query = "SELECT * FROM Help";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Help model = new Help(rs.getInt("WORKERID"),
                        rs.getString("ADDRESS"),
                        rs.getString("SCIENTIFICNAME"),
                        rs.getString("SERVICES"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new Help[result.size()]);}

    public void insert(Help o) {
        try {
            String query = "INSERT INTO HELP VALUES (?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, o.getWorkerID());
            ps.setString(2, o.getAddress());
            ps.setString(3, o.getScientificName());
            ps.setString(4, o.getServices());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void get(int id, String addy) {
        try {

            String query = "SELECT * FROM HELP WHERE WORKERID = ? and ADDRESS = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ps.setString(2,addy);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }
    }

    public void delete(int id, String addy) {
        try {
            String query = "DELETE FROM HELP WHERE WORKERID = ? and ADDRESS = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ps.setString(2,addy);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }


    }

    public void update(Help o) {
        try {
            String query = "UPDATE HELP SET SERVICES = ? WHERE WORKERID = ? and ADDRESS = ? and SCIENTIFICNAME = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getServices());
            ps.setInt(2,o.getWorkerID());
            ps.setString(3,o.getAddress());
            ps.setString(4,o.getScientificName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
