package uk.ac.qub.bookepos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Matt Ralphson on 09/04/16.
 */
public class CheckoutApiEndPoint extends ApiEndPoint {

    @Override
    String getEndPoint() {
        return domain + "sell.php?";
    }

    public void checkout(BasketItem basketItem, String username) {
        HashMap<String, String> checkoutApiParameter = new HashMap<>();
        checkoutApiParameter.put("itemID", Integer.toString(basketItem.getBook().getItemId()));
        checkoutApiParameter.put("username", username);
        checkoutApiParameter.put("quantity", Integer.toString(basketItem.getQuantity()));
        checkoutApiParameter.put("price", Double.toString(basketItem.getBook().getPrice()));
        execute(checkoutApiParameter);
    }

    void handleResult(JSONObject object) {
    }
}
