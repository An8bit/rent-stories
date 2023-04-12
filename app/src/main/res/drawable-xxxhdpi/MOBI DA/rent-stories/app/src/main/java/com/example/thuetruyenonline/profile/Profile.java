package com.example.thuetruyenonline.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thuetruyenonline.Cart.Cart;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    RecyclerView mRecyclerView;
    TextView tvEmail,tvName;
    FirebaseFirestore db;
    DBcontrol dBcontrol = new DBcontrol(Profile.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mRecyclerView = findViewById(R.id.rvProfile);
        db = FirebaseFirestore.getInstance();
        Menu();
        tvEmail=findViewById(R.id.tvEmail);
        tvName=findViewById(R.id.tvName);
        tvEmail.setText(dBcontrol.getProviderData());
        tvName.setText(dBcontrol.getProviderData().substring(0,dBcontrol.getProviderData().indexOf("@")));
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