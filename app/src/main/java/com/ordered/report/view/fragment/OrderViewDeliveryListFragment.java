package com.ordered.report.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordered.report.R;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.view.activity.OrderViewActivity;
import com.ordered.report.view.adapter.OrderViewDeliveryListAdapter;

import java.util.List;

public class OrderViewDeliveryListFragment extends Fragment {

    private OrderViewActivity orderViewActivity;
    RecyclerView recyclerView = null;
    public OrderViewDeliveryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);
        recyclerView = view.findViewById(R.id.delivery_list);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.add_delivery_details);
        floatingActionButton.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        List<DeliveryDetailsEntity> deliveryDetailsEntityList =  orderViewActivity.getOrderedService().getDeliveredOrdersList(orderViewActivity.getOrderEntity());
        OrderViewDeliveryListAdapter orderViewDeliveryListAdapter = new OrderViewDeliveryListAdapter(getActivity(),deliveryDetailsEntityList);
        recyclerView.setAdapter(orderViewDeliveryListAdapter);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
