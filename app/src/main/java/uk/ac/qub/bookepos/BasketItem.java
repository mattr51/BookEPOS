package uk.ac.qub.bookepos;

/**
 * Created by Matt Ralphson on 10/04/16.
 */
public class BasketItem {
    private int quantity;
    private Book book;

    public BasketItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Book getBook() {
        return book;
    }
}
