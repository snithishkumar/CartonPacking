package com.ordered.report.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.MaxValueFilter;
import com.ordered.report.view.activity.OrderDetailsActivity;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link } factory method to
 * create an instance of this fragment.
 */
public class CaptureCartonDetailsFragment extends Fragment {
    private OrderDetailsActivity orderDetailsActivity;
    private Context context;
    private ShowFragment showFragment;

    private TextView vProductName;
    private TextView vProductGroup;
    private EditText vOneSize;
    private EditText vXS;
    private EditText vS;
    private EditText vM;
    private EditText vL;
    private EditText vXL;
    private EditText vXXL;
    private EditText vXXXL;

    private TextView availabilityOneSize;
    private TextView availabilityXs;
    private TextView availabilityX;
    private TextView availabilityM;
    private TextView availabilityL;
    private TextView availabilityS;
    private TextView availabilityXl;
    private TextView availabilityXxl;
    private TextView availabilityXxxl;

    private OrderDetailsListViewModel orderDetailsListViewModel;


    public CaptureCartonDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderDetailsActivity = (OrderDetailsActivity) context;
        showFragment = orderDetailsActivity;

    }

    private void initView(View view) {
        //capture_carton_product_group"
        //
        getActivity().setTitle("CAPTURE DETAILS");
        vProductName = view.findViewById(R.id.capture_carton_product_name);
        vProductGroup = view.findViewById(R.id.capture_carton_product_group);
        vOneSize = view.findViewById(R.id.capture_oneSize);

        vXS = view.findViewById(R.id.capture_xs);
        vS = view.findViewById(R.id.capture_small);
        vM = view.findViewById(R.id.capture_medium);
        vL = view.findViewById(R.id.capture_l);
        vXL = view.findViewById(R.id.capture_xl);
        vXXL = view.findViewById(R.id.capture_xxl);
        vXXXL = view.findViewById(R.id.capture_xxxl);


        availabilityOneSize = view.findViewById(R.id.availability_one_size);
        availabilityS = view.findViewById(R.id.availability_s);
        availabilityXs = view.findViewById(R.id.availability_xs);
        availabilityX = view.findViewById(R.id.capture_carton_product_name);
        availabilityM = view.findViewById(R.id.availability_m);
        availabilityL = view.findViewById(R.id.availability_l);
        availabilityXl = view.findViewById(R.id.availability_xl);
        availabilityXxl = view.findViewById(R.id.availability_xxl);
        availabilityXxxl = view.findViewById(R.id.availability_xxxl);



        orderDetailsListViewModel = orderDetailsActivity.getOrderDetailsListViewModel();

        orderDetailsActivity.getOrderedService().calcAvailableCount(orderDetailsListViewModel,orderDetailsActivity.getCartonDetailsJsonList());
        vProductName.setText(orderDetailsListViewModel.getOrderItemName());
        vProductGroup.setText(orderDetailsListViewModel.getOrderItemGroup());
        setAvailability("One Size",orderDetailsListViewModel.getOrderItemOneSize(),availabilityOneSize,orderDetailsListViewModel.getRemainingOneSize(),vOneSize);

        setAvailability("XS",orderDetailsListViewModel.getOrderItemXS(),availabilityXs,orderDetailsListViewModel.getRemainingXS(),vXS);
        setAvailability("S",orderDetailsListViewModel.getOrderItemS(),availabilityS,orderDetailsListViewModel.getRemainingS(),vS);
        setAvailability("M",orderDetailsListViewModel.getOrderItemM(),availabilityM,orderDetailsListViewModel.getRemainingM(),vM);
        setAvailability("l",orderDetailsListViewModel.getOrderItemL(),availabilityL,orderDetailsListViewModel.getRemainingL(),vL);
        setAvailability("xl",orderDetailsListViewModel.getOrderItemXl(),availabilityXl,orderDetailsListViewModel.getRemainingXl(),vXL);
        setAvailability("xxl",orderDetailsListViewModel.getOrderItemXxl(),availabilityXxl,orderDetailsListViewModel.getRemainingXxl(),vXXL);
        setAvailability("xxxl",orderDetailsListViewModel.getOrderItemXxl(),availabilityXxxl,orderDetailsListViewModel.getRemainingXxxl(),vXXXL);
    }


    private void setAvailability(String text ,String totalVal,TextView textView,String remainingVal,EditText editText){
        if(totalVal == null || totalVal.isEmpty()){
            totalVal = "0";
        }

        if(remainingVal == null || remainingVal.isEmpty()){
            remainingVal = "0";
        }

        editText.setFilters(new InputFilter[]{new MaxValueFilter(remainingVal)});

        textView.setText(text+" --> "+remainingVal+" of "+totalVal);
    }





    private void createOrder() {
        orderDetailsListViewModel.setCartonNumber(orderDetailsActivity.getCartonDetailsJson().getCartonNumber());
        orderDetailsListViewModel.setProductGuid(UUID.randomUUID().toString());
        orderDetailsListViewModel.setProductL(vL.getText().toString());
        orderDetailsListViewModel.setProductM(vM.getText().toString());
        orderDetailsListViewModel.setProductOneSize(vOneSize.getText().toString());
        orderDetailsListViewModel.setProductXl(vXL.getText().toString());
        orderDetailsListViewModel.setProductXS(vXS.getText().toString());
        orderDetailsListViewModel.setProductXxl(vXXL.getText().toString());
        orderDetailsListViewModel.setProductXxxl(vXXXL.getText().toString());
        orderDetailsListViewModel.setProductS(vS.getText().toString());
        orderDetailsListViewModel.setProductCreatedDateTime(System.currentTimeMillis());
        orderDetailsListViewModel.setEdited(true);
        orderDetailsActivity.getCartonDetailsJson().setTotalWeight("0");
        orderDetailsActivity.getCartonDetailsJson().setLastModifiedTime(System.currentTimeMillis());
        orderDetailsActivity.getCartonDetailsJson().setLastModifiedBy(Constants.getLoginUser());
        orderDetailsActivity.getCartonDetailsJson().getOrderDetailsListViewModels().add(orderDetailsListViewModel);
       // orderDetailsActivity.getCartonDetailsJsonList().add(orderDetailsActivity.getCartonDetailsJson());
        orderDetailsActivity.setOrderDetailsListViewModel(null);
        if(!orderDetailsActivity.isFlag()){
            orderDetailsActivity.setCartonDetailsJson(null);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_capture_carton_details, container, false);
        Button doneButton = (Button) view.findViewById(R.id.capture_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
                showFragment.viewFragment(1);
                return;
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
        this.context = activity;
    }


    public interface ShowFragment{
        void viewFragment(int options);
    }

}
