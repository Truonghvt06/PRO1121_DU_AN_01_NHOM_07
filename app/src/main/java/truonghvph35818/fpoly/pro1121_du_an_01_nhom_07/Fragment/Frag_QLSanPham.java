package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.Adapter_thuonghieu;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.SanPhamAdapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.AdminActivity;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.HangDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_QLSanPham extends Fragment {
    Uri uri;
    private int change = 0;
    ImageView anh;
    SanPhamDTO sanPhamDTO;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    List<SanPhamDTO> list;
    FirebaseFirestore db;
    SanPhamAdapter sanPhamAdapter;
    List<HangDTO> hangDTOlist;
    String masp = "";
    String linkanh = "";
    private Adapter_thuonghieu adapterThuonghieu;


    public void setUri(Uri uri) {
        this.uri = uri;
        anh.setImageURI(uri);
        upanh(uri);
    }

    private void upanh(Uri uri) {
        StorageReference storage;
        storage = FirebaseStorage.getInstance().getReference("imge").child(masp);
        storage.putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isComplete()) {
                            storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isComplete()) {
                                        linkanh = task.getResult().toString();
                                    }
                                }
                            });
                        }
                    }
                });

    }

    public Frag_QLSanPham() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_san_pham, container, false);

        recyclerView = view.findViewById(R.id.re_sanPham);
        floatingActionButton = view.findViewById(R.id.float_sanPham);


        return view;
    }

    private void getdata() {
        db.collection("Sanpham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;

                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:

                                list.add(dc.getDocument().toObject(SanPhamDTO.class));
                                sanPhamAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                SanPhamDTO dtoq = dc.getDocument().toObject(SanPhamDTO.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list.set(dc.getOldIndex(), dtoq);
                                    sanPhamAdapter.notifyDataSetChanged();
                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(dtoq);
                                    sanPhamAdapter.notifyDataSetChanged();
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(SanPhamDTO.class);
                                list.remove(dc.getOldIndex());
                                sanPhamAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        sanPhamAdapter = new SanPhamAdapter(getContext(), list, Frag_QLSanPham.this);
        recyclerView.setAdapter(sanPhamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getdata();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                them();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_themsp, null, false);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        masp = UUID.randomUUID().toString();
        anh = view.findViewById(R.id.img_themAnhSP);
        sanPhamDTO = new SanPhamDTO();
        EditText tensp = view.findViewById(R.id.ed_tenSP);
        EditText thuonghieu = view.findViewById(R.id.ed_thuonghieu);
        EditText kichco = view.findViewById(R.id.ed_kichCo);
        EditText gia = view.findViewById(R.id.ed_gia);
        RadioButton giay = view.findViewById(R.id.giay);
        RadioButton dep = view.findViewById(R.id.dep);
        EditText mota = view.findViewById(R.id.ed_moTa);
        Button huy = view.findViewById(R.id.btn_huy_themsp);
        Button luu = view.findViewById(R.id.btn_luu_themsp);

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luudulieu(anh, tensp, thuonghieu, kichco, gia, mota, dialog);
            }
        });
        anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themanh();
            }
        });
        thuonghieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.dailog_thuonghieu, null, false);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                EditText themthuonghieu =view1.findViewById(R.id.edt_themthuonghieu_);
                themthuonghieu.setVisibility(View.GONE);
                ListView dsthuonghieu =view1.findViewById(R.id.list_thuonghieu);
                ImageButton btnthemthuonghieu= view1.findViewById(R.id.ibtn_addthuonghieu);
                hangDTOlist =new ArrayList<>();
                adapterThuonghieu =new Adapter_thuonghieu(hangDTOlist,getContext());
                dsthuonghieu.setAdapter(adapterThuonghieu);
                dlthuonghieu(adapterThuonghieu);
dsthuonghieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        thuonghieu.setText(hangDTOlist.get(i).getTenHang());
        sanPhamDTO.setMaHang(hangDTOlist.get(i).getMaHang());
        dialog.dismiss();
    }
});
                btnthemthuonghieu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = UUID.randomUUID().toString();
                        if (change == 0) {
                           themthuonghieu.setVisibility(View.VISIBLE);
                            change = 1;
                        } else {
                            themthuonghieu.setVisibility(View.GONE);
                            change = 0;
                            db.collection("thuonghieu").document(id).set(new HangDTO(id, themthuonghieu.getText().toString(), new Date().getTime())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        Toast.makeText(getContext(), "Thêm thương hiệu thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        thuonghieu.setText(themthuonghieu.getText().toString());
                                        sanPhamDTO.setMaHang(id);
                                    } else {
                                        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });
            }
        });

    }

    private void dlthuonghieu(Adapter_thuonghieu adapterThuonghieu) {
        db.collection("thuonghieu").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                dc.getDocument().toObject(HangDTO.class);
                                hangDTOlist.add(dc.getDocument().toObject(HangDTO.class));
                                adapterThuonghieu.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                HangDTO dtoq = dc.getDocument().toObject(HangDTO.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    hangDTOlist.set(dc.getOldIndex(), dtoq);
                                   sanPhamAdapter.notifyDataSetChanged();
                                } else {
                                    hangDTOlist.remove(dc.getOldIndex());
                                    hangDTOlist.add(dtoq);
                                    adapterThuonghieu.notifyDataSetChanged();
                                }
                                break;
                            case REMOVED:
                                hangDTOlist.remove(dc.getOldIndex());
                                adapterThuonghieu.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });


    }

    private void themanh() {
        AdminActivity adminActivity = (AdminActivity) getActivity();
        adminActivity.yeucauquyen(getContext());


    }


    private void luudulieu(ImageView anh, EditText tensp, EditText thuonghieu, EditText kichco, EditText gia,  EditText mota, Dialog dialog) {
        if (tensp.getText().toString().isEmpty() ||
                thuonghieu.toString().isEmpty() ||
                kichco.toString().isEmpty() ||
                gia.getText().toString().isEmpty()
                || mota.getText().toString().isEmpty()
                || linkanh.isEmpty()) {
            Toast.makeText(getContext(), "Bạn phải nhập hết", Toast.LENGTH_SHORT).show();
            return;
        }
        ;


        sanPhamDTO.setMaSp(masp);
        sanPhamDTO.setTenSP(tensp.getText().toString().trim());
        sanPhamDTO.setKichCo(Long.parseLong(kichco.getText().toString().trim()));
        sanPhamDTO.setGia(Long.parseLong(gia.getText().toString().trim()));
        sanPhamDTO.setThuonghieu(thuonghieu.getText().toString().trim());
        sanPhamDTO.setMoTa(mota.getText().toString().trim());
        sanPhamDTO.setAnh(linkanh);
        db.collection("Sanpham").document(masp).set(sanPhamDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(getContext(), "Thêm thành công sản phẩm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void xoasanphamql(String masp) {
        db.collection("Sanpham").document(masp).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(getContext(), "Bạn xóa thành công", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Bạn xóa không thành công", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getList() {
        db.collection("Sanpham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isComplete()) {
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (QueryDocumentSnapshot dc : task.getResult()) {
                    list.add(dc.toObject(SanPhamDTO.class));
                    sanPhamAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
