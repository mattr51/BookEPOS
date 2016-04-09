package uk.ac.qub.bookepos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt Ralphson on 09/04/16.
 */
public class InventoryApiEndPoint extends ApiEndPoint {

    private ArrayList<Book> books;

    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    String getEndPoint() {
        return domain + "/html/bepos/inventory.php";
    }

    void handleResult(JSONObject object) {
        try {
            books = new ArrayList<Book>();

            JSONArray inventory = object.getJSONArray("inventory");
            for (int i = 0; i < inventory.length(); i++) {
                JSONObject inventoryItem = inventory.getJSONObject(i);
                Book book = new Book(
                        inventoryItem.getString("title"),
                        inventoryItem.getString("author"),
                        inventoryItem.getDouble("price"));
                books.add(book);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
