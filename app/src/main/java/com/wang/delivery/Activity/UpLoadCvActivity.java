package com.wang.delivery.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Model.Shipper;
import com.wang.delivery.Model.putPDF;
import com.wang.delivery.R;

import java.util.ArrayList;
import java.util.Objects;

public class UpLoadCvActivity extends AppCompatActivity {

    TextView edtSelectFile;
    Button btnUpLoadPDF, loginBtn;

    StorageReference storageReference;

    private FirebaseAuth mAuth;
    DatabaseShipper databaseShipper;

    String ngaySinh, anh, phone, password, gioiTinh, email, diaChi, name, trangThai, idShipper;
    String check = "wait";
    String active = "active";
    String uriFile;

    Shipper shipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_cv);

        btnUpLoadPDF = findViewById(R.id.btnUpLoadPDF);
        edtSelectFile = findViewById(R.id.edtSelectFile);
        loginBtn = findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        idShipper = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        loadData();

        btnUpLoadPDF.setEnabled(false);

        edtSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

    }
//lấy data từ firebase về
    public void loadData() {
        databaseShipper = new DatabaseShipper(getApplicationContext());
        databaseShipper.getAll(new ShipperCallBack() {
            @Override
            public void onSuccess(ArrayList<Shipper> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getToken() != null && lists.get(i).getToken().equalsIgnoreCase(idShipper)) {
                        trangThai = lists.get(i).getTrangThai();
                        name = lists.get(i).getHoTen();
                        ngaySinh = lists.get(i).getNgaysinh();
                        anh = lists.get(i).getAnh();
                        phone = lists.get(i).getPhone();
                        password = lists.get(i).getPassword();
                        gioiTinh = lists.get(i).getGioitinh();
                        email = lists.get(i).getEmail();
                        diaChi = lists.get(i).getDiaChi();
                    }
                }
//kiểm tra trạng thái của shipper
                if (trangThai == active) {
                    startActivity(new Intent(getApplicationContext(), CheckRoleActivity.class));
                } else if (trangThai == check) {
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }
//hàm chọn CV
    private void selectPDF() {
        Intent i = new Intent();
        i.setType("*/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "PDF FILE SELECT"), 1);
        Log.d("Secelct: ", "Thanh cong");
    }
//hàm update CV
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 1 && requestCode == RESULT_OK && data != null && data.getData() != null)
        if (requestCode == 1 && data != null && data.getData() != null) {
            Log.d("Secelct: ", "data true");
            btnUpLoadPDF.setEnabled(true);
            Log.d("Secelct : btnUpload", "enable");
            edtSelectFile.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
            Log.d("Secelct : edt", " get duoc dia chi file");
        }

        btnUpLoadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadPDFFirebase(data.getData());
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
//hàm update CV
    private void upLoadPDFFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang tải file ...");
        progressDialog.show();

        StorageReference reference = storageReference.child("ShipperCV/" + mAuth.getUid());
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();
                        uriFile = uri.toString();

                        Log.d("Secelct", uriFile);
                        shipper = new Shipper(email, password, name, phone, anh, diaChi, ngaySinh, gioiTinh, mAuth.getUid(), check, uriFile);
                        databaseShipper = new DatabaseShipper(getApplicationContext());
                        databaseShipper.insert(shipper);
                        Toast.makeText(UpLoadCvActivity.this, "Tải Lên Hoàn Tất", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Tải File Lên ...." + (int) progress + "%");

            }
        });
    }
}