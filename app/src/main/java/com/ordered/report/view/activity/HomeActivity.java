package com.ordered.report.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ordered.report.R;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.adapter.DeliveryListAdapter;
import com.ordered.report.view.adapter.PackingListAdapter;
import com.ordered.report.view.fragment.AddProductFragment;
import com.ordered.report.view.fragment.DeliveryListFragment;
import com.ordered.report.view.fragment.HistoryFragment;
import com.ordered.report.view.fragment.OrderedFragment;
import com.ordered.report.view.fragment.PackingFragment;
import com.ordered.report.view.fragment.PackingProductDetailsListFragment;
import com.ordered.report.view.fragment.ProductDetailsListFragment;
import com.ordered.report.view.fragment.ProductListFragment;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements PackingListAdapter.PackingListAdapterCallBack, DeliveryListAdapter.DeliveryListAdapterCallBack{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ProgressDialog progressDialog = null;

    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private Toolbar mToolbar;
    private int tabPosition;

    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    private OrderedService orderedService;

    private static int ORDER_TAB_POS = 1;
    private static int PACKING_TAB_POS = 2;
    private static int DELIVERY_TAB_POS = 3;
    private static int HISTORY_TAB_POS = 4;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabPosition= getIntent().getIntExtra("currentTabPosition",0);
        viewPager.setCurrentItem(tabPosition);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        orderedService = new OrderedService(this);
//currentTabPosition


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    public void showOrderedFragment() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }








    public void showOrderDetailsList(String totalNoOfCartons,String orderGuid){
        tabPosition = ORDER_TAB_POS;
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("orderGuid",orderGuid);
        intent.putExtra("totalNoOfCartons",totalNoOfCartons);
        intent.putExtra("currentTabPosition",tabPosition);
        intent.putExtra("view",Constants.VIEW_ORDER);
        startActivity(intent);

    }


    private void showDeliveryDetailsActivity(String view,int deliveryId){
        tabPosition = DELIVERY_TAB_POS;
        Intent intent = new Intent(this, DeliveryListActivity.class);
        intent.putExtra("view",view);
        intent.putExtra("currentTabPosition",tabPosition);
        if(deliveryId > 0){
            intent.putExtra("deliveryId",deliveryId);
        }

        startActivity(intent);
    }



    private void showPackingDetailsList(String orderGuid,String newView){
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("orderGuid",orderGuid);
        intent.putExtra("view",Constants.VIEW_PACKING);
        if(newView != null){
            intent.putExtra("newView",newView);
        }
        tabPosition = PACKING_TAB_POS;
        startActivity(intent);
    }

    public void showProductList(int cartonNo, String order) {
        ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.NO_OF_COTTON, cartonNo);
        bundle.putString(Constants.ORDER, order);
        productDetailsListFragment.setArguments(bundle);
       // getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productDetailsListFragment).addToBackStack(null).commit();
    }


    public void showPackingProductDetailsList(String orderGuid) {
        PackingProductDetailsListFragment packingProductDetails = new PackingProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        packingProductDetails.setArguments(bundle);
        //getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, packingProductDetails).addToBackStack(null).commit();

    }

    public void showSubProductList() {
        ProductListFragment productListFragment = new ProductListFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productListFragment).addToBackStack(null).commit();
    }
    public void showAddProductList(String orderGuid,int totalCotton) {
        AddProductFragment addProductFragment = new AddProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        bundle.putInt(Constants.NO_OF_COTTON, totalCotton);
        addProductFragment.setArguments(bundle);
        //getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, addProductFragment).addToBackStack(null).commit();
    }
    public void showAddProductList() {
        AddProductFragment addProductFragment = new AddProductFragment();
       // getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, addProductFragment).addToBackStack(null).commit();
    }

    public void backClicked(View view) {
        int size = getSupportFragmentManager().getBackStackEntryCount();
        if (size > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
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



    public void showProgress() {
        Log.e("LoginActivity", "showProgress");
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void stopProgress() {
        Log.e("LoginActivity", "stopProgress");
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_delivery_details:
                showDeliveryDetailsActivity(Constants.VIEW_ADD_SHIPPING,0);
                break;
        }
    }

    @Override
    public void showPackingDetails(String orderGuid,String nextView) {
        showPackingDetailsList(orderGuid,nextView);
    }

    @Override
    public void showOrderList(String nextView,int deliveryId) {
        showDeliveryDetailsActivity(nextView,deliveryId);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ORDER_TAB_POS = 0;
        PACKING_TAB_POS = 1;
        DELIVERY_TAB_POS = 2;
        HISTORY_TAB_POS = 3;

        adapter.addFragment(new OrderedFragment(), "Order");
        adapter.addFragment(new PackingFragment(), "Packing");
        adapter.addFragment(new DeliveryListFragment(), "Delivery");
        adapter.addFragment(new HistoryFragment(), "History");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
