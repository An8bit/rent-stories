package com.example.thuetruyenonline.ShowStory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.pagehome.StoryAdapter;

import java.util.ArrayList;

public class ReadAdaper extends RecyclerView.Adapter<ReadAdaper.Read> {
    ArrayList<DataStory> dataStories;
    Listener listener;

    public ReadAdaper(ArrayList<DataStory> dataStories, Listener listener) {
        this.dataStories = dataStories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Read onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter, parent,false);
        return new Read(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Read holder, int position) {
      DataStory dataStory = dataStories.get(position);
      holder.tvTen.setText(dataStory.getChapter());
      holder.TvNoidung.setText(dataStory.getNoidung());

    }

    @Override
    public int getItemCount() {
        return dataStories.size();
    }

    public interface Listener {
    }

    class Read extends RecyclerView.ViewHolder {
        TextView tvTen,TvNoidung;

        public Read(@NonNull View itemView) {

            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTen);
            TvNoidung=itemView.findViewById(R.id.tvnoidung);

        }
    }


}
