package uk.ac.qub.bookepos;

import android.support.v7.app.ActionBarActivity;

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class Sales extends AppCompatActivity {

/** from a very old version, might be somewhere to start? maybe easier to start again though.
 *

 private Toolbar toolbar;                              // Declaring the Toolbar Object


 @Override protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.sales);
 }


 public boolean onCreateOptionsMenu(Menu menu) {
 // Inflate the menu; this adds items to the action bar if it is present.
 getMenuInflater().inflate(R.menu.menu_book_list, menu);
 // If it in menu folder then use R.menu.main and if it in layout then use R.menu.main

 return true;
 }


 //add logic here for retrieving item ID
 public int itemID()
 { return this.itemID();
 }

 // array list for basket
 public void basket(itemID itemID) {

 }

 public float itemPrice() {
 return this.itemPrice();
 }

 public float basketBalance(){
 if (basket == null) {
 return 0;}
 else return this.basketBalance()+ (this.itemPrice * this.quantity);
 }



 public int quantity() {
 if (basket == null) {
 return 0;
 }
 }

 public void addItem (this.itemID){
 // insert logic for adding this .item ID to basket
 }

 public void removeItem (this.itemID){
 // insert logic for removing item from basket
 }

 */
}