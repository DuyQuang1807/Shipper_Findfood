package com.wang.delivery.Databases;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.delivery.CallBack.HDCT1CallBack;
import com.wang.delivery.Model.HDCT1;

import java.util.ArrayList;

public class DatabaseHDCT1 {
    Context context;
    DatabaseReference mRef;
    String key;

    public DatabaseHDCT1(Context context) {
        this.context = context;
        this.mRef = FirebaseDatabase.getInstance().getReference("HDCT");
    }
    public void getAll(final HDCT1CallBack callback) {
        final ArrayList<HDCT1> dataloai = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dataloai.clear();
                    for (DataSnapshot data : snapshot.getChildren()){
                        HDCT1 categories = data.getValue(HDCT1.class);
                        dataloai.add(categories);
                    }
                    callback.onSuccess(dataloai);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toString());
            }
        });
    }
    public void insert(HDCT1 item){
        // push cây theo mã tự tạo
        // string key lấy mã push
        key = mRef.push().getKey();

        HDCT1 hdctTest = new HDCT1(key, key, item.getThoigian(), item.getCheck(), item.getIdUser(),item.getPayment(), item.getIdShipper(), item.getOrderArrayList());

        //insert theo child mã key setvalue theo item
        mRef.child(key).setValue(hdctTest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Đặt Hàng Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Đặt Hàng Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean update(final HDCT1 item){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("idHDCT").getValue(String.class).equalsIgnoreCase(item.getIdHDCT())){
                        key=dataSnapshot.getKey();
                        mRef.child(key).setValue(item);
//                        Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return true;
    }
    public void delete(final String matheloai){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("idHDCT").getValue(String.class).equalsIgnoreCase(matheloai)){
                        key=dataSnapshot.getKey();
                        mRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Delete Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
