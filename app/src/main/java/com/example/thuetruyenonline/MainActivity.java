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
                            Glide.with(MainActivity.this).load("https://www.google.com/imgres?imgurl=https%3A%2F%2Fhoathinh3d.com%2Fwp-content%2Fuploads%2F2022%2F09%2Fdau-pha-thuong-khung-phan-5-gia-nam-hoc-vien-1321.jpg&tbnid=pLBblBzL3xk99M&vet=12ahUKEwiC34CvyoX-AhUvpVYBHU4-DFUQMygAegUIARDGAQ..i&imgrefurl=https%3A%2F%2Fhoathinh3d.com%2Fdau-pha-thuong-khung-phan-5-gia-nam-hoc-vien&docid=k9cVoj3pnLEmLM&w=1335&h=2048&q=%C4%91%C3%A1u%20ph%C3%A1%20th%C6%B0%C6%A1ng%20khung%20p5&ved=2ahUKEwiC34CvyoX-AhUvpVYBHU4-DFUQMygAegUIARDGAQ").into(image);
                    }
                }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }}