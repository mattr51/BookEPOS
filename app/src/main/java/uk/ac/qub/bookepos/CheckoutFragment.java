package uk.ac.qub.bookepos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
                ArrayList<BasketItem> basketItems = basketManager.getBasketItems();
                // Call the as of yet unimplemented CheckoutApiEndPoint method
                // CheckoutApiEndPoint checkout = new CheckoutApiEndPoint();
                // for basketItem in basketItems
                // new HashMap of basketItem args
                // checkout.execute(basketItemhashmap)
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
