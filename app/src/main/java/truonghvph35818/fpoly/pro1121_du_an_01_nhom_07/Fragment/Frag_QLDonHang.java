package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.QL_DonHangAdapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.DonHang;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.giohang;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_QLDonHang extends Fragment {
    RecyclerView recyclerView;
    QL_DonHangAdapter qlDonHangAdapter;
    List<giohang> list_Giohang;
    List<SanPhamDTO> list_SanPham;
    List<DonHang> list_DonHang;

    List<User> list_User;
    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_don_hang, container, false);
        recyclerView = view.findViewById(R.id.re_ql_don_hang);
        loatData();
        return view;
    }
    public void loatData() {
        list_User = new ArrayList<>();
        list_SanPham = new ArrayList<>();
        list_Giohang = new ArrayList<>();
        list_DonHang = new ArrayList<>();
        getAll();
        qlDonHangAdapter = new QL_DonHangAdapter(getContext(),  list_DonHang, list_SanPham, list_User);
        recyclerView.setAdapter(qlDonHangAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

    }


    public void getSp() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Sanpham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot snapshot: task.getResult()){
                        list_SanPham.add(snapshot.toObject(SanPhamDTO.class));
                    }
                    qlDonHangAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getAll() {
        db = FirebaseFirestore.getInstance();
        getSp();
        getKH();
        getHoaDon();
        getSP();

    }

    public void getHoaDon() {

        db.collection("DonHang").whereEqualTo("trangThai", 0).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(DonHang.class);
                                list_DonHang.add(dc.getDocument().toObject(DonHang.class));
                                qlDonHangAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                DonHang donHang = dc.getDocument().toObject(DonHang.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_DonHang.set(dc.getOldIndex(), donHang);
                                } else {
                                    list_DonHang.remove(dc.getOldIndex());
                                    list_DonHang.add(donHang);
                                }
                                qlDonHangAdapter.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(DonHang.class);
                                list_DonHang.remove(dc.getOldIndex());
                                qlDonHangAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }

    public void getKH() {
        db.collection("User").whereEqualTo("chucVu", 3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        Log.e("TAG", "onEvent: " + dc.getType());
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(User.class);
                                list_User.add(dc.getDocument().toObject(User.class));
                                break;
                            case MODIFIED:
                                User user1 = dc.getDocument().toObject(User.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_User.set(dc.getOldIndex(), user1);
                                } else {
                                    list_User.remove(dc.getOldIndex());
                                    list_User.add(user1);
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(User.class);
                                list_User.remove(dc.getOldIndex());
                        }
                    }
                }
            }
        });
    }


    public void getSP() {
        db.collection("Sanpham").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(SanPhamDTO.class);
                                list_SanPham.add(dc.getDocument().toObject(SanPhamDTO.class));
                                break;
                            case MODIFIED:
                                SanPhamDTO sanPhamDTO = dc.getDocument().toObject(SanPhamDTO.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list_SanPham.set(dc.getOldIndex(), sanPhamDTO);
                                } else {
                                    list_SanPham.remove(dc.getOldIndex());
                                    list_SanPham.add(sanPhamDTO);
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(SanPhamDTO.class);
                                list_SanPham.remove(dc.getOldIndex());
                                break;
                        }
                    }
                }
            }
        });
    }
}
