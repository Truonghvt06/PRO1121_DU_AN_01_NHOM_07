package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_Giay;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_Dep;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Frag_Giay();
            case 1:
                return new Frag_Dep();
            default:
                return new Frag_Giay();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
