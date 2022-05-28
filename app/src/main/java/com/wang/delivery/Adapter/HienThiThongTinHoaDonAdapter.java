package com.wang.delivery.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.delivery.LocalStorage.LocalStorage;
import com.wang.delivery.Model.HDCT;
import com.wang.delivery.Model.Order;
import com.wang.delivery.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HienThiThongTinHoaDonAdapter
//        extends RecyclerView.Adapter<HienThiThongTinHoaDonAdapter.MyViewHolder>
{

//    ArrayList<HDCT> cartList;
//    CartHDCTAdapter cartAdapter;
//    Context context;
//
//    String _subtotal, _price, _quantity;
//    LocalStorage localStorage;
//    Gson gson;
//
//    public HienThiThongTinHoaDonAdapter(ArrayList<HDCT> cartList, Context context) {
//        this.cartList = cartList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView;
//        itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_carthdct, parent, false);
//        return new HienThiThongTinHoaDonAdapter.MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//        decimalFormat.applyPattern("#,###,###,###");
//        final HDCT cart = cartList.get(position);
//
//        cartAdapter = new CartHDCTAdapter(cart.getOrderArrayList(),context);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
//        holder.cardView.setLayoutManager(linearLayoutManager);
//        holder.cardView.setAdapter(cartAdapter);
//
//
//        holder.title.setText(cart.getFood().getTenSanPham());
//        holder.attribute.setText(cart.getFood().getMatheloai());
//        holder.quantity.setText(String.valueOf(decimalFormat.format(cart.getSoluongmua())));
//        holder.price.setText(String.valueOf(decimalFormat.format(cart.getFood().getGiaTien())));
//        _subtotal = String.valueOf(decimalFormat.format(cart.getSoluongmua() * cart.getFood().getGiaTien()));
//        holder.subTotal.setText(_subtotal);
//        Picasso.get()
//                .load(cart.getFood().getAnh())
//                .into(holder.imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        holder.progressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        Log.d("Error : ", e.getMessage());
//                    }
//                });
//    }
//
//    @Override
//    public int getItemCount() {
//        return cartList.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView title;
//        ProgressBar progressBar;
//        CardView cardView;
//        TextView offer, currency, price, quantity, attribute, addToCart, subTotal;
//        TextView plus, minus;
//        Button delete;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imageView = itemView.findViewById(R.id.product_image);
//            title = itemView.findViewById(R.id.product_title);
//            progressBar = itemView.findViewById(R.id.progressbar);
//            quantity = itemView.findViewById(R.id.quantity);
//            currency = itemView.findViewById(R.id.product_currency);
//            attribute = itemView.findViewById(R.id.product_attribute);
//            minus = itemView.findViewById(R.id.quantity_minus);
//            subTotal = itemView.findViewById(R.id.sub_total);
//            price = itemView.findViewById(R.id.product_price);
//            cardView = itemView.findViewById(R.id.card_view);
//        }
//    }
//

}
