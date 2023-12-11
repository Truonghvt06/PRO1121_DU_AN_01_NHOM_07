package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.ChiTietSPActivity;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Adapter_item_tt extends RecyclerView.Adapter<Adapter_item_tt.ViewHolder>  {
    Context context;
    List<SanPhamDTO> list;

    public Adapter_item_tt(Context context, List<SanPhamDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(((Activity) context).getLayoutInflater()
                .inflate(R.layout.item_sanpham_trangchu, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getAnh()).
                error(R.drawable.baseline_crop_original_24).into(holder.anhSp);
        holder.TenSp.setText(list.get(position).getTenSP());
        holder.Gia.setText("Giá: " + list.get(position).getGia() + " VND");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSPActivity.class);
                intent.putExtra("Sanpham",list.get(position).getMaSp());
                (context).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
 ImageView anhSp;
 TextView Gia ,TenSp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhSp=itemView.findViewById(R.id.imv_anh_sp_trangchu);
            Gia = itemView.findViewById(R.id.tv_giasp_trangchu);
            TenSp =itemView.findViewById(R.id.tv_tensp_trangchu);
        }
    }
}
