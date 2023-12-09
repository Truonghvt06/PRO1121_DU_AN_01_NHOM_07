package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.Adapter_item_tt;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;

public class show_sanpham extends AppCompatActivity {
    RecyclerView rcv_list;
  Adapter_item_tt itemCuaHang;
    List<SanPhamDTO> list;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sanpham);
        Intent intent = getIntent();
        String[] a = intent.getStringArrayExtra("list");

        if (a== null){
            return;
        }
        list = new ArrayList<>();
        rcv_list = findViewById(R.id.rcv_list_sanPham_more);
        TextView tenhang = findViewById(R.id.tv_ten_hang_show);
        tenhang.setText(a[1]);
        ImageView close = findViewById(R.id.iv_close);
        itemCuaHang = new Adapter_item_tt(show_sanpham.this,list);
        rcv_list.setAdapter(itemCuaHang);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(show_sanpham.this, 2);
        rcv_list.setLayoutManager(gridLayoutManager);
        laydulieu(a[0]);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void laydulieu(String a) {
        db = FirebaseFirestore.getInstance();
        db.collection("Sanpham").whereEqualTo("maHang", a).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value == null) {
                            return;
                        }
                        if (error != null) {
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    list.add(dc.getDocument().toObject(SanPhamDTO.class));
                                    itemCuaHang.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    SanPhamDTO user1 = dc.getDocument().toObject(SanPhamDTO.class);
                                    if (dc.getOldIndex() == dc.getNewIndex()) {
                                        list.set(dc.getOldIndex(), user1);
                                        itemCuaHang.notifyDataSetChanged();
                                    } else {
                                        list.remove(dc.getOldIndex());
                                        list.add(user1);
                                        itemCuaHang.notifyDataSetChanged();
                                    }
                                    break;
                                case REMOVED:
                                    dc.getDocument().toObject(SanPhamDTO.class);
                                    list.remove(dc.getOldIndex());
                                    itemCuaHang.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }
}