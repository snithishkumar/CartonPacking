package com.ordered.report.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ordered.report.HomeActivity;
import com.ordered.report.R;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListViewHolder> {

    private Context context;
    private List<OrderEntity> orderEntities;
    private HomeActivity homeActivity;
    private OrderStatus orderStatus;

    public OrderedListAdapter(Context context, List<OrderEntity> orderEntities, OrderStatus orderStatus) {
        this.context = context;
        homeActivity = (HomeActivity) context;
        this.orderEntities = orderEntities;
        this.orderStatus = orderStatus;
    }

    @Override
    public OrderedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (orderStatus.toString().equals(OrderStatus.ORDERED.toString())) {
            view = LayoutInflater.from(context).inflate(R.layout.ordered_row, parent, false);
        }
        return new OrderedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderedListViewHolder holder, int position) {
        final OrderEntity orderEntity = orderEntities.get(position);
        holder.orderTitle.setText(orderEntity.getOrderId());
         holder.clientName.setText(orderEntity.getClientName());
        holder.createdBy.setText(orderEntity.getCreatedBy());
        int orderCount = getOrderItemsCount(orderEntity);
        holder.orderItemsCount.setText(String.valueOf(orderCount));

        holder.createdDate.setText(formatDate(orderEntity.getOrderedDate()));
        if (orderEntity.getOrderType() == OrderStatus.ORDERED) {
            holder.orderImage.setImageResource(R.mipmap.ordered_icon);
        } else if (orderEntity.getOrderType() == OrderStatus.PACKING) {
            holder.orderImage.setImageResource(R.mipmap.packing_icon);
        } else {
            holder.orderImage.setImageResource(R.mipmap.delivered_icon);
        }
        String date = null;
        if (orderEntity.getOrderedDate() != 0) {
            date = Utils.convertMiliToDate(new Date(Long.valueOf(orderEntity.getOrderedDate())));
            //   holder.createdDate.setText(date);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderEntity.getOrderType() == OrderStatus.ORDERED) {
                    showAlertDialog(orderEntity.getOrderGuid());
                } else if (orderEntity.getOrderType() == OrderStatus.PACKING) {
                     homeActivity.showPackingProductDetailsList(orderEntity.getOrderGuid());
                } else {
                    // generate report here
                }
            }
        });
    }



    private int getOrderItemsCount(OrderEntity orderEntity){
       String orderedItems =  orderEntity.getOrderedItems();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray)jsonParser.parse(orderedItems);
       return  jsonArray.size();
    }


    private String formatDate(long dateTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }

    @Override
    public int getItemCount() {
        return orderEntities.size();
    }

    private void showAlertDialog(final String order) {
        //test
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

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
                                homeActivity.showProductList(Integer.parseInt(userInput.getText().toString()),order);
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        //end

    }
}
