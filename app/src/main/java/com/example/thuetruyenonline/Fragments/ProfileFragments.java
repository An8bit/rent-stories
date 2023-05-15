package com.example.thuetruyenonline.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.ShowStory.ReadActivity;
import com.example.thuetruyenonline.pagehome.Story;
import com.example.thuetruyenonline.profile.ControlProfile;
import com.example.thuetruyenonline.profile.Profile;
import com.example.thuetruyenonline.profile.ProfileAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class ProfileFragments extends Fragment implements ProfileAdapter.Litenner {
    RecyclerView mRecyclerView;
    Story stories;
    ProfileAdapter profileAdapter;
    ArrayList<ControlProfile> controlProfiles;
    FirebaseFirestore db;
    DBcontrol dBcontrol;
    TextView tvName,tvEmail;


    @Override
    public void onResume() {
        super.onResume();
        loadDataFromFirestore();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.activity_profile,container,false);
        dBcontrol=new DBcontrol(requireContext());
        db=FirebaseFirestore.getInstance();
        mRecyclerView=view.findViewById(R.id.rvProfile);
        tvEmail=view.findViewById(R.id.tvEmail);
        tvEmail.setText(dBcontrol.getProviderData());
        dBcontrol.GetProfile(db, new DBcontrol.onGetProfileListener() {
            @Override
            public void onSuccess(ArrayList<ControlProfile> controlProfiles1) {
                controlProfiles=controlProfiles1;
                tvName=view.findViewById(R.id.tvName);
                tvName.setText(dBcontrol.getProviderData().substring(0,dBcontrol.getProviderData().indexOf("@")));
                profileAdapter = new ProfileAdapter(controlProfiles,ProfileFragments.this);
                mRecyclerView.setAdapter(profileAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
            }
            @Override
            public void onFailure(String errorMessage) {
            }
        });
      return view;
    }

    @Override
    public void onGetStory(ControlProfile controlProfile) {
        Intent intent=new Intent(requireContext(), ReadActivity.class);
        intent.putExtra("tentr",controlProfile.getNamestory());
        intent.putExtra("nd",controlProfile.getNoidung());
        intent.putExtra("idtruyen",controlProfile.getIdtruyen());
        startActivity(intent);
    }

    @Override
    public void DeleteItem(ControlProfile controlProfile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bạn có muốn xóa hàng ra khỏi giỏ");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dBcontrol.DeletePro(db,controlProfile.getId());
                Log.e("tt",controlProfile.getId());
                controlProfiles.remove(controlProfile);
                profileAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void loadDataFromFirestore() {
        dBcontrol.GetProfile(db, new DBcontrol.onGetProfileListener() {
            @Override
            public void onSuccess(ArrayList<ControlProfile> controlProfiles1) {
                controlProfiles=controlProfiles1;
                profileAdapter = new ProfileAdapter(controlProfiles,ProfileFragments.this);
                mRecyclerView.setAdapter(profileAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
            }
            @Override
            public void onFailure(String errorMessage) {
                // Handle failure case
            }
        });
    }

}
