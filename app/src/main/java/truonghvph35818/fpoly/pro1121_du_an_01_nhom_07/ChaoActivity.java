package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan.DangNhap;

public class ChaoActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);
        ImageView gif = findViewById(R.id.Anh_gif);
        Glide.with(this).load(R.drawable.zara).into(gif);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (user == null) {
                    Intent intent = new Intent(ChaoActivity.this, DangNhap.class);
                    startActivity(intent);
                    finish();
                } else {
                    dangnhap();
                }
            }
        }, 3500);
    }

    private void dangnhap() {
        db.collection("User").whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    User user = new User();
                    for (QueryDocumentSnapshot c : task.getResult()) {
                        user = c.toObject(User.class);
                        Log.e("TAG", "onComplete: 8 " + user.getChucVu());
                    }
                    if (user.getChucVu() == 2) {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChaoActivity.this, NguoiDungActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChaoActivity.this, AdminActivity.class);
                        startActivity(i);
                        Log.e("TAG", "onComplete: " + user.getEmail());
                    }


                }
            }
        });

    }
}
