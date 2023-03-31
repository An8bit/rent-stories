package com.example.thuetruyenonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore db ;
    EditText e,p;
    ImageButton image;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_login);
        e=findViewById(R.id.etEmail);
        p=findViewById(R.id.etPass);
         image=  findViewById(R.id.imgu);
        db.collection("Truyen")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot docu:task.getResult()){
//                            String tk = docu.get("TaiKhoan").toString();
//                            String pa =docu.get("matkhau").toString();
                            String anh = docu.get("AnhLoad").toString();
                            Glide.with(MainActivity.this).load("").into(image);
                    }
                }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }}