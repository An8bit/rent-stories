package com.example.thuetruyenonline.Cart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.Pay> {

    ArrayList<ControlCart> controlCarts;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    int GiaTien;
    Button tbThue;

    public ShoppingAdapter(ArrayList<ControlCart> controlCarts, Listener listener) {
        this.controlCarts = controlCarts;
        this.listener = listener;
    }
    Listener listener;
    @NonNull
    @Override
    public Pay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay, parent,false);
        return new Pay(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Pay holder, int position) {
        ControlCart controlCart = controlCarts.get(position);
        holder.tvTenTruyen.setText(controlCart.getNameStory());
        StorageReference imageRef = storage.getReferenceFromUrl(controlCart.getImg());
        //sử dụng phương thức getBytes() của StorageReference để tải xuống dữ liệu hình ảnh dưới dạng một mảng byte.
        imageRef.getBytes(1024 * 1024)
                //Khi tải xuống thành công, nó sử dụng BitmapFactory để chuyển đổi mảng byte thành một đối tượng Bitmap. Sau đó, nó hiển thị hình ảnh trong một ImageView bằng cách gọi phương thức setImageBitmap() của ImageView.
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.imgEdit.setImageBitmap(bitmap);
                    }
                })
                //Nếu quá trình tải xuống hình ảnh không thành công, nó sẽ ghi log lỗi và có thể thực hiện các hành động khác như hiển thị thông báo lỗi hoặc thực hiện các hành động khác.
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Failed to load image", e);
                        // Show error message or perform other action
                    }
                });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 listener.onDeleteCart(controlCart);
            }
        });


        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (holder.spinner.getSelectedItem().toString()){
                    case "Chỉnh sửa ngày":
                            holder.tvngaythue.setText(controlCart.getSongaythue());
                            holder.tvGiaTien.setText(controlCart.getSongaythue());
                        break;
                    case "3 ngày":
                        holder.tvngaythue.setText("3 ngày");
                        GiaTien=3000;
                        controlCart.setGiatien(String.valueOf(GiaTien));
                        holder.tvGiaTien.setText(String.valueOf(GiaTien));
                       Update(controlCart,"3 ngày");
                        controlCart.setSongaythue("3 ngày");
                        listener.onEditCart(controlCart);
                        break;
                    case "1 tuần":
                        holder.tvngaythue.setText("1 tuần");
                        GiaTien=7000;
                        controlCart.setGiatien(String.valueOf(GiaTien));
                        holder.tvGiaTien.setText(String.valueOf(GiaTien));
                       Update(controlCart,"1 tuần");
                        controlCart.setSongaythue("1 tuần");
                        listener.onEditCart(controlCart);
                        break;
                    case "1 tháng":
                        holder.tvngaythue.setText("1 tháng");
                        GiaTien=30000;
                        controlCart.setGiatien(String.valueOf(GiaTien));
                        holder.tvGiaTien.setText(String.valueOf(GiaTien));
                        controlCart.setSongaythue("1 tháng");
                        Update(controlCart,"1 tháng");
                        listener.onEditCart(controlCart);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (holder.tvngaythue.getText()=="null"&&holder.tvGiaTien.getText()=="null") {
            listener.onDataChecked(false);
        }else {
            listener.onDataChecked(true);
        }
        }

    @Override
    public  int getItemCount() {
        return controlCarts.size();
    }

    public interface Listener {
        void onDeleteCart(ControlCart controlCart);
        void onEditCart(ControlCart controlCart);
        void onDataChecked(boolean hasData);


    }

    class Pay extends RecyclerView.ViewHolder{

        TextView tvTenTruyen,tvngaythue,tvGiaTien;
        ImageView imgEdit, imgDelete;
        Spinner spinner;
        CheckBox checkBox;

        public Pay(@NonNull View itemView) {
            super(itemView);
            tvTenTruyen=itemView.findViewById(R.id.tvTenTruyen);
            imgEdit=itemView.findViewById(R.id.imgEdit);
            imgDelete=itemView.findViewById(R.id.imgDelete);
           tvngaythue=itemView.findViewById(R.id.tvngaythue);
           //checkBox=itemView.findViewById(R.id.cbCheckItem);
           spinner=itemView.findViewById(R.id.spngaythue);
           tvGiaTien=itemView.findViewById(R.id.tvGiaTien);

        }
    }
    void Update(ControlCart controlCart,String so_ng){
        DocumentReference docRef = db.collection("GioHang").document(controlCart.getId());
        docRef.update("giatien",String.valueOf(GiaTien));
        docRef.update("songaythue",String.valueOf(so_ng));
    }

    }

