package Database;

import org.json.JSONException;
import org.json.JSONObject;

public class DBUser {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public DBUser(int id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("first_name", firstName);
        obj.put("last_name", lastName);
        obj.put("phone_number", phoneNumber);
        return obj;
    }
}
