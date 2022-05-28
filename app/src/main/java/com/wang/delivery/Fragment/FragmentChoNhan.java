package com.wang.delivery.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.delivery.Adapter.XacNhanAdapter;
import com.wang.delivery.CallBack.HDCTCallBack;
import com.wang.delivery.Databases.DatabaseHDCT;
import com.wang.delivery.Model.HDCT;
import com.wang.delivery.R;

import java.util.ArrayList;

public class FragmentChoNhan extends Fragment {

    XacNhanAdapter xacNhanAdapter;
    DatabaseHDCT databaseHDCT;
    RecyclerView rcvChoNhan;
    ArrayList<HDCT> arrayList;
    private String check = "pending";
    FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cho_nhan,container,false);
        rcvChoNhan= view.findViewById(R.id.rcvChoNhan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        rcvChoNhan.setLayoutManager(linearLayoutManager);
        databaseHDCT = new DatabaseHDCT(getActivity());
        arrayList=new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseHDCT.getAll(new HDCTCallBack() {
            @Override
            public void onSuccess(ArrayList<HDCT> lists) {
                arrayList.clear();
                for (int i =0;i<lists.size();i++){
                    if (lists.get(i).getCheck().equalsIgnoreCase(check)){
                        arrayList.add(lists.get(i));
                        xacNhanAdapter = new XacNhanAdapter(arrayList,getActivity());
                        rcvChoNhan.setAdapter(xacNhanAdapter);
                    }
                }
            }


            @Override
            public void onError(String message) {

            }
        });

        return view;
    }
}
