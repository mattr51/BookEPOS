package uk.ac.qub.bookepos;

import org.json.JSONObject;

/**
 * Created by Matt Ralphson on 12/04/16.
 */
public class InsertApiEndPoint extends ApiEndPoint {
    @Override
    String getEndPoint() {
        return domain + "insertItem.php?";
    }

    @Override
    void handleResult(JSONObject result) {
    }
}
