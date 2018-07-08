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
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.adapter.ProductNameListAdapter;
import com.ordered.report.view.adapter.SubCategoryListAdapter;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithish on 26/02/18.
 */

public class ProductNameListFragment extends Fragment {

    private OrderDetailsActivity orderDetailsActivity;

    public ProductNameListFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.product_list);
        getActivity().setTitle("PRODUCT LIST");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        List<OrderDetailsListViewModel>  orderDetailsListViewModels =  orderDetailsActivity.getOrderedService().getOrderDetailsListViewModels(orderDetailsActivity.getOrderGuid());
        ProductNameListAdapter productNameListAdapter = new ProductNameListAdapter(orderDetailsActivity,orderDetailsListViewModels);
        orderDetailsActivity.getOrderedService().calcAvailableCount(orderDetailsListViewModels, orderDetailsActivity.getCartonDetailsJsonList());
        recyclerView.setAdapter(productNameListAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderDetailsActivity = (OrderDetailsActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
