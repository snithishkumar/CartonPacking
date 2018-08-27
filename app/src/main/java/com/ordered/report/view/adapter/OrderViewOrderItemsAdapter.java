package com.ordered.report.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.view.activity.OrderViewActivity;
import com.ordered.report.view.models.OrderViewOrderedItemsListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderViewOrderItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = -1;

    private List<OrderViewOrderedItemsListModel> orderViewOrderedItemsListModels = new ArrayList<>();
    private OrderViewActivity orderViewActivity;

    public OrderViewOrderItemsAdapter(List<OrderViewOrderedItemsListModel> orderViewOrderedItemsListModels, OrderViewActivity orderViewActivity) {
        this.orderViewOrderedItemsListModels = orderViewOrderedItemsListModels;
        this.orderViewActivity = orderViewActivity;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            View view = LayoutInflater.from(orderViewActivity).inflate(R.layout.adapt_order_list_empty, parent, false);

            return new OrderViewOrderItemsAdapter.EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(orderViewActivity).inflate(R.layout.adapter_order_view_ordered_item_list, parent, false);
            return new OrderViewOrderItemsAdapter.OrderViewOrderItemsViewHolder(view);
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof OrderViewOrderItemsAdapter.OrderViewOrderItemsViewHolder) {
            OrderViewOrderItemsAdapter.OrderViewOrderItemsViewHolder holder = (OrderViewOrderItemsAdapter.OrderViewOrderItemsViewHolder) viewHolder;
            OrderViewOrderedItemsListModel orderViewOrderedItemsListModel = orderViewOrderedItemsListModels.get(position);
            setVal(holder.productName,orderViewOrderedItemsListModel.getProductStyle());
            setVal(holder.productGroup,orderViewOrderedItemsListModel.getProductGroup());
            setVal(holder.productColor,orderViewOrderedItemsListModel.getColorStyle());

            setVal(holder.orderedOneSize,orderViewOrderedItemsListModel.getOneSize(),"One Size");
            setVal(holder.orderedXS,orderViewOrderedItemsListModel.getXs(),"XS");
            setVal(holder.orderedS,orderViewOrderedItemsListModel.getS(),"S");
            setVal(holder.orderedM,orderViewOrderedItemsListModel.getM(),"M");
            setVal(holder.orderedL,orderViewOrderedItemsListModel.getL(),"L");
            setVal(holder.orderedXL,orderViewOrderedItemsListModel.getXl(),"XL");
            setVal(holder.orderedXXL,orderViewOrderedItemsListModel.getXxl(),"XXL");
            setVal(holder.orderedXXXL,orderViewOrderedItemsListModel.getXxxl(),"XXXL");


            setVal(holder.deliveryCount,orderViewOrderedItemsListModel.getDeliveryCounts());
            setVal(holder.cartonCount,orderViewOrderedItemsListModel.getCartonCounts());


            setVal(holder.processedOneSize,orderViewOrderedItemsListModel.getProcessedOneSize(),"One Size");
            setVal(holder.processedXS,orderViewOrderedItemsListModel.getProcessedXS(),"XS");
            setVal(holder.processedS,orderViewOrderedItemsListModel.getProcessedS(),"S");
            setVal(holder.processedM,orderViewOrderedItemsListModel.getProcessedM(),"M");
            setVal(holder.processedL,orderViewOrderedItemsListModel.getProcessedL(),"L");
            setVal(holder.processedXL,orderViewOrderedItemsListModel.getProcessedXL(),"XL");
            setVal(holder.processedXXL,orderViewOrderedItemsListModel.getProcessedXXL(),"XXL");
            setVal(holder.processedXXXL,orderViewOrderedItemsListModel.getProcessedXXXL(),"XXXL");



            setVal(holder.deliveredOneSize,orderViewOrderedItemsListModel.getDeliveryOneSize());
            setVal(holder.deliveredXS,orderViewOrderedItemsListModel.getDeliveryXS());
            setVal(holder.deliveredS,orderViewOrderedItemsListModel.getDeliveryS());
            setVal(holder.deliveredM,orderViewOrderedItemsListModel.getDeliveryM());
            setVal(holder.deliveredL,orderViewOrderedItemsListModel.getDeliveryL());
            setVal(holder.deliveredXL,orderViewOrderedItemsListModel.getDeliveryXL());
            setVal(holder.deliveredXXL,orderViewOrderedItemsListModel.getDeliveryXXL());
            setVal(holder.deliveredXXXL,orderViewOrderedItemsListModel.getDeliveryXXXL());



            setVal(holder.unProcessedOneSize,orderViewOrderedItemsListModel.getUnProcessedOneSize(),"One Size");
            setVal(holder.unProcessedXS,orderViewOrderedItemsListModel.getUnProcessedXS(),"XS");
            setVal(holder.unProcessedS,orderViewOrderedItemsListModel.getUnProcessedS(),"S");
            setVal(holder.unProcessedM,orderViewOrderedItemsListModel.getUnProcessedM(),"M");
            setVal(holder.unProcessedL,orderViewOrderedItemsListModel.getUnProcessedL(),"L");
            setVal(holder.unProcessedXL,orderViewOrderedItemsListModel.getUnProcessedXL(),"XL");
            setVal(holder.unProcessedXXL,orderViewOrderedItemsListModel.getUnProcessedXXL(),"XXL");
            setVal(holder.unProcessedXXXL,orderViewOrderedItemsListModel.getUnProcessedXXXL(),"XXXL");

        }
    }


    private void setVal(TextView textView,String val) {
        if (val != null) {
            textView.setText(val);
        }
    }


    private void setVal(TextView textView,String val,String key) {
        if (val != null) {
            textView.setText(key + "->" +val);
        }else{
            textView.setText(key + "-> 0");
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (orderViewOrderedItemsListModels.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return orderViewOrderedItemsListModels.size() > 0 ? orderViewOrderedItemsListModels.size() : 1;
    }


    public class OrderViewOrderItemsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adpt_order_view_order_items_prod_name)
         TextView productName;
        @BindView(R.id.product_color_sel_value)
         TextView productColor;
        @BindView(R.id.adpt_order_view_order_items_prod_group)
         TextView productGroup;

        @BindView(R.id.order_view_order_items_one_size_count)
         TextView orderedOneSize;
        @BindView(R.id.order_view_order_items_xs_count)
         TextView orderedXS;
        @BindView(R.id.order_view_order_items_s_count)
         TextView orderedS;
        @BindView(R.id.order_view_order_items_m_count)
         TextView orderedM;
        @BindView(R.id.order_view_order_items_l_count)
         TextView orderedL;
        @BindView(R.id.order_view_order_items_xl_count)
         TextView orderedXL;
        @BindView(R.id.order_view_order_items_xxl_count)
         TextView orderedXXL;
        @BindView(R.id.order_view_order_items_xxxl_count)
         TextView orderedXXXL;

        @BindView(R.id.adapter_order_view_delivery_count)
         TextView deliveryCount;
        @BindView(R.id.adapter_order_view_carton_count)
         TextView cartonCount;

        @BindView(R.id.processed_one_size_count)
         TextView processedOneSize;
        @BindView(R.id.processed_xs_count)
         TextView processedXS;
        @BindView(R.id.processed_s_count)
         TextView processedS;
        @BindView(R.id.processed_m_count)
         TextView processedM;
        @BindView(R.id.processed_l_count)
         TextView processedL;
        @BindView(R.id.processed_xl_count)
         TextView processedXL;
        @BindView(R.id.processed_xxl_count)
         TextView processedXXL;
        @BindView(R.id.processed_xxxl_count)
         TextView processedXXXL;

        @BindView(R.id.delivered_one_size)
         TextView deliveredOneSize;
        @BindView(R.id.delivered_details_xs)
         TextView deliveredXS;
        @BindView(R.id.delivered_details_s)
         TextView deliveredS;
        @BindView(R.id.delivered_details_m)
         TextView deliveredM;
        @BindView(R.id.delivered_details_l)
         TextView deliveredL;
        @BindView(R.id.delivered_xl)
         TextView deliveredXL;
        @BindView(R.id.delivered_xxl)
         TextView deliveredXXL;
        @BindView(R.id.delivered_xxxl)
         TextView deliveredXXXL;


        @BindView(R.id.unprocessed_one_size_count)
         TextView unProcessedOneSize;
        @BindView(R.id.unprocessed_xs_count)
         TextView unProcessedXS;
        @BindView(R.id.unprocessed_s_count)
         TextView unProcessedS;
        @BindView(R.id.unprocessed_m_count)
         TextView unProcessedM;
        @BindView(R.id.unprocessed_l_count)
         TextView unProcessedL;
        @BindView(R.id.unprocessed_xl_count)
         TextView unProcessedXL;
        @BindView(R.id.unprocessed_xxl_count)
         TextView unProcessedXXL;
        @BindView(R.id.unprocessed_xxxl_count)
         TextView unProcessedXXXL;


        public OrderViewOrderItemsViewHolder(View view) {
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
