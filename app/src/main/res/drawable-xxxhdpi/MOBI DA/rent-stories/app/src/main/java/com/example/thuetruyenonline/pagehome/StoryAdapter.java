package com.example.thuetruyenonline.pagehome;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.DataStory> {

    FirebaseStorage storage = FirebaseStorage.getInstance();


    ArrayList<Story> stories;

    public StoryAdapter(ArrayList<Story> stories, Listener listener) {
        this.stories = stories;
        this.listener = listener;
    }
    Listener listener;

    @NonNull
    @Override
    public DataStory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cell, parent,false);
        return new DataStory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataStory holder, int position) {
      Story story = stories.get(position);
      holder.tvNameStory.setText(story.getNamestory());
        StorageReference imageRef = storage.getReferenceFromUrl(story.getImage());
        //sử dụng phương thức getBytes() của StorageReference để tải xuống dữ liệu hình ảnh dưới dạng một mảng byte.
        imageRef.getBytes(1024 * 1024)
                //Khi tải xuống thành công, nó sử dụng BitmapFactory để chuyển đổi mảng byte thành một đối tượng Bitmap. Sau đó, nó hiển thị hình ảnh trong một ImageView bằng cách gọi phương thức setImageBitmap() của ImageView.
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.ivAnh.setImageBitmap(bitmap);
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            listener.onItemClickListener(story);
          }
      });

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public interface Listener {
        void onItemClickListener(Story story);

    }

    class DataStory extends RecyclerView.ViewHolder{

        TextView tvNameStory;
        ImageView ivAnh;
        public DataStory(@NonNull View itemView) {
            super(itemView);
            tvNameStory=itemView.findViewById(R.id.tvNameStory);
            ivAnh=itemView.findViewById(R.id.ivAnhd);

        }
    }

}
