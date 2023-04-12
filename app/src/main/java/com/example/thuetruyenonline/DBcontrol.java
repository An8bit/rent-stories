package com.example.thuetruyenonline;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.pagehome.Story;
import com.example.thuetruyenonline.pagehome.StoryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBcontrol {

    Context context;
    StoryAdapter storyAdapter;
    public DBcontrol(Context context) {
       this.context=context;
    }
    //sử dụng  listener để đợi việc lấy dữ liệu hoàn tất trước khi trả về kết quả
    public interface OnGetDataListener {
        void onSuccess(ArrayList<Story> stories);
        void onFailure(String errorMessage);
    }
    public interface onGetCartListener{
        void onSucess(ArrayList<ControlCart> controlCarts);
        void onFailure(String errorMessage);
    }

    //hàm sữ lý đọc dữ liệu trên database trả về một ArrayList<Story> Việc lấy dữ liệu từ Cloud
    // Firestore là một hoạt động bất đồng bộ nên không thể trả về kết quả trực tiếp từ hàm  nên tạo
    //ra listener để đợi lấy hết
    public void GetData(String name, FirebaseFirestore db, OnGetDataListener listener){
        ArrayList<Story> stories = new ArrayList<>();
        db.collection(name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        String id = queryDocumentSnapshot.getId();
                        String name = queryDocumentSnapshot.get("TenTruyen").toString();
                        String tacgia = queryDocumentSnapshot.get("TacGia").toString();
                        String gioithieu = queryDocumentSnapshot.get("GioiThieu").toString();
                        String theloai = queryDocumentSnapshot.get("TheLoai").toString();
                        String image = queryDocumentSnapshot.get("AnhLoad").toString();
                        Story story = new Story(id, image, tacgia, gioithieu, name, theloai);
                        stories.add(story);
                    }
                    listener.onSuccess(stories);
                } else {
                    listener.onFailure("");
                    Toast("lỗi");
                }
            }
        });
    }
    public void Search(String newText,FirebaseFirestore db,OnGetDataListener listener) {
        String searchText = newText.toLowerCase();
        ArrayList<Story> stories = new ArrayList<>();
        db.collection("Truyen").orderBy("TenTruyen_lowercase")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff").limit(5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            listener.onSuccess(stories);

                        } else {
                            listener.onFailure("");
                            Toast("loi");

                        }
                    }
                });
    }
        public  void InsertCart(FirebaseFirestore db,Story story,String ngaythue,String giatien){
            CollectionReference Cart= db.collection("GioHang");
            String id = story.getId();
            String name = story.getNamestory();
            String image=story.getImage();
            Map<String, Object> cart = new HashMap<>();
            cart.put("songaythue",ngaythue);
            cart.put("idtruyen",id);
            cart.put("img",image);
            cart.put("namestory",name);
            cart.put("buyer",getProviderData());
            cart.put("giatien",giatien);
            Cart.add(cart).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast("thêm vào giỏ thành công");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast("lỗi");
                }
            });
        }
    public void Sort(String newText,FirebaseFirestore db,OnGetDataListener listener) {
        String searchText = newText.toLowerCase();
        ArrayList<Story> stories = new ArrayList<>();
        db.collection("Truyen").orderBy("TheLoai")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff").limit(5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            listener.onSuccess(stories);

                        } else {
                            listener.onFailure("");
                            Toast("loi");

                        }
                    }
                });
    }
    public void getCart(String name, FirebaseFirestore db, onGetCartListener listener){
        ArrayList <ControlCart> controlCarts = new ArrayList<>();
        db.collection("GioHang").whereEqualTo("buyer",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        String id = queryDocumentSnapshot.getId();
                        String buyer= queryDocumentSnapshot.get("buyer").toString();
                        String idTruyen= queryDocumentSnapshot.get("idtruyen").toString();
                        String img= queryDocumentSnapshot.get("img").toString();
                        String nameStory= queryDocumentSnapshot.get("namestory").toString();
                        String songaythe= queryDocumentSnapshot.get("songaythue").toString();
                        String giatien=queryDocumentSnapshot.get("giatien").toString();
                        ControlCart controlCart = new ControlCart(id,buyer,idTruyen,img,nameStory,songaythe,giatien);
                        controlCarts.add(controlCart);
                    }
                    listener.onSucess(controlCarts);

                } else {
                    listener.onFailure("");
                    Toast("loi");

                }
            }
        });
    }

        public  void Deleteitemcart(FirebaseFirestore db,String iddoc){
            db.collection("GioHang").document(iddoc)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }



    public String getProviderData(){
        String providerId = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                providerId = profile.getUid();
            }
        } else {
            return null;
        }
        return providerId;
    }
    void Toast(String a){
        Toast toast= Toast.makeText(context,a,Toast.LENGTH_SHORT);
        toast.show();
    }
}
