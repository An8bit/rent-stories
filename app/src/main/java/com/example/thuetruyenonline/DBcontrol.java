package com.example.thuetruyenonline;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.ShowStory.DataStory;
import com.example.thuetruyenonline.pagehome.Story;
import com.example.thuetruyenonline.profile.ControlProfile;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DBcontrol {


    Context context;

    public DBcontrol(Context context) {
        this.context = context;
    }

    //sử dụng  listener để đợi việc lấy dữ liệu hoàn tất trước khi trả về kết quả
    public interface OnGetDataListener {
        void onSuccess(ArrayList<Story> stories);

        void onFailure(String errorMessage);
    }

    public interface onGetCartListener {
        void onSucess(ArrayList<ControlCart> controlCarts);

        void onFailure(String errorMessage);
    }

    public interface onGetProfileListener {
        void onSuccess(ArrayList<ControlProfile> controlProfiles);

        void onFailure(String errorMessage);
    }

    public interface onGetChapter {
        void onSuccess(ArrayList<DataStory> dataStories);

        void onFailure(String errorMessage);
    }

    public interface OnGetChapterListener {
        void onSuccess(ArrayList<DataStory> dataStories);

        void onFailure(String errorMessage);
    }

    public interface Ongetgia {
        void onSuccess(String s);

        void onFailure(String errorMessage);
    }

    //hàm sữ lý đọc dữ liệu trên database trả về một ArrayList<Story> Việc lấy dữ liệu từ Cloud
    // Firestore là một hoạt động bất đồng bộ nên không thể trả về kết quả trực tiếp từ hàm  nên tạo
    //ra listener để đợi lấy hết
    public void LoadProfle(FirebaseFirestore db) {
        db.collection("DonHang").whereEqualTo("chudonhang", getProviderData()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                    String chu = queryDocumentSnapshot.get("chudonhang").toString();
                    String idtruyen = queryDocumentSnapshot.get("idtruyen").toString();
                    String name = queryDocumentSnapshot.get("namestory").toString();
                    String song = queryDocumentSnapshot.get("songaythue").toString();
                    String img = queryDocumentSnapshot.get("image").toString();
                    String noidung = queryDocumentSnapshot.get("noidung").toString();
                    String ngayhethan = queryDocumentSnapshot.get("ngayhethan").toString();
                    Map<String, Object> done1 = new HashMap<>();
                    done1.put("chudonhang", chu);
                    done1.put("iddoc", queryDocumentSnapshot.getId());
                    done1.put("idtruyen", idtruyen);
                    done1.put("songaythue", song);
                    done1.put("namestory", name);
                    done1.put("ngayhethan", ngayhethan);
                    done1.put("img", img);
                    done1.put("noidung", noidung);
                    db.collection("TaiKhoan").document().set(done1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e("LoadProfile", "thành công");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("LoadProfile", "thất bại");
                        }
                    });
                }
            }
        });
    }

    public void GetData(String name, FirebaseFirestore db, OnGetDataListener listener) {
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
                        String noidung = queryDocumentSnapshot.get("noidung").toString();
                        String gia = queryDocumentSnapshot.get("gia").toString();
                        Story story = new Story(id, image, tacgia, gioithieu, name, theloai, noidung, gia);
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

    public void Search(String newText, FirebaseFirestore db, OnGetDataListener listener) {
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
                                String name = document.get("TenTruyen").toString();
                                String tacgia = document.get("TacGia").toString();
                                String gioithieu = document.get("GioiThieu").toString();
                                String theloai = document.get("TheLoai").toString();
                                String image = document.get("AnhLoad").toString();
                                String noidung = document.get("noidung").toString();
                                String gia = document.get("gia").toString();
                                Story story = new Story(id, image, tacgia, gioithieu, name, theloai, noidung, gia);
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

    public void InsertCart(FirebaseFirestore db, Story story, String ngaythue, String giatien) {
        String id = story.getId();
        String name = story.getNamestory();
        String image = story.getImage();
        String noidung = story.getNoidung();
        Map<String, Object> cart = new HashMap<>();
        cart.put("songaythue", ngaythue);
        cart.put("idtruyen", id);
        cart.put("img", image);
        cart.put("namestory", name);
        cart.put("buyer", getProviderData());
        cart.put("giatien", giatien);
        cart.put("noidung", noidung);
        cart.put("userID", getProviderData());
        db.collection("GioHang").add(cart).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast("thêm vào giỏ thành công");
            }
        });

    }

    public void Sort(String newText, FirebaseFirestore db, OnGetDataListener listener) {
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
                                String name = document.get("TenTruyen").toString();
                                String tacgia = document.get("TacGia").toString();
                                String gioithieu = document.get("GioiThieu").toString();
                                String theloai = document.get("TheLoai").toString();
                                String image = document.get("AnhLoad").toString();
                                String noidung = document.get("noidung").toString();
                                String gia = document.get("gia").toString();
                                Story story = new Story(id, image, tacgia, gioithieu, name, theloai, noidung, gia);
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

    public void getCart(String name, FirebaseFirestore db, onGetCartListener listener) {
        ArrayList<ControlCart> controlCarts = new ArrayList<>();
        db.collection("GioHang").whereEqualTo("buyer", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        String id = queryDocumentSnapshot.getId();
                        String buyer = queryDocumentSnapshot.get("buyer").toString();
                        String idTruyen = queryDocumentSnapshot.get("idtruyen").toString();
                        String img = queryDocumentSnapshot.get("img").toString();
                        String nameStory = queryDocumentSnapshot.get("namestory").toString();
                        String songaythe = queryDocumentSnapshot.get("songaythue").toString();
                        String giatien = queryDocumentSnapshot.get("giatien").toString();
                        String noidung = queryDocumentSnapshot.get("noidung").toString();
                        ControlCart controlCart = new ControlCart(id, buyer, idTruyen, img, nameStory, songaythe, giatien, noidung);
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

    public void Deleteitemcart(FirebaseFirestore db, String iddoc) {
        db.collection("GioHang").document(iddoc).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast("xóa thành công");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast("xóa thất bại vui lòng thử lại");
            }
        });
    }

    public void DeletePro(FirebaseFirestore db, String iddoc) {
        db.collection("DonHang").document(iddoc).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("lỗi khi xóa Pro", iddoc);
                Toast("xóa thất bại");
            }
        });
        db.collection("TaiKhoan").whereEqualTo("iddoc", iddoc).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                    queryDocumentSnapshot.getReference().delete();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Lỗi xóa", getProviderData());
            }
        });
    }

    //xóa khi thanh toan
    public void DeleteCart(FirebaseFirestore db, String buyer) {
        CollectionReference collectionRef = db.collection("GioHang");

        Query query = collectionRef.whereEqualTo("buyer", buyer);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    document.getReference().delete();
                }
            }
        });

    }

    public void GetProfile(FirebaseFirestore db, onGetProfileListener listener) {
        ArrayList<ControlProfile> controlProfiles = new ArrayList<>();
        db.collection("TaiKhoan").whereEqualTo("chudonhang", getProviderData()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        String chudonhang = queryDocumentSnapshot.get("chudonhang").toString();
                        String id = queryDocumentSnapshot.get("idtruyen").toString();
                        String name = queryDocumentSnapshot.get("namestory").toString();
                        String img = queryDocumentSnapshot.get("img").toString();
                        String song = queryDocumentSnapshot.get("songaythue").toString();
                        String noidung = queryDocumentSnapshot.get("noidung").toString();
                        String ngayhethan = queryDocumentSnapshot.get("ngayhethan").toString();
                        String iddoc = queryDocumentSnapshot.get("iddoc").toString();
                        ControlProfile controlProfile = new ControlProfile(chudonhang, iddoc, name, id, img, song, noidung, ngayhethan);
                        controlProfiles.add(controlProfile);

                    }
                    listener.onSuccess(controlProfiles);

                }
            }
        });
    }

    public void InsertProfile(FirebaseFirestore db, ArrayList<ControlCart> controlCarts, String thanhtien) {


        for (ControlCart v : controlCarts) {

            String namestrory = v.getNameStory();
            String image = v.getImg();
            String songaythue = v.getSongaythue();
            String idtruyen = v.getIdTruyen();
            String noidung = v.getNoidung();
            Map<String, Object> Profiles = new HashMap<>();
            Profiles.put("chudonhang", getProviderData());
            Profiles.put("tongtien", thanhtien);
            Profiles.put("idtruyen", idtruyen);
            Profiles.put("songaythue", songaythue);
            Profiles.put("image", image);
            Profiles.put("namestory", namestrory);
            Profiles.put("noidung", noidung);
            Profiles.put("ngayhethan", NgayHetHan(Integer.parseInt(v.getSongaythue())));
            db.collection("DonHang").document().set(Profiles).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast("thanh toán thành công");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast("giỏ hàng trống hoặc lỗi");
                }
            });

        }
        LoadProfle(db);
    }

    public void getChapter(FirebaseFirestore db,String idtruyen, OnGetChapterListener listener) {
        ArrayList<DataStory> arrayList = new ArrayList<>();
        db.collection("DataStory").whereEqualTo("idtruyen",idtruyen).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot a : task.getResult()){
                    String idtruyen = a.getString("idtruyen");
                    String tenchuong = a.getString("tenchuong");
                    String noidung = a.getString("noidung");
                    DataStory dataStory = new DataStory(tenchuong,idtruyen,noidung);
                    Log.e("ck", tenchuong);
                    arrayList.add(dataStory);
                }
                listener.onSuccess(arrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               listener.onFailure("lỗi");
            }
        });
    }

    public String getProviderData() {
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

    void Toast(String a) {
        Toast toast = Toast.makeText(context, a, Toast.LENGTH_SHORT);
        toast.show();
    }

    String AutoID(FirebaseFirestore db, String colection) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(colection);
        DocumentReference newDocRef = collectionRef.document();

        return newDocRef.getId();
    }

    //định dạng ngày
    private String NgayHetHan(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, i); // Thêm i ngày vào ngày hiện tại
        Date ngayHetHan = calendar.getTime(); // Lấy ngày hết hạn
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        return dateFormat.format(ngayHetHan);
    }

}
