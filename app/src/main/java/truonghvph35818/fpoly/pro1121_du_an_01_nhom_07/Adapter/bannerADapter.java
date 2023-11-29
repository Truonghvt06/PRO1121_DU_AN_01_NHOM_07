package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.banner;

public class bannerADapter extends PagerAdapter {
    private Context context;
    private List<banner> bannerList;

    public bannerADapter(Context context, List<banner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_banner, container,false);
        ImageView imageView =view.findViewById(R.id.img_banner);
        banner banner=bannerList.get(position);
        if (banner!= null){
            Glide .with(context).load(banner.getBannerid()).into(imageView);

        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
       if (bannerList !=null){
           return  bannerList.size();
       }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object) ;


    }
}
