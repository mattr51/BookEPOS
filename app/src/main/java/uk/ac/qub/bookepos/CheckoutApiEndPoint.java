package uk.ac.qub.bookepos;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matt Ralphson on 09/04/16.
 */
public class CheckoutApiEndPoint extends ApiEndPoint {

    @Override
    String getEndPoint() {
        return domain + "sell.php?";
    }

    void handleResult(JSONObject object) {

    }
}
