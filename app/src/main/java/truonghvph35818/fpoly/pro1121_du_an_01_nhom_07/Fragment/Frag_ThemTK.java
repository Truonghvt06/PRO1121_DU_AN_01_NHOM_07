package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class Frag_ThemTK extends Fragment {
    EditText ed_email, ed_mk, ed_remmk;
    Button btn_huy, btn_them;
    String id;
    String email, mk, re_mk;
    List<User> list_user;
    User user = new User();
    UserAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_themtk, container, false);

        ed_email = view.findViewById(R.id.ed_email_themtk);
        ed_mk = view.findViewById(R.id.ed_mk_themtk);
        ed_remmk = view.findViewById(R.id.ed_remk_themtk);
        btn_huy = view.findViewById(R.id.btn_huy_themtk);
        btn_them = view.findViewById(R.id.btn_them_themtk);

        Nghe();

        list_user = new ArrayList<>();
        adapter = new UserAdapter(getContext(), list_user);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_email.setText("");
                ed_mk.setText("");
                ed_remmk.setText("");
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = UUID.randomUUID().toString();
                email = ed_email.getText().toString();
                mk = ed_mk.getText().toString();
                re_mk = ed_remmk.getText().toString();

                if (email.isEmpty() || mk.isEmpty() || re_mk.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else if(!isValidateEmail(email)){
                    Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng Email!", Toast.LENGTH_SHORT).show();
                }else if(!re_mk.equals(mk)){
                    Toast.makeText(getContext(), "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                }else {
                    themTK();
                }

            }
        });


        return view;
    }



    public boolean isValidateEmail(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches();
    }

    private void themTK() {
        firebaseAuth.createUserWithEmailAndPassword(email, mk).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();

                user.setMaUser(user1.getUid());
                user.setEmail(email);
                user.setChucVu(1);
                user.setTrangThai(1);

                firestore.collection("User").document(user.getMaUser()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){
                            Toast.makeText(getContext(), "Thêm tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();

                        }else {
                            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Nghe(){
        firestore.collection("User").whereEqualTo("chucVu", 1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            return;
                        }
                        if (value != null){
                            for (DocumentChange change : value.getDocumentChanges()){
                                switch (change.getType()){
                                    case ADDED:
                                        change.getDocument().toObject(User.class);
                                        list_user.add(change.getDocument().toObject(User.class));
                                        adapter.notifyDataSetChanged();
                                        break;
                                    case MODIFIED:
                                        User user1 = change.getDocument().toObject(User.class);
                                        if (change.getOldIndex() == change.getNewIndex()){
                                            list_user.set(change.getOldIndex(), user1);
                                            adapter.notifyDataSetChanged();
                                        }else {
                                            list_user.remove(change.getOldIndex());
                                            list_user.add(user1);
                                            adapter.notifyDataSetChanged();
                                        }
                                        break;
                                    case REMOVED:
                                        change.getDocument().toObject(User.class);
                                        list_user.remove(change.getOldIndex());
                                        adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
        }
}
