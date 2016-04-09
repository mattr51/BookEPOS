package uk.ac.qub.bookepos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Matt Ralphson
 */
public class Sales extends Activity {
    String user;
    int admin, refund;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales);
    }



}
