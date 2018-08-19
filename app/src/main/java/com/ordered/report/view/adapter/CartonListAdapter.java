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
import android.widget.Toast;

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

public class CartonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CartonDetailsJson> cartonItemModels;
    private OrderDetailsActivity orderDetailsActivity;
    private CartonListAdapterCallBack cartonListAdapterCallBack;
    private static final int EMPTY_VIEW = -1;

    public CartonListAdapter(Context context, List<CartonDetailsJson> cartonItemModels) {
        orderDetailsActivity = (OrderDetailsActivity) context;
        this.cartonItemModels = cartonItemModels;
        cartonListAdapterCallBack = orderDetailsActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(orderDetailsActivity).inflate(R.layout.adapter_cartonnumber_list_empty, parent, false);
            return new EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(orderDetailsActivity).inflate(R.layout.adapter_cartonnumber_list, parent, false);
            return new CartonListViewHolder(view);
        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof CartonListViewHolder){
            CartonListViewHolder holder = (CartonListViewHolder)viewHolder;

            final CartonDetailsJson cartonItemModel = cartonItemModels.get(position);

            holder.cartonNumber.setText("Carton Number:"+cartonItemModel.getCartonNumber());
            holder.noOfProducts.setText("No of Products:"+cartonItemModel.getOrderDetailsListViewModels().size()+"");



            holder.cartonCreatedBy.setText(cartonItemModel.getCreatedBy());
            holder.cartonCreatedDate.setText(formatDate(cartonItemModel.getCreatedDateTime()));
            holder.cartonShippingStatus.setVisibility(View.INVISIBLE);
            if(orderDetailsActivity.getView().equals(Constants.VIEW_PACKING)){
                holder.packingLayout.setVisibility(View.VISIBLE);
                if(cartonItemModel.getTotalWeight() == null || cartonItemModel.getTotalWeight().trim().isEmpty() || cartonItemModel.getTotalWeight().equals("0")){
                    holder.weightStatus.setImageDrawable(orderDetailsActivity.getDrawable(R.drawable.add_weight_48x));
                    holder.cartonTotalWeight.setText("Net Weight: 0kg");
                    holder.cartonShippingStatus.setVisibility(View.INVISIBLE);
                    holder.cartonTotalWeight.setTextColor(orderDetailsActivity.getResources().getColor(R.color.redDark));
                }else{
                    holder.cartonTotalWeight.setText("Net Weight: "+cartonItemModel.getTotalWeight());
                    holder.weightStatus.setImageDrawable(orderDetailsActivity.getDrawable(R.drawable.edit_weight_48x));
                    holder.cartonTotalWeight.setTextColor(orderDetailsActivity.getResources().getColor(R.color.colorBlack));

                    if(cartonItemModel.getDeliverDetailsGuid() != null){
                        holder.cartonShippingStatus.setVisibility(View.VISIBLE);
                    }
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

            weightLayout = itemView.findViewById(R.id.weight_layout);
            packingLayout = itemView.findViewById(R.id.packing_layout);
            mainLayout = itemView.findViewById(R.id.carton_main_layout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   CartonDetailsJson cartonDetailsJson = cartonItemModels.get(getAdapterPosition());
                   if(cartonDetailsJson.getDeliverDetailsGuid() != null){
                       Toast.makeText(orderDetailsActivity,"You cannot view this. Already delivered.",Toast.LENGTH_LONG).show();
                   }else{
                       orderDetailsActivity.setCartonDetailsJson(cartonDetailsJson);
                       cartonListAdapterCallBack.showProductDetailsListFragment();
                   }

                }
            });

            weightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartonDetailsJson cartonDetailsJson = cartonItemModels.get(getAdapterPosition());
                    if(cartonDetailsJson.getDeliverDetailsGuid() != null){
                        Toast.makeText(orderDetailsActivity,"You cannot add or edit. Already delivered.",Toast.LENGTH_LONG).show();
                    }else{
                        if(cartonDetailsJson.getOrderDetailsListViewModels().size() == 0){
                            Toast.makeText(orderDetailsActivity,"Carton box is empty. Please fill the box.",Toast.LENGTH_LONG).show();
                        }else{
                            showAlertDialog(getAdapterPosition());
                        }

                    }

                }
            });
        }
    }

    class  EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View view){
            super(view);

        }
    }


    public interface CartonListAdapterCallBack{
        void showProductDetailsListFragment();
    }
}
