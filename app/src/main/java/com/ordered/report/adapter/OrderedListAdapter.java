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

import com.ordered.report.HomeActivity;
import com.ordered.report.R;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.utils.Utils;

import java.util.Date;
import java.util.List;

public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListViewHolder> {

    private Context context;
    private List<OrderEntity> cartonbookEntities;
    private HomeActivity homeActivity;

    public OrderedListAdapter(Context context, List<OrderEntity> cartonbookEntities) {
        this.context = context;
        homeActivity = (HomeActivity) context;
        this.cartonbookEntities = cartonbookEntities;
    }

    @Override
    public OrderedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ordered_list, parent, false);
        return new OrderedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderedListViewHolder holder, int position) {
        final OrderEntity orderEntity = cartonbookEntities.get(position);
        holder.orderTitle.setText(orderEntity.getOrderId());
        holder.clientName.setText("ClientName :"+ orderEntity.getClientName());
        if(orderEntity.getOrderType() == OrderType.ORDERED) {
            holder.orderImage.setImageResource(R.mipmap.ordered_icon);
        }else if(orderEntity.getOrderType() == OrderType.PACKING){
            holder.orderImage.setImageResource(R.mipmap.packing_icon);
        }else{
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
                if(orderEntity.getOrderType() == OrderType.ORDERED) {
                    showAlertDialog();
                }else if(orderEntity.getOrderType() == OrderType.PACKING){
                    homeActivity.showProductList();
                }else{
                    // generate report here
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartonbookEntities.size();
    }

    private void showAlertDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Enter total number of carton");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        homeActivity.showProductList();
                    }
                });

        alertDialog.show();
    }
}
