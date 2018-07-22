package com.ordered.report.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ordered.report.R;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.fragment.DeliveryCartonListFragment;
import com.ordered.report.view.fragment.DeliveryOrderListFragment;
import com.ordered.report.view.fragment.ShippingDetailsFragment;

public class DeliveryListActivity extends AppCompatActivity {

    private OrderedService orderedService;
    private int deliveryId;
    private String currentView = null;

    public final String LOG_TAG = DeliveryListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_Details_toolbar);
        setSupportActionBar(toolbar);
        init();
        if(currentView.equals(Constants.VIEW_ADD_SHIPPING)){
            showShippingDetailsFragment();
        }else if(currentView.equals(Constants.VIEW_CARTON_LIST)){
            showCartonListFragment();
        }


    }


    private void init(){
        try{
            orderedService = new OrderedService(this);
            currentView = getIntent().getStringExtra("view");
            deliveryId = getIntent().getIntExtra("deliveryId",0);
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in init",e);
        }

    }


    private void showDeliveryOrderListFragment(){
        DeliveryOrderListFragment deliveryOrderListFragment = new DeliveryOrderListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.delivery_details_container, deliveryOrderListFragment).addToBackStack("shippingDetails").commit();
    }


    private void showCartonListFragment(){
        DeliveryCartonListFragment deliveryCartonListFragment = new DeliveryCartonListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.delivery_details_container, deliveryCartonListFragment).addToBackStack("shippingDetails").commit();
    }


    private void showShippingDetailsFragment(){
        ShippingDetailsFragment shippingDetailsFragment = new ShippingDetailsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.delivery_details_container, shippingDetailsFragment).addToBackStack("shippingDetails").commit();
    }

    public OrderedService getOrderedService() {
        return orderedService;
    }

    public int getDeliveryId() {
        return deliveryId;
    }
}
