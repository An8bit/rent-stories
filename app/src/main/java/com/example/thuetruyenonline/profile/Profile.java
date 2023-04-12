package com.example.thuetruyenonline.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thuetruyenonline.Cart.Cart;
import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Profile extends AppCompatActivity implements ProfileAdapter.Litenner {


    RecyclerView mRecyclerView;
    ArrayList<ControlProfile> controlProfiles;
    TextView tvEmail,tvName;
    ControlCart controlCart;
    FirebaseFirestore db;
    DBcontrol dBcontrol = new DBcontrol(Profile.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mRecyclerView = findViewById(R.id.rvprofile);
        db = FirebaseFirestore.getInstance();
        Intent intent=new Intent();
        controlCart=(ControlCart) intent.getSerializableExtra("ThanhToan");
        Menu();
        tvEmail=findViewById(R.id.tvEmail);
        tvName=findViewById(R.id.tvName);
        dBcontrol.InsertProfile(db,controlCart,"jiwerjiwer");

    }

    void Menu(){
        ImageView ivHome,ivProfile,ivCart;
        ivHome=findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ivProfile=findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Profile.class);
                startActivity(intent);
            }
        });
        ivCart=findViewById(R.id.ivCart);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Cart.class);
                startActivity(intent);
            }
        });
    }
}