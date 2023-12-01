package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.Adapter_trangchu;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.bannerADapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.HangDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.banner;

public class Frag_TrangChu extends Fragment {
    private RecyclerView rcy_list,rcy_banner;
    EditText editText;
    ImageView imageView;
    bannerADapter bannerADapter;
    List<HangDTO> listthuonghieu;
    List<SanPhamDTO> sanPhamDTOList;
    FirebaseFirestore db;
    Adapter_trangchu adapterTrangchu;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_trangchu, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listthuonghieu= new ArrayList<>();
        sanPhamDTOList =new ArrayList<>();
        adapterTrangchu=new Adapter_trangchu(listthuonghieu,getContext());

        rcy_banner = view.findViewById(R.id.rcy_banner);
        rcy_list=view.findViewById(R.id.Rcv_trangchu);
        imageView =view.findViewById(R.id.img_timKiem);
        editText =view.findViewById(R.id.ed_timKiem);
        bannerADapter =new bannerADapter(getanh(),getContext());
        rcy_banner.setAdapter(bannerADapter);
        rcy_list.setAdapter(adapterTrangchu);
        rcy_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rcy_banner.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
laydulieu();
    }

    private void laydulieu() {
        db = FirebaseFirestore.getInstance();
        db.collection("thuonghieu").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value == null) {
                    return;
                }
                sanPhamDTOList.clear();
                listthuonghieu.clear();
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
                    }
                    switch (dc.getType()) {
                        case MODIFIED:
//                            manHinhKhachHang.getSupportFragmentManager().beginTransaction().replace(R.id.fcv_KhachHang, new Frag_cuahang()).commit();
                            return;
                    }
                }

                listthuonghieu.add(new HangDTO(maHang, tenHang, sanPhamDTOList));
                listthuonghieu.sort(new Comparator<HangDTO>() {
                    @Override
                    public int compare(HangDTO o1, HangDTO o2) {
                        return (o2.getSanPham().size() - o1.getSanPham().size());
                    }
                });
                adapterTrangchu.notifyDataSetChanged();
                sanPhamDTOList = new ArrayList<>();
            }
        });
        for (HangDTO h : listthuonghieu) {
            if (h.getSanPham().size() <= 0) {
                listthuonghieu.remove(h);
            adapterTrangchu.notifyDataSetChanged();
            }
        }
    }




    private List<banner> getanh() {
        List<banner> list = new ArrayList<>();
        list.add(new banner(R.drawable.bannner1));
        list.add(new banner(R.drawable.banner2));
        list.add(new banner(R.drawable.banner3));
        return  list;
    }
}