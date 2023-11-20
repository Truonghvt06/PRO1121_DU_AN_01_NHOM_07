package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.NguoiDungActivity;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class DangNhap extends AppCompatActivity {
    private TextView tvDangKi,tvQuenMatKhau;
    private EditText edEmail,edMatKhau;
    private Button btnDangNhap;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        firebaseAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edEmail);
        edMatKhau = findViewById(R.id.edMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);

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

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,mk;
                email = edEmail.getText().toString();
                mk = edMatKhau.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập tài khoản!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mk)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,mk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(DangNhap.this, NguoiDungActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Đăng nhập không thành công",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}
