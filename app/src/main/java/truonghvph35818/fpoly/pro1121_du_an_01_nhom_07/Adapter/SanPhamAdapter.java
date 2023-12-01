package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder>{
    Context context;
    List<SanPhamDTO> list;

    public SanPhamAdapter(Context context, List<SanPhamDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View  view = inflater.inflate(R.layout.item_san_pham, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_ten.setText(list.get(position).getTenSP());
        holder.tv_gia.setText(list.get(position).getGia() + "");
        holder.tv_hienThi.setText(list.get(position).getMaHienThi());
        holder.tv_theLoai.setText(list.get(position).getMaTheLoai());

        holder.img_deleteSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_anhSP, img_deleteSP;
        TextView tv_ten, tv_gia, tv_theLoai, tv_hienThi;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                img_anhSP = itemView.findViewById(R.id.img_anh);
                img_deleteSP = itemView.findViewById(R.id.delete_sp);
                tv_ten = itemView.findViewById(R.id.tv_ten_sp);
                tv_gia = itemView.findViewById(R.id.tv_gia_sp);
                tv_theLoai = itemView.findViewById(R.id.tv_theLoai_sp);
                tv_hienThi = itemView.findViewById(R.id.tv_hien_thi_sp);

            }
        }


}


