package com.example.thuetruyenonline.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.util.Listener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.sachthue> {
   Litenner listener;
    ArrayList<ControlProfile> controlProfiles;

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
       holder.tvTenTruyen.setText("");
       holder.tvNgaySd.setText("3");
    }

    @Override
    public int getItemCount() {
        return controlProfiles.size();
    }

    public interface Litenner {
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
            btdoc=itemView.findViewById(R.id.button);
    }
}
}
