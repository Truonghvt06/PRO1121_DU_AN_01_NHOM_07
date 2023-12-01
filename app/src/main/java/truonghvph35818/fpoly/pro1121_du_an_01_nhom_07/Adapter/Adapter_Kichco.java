package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.ChiTietSPActivity;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Adapter_Kichco extends RecyclerView.Adapter<Adapter_Kichco.Viewholder> {
    List<String> list;
    Context context;
    int i = -1;
    public Adapter_Kichco(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(((Activity)context).getLayoutInflater().inflate(R.layout.item_kichco,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.ten.setText(list.get(position));
        String cam = "#FF4800";
        String vang = "#FFC107";
        if (position==i){
            ChiTietSPActivity sanPham = (ChiTietSPActivity) context;
            sanPham.setKickco(list.get(i));
            holder.mau.setBackgroundColor(Color.parseColor(cam));
        }else {
            holder.mau.setBackgroundColor(Color.parseColor(vang));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{
        TextView ten;
        LinearLayout mau;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ten=itemView.findViewById(R.id.tv_kichco_show1);
            mau =itemView.findViewById(R.id.cv_kichco);
        }
    }
}
