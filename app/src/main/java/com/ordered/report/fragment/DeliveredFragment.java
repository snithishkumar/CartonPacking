package com.ordered.report.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordered.report.R;
import com.ordered.report.adapter.OrderedListAdapter;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.OrderedService;

import java.util.List;

public class DeliveredFragment extends Fragment {
    private List<OrderEntity> cartonbookEntities = null;
    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;

    public DeliveredFragment() {
        // Required empty public constructor
    }

    public static DeliveredFragment newInstance(String param1, String param2) {
        DeliveredFragment fragment = new DeliveredFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderedService = new OrderedService(getActivity());
        getActivity().setTitle("ORDER");
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivered, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        OrderedListAdapter mAdapter = new OrderedListAdapter(getActivity(), getOrderedCartonBookList(),OrderStatus.DELIVERED);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public List<OrderEntity> getOrderedCartonBookList() {
        cartonbookEntities = orderedService.getCartonBookEntityByType(OrderStatus.DELIVERED);
        return cartonbookEntities;
    }
}
