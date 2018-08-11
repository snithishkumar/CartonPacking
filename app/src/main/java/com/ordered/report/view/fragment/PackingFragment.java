package com.ordered.report.view.fragment;


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
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.adapter.PackingListAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PackingFragment extends Fragment {
    private List<OrderEntity> cartonbookEntities = new ArrayList<>();
    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;
    PackingListAdapter mAdapter = null;

    public PackingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        orderedService = new OrderedService(getActivity());
        //getActivity().setTitle("PACKING");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        List<OrderEntity> orderEntityList =  getOrderedCartonBookList();
        this.cartonbookEntities.clear();
        cartonbookEntities.addAll(orderEntityList);
        mAdapter = new PackingListAdapter(getActivity(),cartonbookEntities);
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    public List<OrderEntity> getOrderedCartonBookList() {
        return orderedService.getPackingOrdersList();
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
    public void packingResponse(ResponseData responseData){
        List<OrderEntity> orderEntityList =  getOrderedCartonBookList();
        this.cartonbookEntities.clear();
        this.cartonbookEntities.addAll(orderEntityList);
        mAdapter.notifyDataSetChanged();

    }
}
