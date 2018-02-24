package com.ordered.report.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ordered.report.HomeActivity;
import com.ordered.report.R;
import com.ordered.report.adapter.OrderDetailsListAdapter;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;

public class PackingProductDetailsListFragment extends Fragment {
    private HomeActivity homeActivity;
    private Context context;
    private int totalCotton = 0;
    private TextView no_of_cotton;
    private OrderedService orderedService = null;
    private OrderEntity orderEntity = null;
    private String orderGuid;
    private Gson gson = null;
    OrderDetailsListAdapter mAdapter;

    public PackingProductDetailsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            homeActivity = (HomeActivity) context;
            orderedService = new OrderedService(getActivity());
            if (getArguments() != null) {
                orderGuid = getArguments().getString(Constants.ORDER);
                gson = new Gson();
                if (orderGuid != null && !orderGuid.isEmpty()) {
                    orderEntity = orderedService.getOrderEntityByGuid(orderGuid);
                    totalCotton = Integer.valueOf(orderEntity.getCartonCounts());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_packing_pro_details_list, container, false);
        no_of_cotton = (TextView) view.findViewById(R.id.packing_total_carton_no);
        no_of_cotton.setText("" + totalCotton);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.packing_product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        getProductDetails();

        mAdapter = new OrderDetailsListAdapter(getActivity(), new ArrayList<OrderDetailsListViewModel>(),totalCotton);
        recyclerView.setAdapter(mAdapter);
        Button addButton = (Button) view.findViewById(R.id.packing_order_details_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.showAddProductList(null,0);
               // createProductEntity();
            }
        });

        Button orderDetailsDone = (Button) view.findViewById(R.id.packing_order_details_done);
        orderDetailsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if( homeActivity.getProductDetailsJsons().size() > 0){
                    updateOrderDetails();
                    homeActivity.getProductDetailsJsons().clear();
                    homeActivity.showPackingListFragment();
                }else{
                    showAlert();
                }*/

            }
        });

        Button orderDetailsCancel = (Button) view.findViewById(R.id.packing_order_details_cancel);
        orderDetailsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //homeActivity.getProductDetailsJsons().clear();
                homeActivity.showOrderedFragment();
            }
        });
        return view;

    }

    private void showAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(
                getActivity()).create();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }





    private void getProductDetails(){


    }

   /* public void createProductEntity() {
        productEntities = orderedService.getProductEntityList(orderEntity);
        if(productEntities.size()<totalCotton) {
            ProductDetailsEntity productEntity = new ProductDetailsEntity();
            productEntity.setItemGuid(UtilService.getUUID());
            productEntity.setCreatedBy("Admin");
            productEntity.setCreatedTime(UtilService.getCurrentTimeMilli());
            productEntity.setProductName("Package");
            productEntity.setOrderEntity(orderEntity);
            orderedService.createProductEntity(productEntity);
            productEntities = orderedService.getProductEntityList(orderEntity);
            mAdapter.refresh(productEntities);
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(
                    getActivity()).create();

            // Setting Dialog Title
            alertDialog.setTitle("Warning");

            // Setting Dialog Message
            alertDialog.setMessage("Exceed");
//            alertDialog.setIcon(R.drawable.tick);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        Log.e("test", "test");
    }*/
}
