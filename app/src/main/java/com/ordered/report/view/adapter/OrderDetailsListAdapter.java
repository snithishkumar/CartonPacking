package com.ordered.report.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 17/02/18.
 */

public class OrderDetailsListAdapter extends RecyclerView.Adapter<OrderDetailsListAdapter.OrderDetailsViewHolder>{


    private Context context = null;
    private List<OrderDetailsListViewModel>  orderCreationDetails = new ArrayList<>();
    private OrderDetailsActivity orderDetailsActivity;
    private OrderDetailsClickListeners orderDetailsClickListeners;
    //private String totalCartonCount;
    public OrderDetailsListAdapter(Context context, List<OrderDetailsListViewModel> orderDetailsListViewModels) {
        this.orderCreationDetails = orderDetailsListViewModels;
        this.context = context;
        orderDetailsActivity = (OrderDetailsActivity) context;
        orderDetailsClickListeners = orderDetailsActivity;
      //  this.totalCartonCount = totalCartonCount;
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
        OrderDetailsListViewModel productDetailsJson =  orderCreationDetails.get(position);
        setVal( holder.cartonNumber,productDetailsJson.getCartonNumber()+"");
        holder.productName.setText(productDetailsJson.getOrderItemName());
        holder.productGroupName.setText(productDetailsJson.getOrderItemGroup());
        setVal( holder.oneSize,productDetailsJson.getProductOneSize());
        setVal( holder.xs,productDetailsJson.getProductXS());
        setVal( holder.small,productDetailsJson.getProductS());
        setVal( holder.medium,productDetailsJson.getProductM());
        setVal( holder.large,productDetailsJson.getProductL());
        setVal( holder.xl,productDetailsJson.getProductXl());
        setVal( holder.xxl,productDetailsJson.getProductXxl());
        setVal( holder.xxxl,productDetailsJson.getProductXxxl());
        formDateTime(productDetailsJson.getOrderItemCreatedDateTime(),holder.createDateTime);
        formDateTime(productDetailsJson.getOrderItemCreatedDateTime(),holder.lastModifiedDateTime);

        setProductVal(holder.orderedOneSize,"OneSize",productDetailsJson.getOrderItemOneSize());
        setProductVal(holder.orderedXS,"XS",productDetailsJson.getOrderItemXS());
        setProductVal(holder.orderedSmall,"S",productDetailsJson.getOrderItemS());
        setProductVal(holder.orderedMedium,"M",productDetailsJson.getOrderItemM());
        setProductVal(holder.orderedLarge,"L",productDetailsJson.getOrderItemL());
        setProductVal(holder.orderedXl,"XL",productDetailsJson.getOrderItemXl());
        setProductVal(holder.orderedXxl,"XXL",productDetailsJson.getOrderItemXxl());
        setProductVal(holder.orderedXxxl,"XXXL",productDetailsJson.getOrderItemXxxl());




    }


    private void setProductVal(TextView textView,String key,String val){
        if(val != null && !val.isEmpty()){
            textView.setText(key+" --> "+val);
        }else{
            textView.setText(key+" --> 0");
        }
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
        return orderCreationDetails.size();
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

        private TextView orderedOneSize;
        private TextView orderedXS;
        private TextView orderedSmall;
        private TextView orderedMedium;
        private TextView orderedLarge;
        private TextView orderedXl;
        private TextView orderedXxl;
        private TextView orderedXxxl;

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
                   // orderDetailsActivity.getOrderDetailsListViewModels().remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

            orderedOneSize = v.findViewById(R.id.ordered_one_size_count);
            orderedXS = v.findViewById(R.id.ordered_l_count);
            orderedSmall = v.findViewById(R.id.ordered_xs_count);
            orderedMedium = v.findViewById(R.id.ordered_xl_count);
            orderedLarge = v.findViewById(R.id.ordered_s_count);
            orderedXl = v.findViewById(R.id.ordered_xxxl_count);
            orderedXxl = v.findViewById(R.id.ordered_m_count);
            orderedXxxl = v.findViewById(R.id.ordered_xxl_count);


            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderDetailsClickListeners.orderDetailsListOnClick(getAdapterPosition());
                }
            });*/

        }

    }


    public interface OrderDetailsClickListeners{
        void orderDetailsListOnClick(int position);
    }





}
