package uk.ac.qub.bookepos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by Matt Ralphson
 */
public class LoginActivity extends Activity {
    EditText name, password;
    String Name, Password;
    String USER=null;
    int ADMIN = 0;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.main_name);
        password = (EditText) findViewById(R.id.main_password);
    }

    public void main_login(View v){
        Name = name.getText().toString();
        Password = password.getText().toString();
        ApiEndPoint b = new LoginApiEndPoint(this);
        HashMap<String, String> urlParameters = new HashMap<>();
        urlParameters.put("name", Name);
        urlParameters.put("password", Password);
        b.execute(urlParameters);
    }
}