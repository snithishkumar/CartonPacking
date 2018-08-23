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
    OrderViewListAdapterCallBack orderViewListAdapterCallBack;

    private List<OrderViewListModel> orderViewListModels = new ArrayList<>();

    public OrderViewOrderListAdapter(List<OrderViewListModel> orderViewListModels, HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        orderViewListAdapterCallBack = (OrderViewListAdapterCallBack)homeActivity;
        this.orderViewListModels = orderViewListModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapt_order_list_empty, parent, false);

            return new OrderViewOrderListAdapter.EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adapter_order_view_list, parent, false);
            return new OrderViewOrderListAdapter.OrderViewOrderListViewHolder(view);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (orderViewListModels.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return orderViewListModels.size() > 0 ? orderViewListModels.size() : 1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof OrderViewOrderListViewHolder) {
            OrderViewOrderListViewHolder holder = (OrderViewOrderListViewHolder) viewHolder;
            OrderViewListModel orderViewListModel = orderViewListModels.get(position);
            setVal(orderViewListModel.getOrderId(), holder.orderId);
            setVal(orderViewListModel.getOrderStatus(), holder.orderStatus);
            setVal(orderViewListModel.getProductCount(), holder.productCount);
            setVal(orderViewListModel.getCartonCount(), holder.cartonCount);
            setVal(orderViewListModel.getDeliveryCount(), holder.deliveryCount);
            setVal(orderViewListModel.getOrderBy(), holder.orderBy);
            setVal(orderViewListModel.getOrderFrom(), holder.orderFrom);
            setVal(orderViewListModel.getOrderDateTime(), holder.orderDateTime);
        }
    }


    private void setVal(String val, TextView textView) {
        if (val != null) {
            textView.setText(val);
        }
    }

    public class OrderViewOrderListViewHolder extends RecyclerView.ViewHolder {
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
            orderStatus = itemView.findViewById(R.id.adapter_order_view_order_status);
            productCount = itemView.findViewById(R.id.adapter_order_view_order_count);
            cartonCount = itemView.findViewById(R.id.adapter_order_view_carton_count);
            deliveryCount = itemView.findViewById(R.id.adapter_order_view_delivery_count);
            orderBy = itemView.findViewById(R.id.adapter_order_view_order_by);
            orderFrom = itemView.findViewById(R.id.adapter_order_view_order_from);
            orderDateTime = itemView.findViewById(R.id.adapter_order_view_order_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderViewListAdapterCallBack.showDetailsList(orderViewListModels.get(getAdapterPosition()).getOrderGuid());
                    return;
                }
            });
        }

    }


    class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View view) {
            super(view);

        }
    }


    public interface OrderViewListAdapterCallBack{
        void showDetailsList(String orderGuid);
    }
}
