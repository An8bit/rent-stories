package com.example.thuetruyenonline.ShowStory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.Cart.RentStory;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadActivity extends AppCompatActivity {
    TextView tvNoidung,tvName;
//    FirebaseFirestore db =FirebaseFirestore.getInstance();
//    DBcontrol dBcontrol = new DBcontrol(ReadActivity.this);
//    ArrayList<ControlCart> controlCarts;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        tvNoidung=findViewById(R.id.tvnoidung);
        tvName=findViewById(R.id.tvName);
        setupBadge();
       Intent intent = getIntent();
       String id = intent.getStringExtra("tentr");
       String noidung=intent.getStringExtra("nd");
       tvNoidung.setText(noidung);
       tvName.setText(id);

//       db.collection("TaiKhoan").document(dBcontrol.getProviderData()).collection("damua").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//           @Override
//           public void onComplete(@NonNull Task<QuerySnapshot> task) {
//               for (QueryDocumentSnapshot document : task.getResult()){
//                   String noidung = document.getString("noidung");
//                   tvNoidung.setText(noidung);
//               }
//           }
//       });

    }

    private void setupBadge() {

    }



}