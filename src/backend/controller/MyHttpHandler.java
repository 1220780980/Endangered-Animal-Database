package src.backend.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import src.backend.database.DatabaseConnectionHandler;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHttpHandler implements HttpHandler {
    private Statement statement = null;

    public MyHttpHandler(Statement statement) {
        this.statement = statement;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();

        if (httpExchange.getRequestMethod().equals("POST")) {
            // Check the request path to determine what to do with sql
            if (path.equals("/insert.html")) {
                handleInsert(httpExchange);
            } else if (path.equals("/projection.html")) {
                handleProjection(httpExchange);
            } else if (path.equals("/delete.html")) {
                handleDelete(httpExchange);
            } else if (path.equals("/update.html")) {
                handleUpdate(httpExchange);
            } else if (path.equals("/selection.html")) {
                handleSelection(httpExchange);
            } else if (path.equals("/join.html")) {
                handleJoin(httpExchange);
            } else if (path.equals("/aggGroup.html")) {
                handleAggGroup(httpExchange);
            } else if (path.equals("/aggHaving.html")) {
                handleAggHaving(httpExchange);
            } else if (path.equals("/aggNest.html")) {
                handleAggNest(httpExchange);
            } else if (path.equals("/division.html")) {
                handleDivision(httpExchange);
            }
        } else {
            File file = new File("./src/ui" + path);
            handleGet(httpExchange, file);
        }
    }

    private String convertRequest(HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = requestBody.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }

    private void handleDivision(HttpExchange httpExchange) throws IOException {
        // Handle form from division.html
        String requestBodyString = convertRequest(httpExchange);

        // request from update
        StringBuilder response = new StringBuilder();

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String value = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("queryInput")) {
                    value = fieldValue;
                }
            }

            String selectSQL = null;
            if (Objects.equals(value, "animals")) {
                // column = what column you have in SELECT clause
                selectSQL = "SELECT EA.* FROM EndangeredAnimal EA WHERE NOT EXISTS " +
                        "(SELECT LE.Name, LE.Biome FROM LivingEnvironment LE WHERE NOT EXISTS " +
                        "(SELECT * FROM Live L WHERE L.ScientificName = EA.ScientificName AND L.EnvironmentName = LE.Name AND L.Biome = LE.Biome))";
            }

            // Perform query into sql
            String column = "ScientificName,CommonName,Type,Habitat,Appearance";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            String newSQL = "SELECT EA.* <br>FROM EndangeredAnimal EA <br>WHERE NOT EXISTS " +
                    "<br>(SELECT LE.Name, LE.Biome <br>FROM LivingEnvironment LE <br>WHERE NOT EXISTS " +
                    "(SELECT * <br>FROM Live L <br>WHERE L.ScientificName = EA.ScientificName AND L.EnvironmentName = LE.Name AND L.Biome = LE.Biome))";

            response.append("This is the query performed:<br>");
            response.append(newSQL);
            response.append("<br><br>");
            response.append("Query results:<br>");

            while (resultSet.next()) {
                for (String s : column.split(",")) {
                    response.append(resultSet.getObject(s)).append(" | ");
                }
                response.append("<br>");
            }
            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

        } catch (SQLException e) {
            // never happen
            response = new StringBuilder("Failed to execute query");
            httpExchange.sendResponseHeaders(400, response.toString().getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.toString().getBytes());
        out.flush();
        out.close();

    }

    private void handleAggNest(HttpExchange httpExchange) throws IOException {
        // Handle form from aggNest.html
        String requestBodyString = convertRequest(httpExchange);

        // request from update
        StringBuilder response = new StringBuilder();

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String op = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("operator")) {
                    op = fieldValue;
                }
            }

            String column = "PlaceIdentified,COUNT(*)";

            String selectSQL = "SELECT PlaceIdentified, COUNT(*) FROM Threats_2 t2 NATURAL JOIN Threats_1 t1 GROUP BY PlaceIdentified HAVING " +
                    "MAX(t1.ThreatSeverity)" + " " + op + " ALL (SELECT ThreatSeverity FROM Threats_1)";
//            System.out.println(selectSQL);

            // Perform query into sql
            ResultSet resultSet = statement.executeQuery(selectSQL);
            String newSQL = "SELECT PlaceIdentified, COUNT(*) <br>FROM Threats_2 t2 NATURAL JOIN Threats_1 t1 <br>GROUP BY PlaceIdentified <br>HAVING " +
                    "MAX(t1.ThreatSeverity)" + " " + op + " ALL (SELECT ThreatSeverity FROM Threats_1)";

            response.append("This is the query performed:<br>");
            response.append(newSQL);
            response.append("<br>");
            response.append("<br>");
            response.append("Query results:<br>");

            if (!resultSet.next()) {
                response.append("No data meet the condition");
            }
            while (resultSet.next()) {
                for (String s : column.split(",")) {
                    response.append(resultSet.getObject(s)).append(" | ");
                }
                response.append("<br>");
            }
            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

        } catch (SQLException e) {
            // never happen
            System.out.println(e);
            response = new StringBuilder("Failed to execute query");
            httpExchange.sendResponseHeaders(400, response.toString().getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.toString().getBytes());
        out.flush();
        out.close();
    }

    private void handleAggHaving(HttpExchange httpExchange) throws IOException {
        // Handle form from selection.html
        String requestBodyString = convertRequest(httpExchange);

        // request from update
        StringBuilder response = new StringBuilder();

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String table = "";
            String group = "";
            String column = "";
            String having = "";
            String op = "";
            String value = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("table")) {
                    table = fieldValue;
                } else if (fieldName.equals("attributeGroup")) {
                    group = fieldValue;
                } else if (fieldName.equals("columns")) {
                    column = fieldValue;
                } else if (fieldName.equals("attributeHaving")) {
                    having = fieldValue;
                } else if (fieldName.equals("operator")) {
                    op = fieldValue;
                } else if (fieldName.equals("value")) {
                    value = fieldValue;
                }
            }

            String selectSQL;
            if (having.equals("DescriptionOfThreat")) {
                selectSQL = "SELECT " + column + " FROM " + table + " GROUP BY " + group + " HAVING " + having + op + "'" + value + "'";
            } else {
                selectSQL = "SELECT " + column + " FROM " + table + " GROUP BY " + group + " HAVING " + having + op + value;
            }
            // Perform query into sql
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                for (String s : column.split(",")) {
                    response.append(resultSet.getObject(s)).append(" | ");
                }
                response.append("<br>");
            }
            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

        } catch (SQLException e) {
            // never happen
            response = new StringBuilder("Failed to execute query");
            System.out.println(e);
            httpExchange.sendResponseHeaders(400, response.toString().getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.toString().getBytes());
        out.flush();
        out.close();
    }

    private void handleAggGroup(HttpExchange httpExchange) throws IOException {
        // Handle form from selection.html
        String requestBodyString = convertRequest(httpExchange);

        // request from update
        StringBuilder response = new StringBuilder();

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String table = "";
            String attribute = "";
            String column = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("table")) {
                    table = fieldValue;
                } else if (fieldName.equals("attribute")) {
                    attribute = fieldValue;
                } else if (fieldName.equals("columns")) {
                    column = fieldValue;
                }
            }

            String selectSQL = "SELECT " + column + " FROM " + table + " GROUP BY " + attribute;
            // Perform query into sql
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                for (String s : column.split(",")) {
                    response.append(resultSet.getObject(s)).append(" | ");
                }
                response.append("<br>");
            }
            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

        } catch (SQLException e) {
            // never happen
            response = new StringBuilder("Failed to execute query");
            httpExchange.sendResponseHeaders(400, response.toString().getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.toString().getBytes());
        out.flush();
        out.close();
    }

    private void handleJoin(HttpExchange httpExchange) throws IOException {
        // Handle form from selection.html
        String requestBodyString = convertRequest(httpExchange);

        // request from update
        StringBuilder response = new StringBuilder();

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String table1 = "";
            String table2 = "";
            String attribute = "";
            String value = "";
            String column = "";
            String op = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("table1")) {
                    table1 = fieldValue;
                } else if (fieldName.equals("value")) {
                    value = fieldValue;
                } else if (fieldName.equals("attribute")) {
                    attribute = fieldValue;
                } else if (fieldName.equals("columns")) {
                    column = fieldValue;
                } else if (fieldName.equals("operator")) {
                    op = fieldValue;
                } else if (fieldName.equals("table2")) {
                    table2 = fieldValue;
                }
            }

            String selectSQL;
            if (column.split(",")[0].equals("All")) {
                selectSQL = "SELECT * FROM " + table1 + " NATURAL JOIN " + table2;
                column = column.replaceFirst("All,", "");
            } else {
                selectSQL = "SELECT " + column + " FROM " + table1 + " NATURAL JOIN " + table2;
            }
            if (attribute.equals("ThreatID") || attribute.equals("WorkerID")) {
                selectSQL = selectSQL + " WHERE " + attribute + op + value;
            } else {
                selectSQL = selectSQL + " WHERE " + attribute + op + "'" + value + "'";
            }

            // Perform query into sql
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                for (String s : column.split(",")) {
                    response.append(resultSet.getObject(s)).append(" | ");
                }
                response.append("<br>");
            }
            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

        } catch (SQLException e) {
            // never happen
            response = new StringBuilder("Failed to execute query");
            httpExchange.sendResponseHeaders(400, response.toString().getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.toString().getBytes());
        out.flush();
        out.close();
    }

    private void handleSelection(HttpExchange httpExchange) throws IOException {
        // Handle form from selection.html
        String requestBodyString = convertRequest(httpExchange);

        // request from update
        StringBuilder response = new StringBuilder();

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String table = "";
            String attribute = "";
            String value = "";
            String column = "";
            String op = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("table")) {
                    table = fieldValue;
                } else if (fieldName.equals("value")) {
                    value = fieldValue;
                } else if (fieldName.equals("attribute")) {
                    attribute = fieldValue;
                } else if (fieldName.equals("columns")) {
                    column = fieldValue;
                } else if (fieldName.equals("operator")) {
                    op = fieldValue;
                }
            }

            String selectSQL;
            if (column.split(",")[0].equals("All")) {
                selectSQL = "SELECT * FROM " + table;
                column = column.replaceFirst("All,", "");
            } else {
                selectSQL = "SELECT " + column + " FROM " + table;
            }
            selectSQL = selectSQL + " WHERE " + attribute + op + "'" + value + "'";

            // Perform query into sql
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                for (String s : column.split(",")) {
                    response.append(resultSet.getObject(s)).append(" | ");
                }
                response.append("<br>");
            }
            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

        } catch (SQLException e) {
            // never happen
            response = new StringBuilder("Failed to execute query");
            httpExchange.sendResponseHeaders(400, response.toString().getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.toString().getBytes());
        out.flush();
        out.close();
    }

    private void handleUpdate(HttpExchange httpExchange) throws IOException {
        // Handle form from update.html
        String requestBodyString = convertRequest(httpExchange);

        if (requestBodyString.contains("name=\"attributes\"")) {
            projectionHelper(httpExchange, requestBodyString);
        } else {
            // request from update
            String response;

            try {
                // Get the values that should be passed to sql
                Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
                Matcher matcher = pattern.matcher(requestBodyString);

                String table = "";
                String attribute = "";
                String value = "";
                String newValue = "";
                String allAttributes = "";

                while (matcher.find()) {
                    String fieldName = matcher.group(1);
                    String fieldValue = matcher.group(2);

                    if (fieldName.equals("table")) {
                        table = fieldValue;
                    } else if (fieldName.equals("values")) {
                        value = fieldValue;
                    } else if (fieldName.equals("attributeInput")) {
                        attribute = fieldValue;
                    } else if (fieldName.equals("newValue")) {
                        newValue = fieldValue;
                    } else if (fieldName.equals("allAttributes")) {
                        allAttributes = fieldValue;
                    }
                }

                String[] valueList = value.split("\\|");
                String[] newValueList = Arrays.stream(valueList).filter(str -> !str.isEmpty()).toArray(String[]::new);
                StringBuilder selectSQL = new StringBuilder("UPDATE " + table + " SET " + attribute + "='" + newValue + "' WHERE ");
                for (int i = 0; i < newValueList.length; ) {
                    selectSQL.append("(");
                    for (String a : allAttributes.split(",")) {
                        selectSQL.append(a).append("='").append(newValueList[i].trim()).append("' AND ");
                        i++;
                    }
                    selectSQL.delete(selectSQL.length() - 4, selectSQL.length());
                    selectSQL.append(") OR ");
                }
                String sql = selectSQL.toString();

                // Perform query into sql
                statement.executeUpdate(sql.substring(0, sql.length() - 4));
                response = "Successfully update data";
                httpExchange.sendResponseHeaders(200, response.getBytes().length);

            } catch (SQLException e) {
                if (e.getMessage().contains("ORA-02291") || e.getMessage().contains("ORA-02292")) {
                    // handle the foreign key constraint violation
                    response = "Foreign key constraint is violated";
                } else {
                    // handle the primary key unique constraint violation
                    response = "Primary key (Address) value is not unique";
                }
                httpExchange.sendResponseHeaders(400, response.getBytes().length);
            }

            // Send a response
            OutputStream out = httpExchange.getResponseBody();
            out.write(response.getBytes());
            out.flush();
            out.close();
        }
    }


    private void handleDelete(HttpExchange httpExchange) throws IOException {
        // Handle form from delete.html
        String requestBodyString = convertRequest(httpExchange);

        if (requestBodyString.contains("name=\"attributes\"")) {
            projectionHelper(httpExchange, requestBodyString);
        } else {
            // request from delete
            String response;

            try {
                // Get the values that should be passed to sql
                Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
                Matcher matcher = pattern.matcher(requestBodyString);

                String table = "";
                String where = "";
                String attribute = "";

                while (matcher.find()) {
                    String fieldName = matcher.group(1);
                    String fieldValue = matcher.group(2);

                    if (fieldName.equals("table")) {
                        table = fieldValue;
                    } else if (fieldName.equals("where")) {
                        where = fieldValue;
                    } else if (fieldName.equals("attribute")) {
                        attribute = fieldValue;
                    }
                }

                String[] whereList = where.split("\\|");
                StringBuilder selectSQL = new StringBuilder("DELETE FROM " + table + " WHERE ");
                for (String s : whereList) {
                    try {
                        // if number, direct append
                        int num = Integer.parseInt(s.trim());
                        selectSQL.append(attribute).append("=").append(s.trim()).append(" OR ");
                    } catch (NumberFormatException e) {
                        // otherwise, add ""
                        selectSQL.append(attribute).append("='").append(s.trim()).append("'").append(" OR ");
                    }
                }
                String sql = selectSQL.toString();

                // Perform query into sql
                statement.executeUpdate(sql.substring(0, sql.length() - 4));
                response = "Successfully delete data";
                httpExchange.sendResponseHeaders(200, response.getBytes().length);

            } catch (SQLException e) {
                // never happen
                response = "Failed to execute query";
                httpExchange.sendResponseHeaders(400, response.getBytes().length);
            }

            // Send a response
            OutputStream out = httpExchange.getResponseBody();
            out.write(response.getBytes());
            out.flush();
            out.close();
        }
    }

    private void handleProjection(HttpExchange httpExchange) throws IOException {
        // Handle form from projection.html
        String requestBodyString = convertRequest(httpExchange);
        projectionHelper(httpExchange, requestBodyString);

    }

    private void projectionHelper(HttpExchange httpExchange, String requestBodyString) throws IOException {
        StringBuilder response = new StringBuilder();

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String table = "";
            String attributes = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("table")) {
                    table = fieldValue;
                } else if (fieldName.equals("attributes")) {
                    attributes = fieldValue;
                }
            }

            String selectSQL;
            if (attributes.split(",")[0].equals("All")) {
                selectSQL = "SELECT * FROM " + table;
                attributes = attributes.replaceFirst("All,", "");
            } else {
                selectSQL = "SELECT " + attributes + " FROM " + table;
            }

            // Perform query into sql
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                for (String s : attributes.split(",")) {
                    response.append(resultSet.getObject(s)).append(" | ");
                }
                response.append("<br>");
            }

            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

        } catch (SQLException | IOException e) {
            // never happen
            response = new StringBuilder("Failed to execute query");
            httpExchange.sendResponseHeaders(400, response.toString().getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.toString().getBytes());
        out.flush();
        out.close();
    }

    private void handleInsert(HttpExchange httpExchange) throws IOException {
        // Handle form from insert.html
        String requestBodyString = convertRequest(httpExchange);
        String response;

        try {
            // Get the values that should be passed to sql
            Pattern pattern = Pattern.compile("name=\"(.*?)\"\\s*\\n\\s*\\n(.*?)\\s*\\n");
            Matcher matcher = pattern.matcher(requestBodyString);

            String table = "";
            String value1 = "";
            String value2 = "";

            while (matcher.find()) {
                String fieldName = matcher.group(1);
                String fieldValue = matcher.group(2);

                if (fieldName.equals("table")) {
                    table = fieldValue;
                } else if (fieldName.equals("value1")) {
                    value1 = fieldValue;
                } else if (fieldName.equals("value2")) {
                    value2 = fieldValue;
                }
            }

            // Insert data into sql
            String insertSQL = "INSERT INTO " + table + " VALUES (" + "'" + value1 + "', '" + value2 + "')";
            statement.executeUpdate(insertSQL);
            response = "Successfully insert data";
            httpExchange.sendResponseHeaders(200, response.getBytes().length);

        } catch (SQLException e) {
            if (e.getMessage().contains("ORA-02291")) {
                // handle the foreign key constraint violation
                response = "Foreign key constraint is violated";
            } else {
                // handle the primary key unique constraint violation
                response = "Primary key (Address) value is not unique";
            }
            httpExchange.sendResponseHeaders(400, response.getBytes().length);
        }

        // Send a response
        OutputStream out = httpExchange.getResponseBody();
        out.write(response.getBytes());
        out.flush();
        out.close();
    }

    // handle GET method, set up the web
    private void handleGet(HttpExchange httpExchange, File file) throws IOException {
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(bytes);
        in.close();

        httpExchange.sendResponseHeaders(200, bytes.length);

        OutputStream out = httpExchange.getResponseBody();
        out.write(bytes);
        out.close();
    }
}
