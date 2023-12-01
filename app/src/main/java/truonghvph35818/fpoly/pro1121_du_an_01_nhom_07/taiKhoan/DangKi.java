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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class DangKi extends AppCompatActivity {
    private TextView tvDangNhap;
    private EditText edDKHoTen,edDKEmail,edDKSdt,edDKTaiKhoan,edDKMatKhau,edDKNhapLaiMK;
    private Button btnDangKi;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    String email,mk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        edDKHoTen = findViewById(R.id.edDKHoTen);
        edDKEmail = findViewById(R.id.edDKEmail);
        edDKSdt = findViewById(R.id.edDKSdt);
//        edDKTaiKhoan = findViewById(R.id.edDKTaiKhoan);
        edDKMatKhau = findViewById(R.id.edDKMatKhau);
        edDKNhapLaiMK = findViewById(R.id.edDKNhapLaiMK);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        btnDangKi = findViewById(R.id.btnDangKi);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }



        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKi.this,DangNhap.class);
                startActivity(intent);
            }
        });
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();

            }
        });

    }
    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
    private void dangKi(){
        email = edDKEmail.getText().toString();
        mk = edDKMatKhau.getText().toString();
        String  checkMK = edDKNhapLaiMK.getText().toString();
        if(edDKHoTen.getText().toString().isEmpty() || edDKSdt.getText().toString().isEmpty() ){
            Toast.makeText(getApplicationContext(),"Vui lòng không bỏ trống!!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập email!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(mk)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkMK.equals(mk)){
            Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp!!",Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email,mk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                    Taouser();
                }else {
                    Toast.makeText(getApplicationContext(),"Tạo không thành công!!",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void Taouser() {
        User user1=new User();
        user1.setMaUser(user.getUid());
        user1.setHoTen(edDKHoTen.getText().toString());
        user1.setEmail(edDKEmail.getText().toString());
        user1.setSDT(edDKSdt.getText().toString());
        user1.setChucVu(2);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User").document(user1.getMaUser()).set(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
                    Toast.makeText(getApplicationContext(),"Tạo thành công!!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangKi.this, DangNhap.class);
                    startActivity(intent);
                }
            }
        });

    }
}
