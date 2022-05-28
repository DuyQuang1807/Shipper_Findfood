package com.wang.delivery.CallBack;

import com.wang.delivery.Model.Store;

import java.util.ArrayList;

public interface StoreCallBack {
    void onSuccess(ArrayList<Store> lists);
    void onError(String message);
}