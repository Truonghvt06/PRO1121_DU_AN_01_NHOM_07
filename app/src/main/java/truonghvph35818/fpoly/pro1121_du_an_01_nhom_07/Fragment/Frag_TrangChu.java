package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.bannerADapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.banner;

public class Frag_TrangChu extends Fragment {

    private ViewPager viewPager;
    private bannerADapter bannerADapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_trangchu, container, false);
        viewPager =view.findViewById(R.id.view_pager);
        bannerADapter =new bannerADapter(getContext(),getBanerList());
        viewPager.setAdapter(bannerADapter);

        return view;
    }

    private List<banner> getBanerList(){
        List<banner> list =new ArrayList<>();
        list.add(new banner(R.drawable.bannner1));
        list.add(new banner(R.drawable.banner2));
        list.add(new banner(R.drawable.banner3));

        return list;
    }
}
