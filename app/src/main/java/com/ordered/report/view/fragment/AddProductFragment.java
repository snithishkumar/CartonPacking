package com.ordered.report.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ordered.report.HomeActivity;
import com.ordered.report.R;
import com.ordered.report.json.models.OrderCreationDetailsJson;
import com.ordered.report.services.OrderedService;
import com.ordered.report.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {
    private HomeActivity homeActivity;
    private Context context;

    private OrderedService orderedService;

    private AutoCompleteTextView vProductName;
    private AutoCompleteTextView vProductGroup;
    private EditText vOneSize;
    private EditText vXS;
    private EditText vS;
    private EditText vM;
    private EditText vL;
    private EditText vXL;
    private EditText vXXL;
    private EditText vXXXL;
    private Spinner spinner;

    private String orderGuid;
    private int noOfCartons;
private String cartonNumber;

    public AddProductFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity) context;
        orderedService = homeActivity.getOrderedService();

        if (getArguments() != null) {
            orderGuid = getArguments().getString(Constants.ORDER);
            noOfCartons = getArguments().getInt(Constants.NO_OF_COTTON);
        }
    }

    private void initView(View view){
        vProductName = view.findViewById(R.id.product_name);
        vProductGroup = view.findViewById(R.id.product_group);
        vOneSize = view.findViewById(R.id.oneSize);
        vXS = view.findViewById(R.id.xs);
        vS = view.findViewById(R.id.small);
        vM = view.findViewById(R.id.medium);
        vL = view.findViewById(R.id.l);
        vXL = view.findViewById(R.id.xl);
        vXXL = view.findViewById(R.id.xxl);
        vXXXL = view.findViewById(R.id.xxxl);
        setCartonDropDown(view);
        Map<String,List> orderItems = orderedService.getOrderItems(orderGuid);

        ArrayAdapter<List> orderItemsAdapter = new ArrayAdapter<List>(homeActivity, android.R.layout.select_dialog_item, orderItems.get("productName"));
        vProductName.setAdapter(orderItemsAdapter);
        vProductName.setThreshold(1);
        ArrayAdapter<List> productGroupAdapter = new ArrayAdapter<List>(homeActivity, android.R.layout.select_dialog_item, orderItems.get("productGroup"));

        vProductGroup.setAdapter(productGroupAdapter);
        vProductGroup.setThreshold(1);

    }


    private void setCartonDropDown(View view){
        Spinner spinner = (Spinner) view.findViewById(R.id.add_order_carton_number);

        List<String> cartonNumberList = new ArrayList<>();
        for(int i = 1 ; i <= noOfCartons; i++){
            cartonNumberList.add(i+"");
        }
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(homeActivity,android.R.layout.simple_spinner_item,cartonNumberList);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cartonNumber = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void createOrder(){
        OrderCreationDetailsJson productDetailsJson = new OrderCreationDetailsJson();
        productDetailsJson.setL(vL.getText().toString());
        productDetailsJson.setM(vM.getText().toString());
        productDetailsJson.setOneSize(vOneSize.getText().toString());
        productDetailsJson.setProductGroup(vProductGroup.getText().toString());
        productDetailsJson.setProductStyle(vProductName.getText().toString());
        productDetailsJson.setXl(vXL.getText().toString());
        productDetailsJson.setXs( vXS.getText().toString());
        productDetailsJson.setXxl( vXXL.getText().toString());
        productDetailsJson.setXxxl(vXXXL.getText().toString());
       // productDetailsJson.setCartonNumber(1+"");
        productDetailsJson.setS(vS.getText().toString());
        productDetailsJson.setCreatedDateTime(System.currentTimeMillis());
        productDetailsJson.setLastModifiedDateTime(productDetailsJson.getCreatedDateTime());
       // homeActivity.getProductDetailsJsons().add(productDetailsJson);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        Button doneButton = (Button) view.findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
                homeActivity.backClicked(view);
            }
        });
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context= activity;
    }
}
