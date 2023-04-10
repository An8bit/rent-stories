package com.example.thuetruyenonline.pagehome;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thuetruyenonline.Cart;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.DetailStory;
import com.example.thuetruyenonline.Profile;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.Sort;
import com.example.thuetruyenonline.Story;
import com.example.thuetruyenonline.search.Search;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StoryAdapter.Listener {

 ImageView ivHome,ivProfile,ivCart;
 Menu menuSearch;
   RecyclerView rvStory;
   ArrayList<Story> stories;
   StoryAdapter storyAdapter;
   FirebaseFirestore db;
   FloatingActionButton btsort;
    Intent intent;
    String email;
    DBcontrol dBcontrol = new DBcontrol(MainActivity.this);

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
         email = intent.getStringExtra("email");
        rvStory = findViewById(R.id.rvStory);
        db=FirebaseFirestore.getInstance();
        dBcontrol.GetData("Truyen", db, new DBcontrol.OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Story> storie) {
                stories=storie;
                storyAdapter = new StoryAdapter(stories,MainActivity.this);
                rvStory.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                rvStory.setAdapter(storyAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                stories=new ArrayList<>();

            }
        });
        Nagative();

    }
    //nhan sort

    ActivityResultLauncher<Intent> mLaunch  = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
       if(result.getResultCode()==RESULT_OK){
           Story story =(Story) result.getData().getSerializableExtra("HanhDong");
           stories.clear();
           stories.add(story);
       }
        }
    });




    void Nagative(){
        ivHome=findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ivProfile=findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });

        ivCart=findViewById(R.id.ivCart);
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, Cart.class);
                startActivity(intent);
                finish();
            }
        });
        menuSearch=findViewById(R.id.menuSearch);

        //sort
        btsort=findViewById(R.id.btsort);
        btsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Sort.class);
                intent.putExtra("flag",1);
                mLaunch.launch(intent);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onItemClickListener(Story story) {
        Intent intent = new Intent(MainActivity.this, DetailStory.class);
        intent.putExtra("A",story);
        intent.putExtra("email", email);
        startActivity(intent);

    }
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