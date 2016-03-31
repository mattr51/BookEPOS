package uk.ac.qub.bookepos;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Matt Ralphson
 */

/**
 * BookClient contains methods to search openlibrary.org and return information on a book from a
 * string embedded into a url search query
 * Returns a json object
 */
public class BookClient {
    private static final String OPEN_LIB_URL = "http://openlibrary.org/";

    private AsyncHttpClient client;
    public BookClient() {

        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String appendToURL) {
        return OPEN_LIB_URL + appendToURL;
    }

    // Method for accessing the search API
    public void getBooks(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("search.json?q=");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Method for accessing additional information based on openLibraryID
    public void getExtraBookDetails(String openLibraryId, JsonHttpResponseHandler handler) {
        String url = getApiUrl("books/");
        client.get(url + openLibraryId + ".json", handler);
    }

    // Method for accessing additional information based on openLibraryID
    public void getBookFromISBN(String isbn, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("books/&bibkeys=ISBN:");
            client.get(url + URLEncoder.encode(isbn, "utf-8") + ".json", handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
