package com.example.thuetruyenonline.ShowStory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thuetruyenonline.R;

public class ReadTest extends AppCompatActivity {
    TextView tvNoidung,tvName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String id = intent.getStringExtra("tentr");
        String nd = intent.getStringExtra("nd");
        tvName=findViewById(R.id.tvNamet);
        tvNoidung=findViewById(R.id.tvnoidungt);
        tvName.setText(id);
        tvNoidung.setText(nd);
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
