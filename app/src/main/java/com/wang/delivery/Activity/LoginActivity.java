package com.wang.delivery.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.delivery.CallBack.ShipperCallBack;
import com.wang.delivery.Databases.DatabaseShipper;
import com.wang.delivery.Model.Shipper;
import com.wang.delivery.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    Button mloginBtn;
    TextView btndangky, btnquenmatkhau;
    EditText mEmail, mPassword;
    ProgressBar progressBar;
    DatabaseShipper databaseShipper;
    ArrayList<Shipper> datastore;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btndangky = findViewById(R.id.btndangky);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mloginBtn = findViewById(R.id.loginBtn);
        btnquenmatkhau = findViewById(R.id.btnquenmatkhau);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(i);
            }
        });

        btnquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QuenMatKhauActivity.class));
            }
        });
    }
//kiểm tra email
    private boolean validateEmail() {
        if (mEmail.getText().toString().trim().isEmpty()) {
            mEmail.setError("Không để trống");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }
// kiểm tra password
    private boolean validatePassword() {
        if (mPassword.getText().toString().trim().isEmpty()) {
            mPassword.setError("Không để trống");
            return false;
        } else {
            mPassword.setError(null);
            return true;
        }
    }
// hàm login
    private void login() {
        final String ussername = mEmail.getText().toString().trim();
        final String pass1 = mPassword.getText().toString().trim();
        if (ussername.isEmpty() || pass1.isEmpty()) {
            mEmail.setError("Bắt buộc");
            mPassword.setError("Bắt buộc");
            Toast.makeText(getApplicationContext(), "Vui Lòng Nhập Đầy Đủ 2 Trường", Toast.LENGTH_SHORT).show();
            return;
        } else if (pass1.length() < 6) {
            mPassword.setError("Mật khẩu phải lớn hơn 6 ký tự");
            return;
        } else if (!ussername.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
            Toast.makeText(getApplicationContext(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
        } else {
            databaseShipper = new DatabaseShipper(getApplicationContext());
            datastore = new ArrayList<>();
            databaseShipper.getAll(new ShipperCallBack() {
                @Override
                public void onSuccess(ArrayList<Shipper> lists) {
                    datastore.clear();
                    datastore.addAll(lists);
                }

                @Override
                public void onError(String message) {

                }
            });
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(ussername, pass1)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                for (int i = 0; i < datastore.size(); i++) {
                                    if (datastore.get(i).getEmail().equalsIgnoreCase(mEmail.getText().toString()) && datastore.get(i).getPassword().equalsIgnoreCase(mPassword.getText().toString())) {
                                        Toast.makeText(getApplicationContext(), "Login Thành Công", Toast.LENGTH_SHORT).show();
                                        Intent is = new Intent(getApplicationContext(), CheckRoleActivity.class);
                                        startActivity(is);
                                        break;
                                    } else {
                                    }
                                }
                            } else {
                            }
                        }
                    });
        }
    }


    /*-------------------- Kiểm tra sự kiện người dùng đăng nhập, đăng xuất --------------------*/
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), CheckRoleActivity.class));
        }
    }

    /*-------------------- END kiểm tra sự kiện người dùng đăng nhập, đăng xuất --------------------*/
}