package com.ordered.report.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.view.activity.OrderDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class DeliverOrderCartonListAdapter extends ArrayAdapter<CartonDetailsEntity> {

    private List<CartonDetailsEntity> cartonDetailsEntityList;
    private OrderDetailsActivity orderDetailsActivity;
    private DeliveryDetailsEntity deliveryDetailsEntity;

    public DeliverOrderCartonListAdapter(Context context, List<CartonDetailsEntity> cartonItemModels, DeliveryDetailsEntity deliveryDetailsEntity){
        super(context, R.layout.adapter_order_carton_list_deliver, cartonItemModels);
        orderDetailsActivity = (OrderDetailsActivity) context;
        this.cartonDetailsEntityList = cartonItemModels;
        this.deliveryDetailsEntity = deliveryDetailsEntity;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       final CartonDetailsEntity cartonDetailsEntity = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        DeliverOrderListViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new DeliverOrderListViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_order_carton_list_deliver, parent, false);

            viewHolder.cartonNumber =  convertView.findViewById(R.id.packing_order_delivery_carton_no);
            viewHolder.noOfProducts =  convertView.findViewById(R.id.packing_order_delivery_no_products);
            viewHolder.cartonTotalWeights =  convertView.findViewById(R.id.packing_order_delivery_total_weights);
            viewHolder.cartonCreatedDate =  convertView.findViewById(R.id.packing_order_delivery_last_modified_time);
           final RadioButton radioButton = viewHolder.cartonNumber;

            viewHolder.cartonNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(radioButton.isChecked() && cartonDetailsEntity.getDeliveryDetails() == null){
                        cartonDetailsEntity.setDeliveryDetails(deliveryDetailsEntity);
                    }else{
                        radioButton.setChecked(false);
                        cartonDetailsEntity.setDeliveryDetails(null);
                       // notifyDataSetChanged();
                    }
                }
            });
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (DeliverOrderListViewHolder) convertView.getTag();
        }


        viewHolder.cartonNumber.setText("Carton Number : "+cartonDetailsEntity.getCartonNumber());
        viewHolder.noOfProducts.setText("10");

        viewHolder.cartonTotalWeights.setText(cartonDetailsEntity.getTotalWeight());
        viewHolder.cartonCreatedDate.setText(formatDate(cartonDetailsEntity.getCreatedDateTime()));
        // Return the completed view to render on screen
        return convertView;
    }

  /*  private List<CartonDetailsEntity> cartonDetailsEntityList;
    private OrderDetailsActivity orderDetailsActivity;
    private DeliveryDetailsEntity deliveryDetailsEntity;

    public DeliverOrderCartonListAdapter(Context context, List<CartonDetailsEntity> cartonItemModels, DeliveryDetailsEntity deliveryDetailsEntity) {
        orderDetailsActivity = (OrderDetailsActivity) context;
        this.cartonDetailsEntityList = cartonItemModels;
        this.deliveryDetailsEntity = deliveryDetailsEntity;
    }

    @Override
    public DeliverOrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderDetailsActivity).inflate(R.layout.adapter_order_carton_list_deliver, parent, false);

        return new DeliverOrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliverOrderListViewHolder holder, int position) {
        final CartonDetailsEntity cartonItemModel = cartonDetailsEntityList.get(position);

        holder.cartonNumber.setText("Carton Number : "+cartonItemModel.getCartonNumber());
        holder.noOfProducts.setText("10");

        holder.cartonTotalWeights.setText(cartonItemModel.getTotalWeight());
        holder.cartonCreatedDate.setText(formatDate(cartonItemModel.getCreatedDateTime()));


    }








    @Override
    public int getItemCount() {
        return cartonDetailsEntityList.size();
    }


  */

    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }


    private static class DeliverOrderListViewHolder {

        public RadioButton cartonNumber;
        public TextView noOfProducts;
        public TextView cartonTotalWeights;
        public TextView cartonCreatedDate;


    }



}
