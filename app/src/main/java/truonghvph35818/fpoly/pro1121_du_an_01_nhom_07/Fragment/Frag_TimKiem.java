package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.Adapter_timkiem;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.Adapter_trangchu;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.HangDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_TimKiem extends Fragment {
    EditText ed_timKiem;

    private RecyclerView recyclerView;
    SearchView searchView;
    List<SanPhamDTO> sanPhamDTOList;
    FirebaseFirestore db;
    Adapter_timkiem adapter_timkiem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_timkiem, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sanPhamDTOList =new ArrayList<>();
        adapter_timkiem=new Adapter_timkiem(sanPhamDTOList,getContext());


        ed_timKiem = view.findViewById(R.id.ed_timKiem);
        recyclerView =view.findViewById(R.id.rcy_timkiem);
        recyclerView.setAdapter(adapter_timkiem);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        laydulieudb();
        ed_timKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter_timkiem.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void laydulieudb() {
        db=FirebaseFirestore.getInstance();
        db.collection("Sanpham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                db = FirebaseFirestore.getInstance();
                for (DocumentSnapshot dc : value.getDocuments()) {
                    AddListSP(dc.getString("maHang"), dc.getString("tenHang"));
                }

            }
        });
    }

    private void AddListSP(String maHang, String tenHang) {
        db.collection("Sanpham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value == null) {
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (maHang.equals(dc.getDocument().get("maHang").toString())) {
                        //lấy được sản phẩm
                        sanPhamDTOList.add(dc.getDocument().toObject(SanPhamDTO.class));
                        adapter_timkiem.notifyDataSetChanged();
                    }
                    switch (dc.getType()) {
                        case MODIFIED:
//                            manHinhKhachHang.getSupportFragmentManager().beginTransaction().replace(R.id.fcv_KhachHang, new Frag_cuahang()).commit();
                            return;
                    }
                }
            }
        });
    }
}