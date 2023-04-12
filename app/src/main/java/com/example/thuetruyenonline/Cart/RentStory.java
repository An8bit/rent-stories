package com.example.thuetruyenonline.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.pagehome.Story;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RentStory extends AppCompatActivity{

    TextView textCartItemCount;
    int mCartItemCount = 10;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    Button btXong,btHome;
    Spinner spOpt,spOpt1;
    Story story;
    ImageView ivANH;
    TextView tvName,tvCoin;
    int giatien;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_story);
        story=(Story) getIntent().getSerializableExtra("Rent");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btXong=findViewById(R.id.btXong);
        btXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //đọc tên tên trong bảng rồi add vào
                AddGioHang(spOpt.getSelectedItem().toString(),String.valueOf(giatien));
            }
        });
        btHome=findViewById(R.id.btHome);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RentStory.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        spOpt=findViewById(R.id.spOpt);
        tvCoin=findViewById(R.id.tvCoin);
        //Khi người dùng thay đổi giá trị của Spinner, Listener sẽ được gọi và có thể cập nhật
        spOpt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (spOpt.getSelectedItem().toString()){
                    case "3 ngày":
                        giatien=3000;
                        tvCoin.setText(String.valueOf(giatien)+" Đ");
                        break;
                    case "1 tuần":
                        giatien=7000;
                        tvCoin.setText(String.valueOf(giatien)+ " Đ");
                        break;
                    case "1 tháng":
                        giatien=30000;
                        tvCoin.setText(String.valueOf(giatien)+ "Đ");
                        break;
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ivANH=findViewById(R.id.ivAnhd);
        tvName=findViewById(R.id.tvNameStory);
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(story.getImage());
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ivANH.setImageBitmap(bitmap);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        tvName.setText(story.getNamestory());

    }
    //
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_Cart);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
                Intent intent = new Intent (RentStory.this,Cart.class);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_Cart: {
                // Do something
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    void AddGioHang(String day,String giatien){
        DBcontrol dBcontrol = new DBcontrol(RentStory.this);
        dBcontrol.InsertCart(db,story,day,giatien);

    }
    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}