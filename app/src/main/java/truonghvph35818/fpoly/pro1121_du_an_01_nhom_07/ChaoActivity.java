package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.time.Instant;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan.DangNhap;

public class ChaoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);
        ImageView gif = findViewById(R.id.Anh_gif);

        Glide.with(this).load(R.drawable.zara).into(gif);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChaoActivity.this, DangNhap.class);
                startActivity(intent);
                finish();
            }
        },3500);
    }
}