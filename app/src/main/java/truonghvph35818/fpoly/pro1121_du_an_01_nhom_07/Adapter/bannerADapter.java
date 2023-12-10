package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.banner;

public class bannerADapter extends RecyclerView.Adapter<bannerADapter.Viewholder> {
  List<banner> list;
  Context context;

    public bannerADapter(List<banner> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(((Activity)context).getLayoutInflater().inflate(R.layout.item_banner,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
    holder.view.setImageResource(list.get(position).getBannerid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
    ImageView view;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.img_banner);

        }
    }
}





