package com.ordered.report.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordered.report.R;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.adapter.DeliveredListAdapter;
import com.ordered.report.view.adapter.PackingListAdapter;

import java.util.List;

public class PackingFragment extends Fragment {
    private List<OrderEntity> cartonbookEntities = null;
    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;

    public PackingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        orderedService = new OrderedService(getActivity());
        getActivity().setTitle("PACKING");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        PackingListAdapter mAdapter = new PackingListAdapter(getActivity(), getOrderedCartonBookList());
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    public List<OrderEntity> getOrderedCartonBookList() {
        cartonbookEntities = orderedService.getPackingOrdersList();
        return cartonbookEntities;
    }
}
