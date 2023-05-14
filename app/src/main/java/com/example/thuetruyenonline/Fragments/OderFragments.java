package com.example.thuetruyenonline.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.ShowStory.DataStory;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OderFragments extends Fragment {
    TextView tvNoidung,tvName;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DBcontrol dBcontrol = new DBcontrol(requireContext());
    ArrayList<DataStory> dataStories;
    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.read_acti,container,false);
        return view;
    }
}
