package com.wang.delivery.CallBack;

import com.wang.delivery.Model.HDCT1;

import java.util.ArrayList;

public interface HDCT1CallBack {
    void onSuccess(ArrayList<HDCT1> lists);
    void onError(String message);
}
