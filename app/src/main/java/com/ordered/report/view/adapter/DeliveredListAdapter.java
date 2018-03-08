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
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ordered.report.R;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.json.models.CartonInvoiceSummary;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.PdfService;
import com.ordered.report.services.PdfServiceReport;
import com.ordered.report.utils.Utils;
import com.ordered.report.view.activity.HomeActivity;
import com.ordered.report.view.fragment.DeliveredFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliveredListAdapter extends RecyclerView.Adapter<DeliveredListAdapter.DeliveredListViewHolder> {

    private Context context;
    private List<OrderEntity> orderEntities;
    private HomeActivity homeActivity;
    private boolean isPopupShow = false;

    public DeliveredListAdapter(Context context, List<OrderEntity> orderEntities) {
        this.context = context;
        this.orderEntities = orderEntities;
        homeActivity = (HomeActivity) context;

    }

    @Override
    public DeliveredListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ordered_row, parent, false);
        return new DeliveredListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveredListViewHolder holder, int position) {
        final OrderEntity orderEntity = orderEntities.get(position);
       /* if (orderEntity.getOrderType().toString().equals(OrderType.DELIVERED.toString())) {
            holder.report.setVisibility(View.VISIBLE);
        }*/
        holder.orderTitle.setText(orderEntity.getOrderId());
        holder.clientName.setText(orderEntity.getClientName());
        holder.createdBy.setText(orderEntity.getCreatedBy());
        int orderCount = getOrderItemsCount(orderEntity);
        holder.orderItemsCount.setText(String.valueOf(orderCount));

        holder.reportPopUp.setVisibility(View.VISIBLE);
        holder.createdDate.setText(formatDate(orderEntity.getOrderedDate()));
        holder.orderImage.setImageResource(R.drawable.delivery_icon_1x);
        String date = null;
        if (orderEntity.getOrderedDate() != 0) {
            date = Utils.convertMiliToDate(new Date(Long.valueOf(orderEntity.getOrderedDate())));
            //   holder.productColor.setText(date);
        }

    }

    private void showPopup(View v,final OrderEntity orderEntity) {
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
                        CartonInvoiceSummary cartonInvoiceSummary = pdfService.getCartonInvoiceSummary(orderEntity);
                        pdfService.createPdfReport(homeActivity,cartonInvoiceSummary);
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
                        PdfServiceReport pdfServiceReport = new PdfServiceReport(homeActivity,orderEntity);
                         cartonInvoiceSummary = pdfServiceReport.getCartonInvoiceSummary();
                        pdfServiceReport.createPDF(cartonInvoiceSummary);
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
    public int getItemCount() {
        int si = orderEntities.size();
        return orderEntities.size();
    }

    private void showAlertDialog(final String order) {
        //test
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (userInput.getText().toString() != null && !userInput.getText().toString().isEmpty()) {
                                    homeActivity.showProductList(Integer.parseInt(userInput.getText().toString()), order);
                                }

                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        //end
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

        public TextView orderTitle;
        public TextView clientName;
        public TextView createdDate;
        public TextView createdBy;
        public TextView orderItemsCount;
        public TextView report;
        public ImageView orderImage;
        public View view;
        public ImageView reportPopUp;



        public DeliveredListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            orderTitle = (TextView) itemView.findViewById(R.id.ordered_list_order_id);
            clientName = (TextView) itemView.findViewById(R.id.ordered_list_client_name);
            createdDate = (TextView) itemView.findViewById(R.id.ordered_list_order_date);
            createdBy = (TextView) itemView.findViewById(R.id.ordered_list_created_by);
            orderItemsCount = (TextView) itemView.findViewById(R.id.ordered_list_ordered_items);
            orderImage = (ImageView) itemView.findViewById(R.id.order_image);
            reportPopUp = itemView.findViewById(R.id.ordered_list_reports);
            reportPopUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup(view,orderEntities.get(getAdapterPosition()));
                }
            });

        }
    }


}
