package com.example.thuetruyenonline.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.Cart.ShoppingAdapter;
import com.example.thuetruyenonline.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.sachthue > {
    ArrayList<ControlProfile> controlProfiles;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @NonNull
    @Override
    public ProfileAdapter.sachthue onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pay, parent,false);
        return new ProfileAdapter.sachthue(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.sachthue holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class sachthue extends RecyclerView.ViewHolder {
        ImageView imgAnhTruyen,imgdelete;
        TextView tvTenTruyen, tvNgaySd;
        Button btdoc;

        public sachthue(@NonNull View itemView) {
            super(itemView);
            imgAnhTruyen=itemView.findViewById(R.id.imgAnhTruyen);
            imgdelete=itemView.findViewById(R.id.imgdelete);
            tvTenTruyen=itemView.findViewById(R.id.tvTenTruyen);
            tvNgaySd=itemView.findViewById(R.id.tvNgaySd);
            btdoc=itemView.findViewById(R.id.btdoc);
        }
    }
}
