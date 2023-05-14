package com.example.thuetruyenonline.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.example.thuetruyenonline.ShowStory.ReadActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.sachthue> {
    private CountDownTimer countDownTimer;
   Litenner listener;
    ArrayList<ControlProfile> controlProfiles;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Context context;
    DBcontrol dBcontrol = new DBcontrol(context);
    FirebaseFirestore db;

    public ProfileAdapter( ArrayList<ControlProfile> controlProfiles,Litenner listener) {
        this.listener=listener;
        this.controlProfiles = controlProfiles;
    }


    @NonNull
    @Override
    public sachthue onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sachthue, parent,false);
        return new sachthue(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sachthue holder, int position) {
        db=FirebaseFirestore.getInstance();
        ControlProfile controlProfile = controlProfiles.get(position);
       holder.tvTenTruyen.setText(controlProfile.getNamestory());
       holder.tvngayhet.setText(controlProfile.getSongaythue()+" ngày");
        StorageReference imageRef = storage.getReferenceFromUrl(controlProfile.getImg());
        //sử dụng phương thức getBytes() của StorageReference để tải xuống dữ liệu hình ảnh dưới dạng một mảng byte.
        imageRef.getBytes(1024 * 1024)
                //Khi tải xuống thành công, nó sử dụng BitmapFactory để chuyển đổi mảng byte thành một đối tượng Bitmap. Sau đó, nó hiển thị hình ảnh trong một ImageView bằng cách gọi phương thức setImageBitmap() của ImageView.
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.imgAnhTruyen.setImageBitmap(bitmap);
                    }
                })
                //Nếu quá trình tải xuống hình ảnh không thành công, nó sẽ ghi log lỗi và có thể thực hiện các hành động khác như hiển thị thông báo lỗi hoặc thực hiện các hành động khác.
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Failed to load image", e);
                        // Show error message or perform other action
                    }
                });

        holder.btdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listener.onGetStory(controlProfile);
            }
        });
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.DeleteItem(controlProfile);

            }
        });
        countDownTimer = new CountDownTimer(diffInMillis(diffInDays(controlProfile.getNgayhethan())), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Hiển thị đếm ngược
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;
                String countdown = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
                holder.tvNgaySd.setText(countdown);
            }

            @Override
            public void onFinish() {
                // Xử lý khi đếm ngược kết thúc
                holder.tvNgaySd.setText("Hết hạn");
                dBcontrol.DeletePro(db,controlProfile.getId());

            }
        };

        // Bắt đầu đếm ngược
        countDownTimer.start();
    }


    @Override
    public int getItemCount() {
        return controlProfiles.size();
    }

    public interface Litenner {
        void onGetStory(ControlProfile controlProfile);
        void DeleteItem(ControlProfile controlProfile);
    }
    class  sachthue extends RecyclerView.ViewHolder{

            ImageView imgAnhTruyen,imgdelete;
        TextView tvTenTruyen,tvNgaySd,tvngayhet;
        Button btdoc;
    public sachthue(@NonNull View itemView) {
        super(itemView);
             tvngayhet=itemView.findViewById(R.id.tvngayhet);
            imgAnhTruyen=itemView.findViewById(R.id.imgAnhTruyen);
            imgdelete=itemView.findViewById(R.id.imgdelete);
            tvTenTruyen=itemView.findViewById(R.id.tvTenTruyen1111);
            tvNgaySd=itemView.findViewById(R.id.tvNgaySd);
            btdoc=itemView.findViewById(R.id.btdoc);
    }

}
private  Long diffInMillis(int day){
    // Lấy ngày hiện tại và ngày hết hạn
    Calendar currentDate = Calendar.getInstance();
    Calendar expirationDate = Calendar.getInstance();
    expirationDate.add(Calendar.DAY_OF_MONTH, day);
    return expirationDate.getTimeInMillis() - currentDate.getTimeInMillis();

}
    private int diffInDays(String targetDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date targetDate;
        try {
            targetDate = dateFormat.parse(targetDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Trả về giá trị mặc định nếu không thể phân tích chuỗi ngày
        }

        // Lấy ngày hiện tại
        Calendar currentDate = Calendar.getInstance();

        // Thiết lập giờ, phút, giây của ngày hiện tại là 0 để so sánh chỉ ngày
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        // Thiết lập giờ, phút, giây của ngày đích là 0 để so sánh chỉ ngày
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(targetDate);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        targetCalendar.set(Calendar.MILLISECOND, 0);

        // Tính toán số ngày còn lại
        long diffInMillis = targetCalendar.getTimeInMillis() - currentDate.getTimeInMillis();
        int diffInDays = (int) (diffInMillis / (24 * 60 * 60 * 1000));

        return diffInDays;
    }
}

