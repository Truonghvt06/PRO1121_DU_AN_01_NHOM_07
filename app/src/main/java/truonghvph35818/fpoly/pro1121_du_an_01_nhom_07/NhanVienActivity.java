package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_DoanhThu;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_DoiMatKhau;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_QLDonHang;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_QLSanPham;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment.Frag_Top10;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan.DangNhap;

public class NhanVienActivity extends AppCompatActivity {
    Toolbar toolbar;
    Frag_QLSanPham Frag_QLSanPham=new Frag_QLSanPham();
    NavigationView navView;
    //    FrameLayout frameLayout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);
//        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);


        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        drawerToggle = new ActionBarDrawerToggle(NhanVienActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        //Frag mặc định
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new Frag_QLSanPham())
                .commit();


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String title = "";



                if(item.getItemId() == R.id.ql_sanPham_nv){
                    fragment = Frag_QLSanPham ;
                    title = "Quản lý sản phẩm";
                }else if(item.getItemId() == R.id.ql_donHang_nv){
                    fragment = new Frag_QLDonHang();
                    title = "Quản lý đơn hàng";
                }else if(item.getItemId() == R.id.top10_nv){
                    fragment = new Frag_Top10();
                    title = "Top 10 bán chạy";
                }else if(item.getItemId() == R.id.doanh_thu_nv){
                    fragment = new Frag_DoanhThu();
                    title = "Doanh thu";
                }else if(item.getItemId() == R.id.doi_mk_nv){
                    fragment = new Frag_DoiMatKhau();
                    title = "Đổi mật khẩu";
                }else if(item.getItemId() == R.id.dang_xuat_nv) {
                    fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                    FirebaseAuth.getInstance().signOut();



                    if (fragment != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this);
                        builder.setIcon(R.drawable.canh_bao);
                        builder.setTitle("Thng báo!");
                        builder.setMessage("Bạn có chắc muốn đăng xuất?");
                        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(NhanVienActivity.this, DangNhap.class));

                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();
                    }
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
                getSupportActionBar().setTitle(title);
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
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Intent intent = o.getData();
                        if (intent == null) {
                            return;
                        }
                        //để lấy ảnh
                        Frag_QLSanPham.setUri(intent.getData());


                    }

                }
            });
    public void layAnh() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    public void yeucauquyen(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            layAnh();
            return;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            String[] quyen = new String[]{android.Manifest.permission.READ_MEDIA_IMAGES};
            requestPermissions(quyen, CODE_QUYEN);
            return;
        }
        if (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // xử lý sau
            layAnh();
        } else {
            String[] quyen = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(quyen, CODE_QUYEN);
        }
    }

    private static final int CODE_QUYEN = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_QUYEN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layAnh();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}