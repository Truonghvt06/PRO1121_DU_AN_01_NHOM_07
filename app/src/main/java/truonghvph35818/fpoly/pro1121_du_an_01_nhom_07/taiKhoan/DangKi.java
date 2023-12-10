package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class DangKi extends AppCompatActivity {
    private TextView tvDangNhap;
    private EditText edDKHoTen,edDKEmail,edDKSdt,edDKMatKhau,edDKNhapLaiMK;
    private Button btnDangKi;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        edDKHoTen = findViewById(R.id.edDKHoTen);
        edDKEmail = findViewById(R.id.edDKEmail);
        edDKSdt = findViewById(R.id.edDKSdt);
        edDKMatKhau = findViewById(R.id.edDKMatKhau);
        edDKNhapLaiMK = findViewById(R.id.edDKNhapLaiMK);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        btnDangKi = findViewById(R.id.btnDangKi);
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }



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
    private void dangKi(){
        String hoTen = edDKHoTen.getText().toString();
        String sdt = edDKSdt.getText().toString();
        String email = edDKEmail.getText().toString();
        String mk = edDKMatKhau.getText().toString();
        if(email.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || mk.isEmpty()){
            Toast.makeText(getApplicationContext(),"Vui lòng không bỏ trống!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidatePhone(sdt) || sdt.length() < 10 ||sdt.length() >10){
            Toast.makeText(this, "Số điện thoại gồm 10 chữ số!", Toast.LENGTH_SHORT).show();
        }
        if (!isValidateEmail(email)){
            Toast.makeText(this, "Không đúng định dạng của email!", Toast.LENGTH_SHORT).show();
        }
        if (mk.length() < 7 ){
            Toast.makeText(this, "Mật khẩu phải từ 7 kí tự!", Toast.LENGTH_SHORT).show();
        }
        if(!mk.equals(edDKNhapLaiMK.getText().toString())){
            Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp!",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,mk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(DangKi.this, DangNhap.class);
                    startActivity(intent);
                    finishAffinity();
                    TaoUser();
//                    user = firebaseAuth.getCurrentUser();
//                    updateUI(user);
//                    Taouser();
                }else {
                    Toast.makeText(getApplicationContext(),"Đăng ký không thành công!!",Toast.LENGTH_SHORT).show();
//                    updateUI(null);
                }
            }
        });
    }


    public boolean isValidateEmail(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches();
    }
    public boolean isValidatePhone(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.PHONE.matcher(e).matches();
    }



    public void TaoUser(){
        user = firebaseAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        if (user == null){
            return;
        }
        User user1=new User();
        user1.setMaUser(user.getUid());
        user1.setHoTen(edDKHoTen.getText().toString());
        user1.setEmail(edDKEmail.getText().toString());
        user1.setSDT(edDKSdt.getText().toString());
        user1.setChucVu(3);
        user1.setTrangThai(1);

//        firestore.collection("User").document(user1.getMaUser()).set(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isComplete()){
//                    Toast.makeText(getApplicationContext(),"Tạo thành công!!",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(DangKi.this, DangNhap.class);
//                    startActivity(intent);
//                }
//            }
//        });
        firestore.collection("User").document(user1.getMaUser()).set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DangKi.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DangKi.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
