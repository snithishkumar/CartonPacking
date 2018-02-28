package com.ordered.report.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.json.models.CartonItemModel;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.CartonItemEntity;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class CartonListAdapter extends RecyclerView.Adapter<CartonListAdapter.CartonListViewHolder> {

    private List<CartonDetailsJson> cartonItemModels;
    private OrderDetailsActivity orderDetailsActivity;

    public CartonListAdapter(Context context, List<CartonDetailsJson> cartonItemModels) {
        orderDetailsActivity = (OrderDetailsActivity) context;
        this.cartonItemModels = cartonItemModels;
    }

    @Override
    public CartonListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderDetailsActivity).inflate(R.layout.adapter_cartonnumber_list, parent, false);

        return new CartonListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartonListViewHolder holder, int position) {
        final CartonDetailsJson cartonItemModel = cartonItemModels.get(position);

        holder.cartonNumber.setText(cartonItemModel.getCartonNumber());
        holder.noOfProducts.setText(cartonItemModel.getNoOfProducts()+"");

        holder.cartonCreatedBy.setText(cartonItemModel.getCreatedBy());
        holder.cartonCreatedDate.setText(formatDate(cartonItemModel.getCreatedDateTime()));

    }



    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy hh:MM");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }



    @Override
    public int getItemCount() {
        return cartonItemModels.size();
    }


    public class CartonListViewHolder extends RecyclerView.ViewHolder {

        public TextView cartonNumber;
        public TextView noOfProducts;
        public TextView cartonCreatedBy;
        public TextView cartonCreatedDate;



        public CartonListViewHolder(View itemView) {
            super(itemView);

            cartonNumber =  itemView.findViewById(R.id.carton_number);
            noOfProducts =  itemView.findViewById(R.id.products_count_carton_number);
            cartonCreatedBy =  itemView.findViewById(R.id.carton_created_date);
            cartonCreatedDate =  itemView.findViewById(R.id.carton_created_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }

}
