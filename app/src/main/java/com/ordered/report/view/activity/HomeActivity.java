package com.ordered.report.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ordered.report.R;
import com.ordered.report.view.adapter.OrderDetailsListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.ordered.report.view.fragment.AddProductFragment;
import com.ordered.report.view.fragment.CaptureCartonDetailsFragment;
import com.ordered.report.view.fragment.HomeFragment;
import com.ordered.report.view.fragment.PackingFragment;
import com.ordered.report.view.fragment.PackingProductDetailsListFragment;
import com.ordered.report.view.fragment.ProductDetailsListFragment;
import com.ordered.report.view.fragment.ProductListFragment;
import com.ordered.report.services.OrderedService;
import com.ordered.report.json.models.CartonInvoiceSummary;
import com.ordered.report.json.models.OrderCreationDetailsJson;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.ordered.report.utils.NumberToWord;
import com.ordered.report.utils.UtilService;

public class HomeActivity extends AppCompatActivity implements OrderDetailsListAdapter.OrderDetailsClickListeners{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ProgressDialog progressDialog = null;

    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    private OrderedService orderedService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orderedService = new OrderedService(this);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showOrderedFragment() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    public  void showCaptureCartonDetailsFragment(int cartonNo){
        CaptureCartonDetailsFragment captureCartonDetailsFragment = new CaptureCartonDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.NO_OF_COTTON, cartonNo);
        captureCartonDetailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper,captureCartonDetailsFragment).addToBackStack(null).commit();
    }



    public void showPackingListFragment() {
        PackingFragment packingFragment = new PackingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, packingFragment).addToBackStack(null).commit();
    }

    public void showProductList(int cartonNo, String order) {
        ProductDetailsListFragment productDetailsListFragment = new ProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.NO_OF_COTTON, cartonNo);
        bundle.putString(Constants.ORDER, order);
        productDetailsListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productDetailsListFragment).addToBackStack(null).commit();
    }


    public void showPackingProductDetailsList(String orderGuid) {
        PackingProductDetailsListFragment packingProductDetails = new PackingProductDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        packingProductDetails.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, packingProductDetails).addToBackStack(null).commit();

    }

    public void showSubProductList() {
        ProductListFragment productListFragment = new ProductListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, productListFragment).addToBackStack(null).commit();
    }
    public void showAddProductList(String orderGuid,int totalCotton) {
        AddProductFragment addProductFragment = new AddProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER, orderGuid);
        bundle.putInt(Constants.NO_OF_COTTON, totalCotton);
        addProductFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, addProductFragment).addToBackStack(null).commit();
    }
    public void showAddProductList() {
        AddProductFragment addProductFragment = new AddProductFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, addProductFragment).addToBackStack(null).commit();
    }

    public void backClicked(View view) {
        int size = getSupportFragmentManager().getBackStackEntryCount();
        if (size > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public OrderedService getOrderedService() {
        return orderedService;
    }

    private List<OrderDetailsListViewModel> orderDetailsListViewModels = new ArrayList<>();
    private OrderDetailsListViewModel orderDetailsListViewModel;

    public List<OrderDetailsListViewModel> getOrderDetailsListViewModels() {
        return orderDetailsListViewModels;
    }

    public OrderDetailsListViewModel getOrderDetailsListViewModel(){
        return orderDetailsListViewModel;
    }

    public void setOrderDetailsListViewModel(OrderDetailsListViewModel orderDetailsListViewModel){
        this.orderDetailsListViewModel = orderDetailsListViewModel;
    }

    @Override
    public void orderDetailsListOnClick(OrderDetailsListViewModel productDetailsJson, int totalCartonCount) {
        setOrderDetailsListViewModel(productDetailsJson);
        showCaptureCartonDetailsFragment(totalCartonCount);
    }

    public void showProgress() {
        Log.e("LoginActivity", "showProgress");
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void stopProgress() {
        Log.e("LoginActivity", "stopProgress");
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public CartonInvoiceSummary getCartonInvoiceSummary(OrderEntity orderEntity) {
        Gson gson = new Gson();
        String orderDetails = orderEntity.getOrderedItems();
        Type listType = new TypeToken<List<OrderCreationDetailsJson>>() {
        }.getType();

        List<OrderCreationDetailsJson> cottonItemEntities = gson.fromJson(orderDetails, listType);
        List<String> productList = new ArrayList<>();
        // Map<String, List<CottonItemEntity>> cartonListMap = new HashMap<>();
        List<OrderCreationDetailsJson> cottonItemEntityList = new ArrayList<>();
        Map<String, List<OrderCreationDetailsJson>> cartonMap = new HashMap<>();
        for (OrderCreationDetailsJson cottonItemEntity : cottonItemEntities) {
            productList.add(cottonItemEntity.getProductStyle());
            // cottonItemEntityList = cartonItemService.getCottonItemByStyle(cottonItemEntity.getCottonBookListGuid(), cottonItemEntity.getProductStyleCategory());
            cottonItemEntityList = new ArrayList<>();
            cottonItemEntityList.add(cottonItemEntity);
            cartonMap.put(cottonItemEntity.getProductCategory(), cottonItemEntityList);
        }

        String clientAddress = "Exporter\n M/s. GLOBAL IMPEX,\n 8/3401 PONTHIRUMALAI NAGAR\n PANDIAN NAGAR, TIRUPUR - 641602\n INDIA";
        CartonInvoiceSummary cartonInvoiceSummary = new CartonInvoiceSummary();
        cartonInvoiceSummary.setCartonMap(cartonMap);
        cartonInvoiceSummary.setCartonCount(Integer.parseInt(orderEntity.getCartonCounts()));
        cartonInvoiceSummary.setExporterAddress("Exporter\n M/s. GLOBAL IMPEX,\n 8/3401 PONTHIRUMALAI NAGAR\n PANDIAN NAGAR, TIRUPUR - 641602\n INDIA");
        cartonInvoiceSummary.setConsigneAddress("Consignee\n" + clientAddress);
        cartonInvoiceSummary.setOrderNo("Buyer Order No.& Date\n" + "ORDER No:");
        cartonInvoiceSummary.setTermsAndConditions("Terms of Delivery and payment\t\n" + "Accept");
        cartonInvoiceSummary.setTintNo("TIN NO. \t" + "Tint");
        cartonInvoiceSummary.setVessels("Vessel/Flight No.\n" + orderEntity.getOrderType());
        cartonInvoiceSummary.setExporteRef("Exporter Ref.\t\n IE CODE: 0410033006\n");
        cartonInvoiceSummary.setInvoiceWithDate("Invoice No.& Date\t\t\n" + "CREA2342345"+ "/DATE-" + UtilService.formatDateTime(new Date().getTime()));
        return cartonInvoiceSummary;
    }
    public String createPdfReport(CartonInvoiceSummary cartonInvoiceSummary) {
        Document doc = new Document(PageSize.A4);
        PdfWriter docWriter = null;

        DecimalFormat df = new DecimalFormat("0.00");
        String fileName = "Invoice_Report-" + UtilService.reportFormat(new Date().getTime());
        String FILE = Environment.getExternalStorageDirectory().toString() + "/sdcard/" + fileName + ".pdf";
        String root = Environment.getExternalStorageDirectory().toString() + "/sdcard/";
        File myDir = new File(root);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        try {

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font smallFornt = new Font(Font.FontFamily.TIMES_ROMAN, 10);

            //file path
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(FILE));

            //document header attributes
            doc.addAuthor("Invoice");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("Rohin");
            doc.addTitle("Report for Invoice");
            doc.setPageSize(PageSize.LETTER);

            //open document
            doc.open();

            //create a paragraph
            Paragraph paragraph = new Paragraph("");
            Paragraph paragraph1 = new Paragraph();
            //specify column widths
            float[] columnWidths = {2.5f, 2.5f, 2.5f, 2.5f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);

            //insert an empty row
            insertCell(table, "INVOICE", Element.ALIGN_CENTER, 4, bfBold12, "normal");

            //create section heading by cell merging
            //firstCell
            PdfPCell exporterCell = getInsertCell(cartonInvoiceSummary.getExporterAddress(), Element.ALIGN_LEFT, 2, bf12);
            table.addCell(exporterCell);
            //secondcell
            PdfPCell cell = new PdfPCell(new Phrase("hi", bfBold12));
            float[] secondCellWidths = {2.5f, 2.5f};
            PdfPTable nestedTable = new PdfPTable(secondCellWidths);
            cell.setColspan(2);
            nestedTable.setWidthPercentage(100f);
            PdfPCell nCell1 = getInsertCell(cartonInvoiceSummary.getInvoiceWithDate(), Element.ALIGN_LEFT, 1, smallFornt);
            nCell1.setFixedHeight(35f);
            nestedTable.addCell(nCell1);
            PdfPCell nCell2 = getInsertCell(cartonInvoiceSummary.getExporteRef(), Element.ALIGN_LEFT, 1, smallFornt);
            nCell2.setFixedHeight(45f);
            nestedTable.addCell(nCell2);
            PdfPCell nCell3 = getInsertCell(cartonInvoiceSummary.getOrderNo(), Element.ALIGN_LEFT, 2, smallFornt);
            nCell3.setFixedHeight(45f);
            nestedTable.addCell(nCell3);
            PdfPCell nCell4 = getInsertCell(cartonInvoiceSummary.getTintNo(), Element.ALIGN_LEFT, 2, smallFornt);
            nCell4.setFixedHeight(35f);
            nestedTable.addCell(nCell4);
            cell.addElement(nestedTable);
            table.addCell(cell);

            PdfPCell consigneCell = getInsertCell(cartonInvoiceSummary.getConsigneAddress(), Element.ALIGN_LEFT, 2, bf12);
            table.addCell(consigneCell);
            //thirdcell
            PdfPCell buyerCell = new PdfPCell(new Phrase("hi", bfBold12));
            float[] buyerWidths = {2.5f, 2.5f};
            PdfPTable buyerTable = new PdfPTable(buyerWidths);
            buyerCell.setColspan(2);
            buyerTable.setWidthPercentage(100f);
            PdfPCell buyerCell1 = getInsertCell("Buyer (If other than consignee)", Element.ALIGN_LEFT, 2, bf12);
            buyerCell1.setFixedHeight(70f);
            buyerTable.addCell(buyerCell1);
            PdfPCell buyerCel3 = getInsertCell("Country of Origin of Goods \n INDIA", Element.ALIGN_LEFT, 1, bf12);
            buyerCel3.setFixedHeight(35f);
            buyerTable.addCell(buyerCel3);
            PdfPCell buyerCel4 = getInsertCell("Country of final destination\n UK", Element.ALIGN_LEFT, 1, bf12);
            buyerCel4.setFixedHeight(35f);
            buyerTable.addCell(buyerCel4);
            buyerCell.addElement(buyerTable);
            table.addCell(buyerCell);

            insertCell(table, cartonInvoiceSummary.getVessels(), Element.ALIGN_LEFT, 2, bfBold12, "ivno");
            insertCell(table, cartonInvoiceSummary.getTermsAndConditions(), Element.ALIGN_LEFT, 2, bf12, "normal");
            float[] cartonsWidths = {2f, 5f, 1f, 1f, 1f};
            //create PDF table with the given widths
            PdfPTable cartonTable = new PdfPTable(cartonsWidths);
            // set table width a percentage of the page width
            cartonTable.setWidthPercentage(100f);


            insertCell(cartonTable, "Marks & Nos/\nContainer No.", Element.ALIGN_RIGHT, 1, bfBold12, "normal");
            insertCell(cartonTable, "No.&kinds of pkgs Description of Goods", Element.ALIGN_LEFT, 1, bfBold12, "normal");
            insertCell(cartonTable, "Quantity", Element.ALIGN_LEFT, 1, bfBold12, "normal");
            insertCell(cartonTable, "Rate \n GBP", Element.ALIGN_RIGHT, 1, bfBold12, "normal");
            insertCell(cartonTable, "Amount \nGBP ", Element.ALIGN_RIGHT, 1, bfBold12, "normal");
            cartonTable.setHeaderRows(1);
            Map<String, List<OrderCreationDetailsJson>> cartonMap = cartonInvoiceSummary.getCartonMap();
            int pcs = 0;
            long totalAmount = 0;
            for (Map.Entry<String, List<OrderCreationDetailsJson>> entry : cartonMap.entrySet()) {
                String key = entry.getKey();
                List<OrderCreationDetailsJson> cottonItemEntities = entry.getValue();
                PdfPCell cartoncelx = getInsertCell("Carton 0 - " + cartonInvoiceSummary.getCartonCount(), Element.ALIGN_LEFT, 5, bf12);
                cartoncelx.setBorder(PdfPCell.NO_BORDER);
                cartonTable.addCell(cartoncelx);
                for (OrderCreationDetailsJson cottonItemEntity : cottonItemEntities) {

                    pcs = pcs + cottonItemEntity.getQuantity();
                    totalAmount = totalAmount + cottonItemEntity.getAmount();
                    PdfPCell cartoncell1 = getInsertCell("", Element.ALIGN_RIGHT, 1, bf12);
                    cartoncell1.setBorder(PdfPCell.NO_BORDER);
                    cartonTable.addCell(cartoncell1);
                    PdfPCell cartoncell2 = getInsertCell("Style : " + cottonItemEntity.getProductStyle(), Element.ALIGN_LEFT, 1, bf12);
                    cartoncell2.setBorder(PdfPCell.NO_BORDER);
                    cartonTable.addCell(cartoncell2);
                    PdfPCell cartoncell3 = getInsertCell("" + cottonItemEntity.getQuantity(), Element.ALIGN_RIGHT, 1, bf12);
                    cartoncell3.setBorder(PdfPCell.NO_BORDER);
                    cartonTable.addCell(cartoncell3);
                    PdfPCell cartoncell4 = getInsertCell("£" + cottonItemEntity.getRate(), Element.ALIGN_RIGHT, 1, bf12);
                    cartoncell4.setBorder(PdfPCell.NO_BORDER);
                    cartonTable.addCell(cartoncell4);
                    PdfPCell cartoncell5 = getInsertCell("£" + cottonItemEntity.getAmount(), Element.ALIGN_RIGHT, 1, bf12);
                    cartoncell5.setBorder(PdfPCell.NO_BORDER);
                    cartonTable.addCell(cartoncell5);

                }
            }

            PdfPCell pdfPCells[] = cartonTable.getRow(cartonTable.getRows().size() - 1).getCells();
            for (PdfPCell pdfPCell : pdfPCells) {
                pdfPCell.setBorder(PdfPCell.BOTTOM);
            }
            insertCell(cartonTable, "", Element.ALIGN_RIGHT, 2, bf12, "normal");
            insertCell(cartonTable, "" + pcs, Element.ALIGN_RIGHT, 1, bfBold12, "normal");
            insertCell(cartonTable, "", Element.ALIGN_RIGHT, 1, bfBold12, "normal");
            insertCell(cartonTable, "£" + totalAmount, Element.ALIGN_RIGHT, 1, bfBold12, "normal");

            PdfPCell cartonrPcs = getInsertCell("Pcs: " + NumberToWord.convert((int) pcs), Element.ALIGN_LEFT, 5, bf12);
            cartonrPcs.setBorder(PdfPCell.NO_BORDER);
            cartonrPcs.setFixedHeight(30f);
            cartonTable.addCell(cartonrPcs);
            PdfPCell cartonrAmount = getInsertCell("AMOUNT: GBP " + NumberToWord.convert((int) totalAmount), Element.ALIGN_LEFT, 5, bf12);
            cartonrAmount.setBorder(PdfPCell.NO_BORDER);
            cartonrAmount.setFixedHeight(30f);
            cartonTable.addCell(cartonrAmount);
            PdfPCell cartonrDeclaration = getInsertCell("Declaration\n" +
                    "We declare that this Invoice shows the actual price of the\n" +
                    "goods described and that all particulars are true and correct\n", Element.ALIGN_LEFT, 2, bf12);
            cartonrDeclaration.setBorder(PdfPCell.NO_BORDER);
            cartonrDeclaration.setFixedHeight(50f);
            cartonTable.addCell(cartonrDeclaration);
            PdfPCell cartonrSign = getInsertCell("", Element.ALIGN_LEFT, 3, bf12);
            cartonrSign.setFixedHeight(30f);
            cartonTable.addCell(cartonrSign);
            paragraph.add(table);
            paragraph1.add(cartonTable);
            // add the paragraph to the document
            doc.add(paragraph);
            doc.add(paragraph1);
            LineSeparator ls = new LineSeparator();
            doc.add(new Chunk(ls));
            try {
              //  Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(FILE)), "application/pdf");
                startActivity(intent);
                return FILE;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
        return FILE;
    }
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font, String type) {
        if (type.equals("normal")) {
            //create a new cell with the specified Text and Font
            PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
            //set the cell alignment
            cell.setHorizontalAlignment(align);
            //set the cell column span in case you want to merge two or more cells
            cell.setColspan(colspan);
            //in case there is no text and you wan to create an empty row
            if (text.trim().equalsIgnoreCase("")) {
                cell.setMinimumHeight(10f);
            }
            //add the call to the table
            table.addCell(cell);
        } else if (type.equals("ivno")) {
            PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
            float[] columnWidths = {5f};
            PdfPTable nestedTable = new PdfPTable(columnWidths);
            cell.setColspan(colspan);
            nestedTable.setWidthPercentage(100f);
            nestedTable.addCell(new PdfPCell(new Phrase("Pre-Carriage by\t\n")));
            nestedTable.addCell(new Paragraph(text));
            nestedTable.addCell(new Paragraph("Port of Discharge\t\n"));
            cell.addElement(nestedTable);
            table.addCell(cell);
        }
    }

    private PdfPCell getInsertCell(String text, int align, int colspan, Font font) {
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        return cell;
    }


}
