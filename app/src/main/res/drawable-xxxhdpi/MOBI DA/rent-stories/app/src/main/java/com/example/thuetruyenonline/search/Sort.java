package com.example.thuetruyenonline.search;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sort extends AppCompatActivity  {
    ImageButton ibclose;
    FirebaseFirestore db;

    TextView tvHanhDong,tvTienhiep,tvDigioi,tvtringtham,tvhuyenhuyen,tvdoithuong;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        Intent intent= getIntent();
        db = FirebaseFirestore.getInstance();

//nut dong
        ibclose=findViewById(R.id.ibclose);
        ibclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sort.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
        //viết các thể loại lại trong đây ok

        tvHanhDong=findViewById(R.id.tvhanhdong);
        tvHanhDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String a = "hành động";
                intent.putExtra("theloai",a);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tvTienhiep=findViewById(R.id.tvtienhiep);
        tvTienhiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String a = "tiên hiệp";
                intent.putExtra("theloai",a);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tvDigioi=findViewById(R.id.tvDiGioi);
        tvDigioi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String a = "dị giới";
                intent.putExtra("theloai",a);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tvdoithuong=findViewById(R.id.tvDoiThuong);
        tvdoithuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String a = "đời thường";
                intent.putExtra("theloai",a);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tvtringtham=findViewById(R.id.tvTrinhT);
        tvtringtham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String a = "trinh thám";
                intent.putExtra("theloai",a);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tvhuyenhuyen=findViewById(R.id.tvHuyen);
        tvhuyenhuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String a = "huyền huyễn";
                intent.putExtra("theloai",a);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

}