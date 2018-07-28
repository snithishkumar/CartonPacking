package com.ordered.report.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.ordered.report.R;
import com.ordered.report.json.models.CartonInvoiceSummary;
import com.ordered.report.json.models.OrderCreationDetailsJson;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.utils.NumberToWord;
import com.ordered.report.utils.UtilService;
import com.ordered.report.view.adapter.DeliveryListAdapter;
import com.ordered.report.view.adapter.OrderDetailsListAdapter;
import com.ordered.report.view.adapter.OrderListAdapter;
import com.ordered.report.view.adapter.PackingListAdapter;
import com.ordered.report.view.fragment.AddProductFragment;
import com.ordered.report.view.fragment.CaptureCartonDetailsFragment;
import com.ordered.report.view.fragment.HomeFragment;
import com.ordered.report.view.fragment.PackingFragment;
import com.ordered.report.view.fragment.PackingProductDetailsListFragment;
import com.ordered.report.view.fragment.ProductDetailsListFragment;
import com.ordered.report.view.fragment.ProductListFragment;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements PackingListAdapter.PackingListAdapterCallBack, DeliveryListAdapter.DeliveryListAdapterCallBack{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ProgressDialog progressDialog = null;

    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    private OrderedService orderedService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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






    public void showPackingListFragment() {
        PackingFragment packingFragment = new PackingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, packingFragment).addToBackStack(null).commit();
    }


    public void showOrderDetailsList(String totalNoOfCartons,String orderGuid){
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("orderGuid",orderGuid);
        intent.putExtra("totalNoOfCartons",totalNoOfCartons);
        intent.putExtra("view",Constants.VIEW_ORDER);
        startActivity(intent);

    }


    private void showDeliveryDetailsActivity(String view,int deliveryId){
        Intent intent = new Intent(this, DeliveryListActivity.class);
        intent.putExtra("view",view);
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

        startActivity(intent);
    }

    public void showProductList(int cartonNo, String order) {
        ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.NO_OF_COTTON, cartonNo);
        bundle.putString(Constants.ORDER, order);
        productDetailsListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productDetailsListFragment).addToBackStack(null).commit();
    }


    public void showPackingProductDetailsList(String orderGuid) {
        PackingProductDetailsListFragment packingProductDetails = new PackingProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        packingProductDetails.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, packingProductDetails).addToBackStack(null).commit();

    }

    public void showSubProductList() {
        ProductListFragment productListFragment = new ProductListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productListFragment).addToBackStack(null).commit();
    }
    public void showAddProductList(String orderGuid,int totalCotton) {
        AddProductFragment addProductFragment = new AddProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        bundle.putInt(Constants.NO_OF_COTTON, totalCotton);
        addProductFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, addProductFragment).addToBackStack(null).commit();
    }
    public void showAddProductList() {
        AddProductFragment addProductFragment = new AddProductFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, addProductFragment).addToBackStack(null).commit();
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
}
