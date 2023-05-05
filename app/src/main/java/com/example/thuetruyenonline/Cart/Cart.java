package com.example.thuetruyenonline.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.Fragments.CartFragments;
import com.example.thuetruyenonline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Cart extends AppCompatActivity implements ShoppingAdapter.Listener{
   ArrayList<ControlCart>controlCarts = new ArrayList<>();
   boolean hasData1;
   int counts=0;
   RecyclerView rvCart;
    TextView tvTongTien;
    Button btThuetruyen;
    FirebaseFirestore db;
    ShoppingAdapter shoppingAdapter;
    Spinner spTT;
    ImageView iviconTT;
    BottomNavigationView bottomNavigationView;
    DBcontrol dBcontrol = new DBcontrol(Cart.this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart=findViewById(R.id.rvCart);
        btThuetruyen=findViewById(R.id.btnThuetruyen);
        db=FirebaseFirestore.getInstance();
        tvTongTien=findViewById(R.id.tvTongTien);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

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

        iviconTT=findViewById(R.id.iviconTT);
        spTT=findViewById(R.id.spTT);
        spTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spTT.getSelectedItem().toString()){
                    case "Momo":
                        iviconTT.setImageResource(R.drawable.momo);
                        break;
                    case "ZaloPay":
                        iviconTT.setImageResource(R.drawable.zalo);
                        break;
                    case "Banking":
                        iviconTT.setImageResource(R.drawable.banking);
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btThuetruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(controlCarts.size()<=0){
                    Toast("hàng rỗng");
                }
               else if(hasData1) {
                    dBcontrol.InsertProfile(db,controlCarts,tvTongTien.getText().toString());
                    controlCarts.clear();
                    shoppingAdapter.notifyDataSetChanged();
                    dBcontrol.DeleteCart(db, dBcontrol.getProviderData());
                    Intent intent = new Intent(Cart.this, CartFragments.class);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onDeleteCart(ControlCart controlCart) {
        dBcontrol.Deleteitemcart(db,controlCart.getId());
        controlCarts.remove(controlCart);
        tvTongTien.setText(String.valueOf(getTotalPrice(controlCarts)));
        shoppingAdapter.notifyDataSetChanged();
    }
    //hàm tính tiền
    public double getTotalPrice(ArrayList<ControlCart> controlCarts) {
        double total = 0;
          for (int i = 0;i<counts;i++) {
              total += controlCarts.get(i).getTotalPrice();
          }
        return total;
    }

    @Override
    public void onEditCart(ControlCart controlCart) {
        tvTongTien.setText(String.valueOf(getTotalPrice(controlCarts)));
        controlCart.setGiatien(String.valueOf(shoppingAdapter.GiaTien));
        shoppingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChecked(boolean hasdata) {

    }



    void  Toast(String a){
        Toast toast= Toast.makeText(Cart.this,a,Toast.LENGTH_SHORT);
        toast.show();
    }
}
