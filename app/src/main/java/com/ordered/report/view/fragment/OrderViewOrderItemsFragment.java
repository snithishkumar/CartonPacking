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
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.activity.OrderViewActivity;
import com.ordered.report.view.adapter.OrderViewOrderItemsAdapter;
import com.ordered.report.view.adapter.OrderViewOrderListAdapter;
import com.ordered.report.view.models.OrderViewOrderedItemsListModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewOrderItemsFragment  extends Fragment {
    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;
    OrderViewActivity orderViewActivity;
    OrderViewOrderItemsAdapter orderViewOrderListAdapter;
    List<OrderViewOrderedItemsListModel> orderViewListModelList = new ArrayList<>();

    public OrderViewOrderItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderedService = new OrderedService(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView =  view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(orderViewActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        orderViewListModelList.clear();
        orderViewListModelList.addAll(orderedService.getOrderViewOrderedItemsListModels(orderViewActivity.getOrderEntity()));
        orderViewOrderListAdapter = new OrderViewOrderItemsAdapter(orderViewListModelList,orderViewActivity);
        recyclerView.setAdapter(orderViewOrderListAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderViewActivity = (OrderViewActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
