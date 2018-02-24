package com.ordered.report;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ordered.report.adapter.OrderDetailsListAdapter;
import com.ordered.report.fragment.AddProductFragment;
import com.ordered.report.fragment.CaptureCartonDetailsFragment;
import com.ordered.report.fragment.HomeFragment;
import com.ordered.report.fragment.PackingFragment;
import com.ordered.report.fragment.PackingProductDetailsListFragment;
import com.ordered.report.fragment.ProductDetailsListFragment;
import com.ordered.report.fragment.ProductListFragment;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OrderDetailsListAdapter.OrderDetailsClickListeners{

    private static final String TAG = HomeActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    private OrderedService orderedService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orderedService = new OrderedService(this);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showOrderedFragment(){
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    public  void showCaptureCartonDetailsFragment(int cartonNo){
        CaptureCartonDetailsFragment captureCartonDetailsFragment = new CaptureCartonDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.NO_OF_COTTON, cartonNo);
        captureCartonDetailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper,captureCartonDetailsFragment).addToBackStack(null).commit();
    }



    public void showPackingListFragment(){
        PackingFragment packingFragment = new PackingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper,packingFragment).addToBackStack(null).commit();
    }

    public void showProductList(int cartonNo,String order){
        ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.NO_OF_COTTON, cartonNo);
        bundle.putString(Constants.ORDER, order);
        productDetailsListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productDetailsListFragment).addToBackStack(null).commit();
    }



    public void showPackingProductDetailsList(String orderGuid){
        PackingProductDetailsListFragment packingProductDetails = new PackingProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        packingProductDetails.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, packingProductDetails).addToBackStack(null).commit();

    }
    public void showSubProductList(){
        ProductListFragment productListFragment = new ProductListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productListFragment).addToBackStack(null).commit();
    }
    public void showAddProductList(String orderGuid,int totalCotton){
        AddProductFragment addProductFragment = new AddProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        bundle.putInt(Constants.NO_OF_COTTON, totalCotton);
        addProductFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper,addProductFragment).addToBackStack(null).commit();
    }

    public void backClicked(View view){
        int size = getSupportFragmentManager().getBackStackEntryCount();
        if(size > 0){
            getSupportFragmentManager().popBackStack();
        }else{
            finish();
        }
    }

    public OrderedService getOrderedService() {
        return orderedService;
    }

    private List<OrderDetailsListViewModel> orderDetailsListViewModels = new ArrayList<>();
    private OrderDetailsListViewModel orderDetailsListViewModel;

    public List<OrderDetailsListViewModel> getOrderDetailsListViewModels() {
        return orderDetailsListViewModels;
    }

    public OrderDetailsListViewModel getOrderDetailsListViewModel(){
        return orderDetailsListViewModel;
    }

    public void setOrderDetailsListViewModel(OrderDetailsListViewModel orderDetailsListViewModel){
        this.orderDetailsListViewModel = orderDetailsListViewModel;
    }

    @Override
    public void orderDetailsListOnClick(OrderDetailsListViewModel productDetailsJson, int totalCartonCount) {
        setOrderDetailsListViewModel(productDetailsJson);
        showCaptureCartonDetailsFragment(totalCartonCount);
    }
}
