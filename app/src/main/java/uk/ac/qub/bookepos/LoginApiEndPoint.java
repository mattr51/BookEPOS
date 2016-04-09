package uk.ac.qub.bookepos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matt Ralphson on 09/04/16.
 */
public class LoginApiEndPoint extends ApiEndPoint {

    Context context;

    public LoginApiEndPoint(Context context) {
        this.context = context;
    }

    public String getEndPoint() {
        return domain + "/html/bepos/login.php";
    }

    public void handleResult(JSONObject result) {
        String user;
        int admin;
        try {
            JSONObject userObject = result.getJSONObject("user_data");
            user = userObject.getString("user");
            admin = userObject.getInt("admin");

            Intent i = new Intent(context, BookSearchActivity.class);
            i.putExtra("user", user);
            i.putExtra("admin", admin);
            context.startActivity(i);
        } catch (JSONException e) {
            Toast.makeText(context, "Could not log in due to invalid credentials", Toast.LENGTH_SHORT).show();
            // Start anyway
            context.startActivity(new Intent(context, BookSearchActivity.class));
            e.printStackTrace();
        }
    }
}
