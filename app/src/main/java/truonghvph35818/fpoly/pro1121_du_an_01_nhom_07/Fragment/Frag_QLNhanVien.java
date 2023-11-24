package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.UserAdapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_QLNhanVien extends Fragment {
    List<User> list ;
    ImageView img_themAnhNV;
    EditText ed_hoTen, ed_gioiTinh, ed_namSinh, ed_email, ed_sdt, ed_matKhau;
    Button btn_Luu, btn_Huy;
    RecyclerView recyclerView;
    FloatingActionButton button;
    UserAdapter userAdapter;
    String id;
    User user = new User();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    String email,matkhau,hoten,sdt,namsinh,gioitinh;
    Dialog dialog;
    public Frag_QLNhanVien() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_nhan_vien, container, false);
        recyclerView = view.findViewById(R.id.re_nhanVien);
        button = view.findViewById(R.id.float_nhanVien);
        loadData();

        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater2 = getLayoutInflater();
                View view1 = inflater2.inflate(R.layout.dialog_themnv, null);
                builder.setView(view1);
                dialog = builder.create();
                dialog.show();

                img_themAnhNV = view1.findViewById(R.id.img_themAnhNV);
                ed_gioiTinh = view1.findViewById(R.id.ed_gioiTinh);
                ed_namSinh = view1.findViewById(R.id.ed_namSinh);
                ed_email = view1.findViewById(R.id.ed_email);
                ed_matKhau = view1.findViewById(R.id.ed_matKhau);
                ed_hoTen = view1.findViewById(R.id.ed_hoTen);
                ed_sdt = view1.findViewById(R.id.ed_sdt);
                btn_Luu = view1.findViewById(R.id.luu_themsp);
                btn_Huy = view1.findViewById(R.id.huy_themsp);

                btn_Luu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gioitinh = ed_gioiTinh.getText().toString();
                        namsinh = ed_namSinh.getText().toString();
                        email = ed_email.getText().toString();
                        matkhau = ed_matKhau.getText().toString();
                        hoten = ed_hoTen.getText().toString();
                        sdt = ed_sdt.getText().toString();
                        id = UUID.randomUUID().toString();


                        if (email.isEmpty() || matkhau.isEmpty() || hoten.isEmpty() || sdt.isEmpty() || gioitinh.isEmpty() || namsinh.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else if (!isValidateEmail(email)) {
                            Toast.makeText(getContext(), "Không đúng định dạng của email", Toast.LENGTH_SHORT).show();
                        } else if (matkhau.length() < 8) {
                            Toast.makeText(getContext(), "Mật khẩu phải từ 8 chữ số", Toast.LENGTH_SHORT).show();
                        } else if (!isValidatePhone(sdt) || sdt.length() <10) {
                            Toast.makeText(getContext(), "Số điện thoại không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            themTK();


                        }
                    }
                });


                btn_Huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        return view;
    }
    public boolean isValidateEmail(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches();
    }

    public boolean isValidatePhone(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.PHONE.matcher(e).matches();
    }

    public void loadData() {
        nghe();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list =new ArrayList<>();
        recyclerView.setAdapter(new UserAdapter(getContext(), list));
        userAdapter = new UserAdapter(getContext(), list);
        recyclerView.setAdapter(userAdapter);
    }

    public void themTK() {
        firebaseAuth.createUserWithEmailAndPassword(email, matkhau).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                user.setMaUser(user1.getUid());
                user.setEmail(email);
                user.setHoTen(hoten);
                user.setSDT(sdt);
                user.setChucVu(2);
                user.setTrangThai(1);
                db.collection("user").document(user.getMaUser()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void nghe() {
        db.collection("user").whereEqualTo("chucVu", 2)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        Log.e("TAG", "onEvent: " + dc.getType());
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(User.class);
                                list.add(dc.getDocument().toObject(User.class));
                                userAdapter.notifyDataSetChanged();
                                Log.e("TAG", "loi" + dc.getDocument().toObject(User.class));
                                break;
                            case MODIFIED:
                                User user1 = dc.getDocument().toObject(User.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list.set(dc.getOldIndex(), user1);
                                    userAdapter.notifyDataSetChanged();
                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(user1);
                                    userAdapter.notifyDataSetChanged();
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(User.class);
                                list.remove(dc.getOldIndex());
                                userAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
