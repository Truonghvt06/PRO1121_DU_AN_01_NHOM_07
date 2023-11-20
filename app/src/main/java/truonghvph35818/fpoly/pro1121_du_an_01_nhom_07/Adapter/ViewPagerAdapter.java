package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_Nam;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_Nu;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_TreEm;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Frag_Nam();
            case 1:
                return new Frag_Nu();
            case 2:
                return new Frag_TreEm();
            default:
                return new Frag_Nam();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
