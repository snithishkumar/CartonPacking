package com.ordered.report.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ordered.report.R;
import com.ordered.report.json.models.CartonInvoiceSummary;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.PdfService;
import com.ordered.report.services.PdfServiceReport;
import com.ordered.report.utils.Utils;
import com.ordered.report.view.activity.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DeliveryDetailsEntity> deliveryDetailsEntities;
    private HomeActivity homeActivity;
    private boolean isPopupShow = false;
    private static final int EMPTY_VIEW = -1;

    public HistoryListAdapter(Context context, List<DeliveryDetailsEntity> deliveryDetailsEntities) {
        this.context = context;
        this.deliveryDetailsEntities = deliveryDetailsEntities;
        homeActivity = (HomeActivity) context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.adpt_delivery_list_empty, parent, false);

            return new EmptyViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.ordered_row, parent, false);
            return new DeliveredListViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
       if(viewHolder instanceof DeliveredListViewHolder){
           DeliveredListViewHolder holder = (DeliveredListViewHolder)viewHolder;

           final DeliveryDetailsEntity deliveryDetailsEntity = deliveryDetailsEntities.get(position);

           holder.deliverId.setText(deliveryDetailsEntity.getDeliveryId());
           holder.deliverType.setText(deliveryDetailsEntity.getDeliveringType().toString());


           String orderGuids = deliveryDetailsEntity.getOrderGuids();
           int totalOrderCount = 0;
           if(orderGuids != null){
               JsonParser jsonParser = new JsonParser();
               JsonArray orderGuidsOrderList = (JsonArray)jsonParser.parse(orderGuids);
               totalOrderCount = orderGuidsOrderList.size();
           }

           Map<String,Long> countDetails =  homeActivity.getOrderedService().getCartonCount(deliveryDetailsEntity);

           String cartonCounts = String.valueOf(countDetails.get("cartonCounts"));
           String productCounts = String.valueOf(countDetails.get("productCounts"));

           holder.totalOrderCount.setText(String.valueOf(totalOrderCount));
           holder.totalCartonCount.setText(cartonCounts);
           holder.totalProductCount.setText(productCounts);
           holder.placeOfLoading.setText(deliveryDetailsEntity.getPlaceOfLoading());
           holder.placeOfDelivery.setText(deliveryDetailsEntity.getPlaceOfDelivery());
           holder.placeOfDischarge.setText(deliveryDetailsEntity.getPortOfDischarge());
           holder.createdOn.setText(deliveryDetailsEntity.getDeliveryId());
           holder.modifiedOn.setText(deliveryDetailsEntity.getDeliveryId());




       }


    }

    private void showPopup(View v,final DeliveryDetailsEntity deliveryDetailsEntity) {
        isPopupShow = true;
        PopupMenu popup = new PopupMenu(context, v);
        popup.inflate(R.menu.cotton_book_list_popup_item);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {

                    case R.id.mail_report:
                        PdfService pdfService = new PdfService(homeActivity);
                        List<CartonInvoiceSummary> cartonInvoiceSummaryList = pdfService.getCartonInvoiceSummary(deliveryDetailsEntity);
                        pdfService.createPdfReport(homeActivity,cartonInvoiceSummaryList);
                        // homeActivity.showProgress();
                       /* CartonInvoiceSummary cartonInvoiceSummary = homeActivity.getCartonInvoiceSummary(orderEntity);
                        String pdfFile = homeActivity.createPdfReport(cartonInvoiceSummary);
                        ArrayList<Uri> uris = new ArrayList<>();
                        try {
                            if (pdfFile != null) {
                                File zipFile = new File(pdfFile);
                                Uri uri = Uri.fromFile(zipFile);
                                uris.add(uri);
                            }
                            Intent intent = getEmailIntent(uris);
                            context.startActivity(Intent.createChooser(intent, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context,
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }*/
                        break;

                    case R.id.package_report:
                        PdfServiceReport pdfServiceReport = new PdfServiceReport(homeActivity,deliveryDetailsEntity);
                        List<CartonInvoiceSummary> cartonInvoiceSummaries = pdfServiceReport.getCartonInvoiceSummary();
                        pdfServiceReport.createPDF(cartonInvoiceSummaries);
                        // homeActivity.generateReport(cottonBookListEntity);

                        break;
                }
                return false;
            }
        });
        popup.show();
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                isPopupShow = false;
            }
        });
    }


    private int getOrderItemsCount(OrderEntity orderEntity) {
        String orderedItems = orderEntity.getOrderedItems();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(orderedItems);
        return jsonArray.size();
    }


    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }

    @Override
    public int getItemViewType(int position) {
        if (deliveryDetailsEntities.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return deliveryDetailsEntities.size()  > 0 ? deliveryDetailsEntities.size() : 1;
    }




    private Intent getEmailIntent(ArrayList<Uri> urls) {
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("multipart/mixed");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, urls);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, " ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, " ");
        return emailIntent;
    }


    public class DeliveredListViewHolder extends RecyclerView.ViewHolder {

        public TextView deliverId;
        public TextView deliverType;
        public TextView totalOrderCount;
        public TextView totalCartonCount;
        public TextView totalProductCount;
        public TextView placeOfLoading;
        public TextView placeOfDelivery;
        public TextView placeOfDischarge;
        public TextView createdOn;
        public TextView modifiedOn;
        public View view;



        public DeliveredListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            //report_list_deliver_id
            //report_list_type
            //report_list_order_count
            //report_list_carton_count
            //report_list_prod_count
            //report_list_place_of_loading
            //report_list_place_of_delivery
            //report_list_port_of_discharge
            //report_list_created_on
            //report_list_lastmodified_val
            deliverId = (TextView) itemView.findViewById(R.id.report_list_deliver_id);
            deliverType = itemView.findViewById(R.id.report_list_type);
            totalOrderCount = (TextView) itemView.findViewById(R.id.report_list_order_count);
            totalCartonCount = (TextView) itemView.findViewById(R.id.report_list_carton_count);
            totalProductCount = (TextView) itemView.findViewById(R.id.report_list_prod_count);
            placeOfLoading = (TextView) itemView.findViewById(R.id.report_list_place_of_loading);
            placeOfDelivery = (TextView) itemView.findViewById(R.id.report_list_place_of_delivery);
            placeOfDischarge = (TextView) itemView.findViewById(R.id.report_list_port_of_discharge);
            createdOn = (TextView) itemView.findViewById(R.id.report_list_created_on);
            modifiedOn = (TextView) itemView.findViewById(R.id.report_list_lastmodified_val);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup(view,deliveryDetailsEntities.get(getAdapterPosition()));
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
