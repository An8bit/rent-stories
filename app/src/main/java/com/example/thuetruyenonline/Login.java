package com.example.thuetruyenonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;

public class Login extends AppCompatActivity {


    FirebaseFirestore db ;
    EditText email,pass;
    Button Login;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=FirebaseFirestore.getInstance();
        email=findViewById(R.id.etEmail);
        pass=findViewById(R.id.etPass);
        Login=findViewById(R.id.btLogin);
        //đọc document TaiKhoan gán vào biến usersRef
        CollectionReference usersRef = db.collection("TaiKhoan");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tạo một truy vấn đến đến từng dòng ở đây sử dụng query lấy làm whereEqualTo tìm kiếm lọc dữ liệu
                Query query = usersRef.whereEqualTo("matkhau",pass.getText().toString()).whereEqualTo("taikhoan",email.getText().toString());
                query.get().addOnCompleteListener(task -> {
                    //kiểm tra trong task có truy vấn thành công không
                    if (task.isSuccessful()){
                        QuerySnapshot snapshots = task.getResult();
                        //kiểm tra dữ liệu có rỗng hay không
                        if(snapshots.isEmpty()){
                            Toast.makeText(Login.this,"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_LONG).show();
                        }else {
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }else {
                        Toast.makeText(Login.this,"error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}