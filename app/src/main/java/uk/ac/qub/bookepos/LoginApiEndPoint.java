package uk.ac.qub.bookepos;

import android.content.Context;
import android.content.Intent;
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
        return domain + "login.php?";
    }

    public void handleResult(JSONObject result) {
        String user;
        int admin;
        try {
            JSONArray user_data = result.getJSONArray("user_data");
            JSONObject user_details = user_data.getJSONObject(0);
            user = user_details.getString("user");
            admin = user_details.getInt("admin");

            //testing that user is returned
            Toast.makeText(context, "user returned as"+result, Toast.LENGTH_SHORT).show();

            CredentialsManager credentialsManager = new CredentialsManager(context);
            credentialsManager.setLoggedInUser(user, admin == 1);

            Intent i = new Intent(context, BeposActivity.class);
            i.putExtra("user", user);
            i.putExtra("admin", admin);
            context.startActivity(i);
        } catch (JSONException e) {
            Toast.makeText(context, "Could not log in due to invalid credentials", Toast.LENGTH_SHORT).show();
            // Start anyway
            context.startActivity(new Intent(context, BeposActivity.class));
            e.printStackTrace();
        }
    }
}
