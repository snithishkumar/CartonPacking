package com.ordered.report.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ordered.report.R;
import com.ordered.report.enumeration.DeliveringType;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.PaymentStatus;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.adapter.CartonListAdapter;

import java.util.UUID;

/**
 * Created by Nithish on 07/03/18.
 */

public class ShippingDetailsFragment  extends Fragment {

    OrderDetailsActivity orderDetailsActivity;
    private EditText placeOfLoading;
    private EditText placeOfDelivery;
    private EditText portOfDischarge;
    private RadioGroup deliveryType;
    private Button submit;

    public ShippingDetailsFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivering_details, container, false);
        initView(view);


        return view;
    }

    private void populateData(){
        DeliveryDetailsEntity deliveryDetailsEntity = new DeliveryDetailsEntity();
        deliveryDetailsEntity.setDeliveryUUID(UUID.randomUUID().toString());
        if(placeOfDelivery.getText() != null){
            deliveryDetailsEntity.setPlaceOfDelivery(placeOfDelivery.getText().toString());
        }

        if(placeOfLoading.getText() != null){
            deliveryDetailsEntity.setPlaceOfLoading(placeOfLoading.getText().toString());
        }

        if(portOfDischarge.getText() != null){
            deliveryDetailsEntity.setPortOfDischarge(portOfDischarge.getText().toString());
        }

      int id =   deliveryType.getCheckedRadioButtonId();
        switch (id){
            case R.id.carton_delivery_by_air:
                deliveryDetailsEntity.setDeliveringType(DeliveringType.AIR);
                break;
            case R.id.carton_delivery_by_sea:
                deliveryDetailsEntity.setDeliveringType(DeliveringType.SEA);
                break;
            case R.id.carton_delivery_by_road:
                deliveryDetailsEntity.setDeliveringType(DeliveringType.ROAD);
                break;

        }

        OrderEntity orderEntity = orderDetailsActivity.getOrderedService().getOrderEntityByGuid(orderDetailsActivity.getOrderGuid());
        deliveryDetailsEntity.setOrderEntity(orderEntity);
        orderDetailsActivity.getOrderedService().createDelivery(deliveryDetailsEntity);
        orderEntity.setOrderStatus(OrderStatus.DELIVERED);
        orderEntity.setPaymentStatus(PaymentStatus.NOT_PAIED);
        orderEntity.setLastModifiedDate(System.currentTimeMillis());
        orderEntity.setSync(false);
        orderDetailsActivity.getOrderedService().updateOrderUpdates(orderEntity);

        Intent intent = new Intent(orderDetailsActivity, HomeActivity.class);
        startActivity(intent);
        orderDetailsActivity.finish();
    }


    private void initView(View view){
        placeOfLoading = view.findViewById(R.id.place_of_loading);
        placeOfDelivery =  view.findViewById(R.id.place_of_delivery);
        portOfDischarge = view.findViewById(R.id.port_of_discharge);
        deliveryType = view.findViewById(R.id.adapt_pur_delivery);
        submit = view.findViewById(R.id.delivery_in_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateData();
            }
        });
    }

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
