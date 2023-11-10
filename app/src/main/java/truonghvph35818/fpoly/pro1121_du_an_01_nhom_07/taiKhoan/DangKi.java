package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class DangKi extends AppCompatActivity {
    private TextView tvDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKi.this,DangNhap.class);
                startActivity(intent);
            }
        });
    }
}