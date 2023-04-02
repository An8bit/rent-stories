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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    FirebaseFirestore db ;
    EditText email,pass;
    Button Login;
    TextView Register;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=FirebaseFirestore.getInstance();
        email=findViewById(R.id.etEmail);
        pass=findViewById(R.id.etPass);
        Register=findViewById(R.id.tvRegister);
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
                           Toast("Tài khoản hoặc mật khẩu không đúng");
                        }else {
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }else {
                        Toast("Lỗi");
                    }
                });
            }
        });
        //tạo tk bằng cách sử dụng Dialog
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Đăng ký tài khoản");
                builder.setMessage("Vui lòng nhập thông tin đăng ký");
                final EditText usernameInput = new EditText(Login.this);
                usernameInput.setHint("Email");
                final EditText passwordInput = new EditText(Login.this);
                passwordInput.setHint("Mật khẩu");
                passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //tạo ra cái layout để hiện thị các nút trên
                LinearLayout layout = new LinearLayout(Login.this);
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
                        //
                        Map<String, Object> user = new HashMap<>();
                        user.put("taikhoan", emailI);
                        user.put("matkhau", password);
                        Query query = usersRef.whereEqualTo("taikhoan",emailI);
                        query.get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                QuerySnapshot snapshots = task.getResult();
                                //kiểm tra dữ liệu có rỗng hay không
                                if(!snapshots.isEmpty()){
                                    Toast.makeText(Login.this,"Tài khoản tồn tại",Toast.LENGTH_LONG).show();
                                }else {
                                    usersRef.add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {

                                                    //TAG ở đây là một biến log để đưa đi thôi và tạo ra id của document trong bảng TaiKhoan
                                                    Log.d(TAG, "thêm id " + documentReference.getId());
                                                    email.setText(emailI);
                                                    pass.setText(password);
                                                    Toast("Đăng ký thành công");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Lỗi", e);
                                                }
                                            });
                                }
                            }else {
                                Toast("Lỗi");
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
    void Toast(String a){
        Toast toast= Toast.makeText(Login.this,a,Toast.LENGTH_SHORT);
        toast.show();
    }
}