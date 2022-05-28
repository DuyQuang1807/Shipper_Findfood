package com.wang.delivery.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wang.delivery.Adapter.NhanDonAdapter;
import com.wang.delivery.Adapter.Tabadapter;
import com.wang.delivery.CallBack.HDCTCallBack;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.CallBack.UserCallBack;
import com.wang.delivery.Databases.DatabaseHDCT;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Databases.DatabaseUser;
import com.wang.delivery.Fragment.FragmentChoGiao;
import com.wang.delivery.Fragment.FragmentChoNhan;
import com.wang.delivery.Fragment.FragmentDaGiao;
import com.wang.delivery.Model.HDCT;
import com.wang.delivery.Model.Shipper;
import com.wang.delivery.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private NhanDonAdapter nhanDonAdapter;
    private ArrayList<HDCT> hdctArrayList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private de.hdodenhof.circleimageview.CircleImageView anhdaidien;
    private DatabaseShipper databaseShipper;
    private DatabaseHDCT databaseHDCT;
    private FirebaseUser firebaseUser;
    private String anh;
    private RecyclerView rcvHome;

    private LocationManager locationManager;
    private String country, locality, state, noi;
    private TextView txtDiachi;
    private String trangThaiHDCT = "waiting";
    private LinearLayout linearTong;
    private String trangThai = "";
    String check = "active";
    com.airbnb.lottie.LottieAnimationView warning;
    TextView banned;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        anhdaidien = findViewById(R.id.anhdaidien);
        linearTong = findViewById(R.id.linearTong);
        tabLayout = findViewById(R.id.tabLayout);
        warning = findViewById(R.id.animation_view);
        banned = findViewById(R.id.tv_banned);
        rcvHome = findViewById(R.id.rcvHome);

        LoadImage();
        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                // data request
                CheckRole();
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(refresh, 2000);

        hdctArrayList = new ArrayList<>();
        nhanDonAdapter = new NhanDonAdapter(hdctArrayList, getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);


        rcvHome.setLayoutManager(gridLayoutManager);
        rcvHome.setHasFixedSize(true);
        rcvHome.setAdapter(nhanDonAdapter);
        databaseHDCT = new DatabaseHDCT(getApplicationContext());
        databaseHDCT.getAll(new HDCTCallBack() {
            @Override
            public void onSuccess(ArrayList<HDCT> lists) {
                hdctArrayList.clear();
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getCheck().equalsIgnoreCase(trangThaiHDCT)) {
                        hdctArrayList.add(lists.get(i));
                        nhanDonAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();
//        txtDiachi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Vị trí hiện tại của bạn là: ", Toast.LENGTH_SHORT).show();
//            }
//        });
        anhdaidien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrangCaNhanActivity.class);
                v.getContext().startActivity(intent);
            }
        });



    }

    private void CheckRole() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String idUser = firebaseUser.getUid();
            databaseShipper = new DatabaseShipper(getApplicationContext());
            databaseShipper.getAll(new ShipperCallBack() {
                @Override
                public void onSuccess(ArrayList<Shipper> lists) {
                    for (int i = 0; i < lists.size(); i++) {
                        if (lists.get(i).getToken() != null && lists.get(i).getToken().equalsIgnoreCase(idUser)) {
                            trangThai = lists.get(i).getTrangThai();
                        }
                    }
                    if (trangThai.equalsIgnoreCase(check)) {
                        Log.d("TrangThai", trangThai);
                    } else {
                        linearTong.setVisibility(View.GONE);
                        warning.setVisibility(View.VISIBLE);
                        banned.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        } else {
            // No user is signed in
        }
    }

    public void LoadImage() {
        databaseShipper = new DatabaseShipper(getApplicationContext());
        databaseShipper.getAll(new ShipperCallBack() {
            @Override
            public void onSuccess(ArrayList<Shipper> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getToken() != null && lists.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())) {
                        anh = lists.get(i).getAnh();
                    }
                }
                if (anh == null) {
                    Picasso.get().load("https://vnn-imgs-a1.vgcloud.vn/image1.ictnews.vn/_Files/2020/03/17/trend-avatar-1.jpg").into(anhdaidien);
                } else if (anh != null) {
                    Picasso.get().load(anh).into(anhdaidien);
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    /*----------- Lấy vị trí hiện tại -----------*/

    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Làm ơn bật dịch vụ GPS")
                    .setMessage("Chúng tôi cần dịch vụ GPS để hiển thị vị trí của bạn.")
                    .setCancelable(false)
                    .setPositiveButton("Bật", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Tắt", null)
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            country = addresses.get(0).getCountryName();
            locality = addresses.get(0).getAddressLine(0);
            state = addresses.get(0).getAdminArea();
            noi = locality;
            txtDiachi.setText(noi);

        } catch (Exception e) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*----------- End lấy vị trí hiện tại -----------*/
}