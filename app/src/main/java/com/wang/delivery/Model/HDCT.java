package com.wang.delivery.Model;

import java.util.ArrayList;

public class HDCT {
    private String idHDCT;
    private String idHoaDon;
    String thoigian;
    String check;
    String idUser;
    String payment;
    String idShipper;
    ArrayList<Order> orderArrayList;

    public HDCT() {
    }

    public HDCT(String idHDCT, String idHoaDon, String thoigian, String check, String idUser, String payment, String idShipper, ArrayList<Order> orderArrayList) {
        this.idHDCT = idHDCT;
        this.idHoaDon = idHoaDon;
        this.thoigian = thoigian;
        this.check = check;
        this.idUser = idUser;
        this.payment = payment;
        this.idShipper = idShipper;
        this.orderArrayList = orderArrayList;
    }

    public String getIdHDCT() {
        return idHDCT;
    }

    public void setIdHDCT(String idHDCT) {
        this.idHDCT = idHDCT;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getIdShipper() {
        return idShipper;
    }

    public void setIdShipper(String idShipper) {
        this.idShipper = idShipper;
    }

    public ArrayList<Order> getOrderArrayList() {
        return orderArrayList;
    }

    public void setOrderArrayList(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }
}
