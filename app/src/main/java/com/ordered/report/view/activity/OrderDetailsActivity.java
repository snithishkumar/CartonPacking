package com.ordered.report.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ordered.report.R;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.adapter.CartonListAdapter;
import com.ordered.report.view.adapter.OrderDetailsListAdapter;
import com.ordered.report.view.adapter.ProductNameListAdapter;
import com.ordered.report.view.fragment.CaptureCartonDetailsFragment;
import com.ordered.report.view.fragment.CartonListFragment;
import com.ordered.report.view.fragment.CartonNumberPickerFragment;
import com.ordered.report.view.fragment.ProductDetailsListFragment;
import com.ordered.report.view.fragment.ProductNameListFragment;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailsListAdapter.OrderDetailsClickListeners, CaptureCartonDetailsFragment.ShowFragment,ProductNameListAdapter.ProductNameListAdapterCallBack, CartonListAdapter.CartonListAdapterCallBack {

    private String orderGuid;
    private String view;
    private String nextView;
    private String totalNoOfCartons;
   // private String cartonNumber;
    private OrderedService orderedService;
    private OrderDetailsListViewModel orderDetailsListViewModel;
    private int currentTabPosition = 0;

    List<CartonDetailsJson>  cartonDetailsJsonList = new ArrayList<>();
    private CartonDetailsJson cartonDetailsJson;

    public final String LOG_TAG = OrderDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_Details_toolbar);
        setSupportActionBar(toolbar);
        init();
        showCartonListFragment();

    }


    /**
     * Initialize service layer
     */
    private void init(){
        try{
            orderGuid = getIntent().getStringExtra("orderGuid");
            currentTabPosition = getIntent().getIntExtra("currentTabPosition",1);
            totalNoOfCartons = getIntent().getStringExtra("totalNoOfCartons");
            orderedService = new OrderedService(this);
            view = getIntent().getStringExtra("view");
            nextView = getIntent().getStringExtra("newView");
            cartonDetailsJsonList =  getOrderedService().getCartonDetailsJson(orderGuid);
            if(view.equals(Constants.VIEW_PACKING)){
                OrderEntity orderEntity =  getOrderedService().getOrderEntityByGuid(orderGuid);
                totalNoOfCartons = orderEntity.getCartonCounts();
            }
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in init",e);
        }

    }




    private void showCartonListFragment(){
        CartonListFragment cartonListFragment = new CartonListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.order_details_container, cartonListFragment).addToBackStack("cartonList").commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container,captureCartonDetailsFragment).addToBackStack("captureCarton").commit();
    }

    @Override
    public void orderDetailsListOnClick(int position) {
        showCaptureCartonDetailsFragment(position);
    }


    @Override
    public void viewFragment(int options) {
        if(!flag){
            removeAllStack();
            CartonListFragment cartonListFragment = new CartonListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, cartonListFragment).addToBackStack("cartonList").commit();
        }else{
            showProductDetailsListFragment();
        }

        //ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, productDetailsListFragment).addToBackStack(null).commit();

    }


    private void removeAllStack(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    private void showCartonNumberPickerFragment(){
        CartonNumberPickerFragment cartonNumberPickerFragment = new CartonNumberPickerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, cartonNumberPickerFragment).addToBackStack(cartonNumberPickerFragment.getClass().getName()).commit();
    }


    private void showProductNameListFragment(){
        ProductNameListFragment productNameListFragment = new ProductNameListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, productNameListFragment).addToBackStack("productNameList").commit();
    }

    private boolean flag = false;





    public void onClick(View view){
        switch (view.getId()){
            case R.id.carton_footer_add:
                if(totalNoOfCartons != null && Integer.valueOf(totalNoOfCartons)  == cartonDetailsJsonList.size()){
                    showCartonCountAlert();
                }else{
                    showCartonNumberPickerFragment();
                }

                break;

            case R.id.carton_footer_done:
                if(cartonDetailsJsonList.size() > 0){
                    orderedService.saveProductDetails(orderGuid,cartonDetailsJsonList, OrderStatus.PACKING,totalNoOfCartons);
                    finish();
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("currentTabPosition",currentTabPosition);
                    startActivity(intent);
                }else{
                    showAlert();
                }

                break;

            case R.id.packing_list_complete_close:

                if(cartonDetailsJsonList.size() > 0){
                    for(CartonDetailsJson cartonDetailsJson :  cartonDetailsJsonList){
                        if(cartonDetailsJson.getTotalWeight().equals("0")){
                            Toast.makeText(this,"Please add weight to cartons",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    orderedService.saveProductDetails(orderGuid,cartonDetailsJsonList, OrderStatus.DELIVERED,totalNoOfCartons);
                    finish();
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("currentTabPosition",currentTabPosition);
                    startActivity(intent);
                }else{
                    showAlert();
                }

                break;

            case R.id.carton_footer_cancel:
            case R.id.packing_list_complete_cancel:
                finish();
                Intent  intent = new Intent(this, HomeActivity.class);
                intent.putExtra("currentTabPosition",currentTabPosition);
                startActivity(intent);
                break;

            case R.id.carton_number_picking:

                int pos = cartonDetailsJsonList.indexOf(cartonDetailsJson);
                if(pos != -1){
                    cartonDetailsJson = cartonDetailsJsonList.get(pos);
                    if(cartonDetailsJson.getDeliverDetailsGuid() != null){
                        Toast.makeText(this,"This Carton has already delivered. Please try another carton",Toast.LENGTH_LONG).show();
                        return;
                    }
                }else{
                    getCartonDetailsJsonList().add(cartonDetailsJson);
                }


                showProductNameListFragment();
                break;

            case R.id.order_details_add:
                flag = true;
                showProductNameListFragment();
                break;

            case R.id.order_details_done:
                flag = false;
                CartonListFragment cartonListFragment = new CartonListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, cartonListFragment).addToBackStack("cartonList").commit();
                break;


        }
    }






    @Override
    public void callBack() {
        CaptureCartonDetailsFragment captureCartonDetailsFragment = new CaptureCartonDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, captureCartonDetailsFragment).addToBackStack(null).commit();
    }

    @Override
    public void showProductDetailsListFragment() {
        ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.order_details_container, productDetailsListFragment).addToBackStack("productDetailsList").commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();

        if (count == 0) {
            finish();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("currentTabPosition",currentTabPosition);
            startActivity(intent);
            super.onBackPressed();
        } else {
            List<Fragment> fragmentList = fragmentManager.getFragments();
            if(count == 1 && fragmentList.get(0) instanceof CartonListFragment){
                finish();
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("currentTabPosition",currentTabPosition);
                startActivity(intent);
                super.onBackPressed();
            }else{
                fragmentManager.popBackStack();
            }

        }
    }
       /* finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    */

    public OrderedService getOrderedService() {
        return orderedService;
    }

    public String getOrderGuid() {
        return orderGuid;
    }

    public String getTotalNoOfCartons() {
        return totalNoOfCartons;
    }

   /* public List<OrderDetailsListViewModel> getOrderDetailsListViewModels() {
        return orderDetailsListViewModels;
    }

    public String getCartonNumber() {
        return cartonNumber;
    }*/


    public OrderDetailsListViewModel getOrderDetailsListViewModel() {
        return orderDetailsListViewModel;
    }

    public void setOrderDetailsListViewModel(OrderDetailsListViewModel orderDetailsListViewModel) {
        this.orderDetailsListViewModel = orderDetailsListViewModel;
    }


    public CartonDetailsJson getCartonDetailsJson() {
        return cartonDetailsJson;
    }

    public void setCartonDetailsJson(CartonDetailsJson cartonDetailsJson) {
        this.cartonDetailsJson = cartonDetailsJson;
    }

    public List<CartonDetailsJson> getCartonDetailsJsonList() {
        return cartonDetailsJsonList;
    }

    public boolean isFlag() {
        return flag;
    }


    private void showAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Error");

        // Setting Dialog Message
        alertDialog.setMessage("Noting to Save.");
//            alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }



    private void showCartonCountAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


        // Setting Dialog Title
        alertDialog.setTitle("Info");

        // Setting Dialog Message
        alertDialog.setMessage("You have reached max number of cartons. Are you sure want to add one more carton?");
//            alertDialog.setIcon(R.drawable.tick);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                totalNoOfCartons =  String.valueOf(Integer.valueOf(totalNoOfCartons) + 1);
                showCartonNumberPickerFragment();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        // Showing Alert Message
        alertDialog.show();
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

    public int getCurrentTabPosition() {
        return currentTabPosition;
    }

    public String getView() {
        return view;
    }


    /* public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }*/
}
