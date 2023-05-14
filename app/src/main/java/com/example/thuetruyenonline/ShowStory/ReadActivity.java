package com.example.thuetruyenonline.ShowStory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity implements ReadAdaper.Listener {
    TextView tvNoidung,tvName;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DBcontrol dBcontrol = new DBcontrol(ReadActivity.this);
    ArrayList<DataStory> dataStories=new ArrayList<>();
    ReadAdaper readAdaper;
    RecyclerView rcView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_acti);
        tvNoidung=findViewById(R.id.tvnoidung);
        tvName=findViewById(R.id.tvName);
        rcView=findViewById(R.id.ivchapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupBadge();
       Intent intent = getIntent();
       String id = intent.getStringExtra("tentr");
       String idtruyen=intent.getStringExtra("idtruyen");
        Log.e("idto",idtruyen );
       tvName.setText(id);
        dBcontrol.getChapter(db, idtruyen, new DBcontrol.OnGetChapterListener() {
            @Override
            public void onSuccess(ArrayList<DataStory> dataStories1) {
               dataStories.addAll(dataStories1);
                readAdaper=new ReadAdaper(dataStories,ReadActivity.this);
                rcView.setAdapter(readAdaper);
                rcView.setLayoutManager(new LinearLayoutManager(ReadActivity.this, LinearLayoutManager.VERTICAL, false));
                rcView.addItemDecoration(new DividerItemDecoration(ReadActivity.this, LinearLayoutManager.VERTICAL));
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });


    }

    private void setupBadge() {

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}