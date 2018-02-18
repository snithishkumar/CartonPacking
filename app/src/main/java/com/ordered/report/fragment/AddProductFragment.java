package com.ordered.report.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ordered.report.HomeActivity;
import com.ordered.report.R;
import com.ordered.report.json.models.ProductDetailsJson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {
    private HomeActivity homeActivity;
    private Context context;



    private EditText vProductName;
    private EditText vProductGroup;
    private EditText vOneSize;
    private EditText vXS;
    private EditText vS;
    private EditText vM;
    private EditText vL;
    private EditText vXL;
    private EditText vXXL;
    private EditText vXXXL;
    private Spinner spinner;


    public AddProductFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity) context;
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
        //spinner = view.findViewById(R.id.planets_spinner);
    }


    private void createOrder(){
        ProductDetailsJson productDetailsJson = new ProductDetailsJson();
        productDetailsJson.setL(vL.getText().toString());
        productDetailsJson.setM(vM.getText().toString());
        productDetailsJson.setOneSize(vOneSize.getText().toString());
        productDetailsJson.setProductGroup(vProductGroup.getText().toString());
        productDetailsJson.setProductName(vProductName.getText().toString());
        productDetailsJson.setXl(vXL.getText().toString());
        productDetailsJson.setXs( vXS.getText().toString());
        productDetailsJson.setXxl( vXXL.getText().toString());
        productDetailsJson.setXxxl(vXXXL.getText().toString());
        productDetailsJson.setCartonNumber(1);
        productDetailsJson.setS(vS.getText().toString());
        productDetailsJson.setCreatedDateTime(System.currentTimeMillis());
        productDetailsJson.setLastModifiedDateTime(productDetailsJson.getCreatedDateTime());
        homeActivity.getProductDetailsJsons().add(productDetailsJson);
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
