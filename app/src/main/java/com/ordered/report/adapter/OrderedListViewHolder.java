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
    public ImageView orderImage;
    public Button paymentStatus;
    public View view;

    public OrderedListViewHolder(View itemView, TextView orderTitle, TextView clientName, ImageView orderImage, TextView orderDate, Button paymentStatus) {
        super(itemView);
        this.orderTitle = orderTitle;
        this.clientName = clientName;
        this.createdDate = orderDate;
        this.orderImage = orderImage;
        this.paymentStatus = paymentStatus;
    }

    public OrderedListViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        orderTitle = (TextView) itemView.findViewById(R.id.order_title);
        clientName = (TextView) itemView.findViewById(R.id.client_name_view);
        orderImage = (ImageView) itemView.findViewById(R.id.song_cover);
       // createdDate = (TextView) itemView.findViewById(R.id.dateModifiedTextView);
     //   paymentStatus = (Button) itemView.findViewById(R.id.payment_type_view);
    }
}
