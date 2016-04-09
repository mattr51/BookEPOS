package uk.ac.qub.bookepos;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Matt Ralphson on 09/04/16.
 */
abstract class ApiEndPoint extends AsyncTask<HashMap<String, String>, String, String> {

    @Override
    protected String doInBackground(HashMap<String, String>... params) {

        String data = "";
        int tmp;

        HashMap<String, String> urlParameters = params[0];

        try {
            URL url = new URL(getEndPoint());

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(stringify(urlParameters).getBytes());
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
            String dataWithConnectedStrippedOut = s.substring(12);
            JSONObject root = new JSONObject(dataWithConnectedStrippedOut);
            handleResult(root);
        } catch (JSONException e) {
            e.printStackTrace();
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

    protected String domain = "http://54.171.237.154/";

    abstract String getEndPoint();

    abstract void handleResult(JSONObject result);
}