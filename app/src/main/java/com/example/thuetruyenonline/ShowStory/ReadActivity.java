package com.example.thuetruyenonline.ShowStory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.Cart.RentStory;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    TextView tvNoidung;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    DBcontrol dBcontrol = new DBcontrol(ReadActivity.this);
   ArrayList<DataStory> dataStories = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        tvNoidung=findViewById(R.id.tvnoidung);
        setupBadge();
      dBcontrol.getChapter(db, new DBcontrol.OnGetChapterListener() {
          @Override
          public void onSuccess(ArrayList<String> arrayList) {
              for (String s :arrayList ){
                  db.collection("DataStory").whereEqualTo("idtruyen",s).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
                          for (QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                              String noidung = queryDocumentSnapshot.get("noidung").toString();
                              String id = queryDocumentSnapshot.get("idtruyen").toString();
                              DataStory  dataStory = new DataStory(id,noidung);
                              dataStories.add(dataStory);

                          }
                          for (DataStory x:dataStories
                          ) {
                              System.out.println(x.getNoidung());
                              tvNoidung.setText(x.getNoidung());
                          }
                      }
                  });

              }
          }

          @Override
          public void onFailure(String errorMessage) {

          }
      });


    }

    private void setupBadge() {

    }



}