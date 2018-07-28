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
import com.ordered.report.view.activity.DeliveryListActivity;
import com.ordered.report.view.activity.OrderDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeliveryCartonListAdapter extends ArrayAdapter<CartonDetailsEntity> {

    private List<CartonDetailsEntity> cartonDetailsEntityList;
    private DeliveryListActivity deliveryListActivity;

    public DeliveryCartonListAdapter(Context context, List<CartonDetailsEntity> cartonItemModels){
        super(context, R.layout.adapter_carton_list_deliver, cartonItemModels);
        deliveryListActivity = (DeliveryListActivity) context;
        this.cartonDetailsEntityList = cartonItemModels;
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
            convertView = inflater.inflate(R.layout.adapter_carton_list_deliver, parent, false);

            viewHolder.cartonNumber =  convertView.findViewById(R.id.packing_order_delivery_carton_no);
            viewHolder.noOfProducts =  convertView.findViewById(R.id.packing_order_delivery_no_products);
            viewHolder.cartonTotalWeights =  convertView.findViewById(R.id.packing_order_delivery_total_weights);
            viewHolder.orderId = convertView.findViewById(R.id.delivery_carton_list_order_id);
            viewHolder.cartonCreatedDate =  convertView.findViewById(R.id.packing_order_delivery_last_modified_time);
            final RadioButton radioButton = viewHolder.cartonNumber;
            radioButton.setChecked(false);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (DeliverOrderListViewHolder) convertView.getTag();
        }


        viewHolder.cartonNumber.setText("Carton Number : "+cartonDetailsEntity.getCartonNumber());
        viewHolder.noOfProducts.setText("10");
        viewHolder.orderId.setText(cartonDetailsEntity.getOrderEntity().getOrderId());
        viewHolder.cartonTotalWeights.setText(cartonDetailsEntity.getTotalWeight());
        viewHolder.cartonCreatedDate.setText(formatDate(cartonDetailsEntity.getCreatedDateTime()));
        // Return the completed view to render on screen
        return convertView;
    }



    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }


    private static class DeliverOrderListViewHolder {

        public RadioButton cartonNumber;
        public TextView noOfProducts;
        public TextView orderId;
        public TextView cartonTotalWeights;
        public TextView cartonCreatedDate;


    }
}
