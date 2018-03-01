package com.ordered.report.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ordered.report.R;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.utils.Constants;
import com.ordered.report.view.activity.OrderDetailsActivity;

import java.util.UUID;

/**
 * Created by Nithish on 26/02/18.
 */

public class CartonNumberPickerFragment extends Fragment {

    private OrderDetailsActivity orderDetailsActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cartonnumber_picker, container, false);

        getActivity().setTitle("ADD CARTON");
        final TextView tv = (TextView) view.findViewById(R.id.tv);
        NumberPicker np = (NumberPicker) view.findViewById(R.id.np);

        final Button nextButton = view.findViewById(R.id.carton_number_picking);

        //Set TextView text color
        tv.setTextColor(Color.parseColor("#ffd32b3b"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(Integer.valueOf(orderDetailsActivity.getTotalNoOfCartons()));

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                tv.setText("Selected Carton Number : " + newVal);
                nextButton.setVisibility(View.VISIBLE);
                populateCartonDetails(String.valueOf(newVal));
               // orderDetailsActivity.setCartonNumber(String.valueOf(newVal));
            }
        });
        return view;
    }


    private void populateCartonDetails(String cartonNumber){
        CartonDetailsJson cartonDetailsJson = new CartonDetailsJson();
        cartonDetailsJson.setCartonGuid(UUID.randomUUID().toString());
        cartonDetailsJson.setCartonNumber(cartonNumber);
        cartonDetailsJson.setCreatedDateTime(System.currentTimeMillis());
        cartonDetailsJson.setCreatedBy(Constants.getLoginUser());
        cartonDetailsJson.setLastModifiedBy(Constants.getLoginUser());
        cartonDetailsJson.setLastModifiedTime(cartonDetailsJson.getCreatedDateTime());
        orderDetailsActivity.setCartonDetailsJson(cartonDetailsJson);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderDetailsActivity = (OrderDetailsActivity)context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
