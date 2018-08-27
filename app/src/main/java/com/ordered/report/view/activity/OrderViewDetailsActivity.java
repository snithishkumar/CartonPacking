package com.ordered.report.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ordered.report.R;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.services.ServiceUtl;
import com.ordered.report.view.fragment.OrderViewProductNameListFragment;
import com.squareup.otto.Subscribe;

import java.util.List;

public class OrderViewDetailsActivity extends AppCompatActivity {

    private String orderGuid;
    private OrderedService orderedService;
    private OrderEntity orderEntity;
    private String cartonGuid;
    private String deliveryGuid;
    private int tabPosition;
    private int currentTabPosition;
    public final String LOG_TAG = OrderViewDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_Details_toolbar);
        setSupportActionBar(toolbar);
        init();
        showFragment();
    }

    /**
     * Initialize service layer
     */
    private void init(){
        try{
            ServiceUtl.requestSync(this);
            orderGuid = getIntent().getStringExtra("orderGuid");
            cartonGuid = getIntent().getStringExtra("cartonGuid");
            deliveryGuid = getIntent().getStringExtra("deliveryGuid");
            tabPosition = getIntent().getIntExtra("tabPosition",1);
            currentTabPosition = getIntent().getIntExtra("currentTabPosition",1);
            orderedService = new OrderedService(this);
            orderEntity = orderedService.getOrderEntityByGuid(orderGuid);
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in init",e);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, OrderViewActivity.class);
        intent.putExtra("orderGuid",orderEntity.getOrderGuid());
        intent.putExtra("tabPosition",tabPosition);
        intent.putExtra("currentTabPosition",currentTabPosition);
        startActivity(intent);
        finish();
    }

    private void showFragment(){
        OrderViewProductNameListFragment orderViewProductNameListFragment = new OrderViewProductNameListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.order_details_container, orderViewProductNameListFragment).addToBackStack("cartonList").commit();

    }

    public String getCartonGuid() {
        return cartonGuid;
    }

    public String getDeliveryGuid() {
        return deliveryGuid;
    }

    public String getOrderGuid() {
        return orderGuid;
    }

    public OrderedService getOrderedService() {
        return orderedService;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }
}
