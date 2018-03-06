package com.ordered.report.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.OrderDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class CartonListAdapter extends RecyclerView.Adapter<CartonListAdapter.CartonListViewHolder> {

    private List<CartonDetailsJson> cartonItemModels;
    private OrderDetailsActivity orderDetailsActivity;
    private CartonListAdapterCallBack cartonListAdapterCallBack;

    public CartonListAdapter(Context context, List<CartonDetailsJson> cartonItemModels) {
        orderDetailsActivity = (OrderDetailsActivity) context;
        this.cartonItemModels = cartonItemModels;
        cartonListAdapterCallBack = orderDetailsActivity;
    }

    @Override
    public CartonListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderDetailsActivity).inflate(R.layout.adapter_cartonnumber_list, parent, false);

        return new CartonListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartonListViewHolder holder, int position) {
        final CartonDetailsJson cartonItemModel = cartonItemModels.get(position);

        holder.cartonNumber.setText("Carton Number:"+cartonItemModel.getCartonNumber());
        holder.noOfProducts.setText("No of Products:"+cartonItemModel.getOrderDetailsListViewModels().size()+"");

        holder.cartonCreatedBy.setText(cartonItemModel.getCreatedBy());
        holder.cartonCreatedDate.setText(formatDate(cartonItemModel.getCreatedDateTime()));
        if(orderDetailsActivity.getView().equals(Constants.VIEW_PACKING)){
            holder.weightLayout.setVisibility(View.VISIBLE);
            if(cartonItemModel.getTotalWeight() == null){
                holder.cartonWeightStatus.setText("Add Weight");
                holder.cartonTotalWeight.setText("Net Weight: 0kg");
                holder.cartonTotalWeight.setTextColor(orderDetailsActivity.getResources().getColor(R.color.redDark));
            }else{
                holder.cartonTotalWeight.setText("Net Weight: "+cartonItemModel.getTotalWeight());
                holder.cartonWeightStatus.setText("Change");
                holder.cartonTotalWeight.setTextColor(orderDetailsActivity.getResources().getColor(R.color.colorBlack));
            }

        }

    }



    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy hh:MM");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }

    AlertDialog alertDialog = null;
    private void showAlertDialog(final int pos) {
        //test
        LayoutInflater li = LayoutInflater.from(orderDetailsActivity);
        View promptsView = li.inflate(R.layout.catron_weight_view, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                orderDetailsActivity);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.carton_weight_text);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (userInput.getText().toString() != null && !userInput.getText().toString().isEmpty()) {
                                    cartonItemModels.get(pos).setTotalWeight(userInput.getText().toString()+"kg");

                                    alertDialog.dismiss();
                                    notifyDataSetChanged();
                                }

                            }
                        });
        // create alert dialog
         alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Carton Weight");
        // show it
        alertDialog.show();
        //end
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
        public LinearLayout weightLayout;
        public RelativeLayout mainLayout;

        public TextView cartonTotalWeight;
        public TextView cartonWeightStatus;


        public CartonListViewHolder(View itemView) {
            super(itemView);

            cartonNumber =  itemView.findViewById(R.id.carton_number);
            noOfProducts =  itemView.findViewById(R.id.products_count_carton_number);
            cartonCreatedBy =  itemView.findViewById(R.id.carton_created_date);
            cartonCreatedDate =  itemView.findViewById(R.id.carton_created_date);

            cartonTotalWeight =  itemView.findViewById(R.id.carton_net_weight_text);
            cartonWeightStatus =  itemView.findViewById(R.id.carton_net_weight_status);

            weightLayout = itemView.findViewById(R.id.weight_layout);
            mainLayout = itemView.findViewById(R.id.carton_main_layout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderDetailsActivity.setCartonDetailsJson(cartonItemModels.get(getAdapterPosition()));
                    cartonListAdapterCallBack.showProductDetailsListFragment();
                }
            });

            weightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertDialog(getAdapterPosition());
                }
            });
        }
    }


    public interface CartonListAdapterCallBack{
        void showProductDetailsListFragment();
    }
}
