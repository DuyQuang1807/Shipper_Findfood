package com.wang.delivery.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.delivery.CallBack.HDCTCallBack;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.CallBack.StoreCallBack;
import com.wang.delivery.CallBack.UserCallBack;
import com.wang.delivery.Databases.DatabaseHDCT;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Databases.DatabaseStore;
import com.wang.delivery.Databases.DatabaseUser;
import com.wang.delivery.Model.HDCT;
import com.wang.delivery.Model.Order;
import com.wang.delivery.Model.Shipper;
import com.wang.delivery.Model.Store;
import com.wang.delivery.Model.User;
import com.wang.delivery.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class XacNhanAdapter  extends RecyclerView.Adapter<XacNhanAdapter.MyViewHolder> {

    ArrayList<HDCT> cartList;
    Context context;
    CartHDCTAdapter cartAdapter;
    double tongtien=0;
    ArrayList<Order> orderArrayList;
    DatabaseUser daoUser;
    DatabaseShipper databaseShipper;
    DatabaseHDCT databaseHDCT;
    DatabaseStore daoStore;
    FirebaseUser firebaseUser;
    String tokkenstore ="";
    String tokenNguoiMua ="";

    String idNguoiDatMon= "";
    String trangThaiDonHang = "";
    String idCuaHoaDon = "";
    String idCuaHoaDon2 = "";
    String payment = "";
    String thoiGian = "";

    public XacNhanAdapter(ArrayList<HDCT> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }


    public void loadData () {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_xacnhan, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        orderArrayList = new ArrayList<>();

        databaseShipper = new DatabaseShipper(context);
        databaseHDCT = new DatabaseHDCT(context);

        daoStore = new DatabaseStore(context);
        daoUser = new DatabaseUser(context);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final HDCT cart = cartList.get(position);
        holder.txtxacnhan.setText(cart.getCheck());
        holder.txttime.setText(cart.getThoigian());

        holder.btnNhanDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference datahoadon = FirebaseDatabase.getInstance().getReference("HDCT");
                datahoadon.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        orderArrayList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            HDCT hdct = dataSnapshot.getValue(HDCT.class);
                            if (hdct.getIdHDCT().equalsIgnoreCase(cart.getIdHDCT())){
                                idNguoiDatMon = hdct.getIdUser();
                                trangThaiDonHang = hdct.getCheck();
                                idCuaHoaDon = hdct.getIdHDCT();
                                idCuaHoaDon2 = hdct.getIdHoaDon();
                                orderArrayList.clear();
                                orderArrayList.addAll(hdct.getOrderArrayList());
                                payment = hdct.getPayment();
                                thoiGian = hdct.getThoigian();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Log.d("Thien IDHDCT", idCuaHoaDon);

                HDCT hdcttest = new HDCT(idCuaHoaDon, idCuaHoaDon2, thoiGian, "waiting", idNguoiDatMon, payment,firebaseUser.getUid(),  orderArrayList);
                databaseHDCT.update(hdcttest);
                Toast.makeText(context, "Nhận đơn Thành Công", Toast.LENGTH_SHORT).show();
            }
        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.dulieusach);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button quaylai = myDialog.findViewById(R.id.quaylai);
                TextView txttongtien = (TextView) myDialog.findViewById(R.id.txttongtien);
                TextView  txtnguoimua =  myDialog.findViewById(R.id.txtnguoimua);
                final TextView   txtnguoiban =  myDialog.findViewById(R.id.txtnguoiban);
                final RecyclerView recyclerViewsp =  myDialog.findViewById(R.id.recyclesanpham);
                cartAdapter = new CartHDCTAdapter(cart.getOrderArrayList(),context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
                recyclerViewsp.setLayoutManager(linearLayoutManager);
                recyclerViewsp.setAdapter(cartAdapter);
                final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                decimalFormat.applyPattern("#,###,###,###");

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HDCT");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        orderArrayList.clear();
                        double tongtien1 =0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            HDCT hdct = dataSnapshot.getValue(HDCT.class);
                            if (hdct.getIdHDCT().equalsIgnoreCase(cart.getIdHDCT())){
                                orderArrayList.addAll(hdct.getOrderArrayList());

                                for (Order order : orderArrayList){
                                    tongtien1 += order.getSoluongmua() * order.getFood().getGiaTien();
                                    tokkenstore = order.getFood().getTokenstore();
                                    tokenNguoiMua = order.getUser().getName();
                                }

                                Log.d("thien tokenstore", tokkenstore);

                                daoStore.getAll(new StoreCallBack() {
                                    @Override
                                    public void onSuccess(ArrayList<Store> lists) {
                                        for (int i =0;i<lists.size();i++){
                                            if (lists.get(i).getTokenstore().equalsIgnoreCase(tokkenstore)){
                                                txtnguoiban.setText(lists.get(i).getTenCuaHang());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(String message) {

                                    }
                                });
                                txtnguoimua.setText(tokenNguoiMua);
                                txttongtien.setText("Tổng Tiền: \t" + decimalFormat.format(tongtien1)+"VNĐ");
                                break;

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                quaylai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.cancel();
                    }
                });
                myDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        TextView txtxacnhan, txttime;
        Button btnNhanDon, btnHuyDon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtxacnhan =  itemView.findViewById(R.id.txtxacnhan);
            txttime =  itemView.findViewById(R.id.txttime);
//            btnNhanDon = itemView.findViewById(R.id.btnNhanDon);
//            btnHuyDon = itemView.findViewById(R.id.btnHuyDon);
            card_view =  itemView.findViewById(R.id.card_view);
        }
    }
}
