package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.ViewPagerAdapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_TimKiem extends Fragment {
    EditText ed_timKiem;
    private TabLayout tabLayout;
    private ViewPager2 pager2;
    private ViewPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_timkiem, container, false);

        ed_timKiem = view.findViewById(R.id.ed_timKiem);
        tabLayout = view.findViewById(R.id.tab_layout);
        pager2 = view.findViewById(R.id.view_pager);

        adapter = new ViewPagerAdapter(this);
        pager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Giay");
                        break;
                    case 1:
                        tab.setText("Dép");
                        break;


                }
            }
        }).attach();

        return view;
    }
}
