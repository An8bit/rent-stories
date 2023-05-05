package com.example.thuetruyenonline.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thuetruyenonline.Cart.Cart;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.ShowStory.ReadActivity;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.example.thuetruyenonline.pagehome.Story;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Profile extends AppCompatActivity implements ProfileAdapter.Litenner{
    RecyclerView mRecyclerView;
    Story stories;
    ProfileAdapter profileAdapter;
    ArrayList<ControlProfile> controlProfiles;
    FirebaseFirestore db;
    BottomNavigationView bottomNavigationView;
    DBcontrol dBcontrol = new DBcontrol(Profile.this);
    TextView tvName,tvEmail;
    Button btdoc;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
            db=FirebaseFirestore.getInstance();
            mRecyclerView=findViewById(R.id.rvProfile);
            tvEmail=findViewById(R.id.tvEmail);
            tvEmail.setText(dBcontrol.getProviderData());
            dBcontrol.GetProfile(db, new DBcontrol.onGetProfileListener() {
                @Override
                public void onSuccess(ArrayList<ControlProfile> controlProfiles1) {
                    controlProfiles=controlProfiles1;
                    tvName=findViewById(R.id.tvName);
                    tvName.setText(dBcontrol.getProviderData().substring(0,dBcontrol.getProviderData().indexOf("@")));
                    profileAdapter = new ProfileAdapter(controlProfiles,Profile.this);
                    mRecyclerView.setAdapter(profileAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(Profile.this, LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(Profile.this, LinearLayoutManager.VERTICAL));
                }
                @Override
                public void onFailure(String errorMessage) {
                }
            });
        }



    @Override
    public void onGetStory(ControlProfile controlProfile) {
        Intent intent=new Intent(Profile.this,ReadActivity.class);
        intent.putExtra("tentr",controlProfile.getNamestory());
        intent.putExtra("nd",controlProfile.getNoidung());
        startActivity(intent);
    }
}