package Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class JavaDBConnection {
    private Logger log = LoggerFactory.getLogger(JavaDBConnection.class);
    private Connection connection;

    private String url = System.getenv("JDBC_DATABASE_URL");
    {
        if (url == null) {
            log.info("Environment variable not found; using development setting instead");
            url = "jdbc:postgresql://127.0.0.1:5432/testdb";
        }
    }

    public JavaDBConnection() {
        log.info("Loading Driver");
        try {
            Class.forName("org.postgresql.Driver");
            log.info("Driver loaded");
        } catch (ClassNotFoundException e) {
            log.error("Driver not found");
            e.printStackTrace();
        }

        try {
            this.connection = DriverManager.getConnection(url);
            log.info("Database connected");
        } catch (SQLException e) {
            log.error("Connection error");
            e.printStackTrace();
        }
    }

    public String getAllUsers() throws SQLException, JSONException {
        Statement statement = this.connection.createStatement();
        String sql = "SELECT * FROM users ORDER BY id ASC";
        ResultSet results = statement.executeQuery(sql);

        return this.getJSON(this.processMultipleResults(results));
    }

    public String getSpecificUser(int id) throws SQLException, JSONException {
        Statement statement = this.connection.createStatement();
        String sql = String.format("SELECT * FROM users WHERE id = %d", id);
        ResultSet result = statement.executeQuery(sql);

        return this.getJSON(this.processSingleResult(result));
    }

    public String insertUser(String values) throws SQLException, JSONException {
        Statement statement = this.connection.createStatement();
        String sql = "INSERT INTO users (first_name, last_name, phone_number) VALUES" + values + "RETURNING *";

        ResultSet result = statement.executeQuery(sql);

        return this.getJSON(this.processSingleResult(result));
    }

    private DBUser processSingleResult(ResultSet result) throws SQLException {
        result.next();
        int id = result.getInt("id");
        String firstName = result.getString("first_name");
        String lastName = result.getString("last_name");
        String phoneName = result.getString("phone_number");
        DBUser user = new DBUser(id, firstName, lastName, phoneName);
        return user;
    }

    private DBUser[] processMultipleResults(ResultSet results) throws SQLException {
        ArrayList<DBUser> list = new ArrayList<>();

        while (results.next()) {
            int id = results.getInt("id");
            String firstName = results.getString("first_name");
            String lastName = results.getString("last_name");
            String phoneName = results.getString("phone_number");
            DBUser user = new DBUser(id, firstName, lastName, phoneName);
            list.add(user);
        }

        DBUser[] arr = {};
        return list.toArray(arr);
    }

    private String getJSON(DBUser user) throws JSONException {
        return user.getJSONObject().toString();
    }

    private String getJSON(DBUser[] users) throws JSONException {
        JSONArray array = new JSONArray();
        for (DBUser user: users) {
            array.put(user.getJSONObject());
        }

        return array.toString();
    }
}
