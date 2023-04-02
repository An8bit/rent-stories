package com.example.thuetruyenonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StoryAdapter.Listener {
   RecyclerView rvStory;
   ArrayList<Story> stories;
   StoryAdapter storyAdapter;
   FirebaseFirestore db;




    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvStory = findViewById(R.id.rvStory);

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
                   String image=queryDocumentSnapshot.get("AnhLoad").toString();
                    Story story = new Story(id,image,name) ;
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



    }
    void Toast(String a){
        Toast toast= Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    public void onItemClickListener(Story story) {
        Intent intent = new Intent(MainActivity.this,DetailStory.class);
        startActivity(intent);
    }
}