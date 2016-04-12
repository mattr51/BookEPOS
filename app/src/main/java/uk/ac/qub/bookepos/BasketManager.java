package uk.ac.qub.bookepos;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Matt Ralphsson on 10/04/16.
 */
public class BasketManager {

    private ArrayList<BasketItem> basketItems = new ArrayList<>();
    private Context context;

    public BasketManager(Context context) {
        this.context = context;
        deserialise();
    }

    public ArrayList<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void addBookToBasket(Book book, int quantity) {
        BasketItem basketItem = new BasketItem(book, quantity);
        boolean isItemInBasket = false;
        for (BasketItem inBasketItem : basketItems) {
            if (inBasketItem.getBook().getItemId() == book.getItemId()) {
                inBasketItem.setQuantity(inBasketItem.getQuantity() + quantity);
                isItemInBasket = true;
            }
        }
        if (!isItemInBasket)
            basketItems.add(basketItem);
        serialise();
    }

    public void clearBasket() {
        basketItems.clear();
        serialise();
    }

    public double getBasketTotal() {
        double total = 0;
        for (BasketItem basketItem : basketItems) {
            total += basketItem.getQuantity() * basketItem.getBook().getPrice();
        }
        return total;
    }

    private void serialise() {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput("basket.csv", Context.MODE_PRIVATE);
            for (BasketItem basketItem : basketItems) {
                String basketItemString = serialiseBook(basketItem.getBook()) + "|" + basketItem.getQuantity() + "\n";
                fos.write(basketItemString.getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serialiseBook(Book book) {
        String[] bookFields = new String[] {
                Integer.toString(book.getItemId()),
                book.getTitle(),
                book.getAuthor(),
                Double.toString(book.getPrice()) };
        return TextUtils.join("::", bookFields);
    }

    private Book deserialiseBook(String bookString) {
        String[] bookFields = bookString.split("::");
        int itemId = Integer.parseInt(bookFields[0]);
        String title = bookFields[1];
        String author = bookFields[2];
        Double price = Double.parseDouble(bookFields[3]);
        return new Book(itemId, title, author, price);
    }

    private void deserialise() {
        FileInputStream fos = null;
        try {
            fos = context.openFileInput("basket.csv");

            InputStreamReader streamReader = new InputStreamReader(fos);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null ) {
                if (line.isEmpty()) return;
                String[] bookAndQuantity = line.split(Pattern.quote("|"));
                String bookString = bookAndQuantity[0];
                Book book = deserialiseBook(bookString);
                String quantityString = bookAndQuantity[1];
                int quantity = Integer.parseInt(quantityString);
                basketItems.add(new BasketItem(book, quantity));
            }

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
