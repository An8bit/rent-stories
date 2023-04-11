package com.example.thuetruyenonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuetruyenonline.pagehome.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class testloggin extends AppCompatActivity {
    FirebaseFirestore db ;
    EditText email,pass;
    private FirebaseAuth mAuth;
    Button Login;
    TextView Register;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testloggin);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        email = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPass);
        Register = findViewById(R.id.tvRegister);
        Login = findViewById(R.id.btLogin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(testloggin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(testloggin.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Notification(email.getText().toString(), pass.getText().toString(), "Tài khoản chưa đăng ký");
                        }
                    }


                });
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(testloggin.this);
                builder.setTitle("Đăng ký tài khoản");
                builder.setMessage("Vui lòng nhập thông tin đăng ký");
                final EditText usernameInput = new EditText(testloggin.this);
                usernameInput.setHint("Email");
                final EditText passwordInput = new EditText(testloggin.this);
                passwordInput.setHint("Mật khẩu");
                passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //tạo ra cái layout để hiện thị các nút trên
                LinearLayout layout = new LinearLayout(testloggin.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(usernameInput);
                layout.addView(passwordInput);
                builder.setView(layout);
                builder.setPositiveButton("Đăng ký", new DialogInterface.OnClickListener() {
                    //thiếu hàm tìm kiếm tại khoản đó có tồn tại hay không
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý đăng ký tài khoản ở đây
                        //lấy biến nhập vào ở đây
                        String emailI = usernameInput.getText().toString();
                        String password = passwordInput.getText().toString();
                        mAuth.createUserWithEmailAndPassword(emailI, password).addOnCompleteListener(testloggin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    email.setText(emailI);
                                    pass.setText(password);
                                    Toast("Đăng ký thành công");
                                } else {
                                    Notification(emailI, password, "gmail đăng ký rồi");
                                }
                            }
                        });

                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.width = 900;
                    layoutParams.height = 800;
                    window.setAttributes(layoutParams);
                }
            }
        });


    }
    void Notification( String emailI, String password, String a) {
        if (emailI.contains("@gmail.com")) {
            Toast("gmail nhập vào bị sai vui lòng kiểm tra lại");
        } else if (password.length() < 6) {
            Toast("pass nhập vào bị thiết ký tự");
        } else {
            Toast(a);
        }
    }
    void Toast(String a){
        Toast toast= Toast.makeText(testloggin.this,a,Toast.LENGTH_SHORT);
        toast.show();
    }
}