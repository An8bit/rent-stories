package com.example.thuetruyenonline.Cart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.Pay> {

    ArrayList<ControlCart> controlCarts;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public ShoppingAdapter(ArrayList<ControlCart> controlCarts, Listener listener) {
        this.controlCarts = controlCarts;
        this.listener = listener;
    }

    Listener listener;

    @NonNull
    @Override
    public Pay onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pay, parent,false);
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
        holder.spNgayThue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return controlCarts.size();
    }

    public interface Listener {
    }

    class Pay extends RecyclerView.ViewHolder{

        TextView tvTenTruyen;
        ImageView imgEdit, imgDelete;
        Spinner spNgayThue,spThanhToan;
        public Pay(@NonNull View itemView) {
            super(itemView);
            tvTenTruyen=itemView.findViewById(R.id.tvTenTruyen);
            imgEdit=itemView.findViewById(R.id.imgEdit);
            imgDelete=itemView.findViewById(R.id.imgDelete);
            spNgayThue=itemView.findViewById(R.id.spNgayThue);
            spThanhToan=itemView.findViewById(R.id.spThanhToan);
        }
    }
}
