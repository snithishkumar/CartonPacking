package com.ordered.report.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordered.report.R;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.view.activity.OrderViewDetailsActivity;
import com.ordered.report.view.adapter.OrderViewProductNameListAdapter;

import java.util.List;

public class OrderViewProductNameListFragment extends Fragment {

    OrderViewDetailsActivity orderViewDetailsActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderViewDetailsActivity = (OrderViewDetailsActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordered, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        getActivity().setTitle("Product List");

        String cartonGuid = orderViewDetailsActivity.getCartonGuid();
        if(cartonGuid != null){
            List<ProductDetailsEntity> productEntities  =  orderViewDetailsActivity.getOrderedService().getProductEntityList(cartonGuid);
            OrderViewProductNameListAdapter orderViewProductNameListAdapter = new OrderViewProductNameListAdapter(productEntities,orderViewDetailsActivity);
            recyclerView.setAdapter(orderViewProductNameListAdapter);
            return view;
        }else{
            String deliverGuid = orderViewDetailsActivity.getDeliveryGuid();
            List<ProductDetailsEntity> productEntities  =  orderViewDetailsActivity.getOrderedService().getDeliveredProduct(deliverGuid);
            OrderViewProductNameListAdapter orderViewProductNameListAdapter = new OrderViewProductNameListAdapter(productEntities,orderViewDetailsActivity);
            recyclerView.setAdapter(orderViewProductNameListAdapter);
            return view;
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
