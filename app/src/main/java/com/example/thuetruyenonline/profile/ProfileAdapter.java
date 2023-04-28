package com.example.thuetruyenonline.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.ShowStory.ReadActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.sachthue> {
   Litenner listener;
    ArrayList<ControlProfile> controlProfiles;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Context context;

    public ProfileAdapter( ArrayList<ControlProfile> controlProfiles,Litenner listener) {
        this.listener=listener;
        this.controlProfiles = controlProfiles;
    }


    @NonNull
    @Override
    public sachthue onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sachthue, parent,false);
        return new sachthue(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sachthue holder, int position) {
        ControlProfile controlProfile = controlProfiles.get(position);
       holder.tvTenTruyen.setText(controlProfile.getNamestory());
       holder.tvNgaySd.setText(controlProfile.getSongaythue());
        StorageReference imageRef = storage.getReferenceFromUrl(controlProfile.getImg());
        //sử dụng phương thức getBytes() của StorageReference để tải xuống dữ liệu hình ảnh dưới dạng một mảng byte.
        imageRef.getBytes(1024 * 1024)
                //Khi tải xuống thành công, nó sử dụng BitmapFactory để chuyển đổi mảng byte thành một đối tượng Bitmap. Sau đó, nó hiển thị hình ảnh trong một ImageView bằng cách gọi phương thức setImageBitmap() của ImageView.
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.imgAnhTruyen.setImageBitmap(bitmap);
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

        holder.btdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listener.onGetStory(controlProfile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return controlProfiles.size();
    }

    public interface Litenner {
        void onGetStory(ControlProfile controlProfile);
    }
    class  sachthue extends RecyclerView.ViewHolder{

            ImageView imgAnhTruyen,imgdelete;
        TextView tvTenTruyen,tvNgaySd;
        Button btdoc;
    public sachthue(@NonNull View itemView) {
        super(itemView);
            imgAnhTruyen=itemView.findViewById(R.id.imgAnhTruyen);
            imgdelete=itemView.findViewById(R.id.imgdelete);
            tvTenTruyen=itemView.findViewById(R.id.tvTenTruyen1111);
            tvNgaySd=itemView.findViewById(R.id.tvNgaySd);
            btdoc=itemView.findViewById(R.id.btdoc);
    }

}
}
