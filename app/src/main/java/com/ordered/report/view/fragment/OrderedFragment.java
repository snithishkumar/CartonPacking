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
import com.ordered.report.view.adapter.OrderedListAdapter;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;

import java.util.List;

public class OrderedFragment extends Fragment {
    private Context context;
    private List<OrderEntity> cartonbookEntities = null;
    private OrderedService orderedService = null;
    RecyclerView recyclerView =null;
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

        OrderedListAdapter mAdapter = new OrderedListAdapter(getActivity(), getOrderedCartonBookList(),OrderStatus.ORDERED);
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    public List<OrderEntity> getOrderedCartonBookList() {
        cartonbookEntities = orderedService.getCartonBookEntityByType(OrderStatus.ORDERED);
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
}
