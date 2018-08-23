package com.ordered.report.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.view.activity.OrderViewActivity;
import com.ordered.report.view.adapter.OrderViewCartonListAdapter;

import java.util.List;

public class OrderViewCartonListFragment extends Fragment {
    OrderViewActivity orderViewActivity;

    TextView totalCartonCount = null;

    public OrderViewCartonListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orderview_carton_list, container, false);
        totalCartonCount = view.findViewById(R.id.total_carton_count);
        RecyclerView recyclerView = view.findViewById(R.id.carton_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        getActivity().setTitle("CARTON LIST");

        List<CartonDetailsJson>  cartonDetailsJsonList =  orderViewActivity.getOrderedService().getCartonDetailsJson(orderViewActivity.getOrderEntity().getOrderGuid());

        int totalNoOfCartons = Integer.valueOf(cartonDetailsJsonList.size());
        totalCartonCount.setText("Cartons:"+totalNoOfCartons);
        OrderViewCartonListAdapter orderViewCartonListAdapter = new OrderViewCartonListAdapter(orderViewActivity,cartonDetailsJsonList);
        recyclerView.setAdapter(orderViewCartonListAdapter);
        return view;
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
