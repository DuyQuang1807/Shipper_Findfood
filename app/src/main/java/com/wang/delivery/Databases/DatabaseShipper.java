package com.wang.delivery.Databases;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import  com.wang.delivery.CallBack.ShipperCallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.Model.Shipper;

import java.util.ArrayList;

public class DatabaseShipper {
    Context context;
    DatabaseReference mRef;
    String key;
    public DatabaseShipper(Context context) {
        this.context = context;
        this.mRef = FirebaseDatabase.getInstance().getReference("Shipper");
    }

    public void getAll(final ShipperCallBack callback) {
        final ArrayList<Shipper> dataloai = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dataloai.clear();

                    for (DataSnapshot data : snapshot.getChildren()){
                        Shipper user = data.getValue(Shipper.class);
                        dataloai.add(user);
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
    public void insert(Shipper item){
        // push cây theo mã tự tạo
        // string key lấy mã push
        key = mRef.push().getKey();
        //insert theo child mã key setvalue theo item
        Shipper shipper = new Shipper(item.getEmail(), item.getPassword(), item.getHoTen(), item.getPhone(), item.getAnh(), item.getDiaChi(), item.getNgaysinh(), item.getGioitinh(), item.getToken(), item.getTrangThai(), item.getCv() );
        mRef.child(item.getToken()).setValue(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(context, "Sign Up Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(context, "Sign Up Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean update(final Shipper item){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue(String.class)!=null && dataSnapshot.child("email").getValue(String.class).equalsIgnoreCase(item.getEmail())){
                        key=dataSnapshot.getKey();
                        mRef.child(key).setValue(item);
                        Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
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
                    if(dataSnapshot.child("email").getValue(String.class).equalsIgnoreCase(matheloai)){
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
