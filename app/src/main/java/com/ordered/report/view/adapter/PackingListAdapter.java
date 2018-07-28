package com.ordered.report.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ordered.report.R;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 25/02/18.
 */

public class PackingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<OrderEntity> orderEntities;
    private HomeActivity homeActivity;
    private PackingListAdapterCallBack packingListAdapterCallBack;
    private static final int EMPTY_VIEW = -1;

    public PackingListAdapter(Context context, List<OrderEntity> orderEntities) {
        homeActivity = (HomeActivity) context;
        this.orderEntities = orderEntities;
        packingListAdapterCallBack = homeActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapt_packing_list_empty, parent, false);

            return new EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapter_order_list, parent, false);

            return new PackingListAdapter.PackingListViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
       if(viewHolder instanceof PackingListViewHolder){
           PackingListAdapter.PackingListViewHolder holder = (PackingListViewHolder)viewHolder;

           final OrderEntity orderEntity = orderEntities.get(position);

           holder.orderTitle.setText(orderEntity.getOrderId());
           holder.clientName.setText(orderEntity.getClientName());
           holder.createdBy.setText(orderEntity.getCreatedBy());
           int orderCount = getOrderItemsCount(orderEntity);
           holder.orderItemsCount.setText(String.valueOf(orderCount));
           holder.orderImage.setImageResource(R.drawable.order_packing);
           holder.createdDate.setText(formatDate(orderEntity.getOrderedDate()));
       }


    }


    private int getOrderItemsCount(OrderEntity orderEntity) {
        String orderedItems = orderEntity.getOrderedItems();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(orderedItems);
        return jsonArray.size();
    }


    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }

    @Override
    public int getItemViewType(int position) {
        if (orderEntities.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return orderEntities.size()  > 0 ? orderEntities.size() : 1;
    }

    class  EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View view){
            super(view);

        }
    }

    public class PackingListViewHolder extends RecyclerView.ViewHolder {

        public TextView orderTitle;
        public TextView clientName;
        public TextView createdDate;
        public TextView createdBy;
        public TextView orderItemsCount;
        public ImageView orderImage;
        public RelativeLayout relativeLayout;
        public ImageView deliveryView;



        public PackingListViewHolder(View itemView) {
            super(itemView);
            orderTitle = (TextView) itemView.findViewById(R.id.ordered_list_order_id);
            clientName = (TextView) itemView.findViewById(R.id.ordered_list_client_name);
            createdDate = (TextView) itemView.findViewById(R.id.ordered_list_order_date);
            createdBy = (TextView) itemView.findViewById(R.id.ordered_list_created_by);
            orderItemsCount = (TextView) itemView.findViewById(R.id.ordered_list_ordered_items);
            orderImage = (ImageView) itemView.findViewById(R.id.order_image);
            relativeLayout = itemView.findViewById(R.id.order_list_details_layout);
            deliveryView = itemView.findViewById(R.id.ordered_list_packing_delivery);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderEntity orderEntity =  orderEntities.get(getAdapterPosition());
                    packingListAdapterCallBack.showPackingDetails(orderEntity.getOrderGuid(),null);
                    return;
                }
            });

        }
    }


    public interface PackingListAdapterCallBack{
        void showPackingDetails(String orderGuid,String nextView);
    }
}
