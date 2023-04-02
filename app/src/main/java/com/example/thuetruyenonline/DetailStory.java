package com.example.thuetruyenonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class DetailStory extends AppCompatActivity {
    ImageView ivANH,ivBACKGR;
    TextView tvGioiThieu,tvName,tvTacGia;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    Story story;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        story=(Story) intent.getSerializableExtra("A");
        getSupportActionBar().setTitle(story.getNamestory());
        ivANH=findViewById(R.id.ivANH);
        ivBACKGR=findViewById(R.id.ivbackgr);
        tvTacGia=findViewById(R.id.tvTacGia);
        tvName=findViewById(R.id.TVName);
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


    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}