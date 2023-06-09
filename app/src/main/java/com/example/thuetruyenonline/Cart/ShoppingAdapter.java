package com.example.thuetruyenonline.Cart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.Pay> {
    Context context;
    ArrayList<ControlCart> controlCarts;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DBcontrol dBcontrol = new DBcontrol(context);
    int GiaTien, GiaTien1;
    public ShoppingAdapter(ArrayList<ControlCart> controlCarts, Listener listener) {
        this.controlCarts = controlCarts;
        this.listener = listener;
    }

    Listener listener;

    @NonNull
    @Override
    public Pay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay, parent, false);
        return new Pay(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Pay holder, int position) {
        ControlCart controlCart = controlCarts.get(position);
        if (GiaTien1 == 0) {
            GiaTien1 = Integer.parseInt(controlCart.getGiatien());
        }
        Log.e("gai", String.valueOf(GiaTien1));
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
                switch (holder.spinner.getSelectedItem().toString()) {
                    case "Chỉnh sửa ngày":
                        holder.tvngaythue.setText(controlCart.getSongaythue() + " ngày");
                        holder.tvGiaTien.setText(controlCart.getGiatien());
                        listener.onChecked(false);
                        break;
                    case "3 ngày":
                        holder.tvngaythue.setText("3 ngày");
                        GiaTien = GiaTien1;
                        holder.tvGiaTien.setText(String.valueOf(GiaTien));
                        controlCart.setGiatien(String.valueOf(GiaTien));
                        Update(controlCart, "3");
                        controlCart.setSongaythue("3");
                        listener.onEditCart(controlCart);
                        listener.onChecked(true);
                        break;
                    case "1 tuần":
                        holder.tvngaythue.setText("1 tuần");
                        GiaTien = GiaTien1 * 2;
                        holder.tvGiaTien.setText(String.valueOf(GiaTien));
                        controlCart.setGiatien(String.valueOf(GiaTien));
                        Update(controlCart, "7");
                        controlCart.setSongaythue("7");
                        listener.onEditCart(controlCart);
                        listener.onChecked(true);
                        break;
                    case "1 tháng":
                        holder.tvngaythue.setText("1 tháng");
                        GiaTien = GiaTien1 * 4;
                        holder.tvGiaTien.setText(String.valueOf(GiaTien));
                        controlCart.setGiatien(String.valueOf(GiaTien));
                        controlCart.setSongaythue("30");
                        Update(controlCart, "30");
                        listener.onEditCart(controlCart);
                        listener.onChecked(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return controlCarts.size();
    }

    public interface Listener {
        void onDeleteCart(ControlCart controlCart);

        void onEditCart(ControlCart controlCart);

        void onChecked(boolean hasdata);

        void checkbox(ControlCart controlCart);


    }

    class Pay extends RecyclerView.ViewHolder {

        TextView tvTenTruyen, tvngaythue, tvGiaTien;
        ImageView imgEdit, imgDelete;
        Spinner spinner;
        CheckBox checkBox;

        public Pay(@NonNull View itemView) {
            super(itemView);
            tvTenTruyen = itemView.findViewById(R.id.tvTenTruyen);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            tvngaythue = itemView.findViewById(R.id.tvngaythue);
            spinner = itemView.findViewById(R.id.spngaythue);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTien);

        }
    }

    void Update(ControlCart controlCart, String so_ng) {
        DocumentReference docRef = db.collection("GioHang").document(controlCart.getId());
        docRef.update("giatien", String.valueOf(GiaTien));
        docRef.update("songaythue", String.valueOf(so_ng));
    }
}

