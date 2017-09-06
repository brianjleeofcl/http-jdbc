package ServerHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public User(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public User(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        String firstName = object.getString("first_name");
        String lastName = object.getString("last_name");
        String phoneNumber = object.getString("phone_number");
        new User(firstName, lastName, phoneNumber);
    }

    public String getSQLCommand() {
        return String.format("('%s', '%s', '$s')", firstName, lastName, phoneNumber);
    }
}
