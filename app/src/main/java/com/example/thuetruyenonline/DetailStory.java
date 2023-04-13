package com.example.thuetruyenonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thuetruyenonline.Cart.Cart;
import com.example.thuetruyenonline.Cart.RentStory;
import com.example.thuetruyenonline.pagehome.Story;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class DetailStory extends AppCompatActivity {
    ImageView ivANH,ivBACKGR;
    TextView tvGioiThieu,tvName,tvTacGia,tvTaglist,tvaddcart;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AppCompatRatingBar Rate;
    String email;

    Story story;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        story=(Story) intent.getSerializableExtra("A");
        email=intent.getStringExtra("email");
        getSupportActionBar().setTitle(story.getNamestory());
        ivANH=findViewById(R.id.ivmanga_art);
        ivBACKGR=findViewById(R.id.background_art);
        tvTacGia=findViewById(R.id.tvTacGia);
        tvName=findViewById(R.id.tvtitle);
        tvGioiThieu=findViewById(R.id.tvGioiThieu);
        StorageReference imageRef = storage.getReferenceFromUrl(story.getImage());
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                      Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ivANH.setImageBitmap(bitmap);
                        ivBACKGR.setImageBitmap(bitmap);
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
        tvGioiThieu.setText(story.getGioithieu());
        tvTacGia.setText("Tác giả: "+story.getTacgia());
        tvTaglist=findViewById(R.id.tag_list);
        tvTaglist.setText("Thể Loại : "+story.getTheloai());
        Rate=findViewById(R.id.rating);
        Rate.setRating(5);
        tvaddcart=findViewById(R.id.add_to_lib);
        tvaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGioHang("null","0");
            }

        });



    }
    void AddGioHang(String day,String giatien){
        DBcontrol dBcontrol = new DBcontrol(DetailStory.this);
        dBcontrol.InsertCart(db,story,day,giatien);

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}