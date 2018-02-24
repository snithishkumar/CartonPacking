package com.ordered.report.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.ordered.report.R;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.adapter.OrderDetailsListAdapter;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.List;

public class ProductDetailsListFragment extends Fragment {
    private OrderDetailsActivity orderDetailsActivity;
    private Context context;
    private String totalCotton ;
    private TextView no_of_cotton;

    OrderDetailsListAdapter mAdapter;

    public ProductDetailsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            orderDetailsActivity = (OrderDetailsActivity) context;
            if(orderDetailsActivity.getOrderDetailsListViewModels().size() == 0){
                List<OrderDetailsListViewModel>  orderDetailsListViewModels =  orderDetailsActivity.getOrderedService().getOrderDetailsListViewModels(orderDetailsActivity.getOrderGuid());
                orderDetailsActivity.getOrderDetailsListViewModels().addAll(orderDetailsListViewModels);
            }
            totalCotton = orderDetailsActivity.getTotalNoOfCartons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details_list, container, false);
        no_of_cotton = (TextView) view.findViewById(R.id.total_carton_no);
        no_of_cotton.setText(totalCotton);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);



        mAdapter = new OrderDetailsListAdapter(getActivity(), orderDetailsActivity.getOrderDetailsListViewModels(),orderDetailsActivity.getTotalNoOfCartons());
        recyclerView.setAdapter(mAdapter);


        Button orderDetailsDone = view.findViewById(R.id.order_details_done);
        orderDetailsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isSaved =  orderDetailsActivity.getOrderedService().saveProductDetails(orderDetailsActivity.getOrderGuid(),orderDetailsActivity.getOrderDetailsListViewModels(),orderDetailsActivity.getTotalNoOfCartons());
               if(!isSaved){
                   showAlert();
               }else{
                   backToHome();
               }
            }
        });

        Button orderDetailsCancel = view.findViewById(R.id.order_details_cancel);
        orderDetailsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
        return view;

    }


    private void backToHome(){
        Intent intent = new Intent(orderDetailsActivity, HomeActivity.class);
        startActivity(intent);
        orderDetailsActivity.finish();
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





}
