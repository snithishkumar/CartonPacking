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
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.activity.DeliveryListActivity;
import com.ordered.report.view.adapter.DeliveryCartonListAdapter;
import com.ordered.report.view.adapter.DeliveryListAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DeliveryCartonListFragment extends Fragment {

    private List<CartonDetailsEntity> cartonDetailsEntities = new ArrayList<>();

    private OrderedService orderedService = null;
    private DeliveryCartonListAdapter deliveryCartonListAdapter = null;
     private ListView listView;
     private DeliveryListActivity deliveryListActivity = null;

    FloatingActionButton floatingActionButton = null;

    private int deliverId;

    public DeliveryCartonListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_delivery_carton_list, container, false);
        listView = view.findViewById(R.id.delivery_carton_list);
        floatingActionButton = view.findViewById(R.id.add_delivery_details);
        deliverId = deliveryListActivity.getDeliveryId();
        List<CartonDetailsEntity> cartonDetailsEntities =  getCartonDetailsEntities();
        this.cartonDetailsEntities.clear();
        this.cartonDetailsEntities.addAll(cartonDetailsEntities);
        deliveryCartonListAdapter = new DeliveryCartonListAdapter(deliveryListActivity,cartonDetailsEntities);
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
        return orderedService.getCartonDetailsEntities(deliverId);
    }




}
