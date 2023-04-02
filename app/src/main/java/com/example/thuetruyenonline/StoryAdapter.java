package com.example.thuetruyenonline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.DataStory> {

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
      holder.ivAnh.setImageResource(R.drawable.viem);
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
