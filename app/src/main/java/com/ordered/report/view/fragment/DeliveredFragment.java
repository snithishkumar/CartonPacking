package com.ordered.report.view.fragment;

import android.Manifest;
import android.app.ProgressDialog;
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
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DeliveredFragment extends Fragment {
    private List<OrderEntity> cartonbookEntities = new ArrayList<>();
    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ProgressDialog progressDialog = null;
    DeliveredListAdapter mAdapter = null;

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
        List<OrderEntity> orderEntityList =  getOrderedList();
        this.cartonbookEntities.clear();
        this.cartonbookEntities.addAll(orderEntityList);
        mAdapter = new DeliveredListAdapter(getActivity(),cartonbookEntities);
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

    public List<OrderEntity> getOrderedList() {
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
        super.onResume();
    }

    @Subscribe
    public void orderResponse(ResponseData responseData){
        List<OrderEntity> orderEntityList =  getOrderedList();
        this.cartonbookEntities.clear();
        this.cartonbookEntities.addAll(orderEntityList);
        mAdapter.notifyDataSetChanged();

    }


}
