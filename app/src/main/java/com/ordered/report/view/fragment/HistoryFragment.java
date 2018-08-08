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
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.services.OrderedService;
import com.ordered.report.view.adapter.HistoryListAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private List<DeliveryDetailsEntity> deliveryDetailsEntities = new ArrayList<>();
    private OrderedService orderedService = null;
    RecyclerView recyclerView = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ProgressDialog progressDialog = null;
    HistoryListAdapter mAdapter = null;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        List<DeliveryDetailsEntity> deliveryDetailsEntityList =  getDeliveredOrdersList();
        this.deliveryDetailsEntities.clear();
        this.deliveryDetailsEntities.addAll(deliveryDetailsEntityList);
        mAdapter = new HistoryListAdapter(getActivity(),deliveryDetailsEntities);
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

    public List<DeliveryDetailsEntity> getDeliveredOrdersList() {
        return orderedService.getCompletedDeliveryDetailsEntity();
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
        List<DeliveryDetailsEntity> deliveryDetailsEntityList =  getDeliveredOrdersList();
        this.deliveryDetailsEntities.clear();
        this.deliveryDetailsEntities.addAll(deliveryDetailsEntityList);
        mAdapter.notifyDataSetChanged();

    }


}
