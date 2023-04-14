package src.backend.controller;

import src.backend.database.DatabaseConnectionHandler;
import src.backend.delegates.LoginWindowDelegate;
import src.ui.LoginWindow;

import java.io.*;
import java.sql.*;
import java.util.Arrays;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

/**
 * This is the main controller class that will start program. All other controllers in controller dir will handle individual functions.
 */
public class EndangerDB implements LoginWindowDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public EndangerDB() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    // convert reader to string
    private String convertReader(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringWriter stringWriter = new StringWriter();
        char[] buffer = new char[1024];
        int len;

        while ((len = bufferedReader.read(buffer)) != -1) {
            stringWriter.write(buffer, 0, len);
        }
        return stringWriter.toString();
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) throws SQLException, IOException {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            // loads SQL script databaseSetup.sql and excecutes all codes to create all tables and load in all inital
            // data
            Connection conn = dbHandler.getConnection();
            Statement statement = conn.createStatement();

            // remove all exists tables
            String[] myTables = {"Organizations_3", "ResponsibleFor", "Contains", "EndangeredIn", "Live", "Help",
                    "Predator", "Harm", "Affect", "Reduce", "Eat", "CountriesPartOf", "WorkersWorkIn",
                    "LivingEnvironment", "EndangeredAnimal", "Threats_2", "NaturalDisaster", "Food_2",
                    "Organizations_4", "Continents", "Threats_1", "Food_1", "Organizations_1"};
            for (String s : myTables) {
//                statement.executeUpdate("DROP TABLE " + s);
                try {
                    statement.executeUpdate("DROP TABLE " + s);
//                    System.out.println(s);
                } catch (Exception e) {
                    // do nothing
//                    System.out.println(e);
                }
            }

            // run all scripts in databasetSetup.sql
            Reader reader = new BufferedReader(new FileReader("src/backend/scripts/databaseSetup.sql"));
            String allSQL = convertReader(reader);
            String[] SQLList = allSQL.split(";");
            String[] newArray = Arrays.copyOf(SQLList, SQLList.length - 1);
            for (String s : newArray) {
                statement.executeUpdate(s);
            }

            // set up the http server
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", new MyHttpHandler(statement));
            server.setExecutor(null);
            server.start();

//            statement.close();
//            conn.close();

            // not using terminal -> to implement UI interface.
//            TerminalTransactions transaction = new TerminalTransactions();
//            transaction.setupDatabase(this);
//            transaction.showMainMenu(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Insert a branch with the given info
     */
//    public void insert(T t) {
//        dbHandler.insertBranch(model);
//    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Delete branch with given branch ID.
     */
//    public void deleteBranch(int branchId) {
//        dbHandler.deleteBranch(branchId);
//    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Update the branch name for a specific ID
     */

//    public void updateBranch(int branchId, String name) {
//        dbHandler.updateBranch(branchId, name);
//    }

    /**
     * TermainalTransactionsDelegate Implementation
     *
     * Displays information about varies bank branches.
     */
//    public void showBranch() {
//        BranchModel[] models = dbHandler.getBranchInfo();
//
//        for (int i = 0; i < models.length; i++) {
//            BranchModel model = models[i];
//
//            // simplified output formatting; truncation may occur
//            System.out.printf("%-10.10s", model.getId());
//            System.out.printf("%-20.20s", model.getName());
//            if (model.getAddress() == null) {
//                System.out.printf("%-20.20s", " ");
//            } else {
//                System.out.printf("%-20.20s", model.getAddress());
//            }
//            System.out.printf("%-15.15s", model.getCity());
//            if (model.getPhoneNumber() == 0) {
//                System.out.printf("%-15.15s", " ");
//            } else {
//                System.out.printf("%-15.15s", model.getPhoneNumber());
//            }
//
//            System.out.println();
//        }
//    }

    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
//    public void terminalTransactionsFinished() {
//        dbHandler.close();
//        dbHandler = null;
//
//        System.exit(0);
//    }

    /**
     * TerminalTransactionsDelegate Implementation
     *
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
//    public void databaseSetup() {
//        dbHandler.databaseSetup();;
//
//    }

    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        EndangerDB endangerDB = new EndangerDB();
        endangerDB.start();
    }
}