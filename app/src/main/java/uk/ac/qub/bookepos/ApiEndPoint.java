package uk.ac.qub.bookepos;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Matt Ralphson on 09/04/16.
 * Facilitates creation or URL queries and translation of JSON responses into objects
 */
abstract class ApiEndPoint extends AsyncTask<HashMap<String, String>, String, String> {

    @Override
    protected String doInBackground(HashMap<String, String>... params) {
        String data = "";
        int tmp;
        HashMap<String, String> urlParameters = params[0];

        try {
            URL url = new URL(getEndPoint()+stringify(urlParameters));

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            String fullUrl = stringify(urlParameters);
            Log.i("ApiCall", fullUrl);
            os.write(fullUrl.getBytes());
            os.flush();
            os.close();

            InputStream is = httpURLConnection.getInputStream();
            while ((tmp = is.read()) != -1) {
                data += (char) tmp;
            }

            is.close();
            httpURLConnection.disconnect();

            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        try {
           String removedBOM  = s.substring(3);
            JSONObject root = new JSONObject(removedBOM);
            handleResult(root);
            Log.d("s", "onPostExecute: " + removedBOM);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("s", "onPostExecute: " );
        }
    }

    public String stringify(HashMap<String, String> urlParameters) {
        StringBuilder urlParametersString = new StringBuilder();
        Iterator<String> parameterKeys = urlParameters.keySet().iterator();
        while (parameterKeys.hasNext()) {
            String parameterKey = parameterKeys.next();
            urlParametersString.append(parameterKey + "=" + urlParameters.get(parameterKey) + "&");
        }
        return urlParametersString.toString();
    }

    protected String domain = "http://54.171.237.154/html/bepos/";

    abstract String getEndPoint();

    abstract void handleResult(JSONObject result);
}
