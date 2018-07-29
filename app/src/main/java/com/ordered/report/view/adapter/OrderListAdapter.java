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
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ordered.report.R;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.view.activity.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderEntity> orderEntities;
    private HomeActivity homeActivity;
    private static final int EMPTY_VIEW = -1;

    public OrderListAdapter(Context context, List<OrderEntity> orderEntities) {
        homeActivity = (HomeActivity) context;
        this.orderEntities = orderEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapt_order_list_empty, parent, false);

            return new EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapter_order_list, parent, false);
            return new OrderedListViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof  OrderedListViewHolder){
            OrderedListViewHolder holder = (OrderedListViewHolder)viewHolder;
            final OrderEntity orderEntity = orderEntities.get(position);

            holder.orderTitle.setText(orderEntity.getOrderId());
            holder.clientName.setText(orderEntity.getClientName());
            holder.createdBy.setText(orderEntity.getCreatedBy());
            int orderCount = getOrderItemsCount(orderEntity);
            holder.orderItemsCount.setText(String.valueOf(orderCount));
            holder.orderImage.setImageResource(R.drawable.order_icon_3);
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

    private void showAlertDialog(final String order) {
        //test
        LayoutInflater li = LayoutInflater.from(homeActivity);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                homeActivity);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (userInput.getText().toString() != null && !userInput.getText().toString().isEmpty()) {
                                    homeActivity.showOrderDetailsList(userInput.getText().toString(), order);
                                }

                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        //end
    }

    class  EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View view){
            super(view);

        }
    }

    public class OrderedListViewHolder extends RecyclerView.ViewHolder {

        public TextView orderTitle;
        public TextView clientName;
        public TextView createdDate;
        public TextView createdBy;
        public TextView orderItemsCount;
        public ImageView orderImage;



        public OrderedListViewHolder(View itemView) {
            super(itemView);
            orderTitle = (TextView) itemView.findViewById(R.id.ordered_list_order_id);
            clientName = (TextView) itemView.findViewById(R.id.ordered_list_client_name);
            createdDate = (TextView) itemView.findViewById(R.id.ordered_list_order_date);
            createdBy = (TextView) itemView.findViewById(R.id.ordered_list_created_by);
            orderItemsCount = (TextView) itemView.findViewById(R.id.ordered_list_ordered_items);
            orderImage = (ImageView) itemView.findViewById(R.id.order_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderEntity orderEntity =  orderEntities.get(getAdapterPosition());
                    showAlertDialog(orderEntity.getOrderGuid());
                }
            });
        }
    }

}
