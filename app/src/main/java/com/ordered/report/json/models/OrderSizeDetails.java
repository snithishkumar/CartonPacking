package com.ordered.report.json.models;

/**
 * Created by Nithish on 23/02/18.
 */

public class OrderSizeDetails {

    private String oneSize;
    private String xs;
    private String s;
    private String m;
    private String l;
    private String xl;
    private String xxl;
    private String xxxl;



    public String getOneSize() {
        return "OneSize -> "+oneSize;
    }

    public void setOneSize(String oneSize) {
        if(oneSize == null || oneSize.isEmpty()){
            oneSize = "0";
        }
        this.oneSize = oneSize;
    }

    public String getXs() {
        return "XS -> "+xs;
    }

    public void setXs(String xs) {
        if(xs == null || xs.isEmpty()){
            xs = "0";
        }
        this.xs = xs;
    }

    public String getS() {
        return "S -> "+s;
    }

    public void setS(String s) {
        if(s == null || s.isEmpty()){
            s = "0";
        }
        this.s = s;
    }

    public String getM() {
        return "M -> "+m;
    }

    public void setM(String m) {
        if(m == null || m.isEmpty()){
            m = "0";
        }
        this.m = m;
    }

    public String getL() {
        return "L -> "+l;
    }

    public void setL(String l) {
        if(l == null || l.isEmpty()){
            l = "0";
        }
        this.l = l;
    }

    public String getXl() {
        return "XL -> "+xl;
    }

    public void setXl(String xl) {
        if(xl == null || xl.isEmpty()){
            xl = "0";
        }
        this.xl = xl;
    }

    public String getXxl() {
        return "XXL -> "+xxl;
    }

    public void setXxl(String xxl) {
        if(xxl == null || xxl.isEmpty()){
            xxl = "0";
        }
        this.xxl = xxl;
    }

    public String getXxxl() {
        return "XXXL -> "+xxxl;
    }

    public void setXxxl(String xxxl) {
        if(xxxl == null || xxxl.isEmpty()){
            xxxl = "0";
        }
        this.xxxl = xxxl;
    }
}
