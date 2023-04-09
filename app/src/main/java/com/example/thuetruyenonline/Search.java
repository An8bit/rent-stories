package com.example.thuetruyenonline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements SearchResultsAdapter.Listener {
    RecyclerView mRecyclerView;
    private static final String TAG = "Accounts";
    FirebaseFirestore db;
    ArrayList<Story> stories= new ArrayList<>();
    SearchResultsAdapter searchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Khởi tạo RecyclerView
        mRecyclerView = findViewById(R.id.rvStory);
        db = FirebaseFirestore.getInstance();
        searchResultsAdapter = new SearchResultsAdapter(stories, Search.this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(searchResultsAdapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_Search).getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH); // thiết lập thuộc tính imeOptions
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                stories.clear();
               search(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
//                stories.clear();
//                search(newText);
                return false;
            }
        });
        return true;
    }
    private void search(String newText) {
       db.collection("Truyen").orderBy("TenTruyen")
               .startAt(newText)
               .endAt(newText + "\uf8ff").limit(5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       String id = document.getId();
                       String name=document.get("TenTruyen").toString();
                       String tacgia=document.get("TacGia").toString();
                       String gioithieu=document.get("GioiThieu").toString();
                       String theloai=document.get("TheLoai").toString();
                       String image=document.get("AnhLoad").toString();
                       Story story = new Story(id,image,tacgia,gioithieu,name,theloai) ;
                       stories.add(story);
                   }
                   searchResultsAdapter.notifyDataSetChanged();
               } else {
                   Log.d(TAG, "Error getting documents: ", task.getException());

               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast("loi");
           }
       });


} void Toast(String a){
        Toast toast= Toast.makeText(Search.this,a, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onItemClickListener(Story story) {
        Intent intent = new Intent(Search.this,DetailStory.class);
        intent.putExtra("A",story);
        startActivity(intent);
    }
}
