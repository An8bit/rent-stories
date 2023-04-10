package com.example.thuetruyenonline;

import static com.example.thuetruyenonline.R.id.btXong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuetruyenonline.pagehome.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RentStory extends AppCompatActivity{

    FirebaseFirestore db =FirebaseFirestore.getInstance();
    Button btXong,btHome;
    Spinner spOpt,spOpt1;
    Story story;
    ImageView ivANH,ivIcon;
    TextView tvName,tvCoin;
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
                Intent intent = new Intent(RentStory.this,Cart.class);
                AddGioHang(spOpt.getSelectedItem().toString(),spOpt1.getSelectedItem().toString());
                startActivity(intent);
                finish();

            }
        });
        btHome=findViewById(R.id.btHome);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RentStory.this, MainActivity.class);
                //thêm vào database
                AddGioHang(spOpt.getSelectedItem().toString(),spOpt1.getSelectedItem().toString());
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
                int a;
                switch (spOpt.getSelectedItem().toString()){
                    case "3 ngày":
                        a=3000;
                        tvCoin.setText(String.valueOf(a)+" Đ");
                        break;
                    case "1 tuần":
                        a=7000;
                        tvCoin.setText(String.valueOf(a)+ " Đ");
                        break;
                    case "1 tháng":
                        a=30000;
                        tvCoin.setText(String.valueOf(a)+ "Đ");
                        break;
            }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ivIcon=findViewById(R.id.ivIcon);
        spOpt1= findViewById(R.id.spOpt1);
        spOpt1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spOpt1.getSelectedItem().toString()){
                    case "Momo":
                       ivIcon.setImageResource(R.drawable.momo);
                        break;
                    case "ZaloPay":
                       ivIcon.setImageResource(R.drawable.viem);
                        break;
                    case "Banking":
                        ivIcon.setImageResource(R.drawable.tho);
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
                        Log.e("TAG", "Failed to load image", e);
                        // Show error message or perform other action
                    }
                });
        tvName.setText(story.getNamestory());

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    void AddGioHang(String day,String pay){
        DBcontrol dBcontrol = new DBcontrol(RentStory.this);
        dBcontrol.InsertCart(db,story,day,pay);

    }

}