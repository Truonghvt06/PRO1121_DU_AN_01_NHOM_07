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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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


