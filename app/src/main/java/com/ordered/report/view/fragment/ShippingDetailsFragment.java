package com.ordered.report.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.enumeration.DeliveringType;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.PaymentStatus;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.utils.UtilService;
import com.ordered.report.view.activity.DeliveryListActivity;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.adapter.DeliverOrderCartonListAdapter;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nithish on 07/03/18.
 */

public class ShippingDetailsFragment  extends Fragment {

    DeliveryListActivity deliveryListActivity;
    private EditText placeOfLoading;
    private EditText placeOfDelivery;
    private EditText portOfDischarge;
    private RadioGroup deliveryType;
    private Button submit;
    View mainLayOut;

    List<CartonDetailsEntity> cartonDetailsEntityList = null;

    AlertDialog alertDialog;
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
        deliveryDetailsEntity.setDeliveryId(UtilService.getDeliveryId());
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
        deliveryListActivity.getOrderedService().createDelivery(deliveryDetailsEntity);
        deliveryListActivity.finish();
        //showDialog(deliveryDetailsEntity,orderEntity);

    }


    private void saveDeliveryDetails(DeliveryDetailsEntity deliveryDetailsEntity,OrderEntity orderEntity){
        deliveryListActivity.getOrderedService().createDelivery(deliveryDetailsEntity);

        for(CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntityList){
            if(cartonDetailsEntity.getDeliveryDetails() != null){
                deliveryListActivity.getOrderedService().updateCartonDetailsEntity(cartonDetailsEntity);
            }
        }


        orderEntity.setPaymentStatus(PaymentStatus.NOT_PAIED);
        orderEntity.setLastModifiedDate(System.currentTimeMillis());
        orderEntity.setSync(false);
        deliveryListActivity.getOrderedService().updateOrderUpdates(orderEntity);
        Intent intent = new Intent(deliveryListActivity, HomeActivity.class);
        startActivity(intent);
        deliveryListActivity.finish();
        alertDialog.dismiss();
    }

    private void processDeliveryDetails(final DeliveryDetailsEntity deliveryDetailsEntity,final OrderEntity orderEntity){
        orderEntity.setOrderStatus(OrderStatus.DELIVERED);
        boolean isSelected = false;
        boolean isPartial = false;
        for(CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntityList){
            if(cartonDetailsEntity.getDeliveryDetails() != null){
                //orderDetailsActivity.getOrderedService().updateCartonDetailsEntity(cartonDetailsEntity);
                isSelected = true;
            }else{
                orderEntity.setOrderStatus(OrderStatus.PARTIAL_DELIVERED);
                isPartial = true;
            }
        }
        if(isPartial){
            new AlertDialog.Builder(getContext())
                    .setTitle("Title")
                    .setMessage("Do you really want to whatever?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            saveDeliveryDetails(deliveryDetailsEntity,orderEntity);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }else if(isSelected){
            saveDeliveryDetails(deliveryDetailsEntity,orderEntity);
        }else{
            Snackbar snackbar = Snackbar
                    .make(mainLayOut, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.DKGRAY);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();

        }

    }


    public void showDialog(final DeliveryDetailsEntity deliveryDetailsEntity, final OrderEntity orderEntity){
        AlertDialog.Builder  dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle("List of Cartons");
        //dialog.setCancelable(true);

        View view = deliveryListActivity.getLayoutInflater().inflate(R.layout.order_list_delivery_list_view, null);

        ListView list = (ListView) view.findViewById(R.id.ListLikersList);
        cartonDetailsEntityList = deliveryListActivity.getOrderedService().getUnDeliveryCartonDetailsEntity(orderEntity);


        DeliverOrderCartonListAdapter deliverOrderCartonListAdapter = new DeliverOrderCartonListAdapter(deliveryListActivity,cartonDetailsEntityList,deliveryDetailsEntity);
        list.setAdapter(deliverOrderCartonListAdapter);


        dialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                processDeliveryDetails(deliveryDetailsEntity,orderEntity);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

        dialog.setView(view);
        alertDialog = dialog.create();
        alertDialog.show();
    }


    private void initView(View view){
        placeOfLoading = view.findViewById(R.id.place_of_loading);
        placeOfDelivery =  view.findViewById(R.id.place_of_delivery);
        portOfDischarge = view.findViewById(R.id.port_of_discharge);
        deliveryType = view.findViewById(R.id.adapt_pur_delivery);
        mainLayOut = view.findViewById(R.id.main_layout_id);
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
        deliveryListActivity = (DeliveryListActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
