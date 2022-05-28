package com.wang.delivery.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.delivery.Adapter.CartHDCTAdapter;
import com.wang.delivery.Adapter.CartHDCTAdapter1;
import com.wang.delivery.Adapter.HienThiThongTinHoaDonAdapter;
import com.wang.delivery.CallBack.HDCTCallBack;
import com.wang.delivery.CallBack.StoreCallBack;
import com.wang.delivery.CallBack.UserCallBack;
import com.wang.delivery.Databases.DatabaseHDCT;
import com.wang.delivery.Databases.DatabaseHDCT1;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Databases.DatabaseStore;
import com.wang.delivery.Databases.DatabaseUser;
import com.wang.delivery.LocalStorage.LocalStorage;
import com.wang.delivery.Model.HDCT;
import com.wang.delivery.Model.HDCT1;
import com.wang.delivery.Model.Order;
import com.wang.delivery.Model.Order1;
import com.wang.delivery.Model.Store;
import com.wang.delivery.Model.User;
import com.wang.delivery.R;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HienThiThongTinHoaDonActivity extends AppCompatActivity {
    String idHDCT;
    TextView txtDiaChiKhach, txtphoneNguoiMua, txtPayment, txttongtien, txtTenKhach, loi;
    RecyclerView recyclesanpham1;
    ArrayList<HDCT> cartList;
    Button btnXacNhan;

    CartHDCTAdapter1 cartAdapter1 = null;

    double tongtien = 0;
    ArrayList<Order> orderArrayListLoadData;
    ArrayList<Order1> orderArrayList;
    DatabaseUser daoUser;
    DatabaseShipper databaseShipper;
    DatabaseHDCT databaseHDCT;
    DatabaseHDCT1 databaseHDCT1;
    DatabaseStore databaseStore;
    FirebaseUser firebaseUser;
    String tokkenstore = "";
    double tongtien1 = 0;
    double tongtiend = 0;

    String idNguoiDatMon = "";
    String trangThaiDonHang = "";
    String idCuaHoaDon = "";
    String idCuaHoaDon2 = "";
    String payment = "";
    String thoiGian = "";

    String soLuong = "" ;
    String giaTien = "";
    String tongtiena = "";
    LinearLayout layoutTong;
    String check = "";
    String delivering = "delivering";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_thong_tin_hoa_don);

        txtDiaChiKhach = findViewById(R.id.txtDiaChiKhach);
        txtphoneNguoiMua = findViewById(R.id.txtphoneNguoiMua);
        txtPayment = findViewById(R.id.txtPayment);
        txttongtien = findViewById(R.id.txttongtien);
        txtTenKhach = findViewById(R.id.txtTenKhach);
        loi = findViewById(R.id.loi);
        layoutTong = findViewById(R.id.layoutTong);
        recyclesanpham1 = findViewById(R.id.recyclesanpham1);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        Intent intent = getIntent();
// hứng data từ testQR
        idHDCT = intent.getStringExtra("IdHDCTQuangTrong");
//load data cho fragment
        LoadData();

        cartList = new ArrayList<>();

        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");

        databaseShipper = new DatabaseShipper(getApplicationContext());
        databaseHDCT = new DatabaseHDCT(getApplicationContext());
        databaseHDCT1 = new DatabaseHDCT1(getApplicationContext());

        databaseStore = new DatabaseStore(getApplicationContext());
        daoUser = new DatabaseUser(getApplicationContext());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        orderArrayList = new ArrayList<>();
        orderArrayListLoadData = new ArrayList<>();
//lấy data user
        LoadDataUser();
//lấy data cửa hnafg
        LoadDataStore();
//lấy data từ hoá đơn chi tiết
        HienThiDSMA();
//        txtPayment.setText(payment);
//
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference datahoadon = FirebaseDatabase.getInstance().getReference("HDCT");
                datahoadon.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        orderArrayList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            HDCT1 hdct = dataSnapshot.getValue(HDCT1.class);
                            if (hdct.getIdHDCT().equalsIgnoreCase(idHDCT)){
                                idNguoiDatMon = hdct.getIdUser();
                                trangThaiDonHang = hdct.getCheck();
                                idCuaHoaDon = hdct.getIdHDCT();
                                idCuaHoaDon2 = hdct.getIdHoaDon();
                                orderArrayList.clear();
                                orderArrayList.addAll(hdct.getOrderArrayList());
                                payment = hdct.getPayment();
                                thoiGian = hdct.getThoigian();
                                check = hdct.getCheck();
                                Log.d("check trang thai ", check);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Log.d("check IDHDCT", idCuaHoaDon);

                HDCT1 hdcttest = new HDCT1(idCuaHoaDon, idCuaHoaDon2, thoiGian, delivering, idNguoiDatMon, payment, firebaseUser.getUid(), orderArrayList);
                databaseHDCT1.update(hdcttest);
                Log.d("thien token shipper", firebaseUser.getUid());
                Toast.makeText(getApplicationContext(), "Nhận đơn Thành Công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), QuanLyDonHangActivity.class));
            }
        });


    }

    private void HienThiDSMA() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HDCT");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HDCT1 hdct = dataSnapshot.getValue(HDCT1.class);
                    if (hdct.getIdHDCT().equalsIgnoreCase(idHDCT)){
                        orderArrayList.addAll(hdct.getOrderArrayList());
                        cartAdapter1 = new CartHDCTAdapter1(hdct.getOrderArrayList(), getApplicationContext());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        recyclesanpham1.setLayoutManager(linearLayoutManager);
                        recyclesanpham1.setAdapter(cartAdapter1);
                        Log.d("check", "vao duoc adpter");
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LoadDataStore() {
        DatabaseStore databaseStore = new DatabaseStore(getApplicationContext());
        databaseStore.getAll(new StoreCallBack() {
            @Override
            public void onSuccess(ArrayList<Store> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getTokenstore().equalsIgnoreCase(tokkenstore)) {
                        lists.get(i).getEmail();
                        lists.get(i).getPass();
                        lists.get(i).getTenCuaHang();
                        lists.get(i).getPhone();
                        lists.get(i).getImage();
                        lists.get(i).getDiaChi();
                        lists.get(i).getTrangThai();
                        lists.get(i).getMoTa();
                        lists.get(i).getCv();

                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void LoadDataUser() {
        daoUser.getAll(new UserCallBack() {
            @Override
            public void onSuccess(ArrayList<User> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getToken().equalsIgnoreCase(idNguoiDatMon)) {
                        lists.get(i).getEmail();
                        lists.get(i).getPassword();
                        lists.get(i).getName();
                        lists.get(i).getPhone();
                        lists.get(i).getImage();
                        lists.get(i).getDiachi();
                        lists.get(i).getTrangThai();
                        lists.get(i).getName();
                        lists.get(i).getDiachi();
                        txtphoneNguoiMua.setText(lists.get(i).getPhone());
                        txtDiaChiKhach.setText(lists.get(i).getDiachi());
                        txtTenKhach.setText(lists.get(i).getName());
                        Log.d("check soDienThoaiKhach", idNguoiDatMon);
                        Log.d("check soDienThoaiKhach", lists.get(i).getPhone());
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }   //User

    private void LoadData() {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        DatabaseReference datahoadon = FirebaseDatabase.getInstance().getReference("HDCT");
        datahoadon.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderArrayListLoadData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HDCT hdct = dataSnapshot.getValue(HDCT.class);
                    if (hdct.getIdHDCT().equalsIgnoreCase(idHDCT)) {
                        idNguoiDatMon = hdct.getIdUser();
                        trangThaiDonHang = hdct.getCheck();
                        idCuaHoaDon = hdct.getIdHDCT();
                        idCuaHoaDon2 = hdct.getIdHoaDon();
                        payment = hdct.getPayment();
                        thoiGian = hdct.getThoigian();

                        orderArrayListLoadData.clear();
                        orderArrayListLoadData.addAll(hdct.getOrderArrayList());
                        for (Order order : orderArrayListLoadData) {
                            soLuong = String.valueOf(order.getSoluongmua());
                            giaTien = String.valueOf(order.getFood().getGiaTien());
                            tongtiena = String.valueOf((order.getFood().getGiaTien() * ((100 - order.getFood().getKhuyenMai()) * 0.01)));
                            tongtien1 = tongtien1 + order.getSoluongmua() * (order.getFood().getGiaTien() * ((100 - order.getFood().getKhuyenMai()) * 0.01));
                            tokkenstore = order.getFood().getTokenstore();
                            tongtiend = tongtien + order.getSoluongmua() * (order.getFood().getGiaTien() * ((100 - order.getFood().getKhuyenMai()) * 0.01));
                            Log.d("checktongtienHT", String.valueOf(tongtiend));

                        }
                        txttongtien.setText("Tổng Tiền: \t" + decimalFormat.format(tongtien1) + "VNĐ");
                    }
                }
                Log.d("checktongtienKM", tongtiena);

                Log.d("checkSL",soLuong);
                Log.d("checkGT",giaTien);
                Log.d("checktongTien", decimalFormat.format(tongtien1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}