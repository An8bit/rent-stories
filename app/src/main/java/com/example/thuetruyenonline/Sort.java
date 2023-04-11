package com.example.thuetruyenonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thuetruyenonline.pagehome.MainActivity;
import com.example.thuetruyenonline.pagehome.StoryAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Sort extends AppCompatActivity  {
    ImageButton ibclose;
    FirebaseFirestore db;

    TextView tvHanhDong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        Intent intent= getIntent();
        db = FirebaseFirestore.getInstance();
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


    }

}