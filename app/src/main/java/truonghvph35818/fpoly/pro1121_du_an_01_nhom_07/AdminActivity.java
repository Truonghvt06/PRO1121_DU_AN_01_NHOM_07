package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_Chat;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_DoanhThu;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_DoiMatKhau;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_QLDonHang;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_QLNguoiDung;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_QLNhanVien;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_QLSanPham;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_Top10;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan.DangNhap;

public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navView;
//    FrameLayout frameLayout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);
//        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);


        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        drawerToggle = new ActionBarDrawerToggle(AdminActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        //Frag mặc định
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new Frag_QLNhanVien())
                .commit();

        //Frag mặc điịnh
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Frag_QLNhanVien qlNhanVien = new Frag_QLNhanVien();
//        fragmentManager.beginTransaction()
//                .replace(R.id.drawer_layout, qlNhanVien)
//                .commit();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.ql_nhanVien){
                    fragment = new Frag_QLNhanVien();
                }else if(item.getItemId() == R.id.ql_nguoiDung){
                    fragment = new Frag_QLNguoiDung();
                }else if(item.getItemId() == R.id.ql_sanPham){
                    fragment = new Frag_QLSanPham();
                }else if(item.getItemId() == R.id.ql_donHang){
                    fragment = new Frag_QLDonHang();
                }else if(item.getItemId() == R.id.chat){
                    fragment = new Frag_Chat();
                }else if(item.getItemId() == R.id.top10){
                    fragment = new Frag_Top10();
                }else if(item.getItemId() == R.id.doanh_thu){
                    fragment = new Frag_DoanhThu();
                }else if(item.getItemId() == R.id.doi_mk){
                    fragment = new Frag_DoiMatKhau();
                }else if(item.getItemId() == R.id.dang_xuat){
                    AlertDialog.Builder  builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setIcon(R.drawable.canh_bao);
                    builder.setTitle("Thng báo!");
                    builder.setMessage("Bạn có chắc muốn đăng xuất?");
                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(AdminActivity.this, DangNhap.class));

                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
                drawerLayout.close();

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}