package com.example.thuetruyenonline.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thuetruyenonline.Cart.Cart;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.example.thuetruyenonline.pagehome.Story;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Profile extends AppCompatActivity implements ProfileAdapter.Litenner{
    RecyclerView mRecyclerView;
    Story stories;
    ProfileAdapter profileAdapter;
    ArrayList<ControlProfile> controlProfiles;
    FirebaseFirestore db;

    DBcontrol dBcontrol = new DBcontrol(Profile.this);
    TextView tvName,tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
            db=FirebaseFirestore.getInstance();
            mRecyclerView=findViewById(R.id.rvProfile);
            tvEmail=findViewById(R.id.tvEmail);
            tvEmail.setText(dBcontrol.getProviderData());

            Menu();
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

        void Menu(){
            ImageView ivHome,ivProfile,ivCart;
            ivHome=findViewById(R.id.ivHome);
            ivHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            ivProfile=findViewById(R.id.ivProfile);
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this,Profile.class);
                    startActivity(intent);
                    finish();
                }
            });
            ivCart=findViewById(R.id.ivCart);
            ivCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Profile.this, Cart.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }