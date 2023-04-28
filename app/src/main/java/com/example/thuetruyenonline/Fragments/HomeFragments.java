package com.example.thuetruyenonline.Fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.Cart.ShoppingAdapter;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.DetailStory;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.pagehome.MainActivity;
import com.example.thuetruyenonline.pagehome.Story;
import com.example.thuetruyenonline.pagehome.StoryAdapter;
import com.example.thuetruyenonline.search.Search;
import com.example.thuetruyenonline.search.Sort;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeFragments extends Fragment implements StoryAdapter.Listener{

    RecyclerView rvStory;


    FirebaseFirestore db;
    FloatingActionButton btsort;
    DBcontrol dBcontrol;
    Context context,context2;
    ArrayList<Story> stories=new ArrayList<>();
    StoryAdapter storyAdapter;
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.activity_main,container,false);
        setHasOptionsMenu(true);
        context=requireContext();
        dBcontrol = new DBcontrol(context);
        rvStory = view.findViewById(R.id.rvStory);
        viewPager=view.findViewById(R.id.view_pager);
        db = FirebaseFirestore.getInstance();
        dBcontrol.GetData("Truyen", db, new DBcontrol.OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Story> storie) {
                stories.clear();
                stories.addAll(storie);
                storyAdapter = new StoryAdapter(stories,HomeFragments.this);
                rvStory.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                rvStory.setAdapter(storyAdapter);
                storyAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String errorMessage) {


            }
        });
        btsort = view.findViewById(R.id.btsort);
        btsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Sort.class);
                mLaunch.launch(intent);
            }
        });



       return view;
    }
    ActivityResultLauncher<Intent> mLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                // Intent intent = new Intent();
                String data = result.getData().getStringExtra("theloai");
                dBcontrol.Sort(data, db, new DBcontrol.OnGetDataListener() {
                    @Override
                    public void onSuccess(ArrayList<Story> storie1) {
                        stories.clear();
                        stories.addAll(storie1);
                        storyAdapter = new StoryAdapter(stories,HomeFragments.this);
                        rvStory.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        rvStory.setAdapter(storyAdapter);
                        storyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });


            }
        }
    });

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSearch) {
            Intent intent = new Intent(context, Search.class);
            startActivity(intent);
        }
                return super.onOptionsItemSelected(item);
        }


    @Override
    public void onItemClickListener(Story story) {
        Intent intent = new Intent(context,DetailStory.class);
        intent.putExtra("A",story);
        startActivity(intent);
    }
}
