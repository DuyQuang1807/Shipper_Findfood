package com.wang.delivery.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Model.Shipper;
import com.wang.delivery.R;

import java.util.ArrayList;

public class ThayDoiMatKhauActivity extends AppCompatActivity {
    ImageView back;
    EditText edtoldpass, edtpassnew, edtxacnhanpass;
    DatabaseShipper databaseShipper;
    String mail, name, phone, diachi, anh, pass, gioitinh, ngaysinh, cv;
    FirebaseUser firebaseUser;
    Button btnchangepassword;
    ArrayList<Shipper> dataShipper;
    ImageView anhdaidien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_mat_khau);

        back = findViewById(R.id.back);
        edtoldpass = findViewById(R.id.edtoldpass);
        edtpassnew = findViewById(R.id.edtpassnew);
        edtxacnhanpass = findViewById(R.id.edtxacnhanpass);
        anhdaidien = findViewById(R.id.anhdaidien);
        btnchangepassword = findViewById(R.id.btnchangepassword);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dataShipper = new ArrayList<>();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TrangCaNhanActivity.class);
                startActivity(i);
                finish();
            }
        });
// hàm thay đổi password
        btnchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseShipper = new DatabaseShipper(getApplicationContext());
                databaseShipper.getAll(new ShipperCallBack() {
                    @Override
                    public void onSuccess(ArrayList<Shipper> lists) {
                        dataShipper.clear();
                        dataShipper.addAll(lists);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });

                if (edtoldpass.getText().toString().trim().isEmpty() ||
                        edtpassnew.getText().toString().trim().isEmpty() ||
                        edtxacnhanpass.getText().toString().trim().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Vui Lòng Nhập Đầy Đủ 3 Trường", Toast.LENGTH_SHORT).show();

                } else if (edtoldpass.getText().toString().trim().length() < 6 ||
                        edtpassnew.getText().toString().trim().length() < 6 || edtxacnhanpass.getText().toString().trim().length() < 6) {

                    Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < dataShipper.size(); i++) {
                        if (dataShipper.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())) {
                            if (!(edtoldpass.getText().toString().trim().equalsIgnoreCase(dataShipper.get(i).getPassword()))) {
                                Toast.makeText(getApplicationContext(), "Password cũ bạn nhập không đúng", Toast.LENGTH_SHORT).show();
                            } else {
                                pass = dataShipper.get(i).getPassword();
                                name = dataShipper.get(i).getHoTen();
                                phone = dataShipper.get(i).getPhone();
                                diachi = dataShipper.get(i).getDiaChi();
                                mail = dataShipper.get(i).getEmail();
                                anh = dataShipper.get(i).getAnh();
                                gioitinh = dataShipper.get(i).getGioitinh();
                                ngaysinh = dataShipper.get(i).getNgaysinh();
                                cv = dataShipper.get(i).getCv();


                                if (edtpassnew.getText().toString().trim().equalsIgnoreCase(edtoldpass.getText().toString().trim())) {
                                    Toast.makeText(getApplicationContext(), "Password cũ với password mới không được trùng", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!(edtxacnhanpass.getText().toString().equalsIgnoreCase(edtpassnew.getText().toString()))) {
                                        Toast.makeText(getApplicationContext(), "Password Xác nhận phải trùng Với Password mới", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Shipper shipper = new Shipper(mail, edtpassnew.getText().toString().trim(), name, phone, anh, diachi, ngaysinh, gioitinh, firebaseUser.getUid(), "active", cv);
                                        databaseShipper.update(shipper);
                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        String newPassword = edtpassnew.getText().toString().trim();

                                        firebaseUser.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "User password updated.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        Intent intent = new Intent(getApplicationContext(), TrangCaNhanActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        });

    }
}