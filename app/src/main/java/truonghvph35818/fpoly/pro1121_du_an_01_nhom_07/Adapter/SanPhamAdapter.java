package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_QLSanPham;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    Context context;
    List<SanPhamDTO> list;
    Frag_QLSanPham fragQlSanPham;

    public SanPhamAdapter(Context context, List<SanPhamDTO> list, Frag_QLSanPham fragQlSanPham) {
        this.context = context;
        this.list = list;
        this.fragQlSanPham=fragQlSanPham;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_san_pham, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (list.get(position).getAnh()==null){
            return;
        }
        Glide.with(context).load(list.get(position).getAnh()).into(holder.img_anhSP);
        holder.tv_ten.setText(list.get(position).getTenSP());
        holder.tv_gia.setText(Integer.parseInt(list.get(position).getGia() + "") + "VND");
        holder.tv_kich_co.setText(list.get(position).getKichCo() + "");
        holder.tv_thuonghieu.setText(list.get(position).getThuonghieu());


        holder.img_deleteSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle("");
                builder.setMessage("Bạn có chắc muốn xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                   fragQlSanPham.xoasanphamql(list.get(position).getMaSp());
                   dialogInterface.dismiss();
                    }
                });
                     builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();
                         }
                     });

                     builder.create().show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                suaSanPham(position);


                return true;
            }
        });
    }

    private void suaSanPham(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themsp, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        ImageView img_anh = view.findViewById(R.id.img_themAnhSP);
        EditText tensp = view.findViewById(R.id.ed_tenSP);
        EditText thuonghieu = view.findViewById(R.id.ed_thuonghieu);
        EditText kichco = view.findViewById(R.id.ed_kichCo);
        EditText gia = view.findViewById(R.id.ed_gia);

        EditText mota = view.findViewById(R.id.ed_moTa);
        Button huy = view.findViewById(R.id.btn_huy_themsp);
        Button luu = view.findViewById(R.id.btn_luu_themsp);

        SanPhamDTO sanPhamDTO = list.get(position);

        Glide.with(context).load(sanPhamDTO.getAnh()).into(img_anh);
        tensp.setText(sanPhamDTO.getTenSP());
        thuonghieu.setText(sanPhamDTO.getThuonghieu());
        kichco.setText(sanPhamDTO.getKichCo() + "");
        gia.setText(sanPhamDTO.getGia() + "");
        mota.setText(sanPhamDTO.getMoTa());

        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sanPhamDTO.setTenSP(tensp.getText().toString());
//                sanPhamDTO.setThuonghieu(thuonghieu.getText().toString());
//                sanPhamDTO.setKichCo(Long.parseLong(kichco.getText().toString()));
//                sanPhamDTO.setGia(Long.parseLong(gia.getText().toString()));
//                sanPhamDTO.setMoTa(mota.getText().toString());
//
//
//                notifyDataSetChanged();
//                dialog.dismiss();
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("Sanpham").document(list.get(position).getMaSp())
                        .update("tenSP", tensp.getText().toString(),
                                "thuonghieu", thuonghieu.getText().toString(),
                                "kichCo", Long.parseLong(kichco.getText().toString()),
                                "gia", Long.parseLong(gia.getText().toString()),
                                "moTa", mota.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    list.get(position).setTenSP(tensp.getText().toString());
                                    list.get(position).setThuonghieu(thuonghieu.getText().toString());
                                    list.get(position).setKichCo(Long.parseLong(kichco.getText().toString()));
                                    list.get(position).setGia(Long.parseLong(gia.getText().toString()));
                                    list.get(position).setMoTa(mota.getText().toString());

                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_anhSP, img_deleteSP;
        TextView tv_ten, tv_gia, tv_thuonghieu, tv_theloai, tv_kich_co;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anhSP = itemView.findViewById(R.id.img_anh_sp);
            img_deleteSP = itemView.findViewById(R.id.delete_sp);
            tv_ten = itemView.findViewById(R.id.tv_ten_sp);
            tv_gia = itemView.findViewById(R.id.tv_gia_sp);
//            tv_theloai = itemView.findViewById(R.id.tv_theLoai_sp);
            tv_thuonghieu = itemView.findViewById(R.id.tv_thuonghieu_sp);
            tv_kich_co = itemView.findViewById(R.id.tv_kich_co);
        }
    }


}


