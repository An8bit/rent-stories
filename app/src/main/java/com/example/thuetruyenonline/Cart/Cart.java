package com.example.thuetruyenonline.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.Profile;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.Story;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Cart extends AppCompatActivity implements ShoppingAdapter.Listener{

   ArrayList<ControlCart>controlCarts;
   RecyclerView rvCart;
    FirebaseFirestore db;
    ShoppingAdapter shoppingAdapter;
    DBcontrol dBcontrol = new DBcontrol(Cart.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Menu();
        rvCart=findViewById(R.id.rvCart);
        db=FirebaseFirestore.getInstance();
        dBcontrol.getCart(dBcontrol.getProviderData(), db, new DBcontrol.onGetCartListener() {
            @Override
            public void onSucess(ArrayList<ControlCart> controlCarts1) {
                controlCarts=controlCarts1;
               shoppingAdapter=new ShoppingAdapter(controlCarts,Cart.this);
                rvCart.setAdapter(shoppingAdapter);
                rvCart.setLayoutManager(new LinearLayoutManager(Cart.this, LinearLayoutManager.VERTICAL, false));
                rvCart.addItemDecoration(new DividerItemDecoration(Cart.this, LinearLayoutManager.VERTICAL));
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
                Intent intent = new Intent(Cart.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ivProfile=findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Profile.class);
                startActivity(intent);
            }
        });
        ivCart=findViewById(R.id.ivCart);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this,Cart.class);
                startActivity(intent);
            }
        });
    }
}