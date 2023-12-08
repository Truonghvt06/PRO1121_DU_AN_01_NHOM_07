package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;

public class ThongTinTKActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 2;
    ImageView img_back;
    CircleImageView img_anh;
    TextView tv_ten, tv_gioiTinh, tv_ngaySinh, tv_sdt, tv_email;
    LinearLayout ll_ten, ll_gioiTinh, ll_ngaySinh, ll_sdt, ll_email, ll_doiMk;
    User user = new User();
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tkactivity);
        img_back = findViewById(R.id.img_back_tk);
        img_anh = findViewById(R.id.img_avatar_tk);
        tv_ten = findViewById(R.id.tv_ten_tk);
        tv_gioiTinh = findViewById(R.id.tv_gioiTinh_tk);
        tv_ngaySinh = findViewById(R.id.tv_ngaySinh_tk);
        tv_sdt = findViewById(R.id.tv_sdt_tk);
        tv_email = findViewById(R.id.tv_email_tk);
        ll_ten = findViewById(R.id.linear_ten);
        ll_gioiTinh = findViewById(R.id.linear_gioiTinh);
        ll_ngaySinh = findViewById(R.id.linear_ngaySinh);
        ll_sdt = findViewById(R.id.linear_sdt);
        ll_email = findViewById(R.id.linear_email);
        ll_doiMk = findViewById(R.id.linear_doi_mk);
        

        firestore = FirebaseFirestore.getInstance();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });
        //load dữ liệu
        loadUserData();

        firestore.collection("User").document(firebaseUser.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    DocumentSnapshot snapshot = task.getResult();
                                    if (snapshot.exists()){
                                        User user1 = snapshot.toObject(User.class);
                                        if (user1 != null){
                                            String hoTen = user1.getHoTen();
                                            String gioiTinh = user1.getGioiTinh();
                                            String ngaySinh = user1.getNgaySinh();
                                            String sdt = user1.getSDT();
                                            String email = user1.getEmail();

                                            tv_ten.setText(hoTen);
                                            tv_gioiTinh.setText(gioiTinh);
                                            tv_ngaySinh.setText(ngaySinh);
                                            tv_sdt.setText(sdt);
                                            tv_email.setText(email);
                                        }
                                    }
                                }else {
                                    Toast.makeText(ThongTinTKActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        ll_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTKActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_doi_ten_tk, null, false);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();

                EditText ed_ten = view1.findViewById(R.id.ed_suaTen);
                Button btn_huy = view1.findViewById(R.id.btn_huyTen);
                Button btn_sua = view1.findViewById(R.id.btn_suaTen);

                //Set giá trị ban đầu cho EditText là tên hiện tại
                ed_ten.setText(tv_ten.getText().toString());

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Lấy tên mới từ EditText
                        String newName = ed_ten.getText().toString();

                        tv_ten.setText(newName);

//                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(newName)
//                                .build();
//                        firebaseUser.updateProfile(profileChangeRequest)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful()){
//                                            Toast.makeText(ThongTinTKActivity.this, "Cập nhật tên thành công!", Toast.LENGTH_SHORT).show();
//                                        }else {
//                                            Toast.makeText(ThongTinTKActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
                        firestore.collection("User").document(firebaseUser.getUid())
                                .update("hoTen", newName)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(ThongTinTKActivity.this, "Cập nhật lên Firebase thành công!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(ThongTinTKActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }
                });
            }
        });
        ll_gioiTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTKActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_doi_ten_tk, null, false);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();

                EditText ed_gioiTinh = view1.findViewById(R.id.ed_suaTen);
                Button btn_huy = view1.findViewById(R.id.btn_huyTen);
                Button btn_sua = view1.findViewById(R.id.btn_suaTen);

                //Set giá trị ban đầu cho EditText là tên hiện tại
                ed_gioiTinh.setText(tv_gioiTinh.getText().toString());

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Lấy tên mới từ EditText
                        String newGioiTinh = ed_gioiTinh.getText().toString();

                        tv_gioiTinh.setText(newGioiTinh);

                        firestore.collection("User").document(firebaseUser.getUid())
                                .update("gioiTinh", newGioiTinh)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(ThongTinTKActivity.this, "Cập nhật lên Firebase thành công!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(ThongTinTKActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }
                });
            }
        });

        ll_ngaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTKActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_doi_ten_tk, null, false);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();

                EditText ed_ngaySinh = view1.findViewById(R.id.ed_suaTen);
                Button btn_huy = view1.findViewById(R.id.btn_huyTen);
                Button btn_sua = view1.findViewById(R.id.btn_suaTen);

                //Set giá trị ban đầu cho EditText là tên hiện tại
                ed_ngaySinh.setText(tv_ngaySinh.getText().toString());

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Lấy tên mới từ EditText
                        String newnNgaySinh = ed_ngaySinh.getText().toString();

                        tv_ngaySinh.setText(newnNgaySinh);

                        firestore.collection("User").document(firebaseUser.getUid())
                                .update("ngaySinh", newnNgaySinh)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(ThongTinTKActivity.this, "Cập nhật lên Firebase thành công!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(ThongTinTKActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }
                });
            }
        });

        ll_sdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTKActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_doi_ten_tk, null, false);
                builder.setView(view1);
                builder.setCancelable(false);
                Dialog dialog = builder.create();
                dialog.show();

                EditText ed_sdt = view1.findViewById(R.id.ed_suaTen);
                Button btn_huy = view1.findViewById(R.id.btn_huyTen);
                Button btn_sua = view1.findViewById(R.id.btn_suaTen);

                //Set giá trị ban đầu cho EditText là tên hiện tại
                ed_sdt.setText(tv_sdt.getText().toString());

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Lấy tên mới từ EditText
                        String newSdt = ed_sdt.getText().toString();

                        tv_sdt.setText(newSdt);
                        if (!isValidatePhone(newSdt) || newSdt.length() < 10 || newSdt.length() > 10){
                            Toast.makeText(ThongTinTKActivity.this, "Số điện thoại gồm 10 chữ số!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            firestore.collection("User").document(firebaseUser.getUid())
                                    .update("sdt", newSdt)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ThongTinTKActivity.this, "Cập nhật lên Firebase thành công!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ThongTinTKActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        ll_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTKActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_doi_ten_tk, null, false);
                builder.setView(view1);
                builder.setCancelable(false);
                Dialog dialog = builder.create();
                dialog.show();

                EditText ed_email = view1.findViewById(R.id.ed_suaTen);
                Button btn_huy = view1.findViewById(R.id.btn_huyTen);
                Button btn_sua = view1.findViewById(R.id.btn_suaTen);

                //Set giá trị ban đầu cho EditText là tên hiện tại
                ed_email.setText(tv_email.getText().toString());

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Lấy tên mới từ EditText
                        String newEmail = ed_email.getText().toString();

                        tv_email.setText(newEmail);

                        if (!isValidateEmail(newEmail)){
                            Toast.makeText(ThongTinTKActivity.this, "Sai định dạng Email!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            firestore.collection("User").document(firebaseUser.getUid())
                                    .update("email", newEmail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ThongTinTKActivity.this, "Cập nhật lên Firebase thành công!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ThongTinTKActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        ll_doiMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTKActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_doimk_ngdung, null, false);
                builder.setView(view1);
                builder.setCancelable(false);
                Dialog dialog = builder.create();
                dialog.show();

                EditText ed_mkCu = view1.findViewById(R.id.ed_mkcu_nd);
                EditText ed_mkMoi = view1.findViewById(R.id.ed_mkmoi_nd);
                EditText ed_re_mk = view1.findViewById(R.id.ed_re_mkmoi_nd);
                Button btn_huy = view1.findViewById(R.id.btn_huy_nd);
                Button btn_luu = view1.findViewById(R.id.btn_luu_nd);

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn_luu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mkCu = ed_mkCu.getText().toString();
                        String mkMoi = ed_mkMoi.getText().toString();
                        String re_mk = ed_re_mk.getText().toString();

                        if (mkCu.isEmpty() || mkMoi.isEmpty() || re_mk.isEmpty()){
                            Toast.makeText(ThongTinTKActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else if(re_mk.equals(mkMoi)){
                            doiMK(mkCu, mkMoi);
                            finish();
                        }else {
                            Toast.makeText(ThongTinTKActivity.this, "Xác nhận mật khẩu mới sai!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void loadUserData() {
        firestore.collection("User").document(firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                User user1 = snapshot.toObject(User.class);
                                if (user1 != null) {
                                    String imageUrl = user1.getAnh();
                                    if (imageUrl != null && !imageUrl.isEmpty()) {
                                        loadAnh();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(ThongTinTKActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
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
    public void doiMK(String pasCu, String pasMoi) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential authenticator = EmailAuthProvider.getCredential(user.getEmail(), pasCu);
        user.reauthenticate(authenticator).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(pasMoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ThongTinTKActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ThongTinTKActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ThongTinTKActivity.this, "Mật khẩu cũ sai vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Up ảnh lên
    public  void UpLoadAnh(Uri anh){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("images/" + firebaseUser.getUid() + ".jpg");
        
        storageReference.putFile(anh).addOnSuccessListener(taskSnapshot -> {
            updateProfileImage(storageReference);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Tải ảnh thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void updateProfileImage(StorageReference storageReference) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build();

            firebaseUser.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            updateFirestoreUser(uri.toString());
                            loadAnh();
                        }else {
                            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
    //ban anh len Firebase
    private void updateFirestoreUser(String imageUrl) {
        // Update the 'User' document in Firestore with the new image URL
        Map<String, Object> update = new HashMap<>();
        update.put("anh", imageUrl);

        firestore.collection("User").document(firebaseUser.getUid())
                .update(update)
                .addOnSuccessListener(aVoid -> {
                    // Update successful
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
                });
    }
    private void loadAnh() {
        FirebaseStorage.getInstance().getReference()
                .child("images/" + firebaseUser.getUid() + ".jpg")
                .getDownloadUrl()
                .addOnSuccessListener(uri -> Glide.with(this).load(uri).error(R.drawable.warning).into(img_anh))
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    private void openImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            UpLoadAnh(uri);
            loadUserData();
        }
    }
}