package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.HangDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Show_SanPham;

public class Adapter_trangchu extends RecyclerView.Adapter<Adapter_trangchu.Viewholder>  {
    List<HangDTO> list;
    Context context;
   Adapter_item_tt adapterItemTt;

    public Adapter_trangchu(List<HangDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(((Activity)context).getLayoutInflater().inflate(R.layout.item_sanpham_trangchuu,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        if (list.get(position).getSanPham().size()==0){
            return;
        }
        holder.tenHang.setText(list.get(position).getTenHang());

        adapterItemTt=new Adapter_item_tt(context, list.get(position).getSanPham());
        holder.rcv_list.setAdapter(adapterItemTt);
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.rcv_list.setLayoutManager(manager);
        holder.xemthem.setText("Xem thêm");
        Log.e("TAG", "onBindViewHolder: "+list.get(position).getSanPham().get(0).getTenSP() );
        holder.xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Show_SanPham.class);
                List<SanPhamDTO> phamList = list.get(position).getSanPham();
                String [] s = new String[]{list.get(position).getMaHang(),list.get(position).getTenHang()};
                intent.putExtra("list",  s);
                ((Activity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{
       TextView tenHang;
       TextView xemthem;
       RecyclerView rcv_list;
       public Viewholder(@NonNull View itemView) {
           super(itemView);
           tenHang = itemView.findViewById(R.id.tv_tenhang);
           xemthem = itemView.findViewById(R.id.ll_xemthem_moi);
           rcv_list = itemView.findViewById(R.id.rcv_list_sp_khach);
       }
   }

}
