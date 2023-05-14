package com.example.thuetruyenonline.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuetruyenonline.Cart.ControlCart;
import com.example.thuetruyenonline.Cart.ShoppingAdapter;
import com.example.thuetruyenonline.DBcontrol;
import com.example.thuetruyenonline.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartFragments extends Fragment implements ShoppingAdapter.Listener {
    boolean hasData1;
    int count=0;
    ArrayList<ControlCart> controlCarts = new ArrayList<>();
    RecyclerView rvCart;
    TextView tvTongTien;
    Button btThuetruyen;
    FirebaseFirestore db;
    ShoppingAdapter shoppingAdapter;
    Spinner spTT;
    ImageView iviconTT;
    BottomNavigationView bottomNavigationView;
    DBcontrol dBcontrol;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.activity_cart,container,false);
      dBcontrol=new DBcontrol(requireContext());
        rvCart=view.findViewById(R.id.rvCart);
        btThuetruyen=view.findViewById(R.id.btnThuetruyen);
        db=FirebaseFirestore.getInstance();
        tvTongTien=view.findViewById(R.id.tvTongTien);
        dBcontrol.getCart(dBcontrol.getProviderData(), db, new DBcontrol.onGetCartListener() {
            @Override
            public void onSucess(ArrayList<ControlCart> controlCarts1) {
                controlCarts.addAll(controlCarts1);
                shoppingAdapter=new ShoppingAdapter(controlCarts,CartFragments.this);
                rvCart.setAdapter(shoppingAdapter);
                rvCart.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                rvCart.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
            }

            @Override
            public void onFailure(String errorMessage) {
            }
        });
        //chưa chỉnh phương thức thanh toán vào database
        iviconTT=view.findViewById(R.id.iviconTT);
        spTT=view.findViewById(R.id.spTT);
        spTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spTT.getSelectedItem().toString()){
                    case "Momo":
                        iviconTT.setImageResource(R.drawable.momo);
                        break;
                    case "ZaloPay":
                        iviconTT.setImageResource(R.drawable.zalo);
                        break;
                    case "Banking":
                        iviconTT.setImageResource(R.drawable.banking);
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btThuetruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(controlCarts.size()<=0){
                    Toast("hàng rỗng");
                }
                else if(hasData1==true) {
                    dBcontrol.InsertProfile(db,controlCarts,tvTongTien.getText().toString());
                    controlCarts.clear();
                    shoppingAdapter.notifyDataSetChanged();
                    dBcontrol.DeleteCart(db, dBcontrol.getProviderData());
                    Toast("thuê thành công vào mục tài khoản để đọc");
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Bạn đã chưa chọn ngày hãy chọn ngày lại");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
      return view;
    }

    @Override
    public void onDeleteCart(ControlCart controlCart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bạn có muốn xóa hàng ra khỏi giỏ");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dBcontrol.Deleteitemcart(db,controlCart.getId());
                controlCarts.remove(controlCart);
                shoppingAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    public void onEditCart(ControlCart controlCart) {
       // tvTongTien.setText(String.valueOf(getTotalPrice(controlCarts)));
        shoppingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChecked(boolean hasdata) {
        hasData1=hasdata;
    }

    @Override
    public void checkbox(ControlCart controlCart) {
        tvTongTien.setText(String.valueOf(getTotalPrice(controlCarts)));
        shoppingAdapter.notifyDataSetChanged();
    }

    //hàm tính tiền
    public double getTotalPrice(ArrayList<ControlCart> controlCarts) {
        double total = 0;
        for (int i = 0;i<controlCarts.size();i++) {
            total += controlCarts.get(i).getTotalPrice();
        }
        return total;
    }
    void  Toast(String a){
        Toast toast= Toast.makeText(requireContext(),a,Toast.LENGTH_SHORT);
        toast.show();
    }


}
