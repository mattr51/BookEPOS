package uk.ac.qub.bookepos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
    private Book book;
    private boolean instock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        // Fetch views
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        // Use the book to populate the data into our views
        this.book = (Book) getIntent().getSerializableExtra(BookSearchFragment.BOOK_DETAIL_KEY);
        instock = (boolean) getIntent().getExtras().getBoolean(BookSearchFragment.STOCK);
        loadBook(this.book);
    }

    // Populate data for the book
    private void loadBook(Book book) {
        //change activity title
        this.setTitle(book.getTitle());
        // Get Image - unneccessary probabl0y but it's here...
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

    /**
     * Adds item to basket with current price and quantity
     */
    public void addToBasket(View view) {
       // try{
      if (instock = true) {
            BasketManager basketManager = new BasketManager(getApplicationContext());
            EditText quantityEditText = (EditText) findViewById(R.id.etQuant);
         //   EditText priceEditText = (EditText) findViewById(R.id.etPrice);
            int quantity = Integer.parseInt(quantityEditText.getText().toString());
       //     int price = Integer.parseInt(quantityEditText.getText().toString());
            basketManager.addBookToBasket(this.book, quantity);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Not in stock - update stock instead", Toast.LENGTH_SHORT);
        }
    }
}