package com.example.thuetruyenonline;

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

import com.bumptech.glide.Glide;
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
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.ivAnh.setImageBitmap(bitmap);
                    }
                })
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

    interface Listener {
        void onItemClickListener(Story story);
    }

    class DataStory extends RecyclerView.ViewHolder{

        TextView tvNameStory;
        ImageView ivAnh;
        public DataStory(@NonNull View itemView) {
            super(itemView);
            tvNameStory=itemView.findViewById(R.id.tvNameStory);
            ivAnh=itemView.findViewById(R.id.ivAnh);
        }
    }

}
