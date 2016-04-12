package uk.ac.qub.bookepos;

import org.json.JSONObject;

/**
 * Created by Matt Ralphson on 12/04/16.
 */
public class StockApiEndPoint extends ApiEndPoint {
    @Override
    String getEndPoint() {
        return domain + "update.php?";
    }

    @Override
    void handleResult(JSONObject result) {
    }
}
