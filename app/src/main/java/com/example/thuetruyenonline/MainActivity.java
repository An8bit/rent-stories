package com.example.thuetruyenonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StoryAdapter.Listener {

 ImageView ivHome,ivProfile,ivCart;
 Menu menuSearch;
   RecyclerView rvStory;
   ArrayList<Story> stories;
   StoryAdapter storyAdapter;
   FirebaseFirestore db;
    Intent intent;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvStory = findViewById(R.id.rvStory);
        getSupportActionBar().setTitle("Trang chủ");
        db=FirebaseFirestore.getInstance();
        stories = new ArrayList<>();
        storyAdapter = new StoryAdapter(stories,MainActivity.this);
        rvStory.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rvStory.setAdapter(storyAdapter);
        db.collection("Truyen").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                    String id = queryDocumentSnapshot.getId();
                    String name=queryDocumentSnapshot.get("TenTruyen").toString();
                    String tacgia=queryDocumentSnapshot.get("TacGia").toString();
                    String gioithieu=queryDocumentSnapshot.get("GioiThieu").toString();
                    String theloai=queryDocumentSnapshot.get("TheLoai").toString();
                   String image=queryDocumentSnapshot.get("AnhLoad").toString();

                    Story story = new Story(id,image,tacgia,gioithieu,name,theloai) ;
                    stories.add(story);
                }
                storyAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             Toast("loi");
            }
        });

       ivHome=findViewById(R.id.ivHome);
       ivHome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                intent = new Intent(MainActivity.this,MainActivity.class);
               startActivity(intent);
           }
       });

       ivProfile=findViewById(R.id.ivProfile);
       ivProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                intent = new Intent(MainActivity.this,Profile.class);
               startActivity(intent);
           }
       });

       ivCart=findViewById(R.id.ivCart);
       ivCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                intent = new Intent(MainActivity.this,Cart.class);
               startActivity(intent);
           }
       });
       menuSearch=findViewById(R.id.menuSearch);

    }
    void Toast(String a){
        Toast toast= Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    public void onItemClickListener(Story story) {
        Intent intent = new Intent(MainActivity.this,DetailStory.class);
        intent.putExtra("A",story);
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