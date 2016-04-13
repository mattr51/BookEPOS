package uk.ac.qub.bookepos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matt Ralphson
 */
public class CheckoutFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        Button clearBasketButton = (Button)view.findViewById(R.id.btn_clear_basket);
        clearBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasketManager basketManager = new BasketManager(getContext());
                basketManager.clearBasket();
                updateTotal();
            }
        });

        Button checkoutButton = (Button)view.findViewById(R.id.btn_checkout);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasketManager basketManager = new BasketManager(getContext());
                String total = new DecimalFormat("0.00").format(basketManager.getBasketTotal());
                int itemCount = basketManager.getBasketItemCount();
                basketManager.checkout();
                Toast.makeText(
                        getContext(),
                        "Sold " + itemCount + " item(s) for £" + total,
                        Toast.LENGTH_LONG).show();
                updateTotal();
            }
        });

        Button cashUpButton = (Button)view.findViewById(R.id.btCashUp);
        cashUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CashUpApiEndPoint cashUpApiEndPoint = new CashUpApiEndPoint(getContext());
                HashMap<String, String> urlParameters = new HashMap<>();
                cashUpApiEndPoint.execute(urlParameters);
            }

        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        updateTotal();
    }

    public void updateTotal() {
        BasketManager basketManager = new BasketManager(getContext());
        TextView totalText = (TextView) getView().findViewById(R.id.textTotal);
        String basketTotalString = "Basket Total £" + new DecimalFormat("0.00").format(basketManager.getBasketTotal());
        totalText.setText(basketTotalString);
    }




}
