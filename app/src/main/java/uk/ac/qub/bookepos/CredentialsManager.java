package uk.ac.qub.bookepos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Matt Ralphson on 10/04/16.
 */
public class CredentialsManager {
    private final String CREDENTIALS_KEY = "CREDENTIALS";
    private final String LOGGED_IN_USER_KEY = "LOGGED_IN_USER";
    private final String LOGGED_IN_USER_IS_ADMIN_KEY = "LOGGED_IN_USER_IS_ADMIN";
    private final SharedPreferences settings;

    public CredentialsManager(Context context) {
        settings = context.getSharedPreferences(CREDENTIALS_KEY, Context.MODE_PRIVATE);
    }

    public void setLoggedInUser(String user, Boolean isAdmin) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LOGGED_IN_USER_KEY, user);
        editor.putBoolean(LOGGED_IN_USER_IS_ADMIN_KEY, isAdmin);
        editor.commit();
    }

    public String getLoggedInUser() {
        return settings.getString(LOGGED_IN_USER_KEY, "");
    }

    public Boolean isLoggedInUserAdmin() {
        return settings.getBoolean(LOGGED_IN_USER_IS_ADMIN_KEY, false);
    }
}
