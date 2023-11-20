package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class QuenMatKhau extends AppCompatActivity {
    private EditText edQMKEmail;
    private Button btnLayLaiMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        edQMKEmail = findViewById(R.id.edQMKEmail);
        btnLayLaiMK = findViewById(R.id.btnLayLaiMK);

        btnLayLaiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String email = edQMKEmail.getText().toString();

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Thành công!! Kiểm tra email để lấy lại mật khẩu",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(QuenMatKhau.this, DangNhap.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
}