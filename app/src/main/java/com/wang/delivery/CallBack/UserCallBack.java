package com.wang.delivery.CallBack;

import com.wang.delivery.Model.User;

import java.util.ArrayList;

public interface UserCallBack {
    void onSuccess(ArrayList<User> lists);
    void onError(String message);
}
