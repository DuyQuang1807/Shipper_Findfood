package com.wang.delivery.CallBack;

import com.wang.delivery.Model.Shipper;

import java.util.ArrayList;

public interface ShipperCallBack {
    void onSuccess(ArrayList<Shipper> lists);
    void onError(String message);
}