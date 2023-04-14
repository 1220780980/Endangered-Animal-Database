package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.model.Affect;
import src.backend.model.Predator;
import src.backend.model.WorkersWorkIn;
import src.backend.utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkersWorkInController {

    private DatabaseConnectionHandler dbHandler = null;
    private Connection connection;
    public WorkersWorkInController() {
        dbHandler = new DatabaseConnectionHandler();
        connection = dbHandler.getConnection();
    }

    public WorkersWorkIn[] list() {
        ArrayList<WorkersWorkIn> result = new ArrayList<WorkersWorkIn>();

        try {
            String query = "SELECT * FROM WORKERSWORKIN";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                WorkersWorkIn model = new WorkersWorkIn(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("Address")
                );
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result.toArray(new WorkersWorkIn[result.size()]);}

    public void insert(WorkersWorkIn o) {
        try {
            String query = "INSERT INTO WORKERSWORKIN VALUES (?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, o.getID());
            ps.setString(2, o.getName());
            ps.setString(3,o.getGender());
            ps.setString(4,o.getAddress());
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

            String query = "SELECT * FROM WORKERSWORKIN WHERE ID = ?";
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
            String query = "DELETE FROM WORKERSWORKIN WHERE ID = ?";
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

    public void update(WorkersWorkIn o) {
        try {
            String query = "UPDATE WORKERSWORKIN SET NAME = ?, GENDER = ?, ADDRESS = ? WHERE ID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, o.getName());
            ps.setString(2,o.getGender());
            ps.setString(3,o.getAddress());
            ps.setInt(4,o.getID());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbHandler.rollbackConnection();
        }

    }
}
