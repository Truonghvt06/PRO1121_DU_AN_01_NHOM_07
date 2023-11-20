package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_GioHang;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_TaiKhoan;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_TimKiem;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_TrangChu;

public class NguoiDungActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.menu_nav);

        //Frag mặc định
        FragmentManager fragmentManager = getSupportFragmentManager();
        Frag_TrangChu frag_trangChu = new Frag_TrangChu();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, frag_trangChu)
                .commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.trang_chu){
                    fragment = new Frag_TrangChu();
                } else if (item.getItemId() == R.id.tim_kiem) {
                    fragment = new Frag_TimKiem();
                }else if (item.getItemId() == R.id.gio_hang) {
                    fragment = new Frag_GioHang();
                }else if (item.getItemId() == R.id.tai_khoan) {
                    fragment = new Frag_TaiKhoan();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
                return true;
            }
        });
    }
}