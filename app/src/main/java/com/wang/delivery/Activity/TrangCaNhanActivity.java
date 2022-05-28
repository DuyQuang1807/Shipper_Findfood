package com.wang.delivery.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Databases.DatabaseUser;
import com.wang.delivery.Model.Shipper;
import com.wang.delivery.R;

import java.util.ArrayList;

public class TrangCaNhanActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 50;
    public static ImageView back;
    RelativeLayout edtEditProfile;
    ImageView profileCircleImageView;
    TextView usernameTextView, email, txtlogout,txteditprofile,txtchangepassword, qldh;
    TextView txtVersion, txtQRCode;
    FirebaseUser firebaseUser;

    DatabaseShipper databaseShipper;

    FirebaseAuth fAuth;
    FirebaseDatabase fData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);

        back = findViewById(R.id.back);
        txtVersion = findViewById(R.id.txtVersion);
        edtEditProfile = findViewById(R.id.edtEditProfile);
        profileCircleImageView = findViewById(R.id.profileCircleImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        email= findViewById(R.id.email);
        txtQRCode = findViewById(R.id.txtQRCode);
        txtlogout = findViewById(R.id.txtlogout);
        txtchangepassword = findViewById(R.id.txtchangepassword);
        txteditprofile = findViewById(R.id.txteditprofile);
        qldh = findViewById(R.id.qldh);

        fAuth = FirebaseAuth.getInstance();
        fData = FirebaseDatabase.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        LoadData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
// quản lý đơn
        qldh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QuanLyDonHangActivity.class));
            }
        });
// quét QR
        txtQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionApp();
            }
        });

// thay đổi thông tin cá nhân
        edtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iProfile = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(iProfile);
            }
        });
// thay đổi thông tin cá nhân
        txteditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iProfile = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(iProfile);
            }
        });
// thay đổi mật khẩu
        txtchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iQuenPass = new Intent(getApplicationContext(), ThayDoiMatKhauActivity.class);
                startActivity(iQuenPass);
                finish();
            }
        });
// logout
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txtVersion.setText( getString(R.string.phienban) + " " +packageInfo.versionName);
    }
//hàm lấy data
    private void LoadData() {
        databaseShipper = new DatabaseShipper(getApplicationContext());
        databaseShipper.getAll(new ShipperCallBack() {
            @Override
            public void onSuccess(ArrayList<Shipper> lists) {
                for (int i =0 ; i<lists.size();i++){
                    if (lists.get(i).getToken()!=null && lists.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())){
                        email.setText(lists.get(i).getEmail());
                        usernameTextView.setText(lists.get(i).getHoTen());
                        Picasso.get()
                                .load(lists.get(i).getAnh()).into(profileCircleImageView);
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void checkPermissionApp() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(getApplicationContext(), testQRCode.class);
            startActivity(intent);
            finish();
        } else {
            String[] permission = {Manifest.permission.CAMERA};
            requestPermissions(permission, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getApplicationContext(), testQRCode.class);
                startActivity(intent);
                finish();
            } else {
                openSettingPermission();
            }
        }
    }

}