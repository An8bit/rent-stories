package com.example.thuetruyenonline.search;

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

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.DetailStory;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.Story;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements SearchResultsAdapter.Listener {
    RecyclerView mRecyclerView;
    FirebaseFirestore db;
    ArrayList<Story> stories = new ArrayList<>();
    DBcontrol dBcontrol = new DBcontrol(Search.this);
    SearchResultsAdapter searchResultsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Khởi tạo RecyclerView
        mRecyclerView = findViewById(R.id.rvStory);
        db = FirebaseFirestore.getInstance();
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
                return false;
            }
        });
        return true;
    }

private void search(String newText) {
   dBcontrol.Search(newText, db, new DBcontrol.OnGetDataListener() {
       @Override
       public void onSuccess(ArrayList<Story> storie) {
           stories=storie;
           searchResultsAdapter = new SearchResultsAdapter(stories, Search.this);
           mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
           mRecyclerView.setAdapter(searchResultsAdapter);
       }

       @Override
       public void onFailure(String errorMessage) {
           stories=new ArrayList<>();
       }
   });
}

    @Override
    public void onItemClickListener(Story story) {
        Intent intent = new Intent(Search.this, DetailStory.class);
        intent.putExtra("A",story);
        startActivity(intent);
    }
}
