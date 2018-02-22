package com.ordered.report.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ordered.report.R;


public class OrderedListViewHolder extends RecyclerView.ViewHolder {

    public TextView orderTitle;
    public TextView clientName;
    public TextView createdDate;
    public TextView createdBy;
    public TextView orderItemsCount;
    public TextView report;
    public ImageView orderImage;
    public View view;



    public OrderedListViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        orderTitle = (TextView) itemView.findViewById(R.id.ordered_list_order_id);
        clientName = (TextView) itemView.findViewById(R.id.ordered_list_client_name);
        createdDate = (TextView) itemView.findViewById(R.id.ordered_list_order_date);
        createdBy = (TextView) itemView.findViewById(R.id.ordered_list_created_by);
        orderItemsCount = (TextView) itemView.findViewById(R.id.ordered_list_ordered_items);
        report = (TextView) itemView.findViewById(R.id.report_generate);
        orderImage = (ImageView) itemView.findViewById(R.id.order_image);


    }
}
