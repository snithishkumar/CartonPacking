package com.ordered.report.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class ProductNameListAdapter extends RecyclerView.Adapter<ProductNameListAdapter.ProductNameViewHolder> {

    private List<OrderDetailsListViewModel> orderDetailsListViewModels;
    private OrderDetailsActivity orderDetailsActivity;
    private ProductNameListAdapterCallBack productNameListAdapterCallBack;

    public ProductNameListAdapter(Context context, List<OrderDetailsListViewModel> orderDetailsListViewModels) {
        orderDetailsActivity = (OrderDetailsActivity) context;
        this.orderDetailsListViewModels = orderDetailsListViewModels;
        productNameListAdapterCallBack = orderDetailsActivity;
    }

    @Override
    public ProductNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderDetailsActivity).inflate(R.layout.adapter_product_list, parent, false);

        return new ProductNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductNameViewHolder holder, int position) {
        final OrderDetailsListViewModel orderDetailsListViewModel = orderDetailsListViewModels.get(position);

        holder.productName.setText(orderDetailsListViewModel.getOrderItemName());
        holder.productGroup.setText(orderDetailsListViewModel.getOrderItemGroup());

        holder.productColor.setText(orderDetailsListViewModel.getOrderItemColor());

    }





    @Override
    public int getItemCount() {
        return orderDetailsListViewModels.size();
    }


    public class ProductNameViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;
        public TextView productGroup;
        public TextView productColor;


        public ProductNameViewHolder(View itemView) {
            super(itemView);
            productName =  itemView.findViewById(R.id.product_name_sel);
            productGroup = itemView.findViewById(R.id.product_group_sel);
            productColor =  itemView.findViewById(R.id.product_color_sel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderDetailsListViewModel orderDetailsListViewModel =  orderDetailsListViewModels.get(getAdapterPosition());
                    orderDetailsActivity.setOrderDetailsListViewModel(orderDetailsListViewModel);
                    productNameListAdapterCallBack.callBack();
                    return;
                }
            });
        }
    }

    public interface ProductNameListAdapterCallBack{
        void callBack();
    }

}
