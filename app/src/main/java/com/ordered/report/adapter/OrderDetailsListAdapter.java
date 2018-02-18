package com.ordered.report.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ordered.report.HomeActivity;
import com.ordered.report.R;
import com.ordered.report.json.models.ProductDetailsJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 17/02/18.
 */

public class OrderDetailsListAdapter extends RecyclerView.Adapter<OrderDetailsListAdapter.OrderDetailsViewHolder>{


    private Context context = null;
    private List<ProductDetailsJson> productEntities = new ArrayList<>();
    private HomeActivity homeActivity;
    public OrderDetailsListAdapter(Context context, List<ProductDetailsJson> productEntities) {
        this.productEntities = productEntities;
        this.context = context;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public OrderDetailsListAdapter.OrderDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_order_detail_list, parent, false);

        return new OrderDetailsListAdapter.OrderDetailsViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(OrderDetailsViewHolder holder, int position) {
        ProductDetailsJson productDetailsJson =  productEntities.get(position);
        setVal( holder.cartonNumber,productDetailsJson.getCartonNumber()+"");
        holder.productName.setText(productDetailsJson.getProductName());
        holder.productGroupName.setText(productDetailsJson.getProductGroup());
        setVal( holder.oneSize,productDetailsJson.getOneSize());
        setVal( holder.xs,productDetailsJson.getXs());
        setVal( holder.small,productDetailsJson.getS());
        setVal( holder.medium,productDetailsJson.getM());
        setVal( holder.xl,productDetailsJson.getXl());
        setVal( holder.xxl,productDetailsJson.getXxl());
        setVal( holder.xxxl,productDetailsJson.getXxxl());
        holder.productGroupName.setText(productDetailsJson.getProductGroup());
        formDateTime(productDetailsJson.getCreatedDateTime(),holder.createDateTime);
        formDateTime(productDetailsJson.getLastModifiedDateTime(),holder.lastModifiedDateTime);

    }

    private void setVal(Button button,String val){
        if(val != null && !val.isEmpty()){
            button.setText(val);
        }else{
            button.setText("0");
        }

    }

    private void formDateTime(long milliSeconds,TextView createDateTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date(milliSeconds);
        String res =  simpleDateFormat.format(date);
        createDateTime.setText(res);
    }

    @Override
    public int getItemCount() {
        return productEntities.size();
    }


    public  class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        private Button cartonNumber;
        private TextView productName;
        private TextView productGroupName;
        private Button oneSize;
        private Button xs;
        private Button small;
        private Button medium;
        private Button large;
        private Button xl;
        private Button xxl;
        private Button xxxl;
        private TextView createDateTime;
        private TextView lastModifiedDateTime;
        private ImageView del;

        public OrderDetailsViewHolder(View v) {
            super(v);

            productName= v.findViewById(R.id.order_details_pro_name);
            productGroupName =  v.findViewById(R.id.order_details_pro_group);
            oneSize =  v.findViewById(R.id.order_details_one_size);
            xs =  v.findViewById(R.id.order_details_xs);
            small =  v.findViewById(R.id.order_details_s);
            medium =  v.findViewById(R.id.order_details_m);
            large =  v.findViewById(R.id.order_details_l);
            xl =  v.findViewById(R.id.order_details_xl);
            xxl =  v.findViewById(R.id.order_details_xxl);
            xxxl =  v.findViewById(R.id.order_details_xxxl);
            cartonNumber =  v.findViewById(R.id.order_details_carton_no);
            createDateTime = v.findViewById(R.id.order_details_created_date);
            lastModifiedDateTime = v.findViewById(R.id.order_details_lastmodified_val);
            del = v.findViewById(R.id.order_details_pro_del);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeActivity.getProductDetailsJsons().remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }

    }



    public void refresh(List<ProductDetailsJson> productEntities){
        this.productEntities=productEntities;
        notifyDataSetChanged();
    }
}
