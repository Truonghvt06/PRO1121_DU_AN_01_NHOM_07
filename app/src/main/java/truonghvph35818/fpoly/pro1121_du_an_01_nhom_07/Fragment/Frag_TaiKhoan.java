package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.ThongTinLHActivity;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.ThongTinTKActivity;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan.DangNhap;

public class Frag_TaiKhoan extends Fragment {
    private static final int YOUR_IMAGE_REQUEST_CODE = 1;
    LinearLayout ll_lienHe, ll_dangxuat, ll_thongtintk, ll_lichsudonhang;
    FirebaseFirestore firestore ;
    FirebaseAuth auth;
    CircleImageView img_anh;
//    User user;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_taikhoan, container, false);

        img_anh = view.findViewById(R.id.img_anh_tk);
        ll_dangxuat = view.findViewById(R.id.ll_Dangxuat);
        ll_thongtintk = view.findViewById(R.id.ll_thongtintk);
        ll_lienHe = view.findViewById(R.id.ll_lienHe);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        firestore.collection("User").document(auth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            String img = user.getAnh();
                            if (img != null && !img.isEmpty()) {
                                Glide.with(this).load(img).error(R.drawable.zara).into(img_anh);
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });

        img_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThongTinTKActivity.class);
                startActivityForResult(intent, YOUR_IMAGE_REQUEST_CODE);
            }
        });


        ll_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dangxuat();
            }
        });
        ll_thongtintk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ThongTinTKActivity.class));
            }
        });

        ll_lienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ThongTinLHActivity.class));

            }
        });



        return view;

    }

    private void thongtintk() {


    }

    private void Dangxuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");

        builder.setMessage("Bạn có muốn đăng xuất");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), DangNhap.class);
                getActivity().finishAffinity();
                if (!getActivity().isFinishing()) {
                    return;
                }
                startActivity(intent);
            }
        });
        builder.create().show();


    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == YOUR_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            firestore.collection("User").document(auth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            if (user != null) {
                                String img = user.getAnh();
                                if (img != null && !img.isEmpty()) {
                                    Glide.with(this).load(img).error(R.drawable.zara).into(img_anh);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    });
        }

    }
}
