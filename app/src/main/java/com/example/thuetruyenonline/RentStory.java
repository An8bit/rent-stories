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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

public class RentStory extends AppCompatActivity {

    String random;
    Button btXong;
    Spinner spOpt;
    Story story;
    ImageView ivANH;
    TextView tvName,tvCoin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_story);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btXong=findViewById(R.id.btXong);
        btXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                random = Random();
                AlertDialog.Builder builder = new AlertDialog.Builder(RentStory.this);
                builder.setTitle("Thông tin thanh toán");
                builder.setMessage("Vui lòng copy lại mã để kích hoạt sách");
                final TextView key = new TextView(RentStory.this);
                key.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                key.setText(random);
                LinearLayout layout = new LinearLayout(RentStory.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(key);
                builder.setView(layout);
                builder.setPositiveButton("copy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        String textToCopy =random ;
                        ClipData clipData = ClipData.newPlainText("label", textToCopy);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getApplicationContext(), "Đã copy nội dung", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        story=(Story) getIntent().getSerializableExtra("Rent");
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
    String Random(){
        int length = 10; // Độ dài của chuỗi ngẫu nhiên
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Các ký tự có thể có trong chuỗi ngẫu nhiên
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return  sb.toString();

    }
}