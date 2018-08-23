package com.ordered.report.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.activity.OrderViewActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderViewDeliveryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DeliveryDetailsEntity> deliveryDetailsEntities;
    private OrderViewActivity orderViewActivity;
    private static final int EMPTY_VIEW = -1;

    public OrderViewDeliveryListAdapter(Context context, List<DeliveryDetailsEntity> deliveryDetailsEntities) {
        this.context = context;
        this.deliveryDetailsEntities = deliveryDetailsEntities;

        orderViewActivity = (OrderViewActivity) context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(orderViewActivity).inflate(R.layout.adpt_delivery_list_empty, parent, false);

            return new OrderViewDeliveryListAdapter.EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.adpater_deliver_list, parent, false);
            return new OrderViewDeliveryListAdapter.DeliveredListViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof OrderViewDeliveryListAdapter.DeliveredListViewHolder){
            DeliveryListAdapter.DeliveredListViewHolder holder = (DeliveryListAdapter.DeliveredListViewHolder)viewHolder;

            final DeliveryDetailsEntity deliveryDetailsEntity = deliveryDetailsEntities.get(position);

            holder.deliverId.setText(deliveryDetailsEntity.getDeliveryId());
            holder.deliverType.setText(deliveryDetailsEntity.getDeliveringType().toString());
            holder.placeOfDelivery.setText(deliveryDetailsEntity.getPlaceOfDelivery());
            holder.placeOfLoading.setText(deliveryDetailsEntity.getPlaceOfLoading());
            holder.portOfDischarge.setText(deliveryDetailsEntity.getPortOfDischarge());



            holder.createdBy.setText("admin");

        }


    }






    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }

    @Override
    public int getItemViewType(int position) {
        if (deliveryDetailsEntities.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return deliveryDetailsEntities.size()  > 0 ? deliveryDetailsEntities.size() : 1;
    }





    public class DeliveredListViewHolder extends RecyclerView.ViewHolder {

        public TextView deliverId;
        public TextView deliverType;
        public TextView placeOfLoading;
        public TextView placeOfDelivery;
        public TextView portOfDischarge;
        public TextView createdBy;
        public TextView createdDateTime;
        public TextView report;
        public View view;



        public DeliveredListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            deliverId = itemView.findViewById(R.id.delivery_list_deliver_id);
            deliverType = (TextView) itemView.findViewById(R.id.delivery_list_type);
            placeOfLoading = (TextView) itemView.findViewById(R.id.delivery_place_of_loading);
            placeOfDelivery = (TextView) itemView.findViewById(R.id.delivery_place_of_delivery);
            portOfDischarge = (TextView) itemView.findViewById(R.id.delivery_port_of_discharge);
            createdBy = (TextView) itemView.findViewById(R.id.delivery_list_created_by);
            createdDateTime = (TextView) itemView.findViewById(R.id.delivery_list_lastmodified_val);


        }
    }

    class  EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View view){
            super(view);

        }
    }
}
