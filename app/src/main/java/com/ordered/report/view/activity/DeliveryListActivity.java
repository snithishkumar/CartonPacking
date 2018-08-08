package com.ordered.report.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ordered.report.R;
import com.ordered.report.enumeration.Status;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.adapter.DeliveryOrderListAdapter;
import com.ordered.report.view.fragment.DeliveryCartonListFragment;
import com.ordered.report.view.fragment.DeliveryOrderCartonListFragment;
import com.ordered.report.view.fragment.DeliveryOrderListFragment;
import com.ordered.report.view.fragment.ShippingDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class DeliveryListActivity extends AppCompatActivity implements DeliveryOrderListAdapter.DeliveryOrderListAdapterCallBack{

    private OrderedService orderedService;
    private int deliveryId;
    private String currentView = null;
    private OrderEntity orderEntity = null;
    private List<Integer> selectedCartonList = new ArrayList<>();

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


    private void showCartonListFragment(){
        DeliveryCartonListFragment deliveryCartonListFragment = new DeliveryCartonListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.delivery_details_container, deliveryCartonListFragment).addToBackStack("shippingDetails").commit();
    }


    //

    public void onClick(View view){
        switch (view.getId()){
            case R.id.add_cartons:
                DeliveryOrderListFragment deliveryOrderListFragment = new DeliveryOrderListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.delivery_details_container, deliveryOrderListFragment).addToBackStack("shippingDetails").commit();
                break;
            case R.id.delivery_order_complete:
                orderedService.addDelivery(selectedCartonList,deliveryId, Status.IN_PROGRESS);
                finish();
                break;

            case R.id.add_order_cartons:
                deliveryOrderListFragment = new DeliveryOrderListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.delivery_details_container, deliveryOrderListFragment).addToBackStack("shippingDetails").commit();
                break;

            case R.id.move_report:
                if(selectedCartonList.size() > 0){
                    orderedService.addDelivery(selectedCartonList,deliveryId,Status.COMPLETED);
                    finish();
                }else{
                    Toast.makeText(this,"Carton list not selected.",Toast.LENGTH_LONG).show();
                    return;
                }


                break;
        }
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

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public List<Integer> getSelectedCartonList() {
        return selectedCartonList;
    }

    @Override
    public void showOrderList(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
        DeliveryOrderCartonListFragment deliveryOrderCartonListFragment = new DeliveryOrderCartonListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.delivery_details_container, deliveryOrderCartonListFragment).addToBackStack("shippingDetails").commit();
    }
}
