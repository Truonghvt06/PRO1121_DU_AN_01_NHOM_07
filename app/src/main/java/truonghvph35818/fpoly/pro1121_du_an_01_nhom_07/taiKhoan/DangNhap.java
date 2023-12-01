package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.AdminActivity;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
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
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("User").whereEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isComplete()){
                                        User user= new User();
                                        for (QueryDocumentSnapshot c :task.getResult()){
                                            user=c.toObject(User.class);
                                            Log.e("TAG", "onComplete: 8 "+user.getChucVu() );
                                        }
                                        if (user.getChucVu() == 2){
                                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(DangNhap.this, NguoiDungActivity.class);
                                            startActivity(i);
                                        }else {
                                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(DangNhap.this, AdminActivity.class);
                                            startActivity(i);
                                            Log.e("TAG", "onComplete: "+user.getEmail() );
                                        }


                                    }
                                }
                            });

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
