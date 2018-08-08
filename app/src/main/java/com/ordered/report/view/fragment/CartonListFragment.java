package com.ordered.report.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.adapter.CartonListAdapter;
import com.ordered.report.view.adapter.ProductNameListAdapter;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.List;

/**
 * Created by Nithish on 26/02/18.
 */

public class CartonListFragment extends Fragment {

    private OrderDetailsActivity orderDetailsActivity;
    TextView remaningCartonCount = null;

    LinearLayout inProgressFooter = null;
    LinearLayout completedFooter = null;


    public CartonListFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carton_list, container, false);
        remaningCartonCount = view.findViewById(R.id.remaning_carton_count);
        inProgressFooter = view.findViewById(R.id.carton_footer_bar);
        completedFooter = view.findViewById(R.id.packing_list_complete_footer);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.carton_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        getActivity().setTitle("CARTON LIST");
        int totalNoOfCartons = Integer.valueOf(orderDetailsActivity.getTotalNoOfCartons());
        totalNoOfCartons =  totalNoOfCartons - orderDetailsActivity.getCartonDetailsJsonList().size();
        remaningCartonCount.setText("Remaning Cartons:"+totalNoOfCartons);
        CartonListAdapter  cartonListAdapter = new CartonListAdapter(orderDetailsActivity,orderDetailsActivity.getCartonDetailsJsonList());
        //ProductNameListAdapter productNameListAdapter = new ProductNameListAdapter(orderDetailsActivity,orderDetailsListViewModels);
        recyclerView.setAdapter(cartonListAdapter);
        if(!orderDetailsActivity.getView().equals(Constants.VIEW_ORDER)){
            checkAvailability();
        }

        return view;
    }


    private void checkAvailability(){
        boolean res = orderDetailsActivity.getOrderedService().checkAvailability(orderDetailsActivity.getCartonDetailsJsonList(),orderDetailsActivity.getOrderGuid());
        if(res){
            inProgressFooter.setVisibility(View.VISIBLE);
            completedFooter.setVisibility(View.GONE);
        }else{
            inProgressFooter.setVisibility(View.GONE);
            completedFooter.setVisibility(View.VISIBLE);
        }
    }

    //checkAvailability

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderDetailsActivity = (OrderDetailsActivity) context;

    }



    @Override
    public void onDetach() {
        super.onDetach();
    }
}
