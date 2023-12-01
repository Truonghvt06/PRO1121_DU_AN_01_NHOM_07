package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.bannerADapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.banner;

public class Frag_TrangChu extends Fragment {
    private RecyclerView rcy_list,rcy_banner;
    EditText editText;
    ImageView imageView;
    bannerADapter bannerADapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_trangchu, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcy_banner = view.findViewById(R.id.rcy_banner);
        rcy_list=view.findViewById(R.id.Rcv_trangchu);
        imageView =view.findViewById(R.id.img_timKiem);
        editText =view.findViewById(R.id.ed_timKiem);
        bannerADapter =new bannerADapter(getanh(),getContext());
        rcy_banner.setAdapter(bannerADapter);
        rcy_banner.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

    }

    private List<banner> getanh() {
        List<banner> list = new ArrayList<>();
        list.add(new banner(R.drawable.bannner1));
        list.add(new banner(R.drawable.banner2));
        list.add(new banner(R.drawable.banner3));
        return  list;
    }
}