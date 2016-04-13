package uk.ac.qub.bookepos;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matt Ralphson on 09/04/16.
 */
public class CashUpApiEndPoint extends ApiEndPoint {

    Context context;

    public CashUpApiEndPoint(Context context) {
        this.context = context;
    }

    public String getEndPoint() {
        return domain + "cashup.php?";
    }

    public void handleResult(JSONObject result) {
        String titles;
        int items;
        double income;
        try {
            JSONArray cashup = result.getJSONArray("cashup");
            JSONObject todaysTakings = cashup.getJSONObject(0);
            titles = todaysTakings.getString("titles");
            items = todaysTakings.getInt("items");
            income = todaysTakings.getDouble("income");
            Toast.makeText(context, "Today's income: £"+income+ "from "+items+" items sold", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            Toast.makeText(context, "Could not get figures for today...", Toast.LENGTH_SHORT).show();
            // Start anyway
            // context.startActivity(new Intent(context, BeposActivity.class));
            e.printStackTrace();
        }
    }
}