package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.Don;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.DonHang;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class QL_DonHangAdapter extends RecyclerView.Adapter<QL_DonHangAdapter.ViewHolder> {
    Context context;
    List<DonHang> list_donHang;
    List<SanPhamDTO> list_sanPhamDTO;
    List<User> list_Users;
    FirebaseFirestore firestore;

    DonHang donHanga = null;
    User user = null;

    public QL_DonHangAdapter(Context context, List<DonHang> list_donHang, List<SanPhamDTO> list_sanPhamDTO, List<User> list_Users) {
        this.context = context;
        this.list_donHang = list_donHang;
        this.list_sanPhamDTO = list_sanPhamDTO;
        this.list_Users = list_Users;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public QL_DonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ql_don_hang, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QL_DonHangAdapter.ViewHolder holder, int position) {
        if (list_Users.size() <= 0 && list_donHang.size() <= 0 && list_sanPhamDTO.size() <= 0) {
            return;
        }
        String[] data = getdata(list_Users, list_donHang.get(position));
        if (data.length <= 0) {
            return;
        }
        DonHang donHang = list_donHang.get(position);
        if (donHang == null) {
            return;
        }
        donHanga = donHang;
        SanPhamDTO sp = getmaSP(list_sanPhamDTO.get(position).getMaSp());
        Glide.with(context).load(sp.getAnh()).error(R.drawable.baseline_crop_original_24).into(holder.anh);

//        Long gia = Long.parseLong(data[3]);
//        tienHoan = gia;
        String maKH = list_donHang.get(position).getMaKhachHang();
        holder.tv_tenKH.setText("Họ tên:" + data[0]);
        holder.tv_diaChi.setText("Địa chỉ: " + data[1]);
        holder.tv_sdt.setText("Sđt: " + data[2]);
        holder.tv_tensp.setText("Tên SP: " + data[3]);
        holder.tv_gia.setText("Giá :" + data[4]);
        holder.tv_soluong.setText("Số lượng sản phẩm mua: " + data[5]);
        holder.img_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangThai(3, donHang);
            }
        });
        holder.img_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangThai(1, donHang);
            }
        });
    }

    public SanPhamDTO getmaSP(String masp) {
        SanPhamDTO sanPham = new SanPhamDTO();
        if (masp.equals(sanPham.getMaSp())) {
            return sanPham;
        }
        return sanPham;
    }

    private void trangThai(int i, DonHang donHang) {
        if (donHang == null) {
            return;
        }
        donHang.setTrangThai(i);
        firestore.collection("DonHang").document(donHang.getMaDonHang()).set(donHang).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(context, "Đang chạy bước 2", Toast.LENGTH_SHORT).show();
                    if (i == 1) {
                        updataDonHang(i, donHang);
                    }

                } else {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updataDonHang(int i, DonHang donHang) {
        if (donHang == null) {
            return;
        }
        donHang.setTrangThai(i);
        firestore.collection("DonHangDaDuyet").document(donHang.getMaDonHang()).set(donHang).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(context, "Đang chạy bước 3", Toast.LENGTH_SHORT).show();
                    setTop(donHang);

                } else {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setTop(DonHang donHang) {
        for (Don d : donHang.getListSP()) {
            getTop(d.getMaSP(), d.getSoLuong());

        }
    }

    private void capnhatSoluong(String maSP, Long soLuong) {
        final SanPhamDTO[] sp = {new SanPhamDTO()};
        firestore.collection("Sanpham").document(maSP).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isComplete()) {
                    return;
                }
                sp[0] = task.getResult().toObject(SanPhamDTO.class);
                if (sp[0].getSoLuong() < soLuong) {
                    Toast.makeText(context, "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
                    huyDon(donHanga, user);
                    return;
                }
                sp[0] = task.getResult().toObject(SanPhamDTO.class);
                sp[0].setSoLuong(sp[0].getSoLuong() - soLuong);
                firestore.collection("Sanpham").document(maSP).set(sp[0]).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isComplete()) {
                            return;
                        }
                        Toast.makeText(context, "Hoàn thành", Toast.LENGTH_SHORT).show();
//                        guiThongBao();
//                        Log.e(TAG, "onComplete: " + "trừ hàng thành công");
                    }
                });

            }
        });

    }

//    private void guiThongBao() {
//        String id = UUID.randomUUID().toString();
//        db.collection("thongBao").document(id)
//                .set(new ThongBao(id,user.getMaUser(),"Đơn hàng của bạn đang được giao",3,new Date().getTime())).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (!task.isComplete()){
//                            Log.e(TAG, "onComplete: "+"Lỗi 241" );
//                            return;
//                        }
//                        Log.e(TAG, "onComplete: "+"thong bao thanh cong" );
//                        progressDialog.cancel();
//                    }
//                });
//    }

    private void huyDon(DonHang donHang, User user) {
        donHang.setTrangThai(3);
        firestore.collection("DonHang").document(donHang.getMaDonHang()).set(donHang).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isComplete()) {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

//    private void hoanTien(User user, Long tienHoan) {
//
//        firestore.collection("User").document(user.getMaUser()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (!task.isComplete()) {
//                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                progressDialog.cancel();
//                Toast.makeText(context, "Hoàn tiền thành công", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void getTop(String maSP, Long sl) {
        final Long[] i = {0l};
        firestore.collection("Top10").document(maSP).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isComplete()) {
                    return;
                }
                i[0] = task.getResult().getLong("soLuong");
                if (i[0] == null) {
                    i[0] = 0l;
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("soLuong", i[0] + sl);
                map.put("maSP", maSP);
                firestore.collection("Top10").document(maSP).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(context, "Đang chạy bước 4", Toast.LENGTH_SHORT).show();
                            capnhatSoluong(maSP, sl);
//                            Log.e(TAG, "onComplete: " + "đẩy dữ liệu thành công");
                        }
                    }
                });
            }
        });
    }

    private String[] getdata(List<User> list_Users, DonHang donHang) {
        if (list_Users.size() <= 0 && list_donHang.size() <= 0 && list_sanPhamDTO.size() <= 0) {
            return new String[]{};
        }
        String[] a = new String[]{"", "", "", "", ""};
        for (User u : list_Users) {
            if (donHang.getMaKhachHang()
                    .equals(u.getMaUser())) {
                a[0] = u.getHoTen();
                a[1] = u.getChonDiaCHi();
                a[2] = u.getSDT();
            }
        }
        a[3] = donHang.getGiaDon() + "";
        Long soluong = 0l;
        for (Don d : donHang.getListSP()) {
            soluong += d.getSoLuong();
        }
        a[4] = String.valueOf((soluong));
        return a;
    }


    @Override
    public int getItemCount() {
        return list_donHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenKH, tv_gia, tv_diaChi, tv_sdt, tv_soluong, tv_tensp;
        ImageView img_xoa, img_xacnhan;
        ImageView anh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenKH = itemView.findViewById(R.id.tv_tenKhach);
            tv_diaChi = itemView.findViewById(R.id.tv_diaChi);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_soluong = itemView.findViewById(R.id.tv_soLuong_);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            anh = itemView.findViewById(R.id.img_anhsp);

            img_xoa = itemView.findViewById(R.id.img_Huy);
            img_xacnhan = itemView.findViewById(R.id.img_XacNhan);
        }

    }
}
