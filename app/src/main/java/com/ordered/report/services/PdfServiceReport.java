package com.ordered.report.services;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
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
import com.ordered.report.dao.OrderDAO;
import com.ordered.report.json.models.CartonInvoiceSummary;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.ClientDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.utils.DeviceConfig;
import com.ordered.report.utils.UtilService;
import com.ordered.report.view.activity.HomeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 3/2/2018.
 */

public class PdfServiceReport {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private HomeActivity homeActivity;

    Gson gson;
    OrderDAO orderDAO;
    Font bf12;
    private ClientDetailsEntity clientDetailsEntity;
    private DeliveryDetailsEntity deliveryDetailsEntity;

    public  PdfServiceReport(Context context,DeliveryDetailsEntity deliveryDetailsEntity){
        try{
            gson = new Gson();
            orderDAO = new OrderDAO(context);
            this.homeActivity = (HomeActivity)context;
            this.deliveryDetailsEntity = deliveryDetailsEntity;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<CartonInvoiceSummary> getCartonInvoiceSummary() {
        List<CartonInvoiceSummary> cartonInvoiceSummaryList = new ArrayList<>();


        String orderGuids =  deliveryDetailsEntity.getOrderGuids();
        JsonParser jsonParser = new JsonParser();
        JsonArray orderGuidsList = (JsonArray)jsonParser.parse(orderGuids);
        int size = orderGuidsList.size();
        for(int i =0; i< size; i++) {
            String orderGuid = orderGuidsList.get(0).getAsString();
            OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(orderGuid);

            clientDetailsEntity = orderDAO.getClientDetailsEntity(orderEntity);


            String clientAddress = clientDetailsEntity.getExporterDetails();
            CartonInvoiceSummary cartonInvoiceSummary = new CartonInvoiceSummary();
            cartonInvoiceSummary.setCartonCount(Integer.parseInt(orderEntity.getCartonCounts()));

            cartonInvoiceSummary.setExporterAddress(clientDetailsEntity.getExporterDetails());
            cartonInvoiceSummary.setConsigneAddress("Consignee\n" + clientAddress);
            cartonInvoiceSummary.setOrderNo("Buyer Order No.& Date\n" + "ORDER No:"+orderEntity.getOrderId()+" \nDate:"+UtilService.formatDateTime(orderEntity.getOrderedDate()));
            cartonInvoiceSummary.setTermsAndConditions("Terms of Delivery and payment\t\n" + "Accept");
            cartonInvoiceSummary.setTintNo("TIN NO: \t" + clientDetailsEntity.getTinNumber());
            cartonInvoiceSummary.setVessels("Vessel/Flight No.\n" + deliveryDetailsEntity.getDeliveringType().toString());
            cartonInvoiceSummary.setExporteRef("Exporter Ref.\t\n IE CODE: 0410033006\n");
            cartonInvoiceSummary.setInvoiceWithDate("Invoice No.& Date\t\t\n" + "CREA2342345" + "/DATE-" + UtilService.formatDateTime(new Date().getTime()));
            cartonInvoiceSummaryList.add(cartonInvoiceSummary);

        }
return cartonInvoiceSummaryList;

    }

    private void loadRowHeader(PdfPTable table,Font bfBold12 ){
        insertCell(table, "CTN NO", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "STYLE", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "COLOR", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "1Size", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "XS", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "S", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "M", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "L", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "XL", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "XXL", Element.ALIGN_CENTER, 1, bfBold12, "normal");

        insertCell(table, "PCS/CTN", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "TTL\nCTN", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "TTL PCS", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "NT.WGT", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(table, "GR.WGT", Element.ALIGN_CENTER, 1, bfBold12, "normal");


    }

    private void insertNoBorderCell(PdfPTable table, String text, int align, int colspan, Font font, String type){
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);

        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setFixedHeight(30f);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);
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
            nestedTable.addCell(new Paragraph("Port of Discharge: "+deliveryDetailsEntity.getPortOfDischarge()));
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

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



    private void defaultHeader(Document doc){
        //document header attributes
        doc.addAuthor("Test");
        doc.addCreationDate();
        doc.addProducer();
        doc.addCreator("MySampleCode.com");
        doc.addTitle("Packing Details Report");
        doc.setPageSize(PageSize.LETTER);

        //open document
        doc.open();
    }

    private void loadVal(PdfPTable cartonTable,String val){
        PdfPCell productName = getInsertCell(val, Element.ALIGN_CENTER, 1, bf12);
        cartonTable.addCell(productName);
    }

    private int getTotalProductCount(ProductDetailsEntity productDetailsEntity){
        int totalCount = 0;
        totalCount = totalCount + stringTOInt(productDetailsEntity.getOneSize());
        totalCount = totalCount + stringTOInt(productDetailsEntity.getXs());
        totalCount = totalCount + stringTOInt(productDetailsEntity.getS());
        totalCount = totalCount + stringTOInt(productDetailsEntity.getM());
        totalCount = totalCount + stringTOInt(productDetailsEntity.getL());
        totalCount = totalCount + stringTOInt(productDetailsEntity.getXl());
        totalCount = totalCount + stringTOInt(productDetailsEntity.getXxl());
        return totalCount;
    }


    private int stringTOInt(String val){
        return (val != null && !val.isEmpty()) ? Integer.valueOf(val) : 0;
    }


    private void loadProductDetails(PdfPTable cartonTable,CartonInvoiceSummary cartonInvoiceSummary){
       String orderGuids =  deliveryDetailsEntity.getOrderGuids();

        JsonParser jsonParser = new JsonParser();
        JsonArray  orderGuidsList = (JsonArray)jsonParser.parse(orderGuids);
        int noOfOrders = orderGuidsList.size();
        for(int i =0; i< noOfOrders; i++){
            String orderGuid =  orderGuidsList.get(0).getAsString();
            OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(orderGuid);

            List<CartonDetailsEntity> cartonDetailsEntityList =  orderDAO.getCartonDetailsList(orderEntity,deliveryDetailsEntity);

            for(CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntityList){
                int pos = 0;
                String cartonNumber = cartonDetailsEntity.getCartonNumber();
                List<ProductDetailsEntity> productDetailsEntityList =  orderDAO.getProductDetailsEntityList(orderEntity,cartonDetailsEntity);
                int size = productDetailsEntityList.size();
                int totalItemsCount = 0;
                for(ProductDetailsEntity productDetailsEntity : productDetailsEntityList){
                    loadVal(cartonTable,(pos == 0) ? cartonNumber : "");


                    loadVal(cartonTable,productDetailsEntity.getProductName());
                    loadVal(cartonTable,productDetailsEntity.getColorStyle());
                    loadVal(cartonTable,productDetailsEntity.getOneSize());

                    loadVal(cartonTable,productDetailsEntity.getXs());
                    loadVal(cartonTable,productDetailsEntity.getS());
                    loadVal(cartonTable,productDetailsEntity.getM());
                    loadVal(cartonTable,productDetailsEntity.getL());

                    loadVal(cartonTable,productDetailsEntity.getXl());
                    loadVal(cartonTable,productDetailsEntity.getXxl());
                    int totalProCount = getTotalProductCount(productDetailsEntity);
                    totalItemsCount = totalItemsCount + totalProCount;
                    loadVal(cartonTable,totalProCount+"");
                    if(pos < (size - 1)){
                        loadVal(cartonTable,"");
                        loadVal(cartonTable,"");
                        loadVal(cartonTable,"");
                        loadVal(cartonTable,"");
                    }else{
                        loadVal(cartonTable,"1");
                        loadVal(cartonTable,""+totalItemsCount);
                        loadVal(cartonTable,cartonDetailsEntity.getTotalWeight());
                        loadVal(cartonTable,cartonDetailsEntity.getTotalWeight());
                    }
                    pos++;
                }
                cartonInvoiceSummary.setTotalCartonCount(cartonInvoiceSummary.getTotalCartonCount() + 1);
                cartonInvoiceSummary.setTotalProductsCount(cartonInvoiceSummary.getTotalProductsCount() + totalItemsCount);
                String totalWeight = cartonDetailsEntity.getTotalWeight();
                if(totalWeight != null){
                    totalWeight = totalWeight.replace("kg","");
                }
                cartonInvoiceSummary.setTotalWeight(cartonInvoiceSummary.getTotalWeight() + Double.valueOf(totalWeight));
                cartonInvoiceSummary.setTotalGrossWeight(cartonInvoiceSummary.getTotalGrossWeight() + Double.valueOf(totalWeight));
            }
        }


    }

    public void createPDF(List<CartonInvoiceSummary> cartonInvoiceSummaries) {
       // verifyStoragePermissions();
        Document doc = new Document(PageSize.A4);
        PdfWriter docWriter = null;
        DecimalFormat df = new DecimalFormat("0.00");
        String fileName = "Invoice_Report-" + UtilService.reportFormat(new Date().getTime());
        String FILE = DeviceConfig.getAppRootPath(homeActivity) + "/" + fileName + ".pdf";
        String root = DeviceConfig.getAppRootPath(homeActivity) + "/";


        File myDir = new File(root);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        try {
            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            //file path
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(FILE));

            defaultHeader(doc);


            int totalCount = cartonInvoiceSummaries.size();
            for(int i = 0; i < totalCount; i++){
                if(i != 0){
                    doc.newPage();
                }
                populateReport(cartonInvoiceSummaries.get(i),bfBold12,smallFont,doc);
            }

            try {
                //  Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(new File(FILE)), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open File");
                try {

                    homeActivity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }
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
    }


    private void populateReport(CartonInvoiceSummary  cartonInvoiceSummary, Font bfBold12,Font smallFornt, Document doc)throws  Exception{
        //create a paragraph
        Paragraph paragraph = new Paragraph("PACKING LIST");
        Paragraph paragraph1 = new Paragraph();
        //specify column widths
        float[] columnWidths = {2.5f, 2.5f, 2.5f, 2.5f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(100f);

        //insert an empty row
        insertCell(table, "PACKING LIST", Element.ALIGN_LEFT, 4, bfBold12, "normal");

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
        PdfPCell buyerCel3 = getInsertCell("Country of Origin of Goods \n"+clientDetailsEntity.getExporterCountry(), Element.ALIGN_LEFT, 1, bf12);
        buyerCel3.setFixedHeight(35f);
        buyerTable.addCell(buyerCel3);
        PdfPCell buyerCel4 = getInsertCell("Country of final destination\n"+clientDetailsEntity.getConsigneeCountry(), Element.ALIGN_LEFT, 1, bf12);
        buyerCel4.setFixedHeight(35f);
        buyerTable.addCell(buyerCel4);
        buyerCell.addElement(buyerTable);
        table.addCell(buyerCell);

        insertCell(table, cartonInvoiceSummary.getVessels(), Element.ALIGN_LEFT, 2, bfBold12, "ivno");
        insertCell(table, cartonInvoiceSummary.getTermsAndConditions(), Element.ALIGN_LEFT, 2, bf12, "normal");
        float[] cartonsWidths = {1f, 3f, 1.5f, .8f, .8f, .8f, .8f, .8f, 1f, 1f, 1f, 1f, 1.5f, 1.5f, 2f};
        //create PDF table with the given widths
        PdfPTable cartonTable = new PdfPTable(cartonsWidths);
        // set table width a percentage of the page width
        cartonTable.setWidthPercentage(100f);

        loadRowHeader(cartonTable,bfBold12);
        cartonTable.setHeaderRows(1);

        loadProductDetails(cartonTable,cartonInvoiceSummary);

        insertNoBorderCell(cartonTable, "Total", Element.ALIGN_RIGHT, 11, bfBold12, "normal");

        insertCell(cartonTable, "" +cartonInvoiceSummary.getTotalCartonCount() , Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(cartonTable, cartonInvoiceSummary.getTotalProductsCount()+"" , Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(cartonTable, ""+cartonInvoiceSummary.getTotalWeight(), Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(cartonTable, "" + cartonInvoiceSummary.getTotalGrossWeight(), Element.ALIGN_CENTER, 1, bfBold12, "normal");


        insertNoBorderCell(cartonTable, "GRAND TOTAL" , Element.ALIGN_RIGHT, 12, bfBold12, "normal");
        insertCell(cartonTable, "" + cartonInvoiceSummary.getTotalProductsCount()+"PCS", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(cartonTable, ""+cartonInvoiceSummary.getTotalWeight()+"KGS", Element.ALIGN_CENTER, 1, bfBold12, "normal");
        insertCell(cartonTable, "" + cartonInvoiceSummary.getTotalGrossWeight()+"KGS", Element.ALIGN_CENTER, 1, bfBold12, "normal");

        PdfPCell empty1 = getInsertCell(" ", Element.ALIGN_CENTER, 15, bf12);
        empty1.setBorder(PdfPCell.NO_BORDER);
        empty1.setFixedHeight(20f);
        cartonTable.addCell(empty1);

        PdfPCell qtyView = getInsertCell("TOTAL QUANTITY  ", Element.ALIGN_CENTER, 3, bf12);
        qtyView.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(qtyView);
        PdfPCell qty = getInsertCell(""+cartonInvoiceSummary.getTotalProductsCount(), Element.ALIGN_LEFT, 13, bf12);
        qty.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(qty);
        PdfPCell cartonView = getInsertCell("TOTAL CARTONS  ", Element.ALIGN_CENTER, 3, bf12);
        cartonView.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(cartonView);
        PdfPCell totalCartons = getInsertCell(""+cartonInvoiceSummary.getTotalCartonCount(), Element.ALIGN_LEFT, 13, bf12);
        totalCartons.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(totalCartons);
        PdfPCell netWeightView = getInsertCell("NETWEIGHT  ", Element.ALIGN_CENTER, 3, bf12);
        netWeightView.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(netWeightView);
        PdfPCell netWeight1 = getInsertCell(""+cartonInvoiceSummary.getTotalWeight(), Element.ALIGN_LEFT, 13, bf12);
        netWeight1.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(netWeight1);
        PdfPCell grossWeightView = getInsertCell("G.WEIGHT  ", Element.ALIGN_CENTER, 3, bf12);
        grossWeightView.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(grossWeightView);
        PdfPCell grossWeight1 = getInsertCell(""+cartonInvoiceSummary.getTotalGrossWeight(), Element.ALIGN_LEFT, 13, bf12);
        grossWeight1.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(grossWeight1);

        //carton measurement

        PdfPCell measurementView = getInsertCell("CARTON MEASUREMENT  ", Element.ALIGN_CENTER, 3, bf12);
        measurementView.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(measurementView);
        PdfPCell measurementVal = getInsertCell("14 X 12 X 43", Element.ALIGN_LEFT, 13, bf12);
        measurementVal.setBorder(PdfPCell.NO_BORDER);
        cartonTable.addCell(measurementVal);
//empty Space
        PdfPCell empty = getInsertCell(" ", Element.ALIGN_CENTER, 15, bf12);
        empty.setBorder(PdfPCell.NO_BORDER);
        empty.setFixedHeight(20f);
        cartonTable.addCell(empty);


        PdfPCell cartonrDeclaration = getInsertCell("Declaration:\t\t\n" +
                "We declare that this invoice shows the actual price of the goods\t\t\n" +
                "described and that all particulars are true and correct.\t\t\n", Element.ALIGN_BOTTOM, 11, bf12);
        cartonrDeclaration.setBorder(PdfPCell.NO_BORDER);
        cartonrDeclaration.setFixedHeight(50f);
        cartonTable.addCell(cartonrDeclaration);
        PdfPCell cartonrSign = getInsertCell("", Element.ALIGN_LEFT, 4, bf12);
        cartonrSign.setFixedHeight(30f);
        cartonTable.addCell(cartonrSign);
        //add the PDF table to the paragraph

        paragraph.add(table);
        paragraph1.add(cartonTable);
        // add the paragraph to the document
        doc.add(paragraph);
        doc.add(paragraph1);
        LineSeparator ls = new LineSeparator();
        doc.add(new Chunk(ls));
    }


}
