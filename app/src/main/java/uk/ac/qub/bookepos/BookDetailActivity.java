package uk.ac.qub.bookepos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;
    private EditText etPrice;
    private EditText etQuant;
    private Button btUpdateStock;
    private Button btAddToBasket;
    private BookClient client;
    public String publishBy;
    private Book book;
    private boolean newItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        newItem = getIntent().getBooleanExtra(BookSearchFragment.NEWSTOCK,false);
        Log.v("newItem","variable is"+newItem);

        // Fetch views
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        etQuant = (EditText) findViewById(R.id.etQuant);
        etPrice = (EditText) findViewById(R.id.etPrice);
        btAddToBasket  = (Button) findViewById(R.id.btAddToBasket);
        // Use the book to populate the data into our views
        this.book = (Book) getIntent().getSerializableExtra(BookSearchFragment.BOOK_DETAIL_KEY);
        loadBook(this.book);

        etQuant.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        etQuant.addTextChangedListener(getQuantityChangeWatcher());
    }

    private TextWatcher getQuantityChangeWatcher() {
        return new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0 && tryParseInt(s.toString())) {

                    if (Integer.parseInt(s.toString()) >= 0)
                        btAddToBasket.setText("Add to Basket");
                    else
                        btAddToBasket.setText("Refund Basket");
                }
            }

            private boolean tryParseInt(String value) {
                try {
                    Integer.parseInt(value);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        };
    }

    // Populate data for the book
    private void loadBook(Book book) {
        //change activity title
        this.setTitle(book.getTitle());
        Picasso.with(this).load(Uri.parse(book.getLargeCoverUrl())).error(R.mipmap.ic_launcher).into(ivBookCover);
        // set title
        tvTitle.setText(book.getTitle());
        //set author
        tvAuthor.setText(book.getAuthor());
        // fetch extra book data from books API
        client = new BookClient();
        client.getExtraBookDetails(book.getOpenLibraryId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("publishers")) {
                        // display comma separated list of publishers
                        final JSONArray publisher = response.getJSONArray("publishers");
                        final int numPublishers = publisher.length();
                        final String[] publishers = new String[numPublishers];
                        for (int i = 0; i < numPublishers; ++i) {
                            publishers[i] = publisher.getString(i);
                        }
                        tvPublisher.setText(TextUtils.join(", ", publishers));
                        publishBy = tvPublisher.getText().toString();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

      /*  //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public void addToBasket(View view) {
        BasketManager basketManager = new BasketManager(getApplicationContext());
        int quantity = Integer.parseInt(etQuant.getText().toString());
        basketManager.addBookToBasket(this.book, quantity);
        String action = quantity >= 0 ? "Added " : "Refunded ";
        String message = action + quantity + " copies of " + this.book.getTitle() + " to the basket";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        finish();
    }

    public void updateStock(View view) {
        Log.v("newItem","variable is"+newItem);
        if(!newItem) {
            //Updates Current Stock
            StockApiEndPoint stockApiEndPoint = new StockApiEndPoint();
            HashMap<String, String> stockUpdate = new HashMap<>();

            stockUpdate.put("itemID", Integer.toString(this.book.getItemId()));

            String message = "Updated stock for " + this.book.getTitle();

            if (etPrice.getText().toString() != "") {
                stockUpdate.put("price", etPrice.getText().toString());
                message += " - price updated: " + etPrice.getText();
            }
            if (etQuant.getText().toString() != "") {
                stockUpdate.put("quantity", etQuant.getText().toString());
                message += " - quantity added: " + etQuant.getText();
            }

            stockApiEndPoint.execute(stockUpdate);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            finish();
        } else {
            InsertApiEndPoint insertApiEndPoint = new InsertApiEndPoint();
            HashMap<String, String> stockUpdate = new HashMap<>();

          //  stockUpdate.put("itemID", Integer.toString(this.book.getItemId()));

            String message = "Updated stock for " + this.book.getTitle();
            stockUpdate.put("title", this.book.getTitle());
            stockUpdate.put("publishedBy", this.book.getPublishedBy());
            stockUpdate.put("authors", this.book.getAuthor());
            stockUpdate.put("isbn", this.book.getIsbn());
            if (etPrice.getText().toString() != "") {
                stockUpdate.put("price", etPrice.getText().toString());
                message += " - price updated: " + etPrice.getText();
            }
            if (etQuant.getText().toString() != "") {
                stockUpdate.put("quantity", etQuant.getText().toString());
                message += " - quantity added: " + etQuant.getText();
            }
            CredentialsManager credentialsManager = new CredentialsManager(getApplicationContext());
            stockUpdate.put("username", credentialsManager.getLoggedInUser().toString());
            insertApiEndPoint.execute(stockUpdate);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            finish();
        }


    }
}