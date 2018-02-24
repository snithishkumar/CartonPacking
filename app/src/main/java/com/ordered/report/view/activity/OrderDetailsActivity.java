package com.ordered.report.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ordered.report.R;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.adapter.OrderDetailsListAdapter;
import com.ordered.report.view.fragment.CaptureCartonDetailsFragment;
import com.ordered.report.view.fragment.HomeFragment;
import com.ordered.report.view.fragment.ProductDetailsListFragment;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailsListAdapter.OrderDetailsClickListeners, CaptureCartonDetailsFragment.ShowFragment {

    private String orderGuid;
    private String totalNoOfCartons;
    private OrderedService orderedService;
   private  List<OrderDetailsListViewModel> orderDetailsListViewModels = new ArrayList<>();

    public final String LOG_TAG = OrderDetailsActivity.class.getSimpleName();

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
            orderGuid = getIntent().getStringExtra("orderGuid");
            totalNoOfCartons = getIntent().getStringExtra("totalNoOfCartons");
            orderedService = new OrderedService(this);
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in init",e);
        }

    }


    private void showFragment(){
        ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NO_OF_COTTON, totalNoOfCartons);
        bundle.putString(Constants.ORDER, orderGuid);
        productDetailsListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.order_details_container, productDetailsListFragment).addToBackStack(null).commit();

    }


    public  void showCaptureCartonDetailsFragment(int pos){
        CaptureCartonDetailsFragment captureCartonDetailsFragment = new CaptureCartonDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ORDER_DETAILS_POS, pos);
        captureCartonDetailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container,captureCartonDetailsFragment).addToBackStack(null).commit();
    }

    @Override
    public void orderDetailsListOnClick(int position) {
        showCaptureCartonDetailsFragment(position);
    }


    @Override
    public void viewFragment(int options) {
        ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, productDetailsListFragment).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public OrderedService getOrderedService() {
        return orderedService;
    }

    public String getOrderGuid() {
        return orderGuid;
    }

    public String getTotalNoOfCartons() {
        return totalNoOfCartons;
    }

    public List<OrderDetailsListViewModel> getOrderDetailsListViewModels() {
        return orderDetailsListViewModels;
    }
}
