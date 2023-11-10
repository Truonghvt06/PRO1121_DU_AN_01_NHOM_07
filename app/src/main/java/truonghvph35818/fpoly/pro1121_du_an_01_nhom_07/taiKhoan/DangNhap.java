package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class DangNhap extends AppCompatActivity {
    private TextView tvDangKi,tvQuenMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        tvDangKi = findViewById(R.id.tvDangKi);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        tvDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dangKi = new Intent(DangNhap.this,DangKi.class);
                startActivity(dangKi);
            }
        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quenMK = new Intent(DangNhap.this,QuenMatKhau.class);
                startActivity(quenMK);
            }
        });
    }
}