package com.example.thuetruyenonline.pagehome;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.DetailStory;
import com.example.thuetruyenonline.Fragments.ViewPagerAdapter;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.search.Sort;
import com.example.thuetruyenonline.search.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StoryAdapter.Listener {


    Menu menuSearch;
    BottomNavigationView bottomNavigationView;
    RecyclerView rvStory;
    ArrayList<Story> stories;
    StoryAdapter storyAdapter;
    FirebaseFirestore db;
    FloatingActionButton btsort;
    ViewPager vpage;
    DBcontrol dBcontrol = new DBcontrol(MainActivity.this);

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvStory = findViewById(R.id.rvStory);
        vpage = findViewById(R.id.view_pager);
        db = FirebaseFirestore.getInstance();
        dBcontrol.GetData("Truyen", db, new DBcontrol.OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Story> storie) {
                stories = storie;
                storyAdapter = new StoryAdapter(stories, MainActivity.this);
                rvStory.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                rvStory.setAdapter(storyAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                stories = new ArrayList<>();

            }
        });
        btsort = findViewById(R.id.btsort);
        btsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sort.class);
                mLaunch.launch(intent);

            }
        });
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.profile:
//                        Intent profile = new Intent(MainActivity2.this, Profile.class);
//                        startActivity(profile);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                        finish();
                        return true;
                    case R.id.cart:
//                        Intent cart = new Intent(MainActivity2.this, Cart.class);
//                        startActivity(cart);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                        finish();
                        return true;
                }
                return false;
            }
        });
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
                        stories = storie1;
                        storyAdapter = new StoryAdapter(stories, MainActivity.this);
                        rvStory.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        rvStory.setAdapter(storyAdapter);
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });


            }
        }
    });

    @Override
    public void onItemClickListener(Story story) {
        Intent intent = new Intent(MainActivity.this, DetailStory.class);
        intent.putExtra("A", story);
        startActivity(intent);

    }

    //nút search
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuSearch) {
            Intent intent = new Intent(MainActivity.this, Search.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}