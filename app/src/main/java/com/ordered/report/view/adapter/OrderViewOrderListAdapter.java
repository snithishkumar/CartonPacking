package com.ordered.report.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.models.OrderViewListModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int EMPTY_VIEW = -1;
    private HomeActivity homeActivity;

    private List<OrderViewListModel> orderViewListModels = new ArrayList<>();

    public OrderViewOrderListAdapter(List<OrderViewListModel> orderViewListModels, HomeActivity homeActivity){
this.homeActivity = homeActivity;
this.orderViewListModels = orderViewListModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapt_order_list_empty, parent, false);

            return new OrderViewOrderListAdapter.EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapter_order_view_list, parent, false);
            return new OrderViewOrderListAdapter.OrderViewOrderListViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof OrderViewOrderListViewHolder){
            OrderViewOrderListViewHolder holder = (OrderViewOrderListViewHolder)viewHolder;
              orderViewListModels.get(position);
        }
    }

    public  class OrderViewOrderListViewHolder extends RecyclerView.ViewHolder{
        private TextView orderId;
        private TextView orderStatus;
        private TextView productCount;
        private TextView cartonCount;
        private TextView deliveryCount;
        private TextView orderBy;
        private TextView orderFrom;
        private TextView orderDateTime;




        public OrderViewOrderListViewHolder(View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.adapter_order_view_orderid);
              orderStatus= itemView.findViewById(R.id.adapter_order_view_order_status);
            productCount= itemView.findViewById(R.id.adapter_order_view_order_count);
            cartonCount= itemView.findViewById(R.id.adapter_order_view_carton_count);
            deliveryCount= itemView.findViewById(R.id.adapter_order_view_delivery_count);
            orderBy= itemView.findViewById(R.id.adapter_order_view_order_by);
            orderFrom= itemView.findViewById(R.id.adapter_order_view_order_from);
            orderDateTime= itemView.findViewById(R.id.adapter_order_view_order_date);
        }

    }



    class  EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View view){
            super(view);

        }
    }
}
