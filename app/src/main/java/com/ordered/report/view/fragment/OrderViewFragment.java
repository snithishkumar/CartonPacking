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
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.adapter.HistoryListAdapter;
import com.ordered.report.view.adapter.OrderViewOrderListAdapter;
import com.ordered.report.view.models.OrderViewListModel;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class OrderViewFragment extends Fragment {
    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;
    List<OrderViewListModel> orderViewListModelList = new ArrayList<>();
    OrderViewOrderListAdapter orderViewOrderListAdapter;
    HomeActivity homeActivity;

    public OrderViewFragment() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(homeActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        orderViewListModelList.clear();
        orderViewListModelList.addAll(orderedService.getOrderViewListModels());
        orderViewOrderListAdapter = new OrderViewOrderListAdapter(orderViewListModelList,homeActivity);
        recyclerView.setAdapter(orderViewOrderListAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public List<DeliveryDetailsEntity> getDeliveredOrdersList() {
        return orderedService.getCompletedDeliveryDetailsEntity();
    }



    @Override
    public void onPause() {
        AppBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume(){
        AppBus.getInstance().register(this);
        super.onResume();
    }

    @Subscribe
    public void orderResponse(ResponseData responseData){
        orderViewListModelList.clear();
        orderViewListModelList.addAll(orderedService.getOrderViewListModels());
        orderViewOrderListAdapter.notifyDataSetChanged();

    }



}
