package com.ordered.report.view;

import android.text.InputFilter;
import android.text.Spanned;

public class MaxValueFilter implements InputFilter {

    private int maxValue = -1;
    public MaxValueFilter(String maxValue){
        if(maxValue != null && !maxValue.trim().isEmpty()){
            this.maxValue = Integer.valueOf(maxValue);
        }

    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if(maxValue == -1){
            return null;
        }
        try{
            int input = Integer.parseInt(dest.toString() + source.toString());
            if(input > maxValue){
                return "";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
