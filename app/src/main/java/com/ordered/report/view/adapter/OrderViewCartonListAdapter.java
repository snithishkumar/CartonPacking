package com.ordered.report.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.OrderViewActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderViewCartonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CartonDetailsJson> cartonItemModels;
    private OrderViewActivity orderViewActivity;
    private static final int EMPTY_VIEW = -1;

    public OrderViewCartonListAdapter(Context context, List<CartonDetailsJson> cartonItemModels) {
        orderViewActivity = (OrderViewActivity) context;
        this.cartonItemModels = cartonItemModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(orderViewActivity).inflate(R.layout.adapter_cartonnumber_list_empty, parent, false);
            return new OrderViewCartonListAdapter.EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(orderViewActivity).inflate(R.layout.adapter_cartonnumber_list, parent, false);
            return new OrderViewCartonListAdapter.CartonListViewHolder(view);
        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof OrderViewCartonListAdapter.CartonListViewHolder){
            OrderViewCartonListAdapter.CartonListViewHolder holder = (OrderViewCartonListAdapter.CartonListViewHolder)viewHolder;

            final CartonDetailsJson cartonItemModel = cartonItemModels.get(position);

            holder.cartonNumber.setText("Carton Number:"+cartonItemModel.getCartonNumber());
            holder.noOfProducts.setText("No of Products:"+cartonItemModel.getOrderDetailsListViewModels().size()+"");



            holder.cartonCreatedBy.setText(cartonItemModel.getCreatedBy());
            holder.cartonCreatedDate.setText(formatDate(cartonItemModel.getCreatedDateTime()));
            holder.cartonShippingStatus.setVisibility(View.INVISIBLE);
            holder.weightStatus.setVisibility(View.GONE);
            if(cartonItemModel.getTotalWeight() == null || cartonItemModel.getTotalWeight().trim().isEmpty() || cartonItemModel.getTotalWeight().equals("0")){
                holder.cartonTotalWeight.setText("Net Weight: 0kg");
                holder.cartonShippingStatus.setVisibility(View.INVISIBLE);
                holder.cartonTotalWeight.setTextColor(orderViewActivity.getResources().getColor(R.color.redDark));
            }else{
                holder.cartonTotalWeight.setText("Net Weight: "+cartonItemModel.getTotalWeight());
                holder.cartonTotalWeight.setTextColor(orderViewActivity.getResources().getColor(R.color.colorBlack));

                if(cartonItemModel.getDeliverDetailsGuid() != null){
                    holder.cartonShippingStatus.setVisibility(View.VISIBLE);
                }
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (cartonItemModels.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }




    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }




    @Override
    public int getItemCount() {
        return cartonItemModels.size() > 0 ? cartonItemModels.size() : 1;
    }


    public class CartonListViewHolder extends RecyclerView.ViewHolder {

        public TextView cartonNumber;
        public TextView noOfProducts;
        public TextView cartonCreatedBy;
        public TextView cartonCreatedDate;
        public LinearLayout weightLayout;
        public LinearLayout packingLayout;
        public RelativeLayout mainLayout;

        public ImageView weightStatus;

        public TextView cartonTotalWeight;
        public TextView cartonShippingStatus;


        public CartonListViewHolder(View itemView) {
            super(itemView);

            cartonNumber =  itemView.findViewById(R.id.carton_number);
            noOfProducts =  itemView.findViewById(R.id.products_count_carton_number);
            cartonCreatedBy =  itemView.findViewById(R.id.carton_created_date);
            cartonCreatedDate =  itemView.findViewById(R.id.carton_created_date);
            weightStatus = itemView.findViewById(R.id.carton_net_weight_status);

            //
            cartonTotalWeight =  itemView.findViewById(R.id.carton_net_weight_text);
            cartonShippingStatus =  itemView.findViewById(R.id.carton_net_shipping_status);
            //carton_net_weight_status

            weightLayout = itemView.findViewById(R.id.weight_layout);
            packingLayout = itemView.findViewById(R.id.packing_layout);
            mainLayout = itemView.findViewById(R.id.carton_main_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartonDetailsJson cartonItemModel = cartonItemModels.get(getAdapterPosition());
                    ResponseData responseData = new ResponseData();
                    responseData.setData(cartonItemModel);
                    AppBus.getInstance().post(responseData);

                }
            });

        }
    }

    class  EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View view){
            super(view);

        }
    }
}
