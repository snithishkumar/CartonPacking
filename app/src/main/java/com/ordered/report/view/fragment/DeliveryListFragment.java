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
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.adapter.DeliveryListAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DeliveryListFragment extends Fragment {

    private List<DeliveryDetailsEntity> deliveryDetailsEntities = new ArrayList<>();

    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;
    private DeliveryListAdapter deliveryListAdapter = null;

    FloatingActionButton floatingActionButton = null;

    public DeliveryListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.delivery_list);
        floatingActionButton = view.findViewById(R.id.add_delivery_details);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        List<DeliveryDetailsEntity> deliveryDetailsEntityList =  getDeliveredOrdersList();
        this.deliveryDetailsEntities.clear();
        this.deliveryDetailsEntities.addAll(deliveryDetailsEntityList);
        deliveryListAdapter = new DeliveryListAdapter(getActivity(),deliveryDetailsEntities);
        recyclerView.setAdapter(deliveryListAdapter);
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

    public List<DeliveryDetailsEntity> getDeliveredOrdersList() {
        return orderedService.getDeliveredOrdersList();
    }



    @Override
    public void onPause() {
        AppBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume(){
        AppBus.getInstance().register(this);
        orderResponse(null);
        super.onResume();
    }

    @Subscribe
    public void orderResponse(ResponseData responseData){
        List<DeliveryDetailsEntity> deliveryDetailsEntityList =  getDeliveredOrdersList();
        this.deliveryDetailsEntities.clear();
        this.deliveryDetailsEntities.addAll(deliveryDetailsEntityList);
        deliveryListAdapter.notifyDataSetChanged();

    }

}
