package com.wang.delivery.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Model.Shipper;
import com.wang.delivery.R;

import java.util.ArrayList;
import java.util.Objects;

public class CheckRoleActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseShipper databaseShipper;
    com.airbnb.lottie.LottieAnimationView warning;
    TextView banned, tv_wait;
    ImageView logo;
    String trangThai;
    String check = "active";
    String wait = "wait";
    String pending = "pending";
    String ibanned = "banned";
    String testPermission = "done";
    String donePermission = "";

    GoogleApiClient googleApiClient;
    public static final int REQUEST_PERMISSION_CODE = 50;
    private static int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_role);

        warning = findViewById(R.id.animation_view);
        banned = findViewById(R.id.tv_banned);
        tv_wait = findViewById(R.id.tv_wait);
        logo = findViewById(R.id.image_owl);

        mAuth = FirebaseAuth.getInstance();

        checkPermissionApp();
//        checkTrangthai();


    }
// kiểm tra trạng thái của shipper
    private void checkTrangthai() {
        String idUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        databaseShipper = new DatabaseShipper(getApplicationContext());
        databaseShipper.getAll(new ShipperCallBack() {
            @Override
            public void onSuccess(ArrayList<Shipper> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getToken() != null && lists.get(i).getToken().equalsIgnoreCase(idUser)) {
                        trangThai = lists.get(i).getTrangThai();
                    }
                }
//trạng thái là active (đã được admin xác nhận)
                if (trangThai.equalsIgnoreCase(check)) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
//trạng thái là pending (chưa update CV)
                if (trangThai.equalsIgnoreCase(pending)) {
                    startActivity(new Intent(getApplicationContext(), UpLoadCvActivity.class));
                    finish();
                }
//trạng thái là wait (đang đợi admin xác nhận)
                if (trangThai.equalsIgnoreCase(wait)) {
                    logo.setVisibility(View.GONE);
                    tv_wait.setVisibility(View.VISIBLE);
                    warning.setVisibility(View.VISIBLE);
                }
//trạng thái là banned (tài khoản bị ban bởi admin)
                if (trangThai.equalsIgnoreCase(ibanned)){
                    logo.setVisibility(View.GONE);
                    warning.setVisibility(View.VISIBLE);
                    banned.setVisibility(View.VISIBLE);
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
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            checkTrangthai();
        } else {
            String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA};
            requestPermissions(permission, REQUEST_PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkTrangthai();
            } else {
                checkTrangthai();
            }
        }
    }

}