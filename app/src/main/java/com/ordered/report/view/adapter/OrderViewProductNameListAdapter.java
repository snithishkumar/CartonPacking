package com.ordered.report.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.activity.OrderViewActivity;
import com.ordered.report.view.activity.OrderViewDetailsActivity;
import com.ordered.report.view.models.OrderViewListModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderViewProductNameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = -1;

    private List<ProductDetailsEntity> productDetailsEntities = new ArrayList<>();
    private OrderViewDetailsActivity orderViewDetailsActivity;


    public OrderViewProductNameListAdapter(List<ProductDetailsEntity> productDetailsEntities, OrderViewDetailsActivity orderViewDetailsActivity) {
        this.orderViewDetailsActivity = orderViewDetailsActivity;
        this.productDetailsEntities = productDetailsEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            View view = LayoutInflater.from(orderViewDetailsActivity).inflate(R.layout.adapt_order_list_empty, parent, false);

            return new OrderViewProductNameListAdapter.EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(orderViewDetailsActivity).inflate(R.layout.adapter_order_view_product_list, parent, false);
            return new OrderViewProductNameListAdapter.OrderViewProductNameListViewHolder(view);
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof OrderViewProductNameListAdapter.OrderViewProductNameListViewHolder) {


            OrderViewProductNameListAdapter.OrderViewProductNameListViewHolder holder = (OrderViewProductNameListAdapter.OrderViewProductNameListViewHolder) viewHolder;
            ProductDetailsEntity productDetailsEntity = productDetailsEntities.get(position);
            setVal(productDetailsEntity.getProductName(),holder.productName);
            setVal(productDetailsEntity.getCartonNumber().getCartonNumber(),holder.cartonNo);
            setVal(productDetailsEntity.getProductGroup(),holder.productCategory);

            setVal(productDetailsEntity.getOneSize(), holder.productOneSize,"One Size");
            setVal(productDetailsEntity.getXs(), holder.productXS,"XS");
            setVal(productDetailsEntity.getS(), holder.productS,"s");
            setVal(productDetailsEntity.getM(), holder.productM,"M");
            setVal(productDetailsEntity.getL(), holder.productL,"L");
            setVal(productDetailsEntity.getXl(), holder.productXL,"XL");
            setVal(productDetailsEntity.getXxl(), holder.productXXL,"XXL");
            setVal(productDetailsEntity.getXxxl(), holder.productXXXL,"XXXL");
            setVal(formatDate(productDetailsEntity.getCreatedDateTime()), holder.createDateTime);
            setVal(formatDate(productDetailsEntity.getLastModifiedDateTime()), holder.modifiedDateTime);
        }
    }

    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }

    private void setVal(String val, TextView textView,String key) {
        if (val != null && !val.trim().isEmpty()) {
            textView.setText(key +" -> "+val);
        }else{
            textView.setText(key +" -> 0");
        }
    }



    private void setVal(String val, TextView textView) {
        if (val != null) {
            textView.setText(val);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (productDetailsEntities.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return productDetailsEntities.size() > 0 ? productDetailsEntities.size() : 1;
    }


    public class OrderViewProductNameListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_view_pro_name)
        TextView productName;
        @BindView(R.id.order_view_pro_carton_no)
        TextView cartonNo;
        @BindView(R.id.order_view_pro_category)
        TextView productCategory;
        @BindView(R.id.order_view_pro_one_size_count)
        TextView productOneSize;
        @BindView(R.id.order_view_pro_xs_count)
        TextView productXS;
        @BindView(R.id.order_view_pro_s_count)
        TextView productS;
        @BindView(R.id.order_view_pro_m_count)
        TextView productM;
        @BindView(R.id.order_view_pro_l_count)
        TextView productL;
        @BindView(R.id.order_view_pro_xl_count)
        TextView productXL;
        @BindView(R.id.order_view_pro_xxl_count)
        TextView productXXL;
        @BindView(R.id.order_view_pro_xxxl_count)
        TextView productXXXL;
        @BindView(R.id.order_details_created_date)
        TextView createDateTime;
        @BindView(R.id.order_details_lastmodified_val)
        TextView modifiedDateTime;


        public OrderViewProductNameListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View view) {
            super(view);

        }
    }
}
