package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.AdapterTop10;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.DonHang;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.HangDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_Top10 extends Fragment {
    RecyclerView recyclerView;
    List<SanPhamDTO> list_SanPham;
    AdapterTop10 adapterTop10;
    List<HashMap<String, Object>> list_top10;

    public Frag_Top10() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_top10, container, false);
        recyclerView = view.findViewById(R.id.re_top10);
        list_SanPham = new ArrayList<>();
        list_top10 = new ArrayList<>();
        getSP();
        getTop10();
        adapterTop10 = new AdapterTop10(getContext(), list_SanPham, list_top10);
        recyclerView.setAdapter(adapterTop10);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
    public void getSP() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Sanpham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        list_SanPham.add(snapshot.toObject(SanPhamDTO.class));
                    }
                    adapterTop10.notifyDataSetChanged();
                }
            }
        });
    }

    public void getTop10() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Top10").orderBy("soLuong", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        HashMap<String, Object> top10 = new HashMap<>();
                        top10.put("soLuong", snapshot.getLong("soLuong"));
                        top10.put("maSP", snapshot.get("maSP"));
                        list_top10.add(top10);
                        Log.e("TaG",""+top10.get("soLuong"));

                    }
                    adapterTop10.notifyDataSetChanged();
                }
            }
        });
    }
}
