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

public class Sort extends AppCompatActivity implements StoryAdapter.Listener {
    ImageButton ibclose;
    FirebaseFirestore db;
    RecyclerView rvStory;
    ArrayList<Story> stories;
    StoryAdapter storyAdapter;
    Intent intent;
    String email;
    DBcontrol dBcontrol = new DBcontrol(Sort.this);

    int flag;
    TextView tvHanhDong;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        Intent intent= getIntent();
        flag=intent.getIntExtra("HanhDong",0);
        db = FirebaseFirestore.getInstance();
        tvHanhDong=findViewById(R.id.tvhanhdong);
        tvHanhDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "HanhDong";
                Sort(a,db);

                Intent intent =new Intent(Sort.this,MainActivity.class);
                intent.putExtra("HanhDong",a);
                intent.putExtra("flag",1);
                    startActivity(intent);
                    finish();
            }
        });

        ibclose=findViewById(R.id.ibclose);
        ibclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sort.this, MainActivity.class);
                setResult(RESULT_OK,intent);
                intent.putExtra("flag",1);
                intent.putExtra("HanhDong",stories);
                startActivity(intent);
                finish();
            }

        });


    }

    void Sort(String Sorts,FirebaseFirestore db){
        dBcontrol.Sort(Sorts, db, new DBcontrol.OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Story> storie) {
                stories=storie;
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

    }

    @Override
    public void onItemClickListener(Story story) {


    }
}