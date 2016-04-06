package uk.ac.qub.bookepos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matt Ralphson
 */
public class Login extends Activity {
        EditText name, password;
        String Name, Password;
        String NAME=null, PASSWORD=null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            name = (EditText) findViewById(R.id.main_name);
            password = (EditText) findViewById(R.id.main_password);
        }

        public void main_login(View v){
            Name = name.getText().toString();
            Password = password.getText().toString();
            BackGround b = new BackGround();
            b.execute(Name, Password);
        }

        class BackGround extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... params) {
                String name = params[0];
                String password = params[1];
                String data="";
                int tmp;

                try {
                    URL url = new URL("http://54.171.237.154/html/bepos/login.php/login.php");
                    String urlParams = "name="+name+"&password="+password;

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    while((tmp=is.read())!=-1){
                        data+= (char)tmp;
                    }

                    is.close();
                    httpURLConnection.disconnect();

                    return data;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return "Exception: "+e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Exception: "+e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                String err=null;
                try {
                    JSONObject root = new JSONObject(s);
                    JSONObject user_data = root.getJSONObject("user_data");
                    NAME = user_data.getString("name");
                    PASSWORD = user_data.getString("password");
                } catch (JSONException e) {
                    e.printStackTrace();
                    err = "Exception: "+e.getMessage();

            }
        }
    }
}
