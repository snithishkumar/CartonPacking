package com.ordered.report.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordered.report.R;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.adapter.DeliveredListAdapter;
import com.ordered.report.view.adapter.OrderListAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class OrderedFragment extends Fragment {
    private Context context;
    private List<OrderEntity> cartonbookEntities = new ArrayList<>();
    private OrderedService orderedService = null;
    RecyclerView recyclerView =null;
    OrderListAdapter mAdapter = null;
    public OrderedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordered, container, false);
        orderedService = new OrderedService(getActivity());
        getActivity().setTitle("ORDER");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        List<OrderEntity> orderEntityList =  getOrderedCartonBookList();
        this.cartonbookEntities.clear();
        this.cartonbookEntities.addAll(orderEntityList);
        mAdapter = new OrderListAdapter(getActivity(), this.cartonbookEntities);
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    public List<OrderEntity> getOrderedCartonBookList() {
        List<OrderEntity> cartonbookEntities = orderedService.getOrdersList();
        return cartonbookEntities;
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
        List<OrderEntity> orderEntityList =  getOrderedCartonBookList();
        this.cartonbookEntities.clear();
        this.cartonbookEntities.addAll(orderEntityList);
        mAdapter.notifyDataSetChanged();

    }
}
