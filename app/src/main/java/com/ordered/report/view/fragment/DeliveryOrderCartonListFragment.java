package com.ordered.report.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ordered.report.R;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.activity.DeliveryListActivity;
import com.ordered.report.view.adapter.DeliverOrderCartonListAdapter;
import com.ordered.report.view.adapter.DeliveryCartonListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeliveryOrderCartonListFragment extends Fragment {

    private List<CartonDetailsEntity> cartonDetailsEntities = new ArrayList<>();

    private OrderedService orderedService = null;
    private DeliverOrderCartonListAdapter deliveryCartonListAdapter = null;
     private ListView listView;
     private DeliveryListActivity deliveryListActivity = null;

    FloatingActionButton floatingActionButton = null;

    private int deliverId;

    public DeliveryOrderCartonListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderedService = new OrderedService(getActivity());
        getActivity().setTitle("DELIVERY");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_order_carton_list, container, false);
        listView = view.findViewById(R.id.delivery_order_carton_list);
        floatingActionButton = view.findViewById(R.id.add_order_cartons);
        deliverId = deliveryListActivity.getDeliveryId();
        List<CartonDetailsEntity> cartonDetailsEntities =  getCartonDetailsEntities();
        this.cartonDetailsEntities.clear();
        this.cartonDetailsEntities.addAll(cartonDetailsEntities);
        deliveryCartonListAdapter = new DeliverOrderCartonListAdapter(deliveryListActivity,cartonDetailsEntities);
        listView.setAdapter(deliveryCartonListAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        deliveryListActivity = (DeliveryListActivity)context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public List<CartonDetailsEntity> getCartonDetailsEntities() {
        return orderedService.getUnDeliveryCartonDetailsEntity(deliveryListActivity.getOrderEntity());
    }




}
