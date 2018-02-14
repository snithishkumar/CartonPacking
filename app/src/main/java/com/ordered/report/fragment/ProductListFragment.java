package com.ordered.report.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ordered.report.HomeActivity;
import com.ordered.report.R;
import com.ordered.report.adapter.ProductListAdapter;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.utils.UtilService;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HomeActivity homeActivity;
    private Context context;
    private int totalCotton = 0;
    private TextView no_of_cotton;
    private OrderedService orderedService = null;
    private OrderEntity orderEntity = null;
    private String orderGuid;
    private Gson gson = null;
    List<ProductEntity> productEntities;
    ProductListAdapter mAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            homeActivity = (HomeActivity) context;
            orderedService = new OrderedService(getActivity());
            if (getArguments() != null) {
                totalCotton = getArguments().getInt(Constants.NO_OF_COTTON);
                orderGuid = getArguments().getString(Constants.ORDER);
                gson = new Gson();
                if (orderGuid != null && !orderGuid.isEmpty()) {
                    orderEntity = orderedService.getOrderEntityByGuid(orderGuid);
                    productEntities = orderedService.getProductEntityList(orderEntity);
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
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        no_of_cotton = (TextView) view.findViewById(R.id.total_carton_no);
        no_of_cotton.setText("" + totalCotton);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        List<String> list = new ArrayList<>();
        list.add("one");
        mAdapter = new ProductListAdapter(getActivity(), productEntities);
        recyclerView.setAdapter(mAdapter);
        Button addButton = (Button) view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.showAddProductList();
               // createProductEntity();
            }
        });
        return view;
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

    public void createProductEntity() {
        productEntities = orderedService.getProductEntityList(orderEntity);
        if(productEntities.size()<totalCotton) {
            ProductEntity productEntity = new ProductEntity();
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
    }
}
