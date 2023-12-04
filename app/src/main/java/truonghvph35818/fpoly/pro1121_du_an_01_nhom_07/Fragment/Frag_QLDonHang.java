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

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_QLDonHang extends Fragment {
    RecyclerView recyclerView;
    QL_DonHangAdapter qlDonHangAdapter;
    List<SanPhamDTO> list_sp;
    List<DonHang> list_dh;

    List<User> list_User;
    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_don_hang, container, false);
//        loatData();
        return view;
    }
//    public void loatData() {
//        list_User = new ArrayList<>();
//        list_sp = new ArrayList<>();
//        list_gioHang = new ArrayList<>();
//        list_dh = new ArrayList<>();
//        getAll();
//        adapterQuanlyhoadon = new Adapter_quanlyhoadon(list_sp, list_User, list_dh, getContext());
//        recyclerView.setAdapter(adapterQuanlyhoadon);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//
//    }
//
//
//    public void getSp() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("sanPham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isComplete()) {
//                    for (QueryDocumentSnapshot snapshot: task.getResult()){
//                        list_sp.add(snapshot.toObject(SanPham.class));
//                    }
//                    adapterQuanlyhoadon.notifyDataSetChanged();
//                }
//            }
//        });
//    }
//
//    private void getAll() {
//        db = FirebaseFirestore.getInstance();
//        getSP();
//        getKH();
//        getHoaDon();
//        getSp();
//
//    }
//
//    public void getHoaDon() {
//
//        db.collection("donHang").whereEqualTo("trangThai", 0).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Toast.makeText(getContext(), "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (value != null) {
//                    for (DocumentChange dc : value.getDocumentChanges()) {
//                        switch (dc.getType()) {
//                            case ADDED:
//                                dc.getDocument().toObject(DonHang.class);
//                                list_dh.add(dc.getDocument().toObject(DonHang.class));
//                                adapterQuanlyhoadon.notifyDataSetChanged();
//                                break;
//                            case MODIFIED:
//                                DonHang dtoq = dc.getDocument().toObject(DonHang.class);
//                                if (dc.getOldIndex() == dc.getNewIndex()) {
//                                    list_dh.set(dc.getOldIndex(), dtoq);
//                                } else {
//                                    list_dh.remove(dc.getOldIndex());
//                                    list_dh.add(dtoq);
//                                }
//                                adapterQuanlyhoadon.notifyDataSetChanged();
//                                break;
//                            case REMOVED:
//                                dc.getDocument().toObject(DonHang.class);
//                                list_dh.remove(dc.getOldIndex());
//                                adapterQuanlyhoadon.notifyDataSetChanged();
//                                break;
//                        }
//                    }
//                }
//            }
//        });
//    }

    public void getKH() {
        db.collection("user").whereEqualTo("chucVu", 3).addSnapshotListener(new EventListener<QuerySnapshot>() {
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


//    public void getSP() {
//        db.collection("sanPham").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Toast.makeText(getContext(), "Lỗi không có dữ liệu", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (value != null) {
//                    for (DocumentChange dc : value.getDocumentChanges()) {
//                        switch (dc.getType()) {
//                            case ADDED:
//                                dc.getDocument().toObject(SanPham.class);
//                                list_sp.add(dc.getDocument().toObject(SanPham.class));
//                                break;
//                            case MODIFIED:
//                                SanPham dtoq = dc.getDocument().toObject(SanPham.class);
//                                if (dc.getOldIndex() == dc.getNewIndex()) {
//                                    list_sp.set(dc.getOldIndex(), dtoq);
//                                } else {
//                                    list_sp.remove(dc.getOldIndex());
//                                    list_sp.add(dtoq);
//                                }
//                                break;
//                            case REMOVED:
//                                dc.getDocument().toObject(SanPham.class);
//                                list_sp.remove(dc.getOldIndex());
//                                break;
//                        }
//                    }
//                }
//            }
//        });
//    }
}
