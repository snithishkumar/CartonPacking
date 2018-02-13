package com.ordered.report.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordered.report.R;
import com.ordered.report.adapter.OrderedListAdapter;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.CartonbookService;

import java.util.List;

public class PackingFragment extends Fragment {
    private List<OrderEntity> cartonbookEntities = null;
    private CartonbookService cartonbookService = null;
    RecyclerView recyclerView = null;

    public PackingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        cartonbookService = new CartonbookService(getActivity());
        getActivity().setTitle("ORDER");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        OrderedListAdapter mAdapter = new OrderedListAdapter(getActivity(), getOrderedCartonBookList());
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    public List<OrderEntity> getOrderedCartonBookList() {
        cartonbookEntities = cartonbookService.getCartonBookEntityByType(OrderType.PACKING);
        return cartonbookEntities;
    }
}
