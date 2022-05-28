package com.wang.delivery.CallBack;

import com.wang.delivery.Model.HDCT;

import java.util.ArrayList;

public interface HDCTCallBack {
    void onSuccess(ArrayList<HDCT> lists);
    void onError(String message);
}
