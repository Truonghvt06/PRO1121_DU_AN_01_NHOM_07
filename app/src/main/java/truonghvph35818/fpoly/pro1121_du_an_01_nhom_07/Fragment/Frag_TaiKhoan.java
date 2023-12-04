package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.taiKhoan.DangNhap;

public class Frag_TaiKhoan extends Fragment {
    LinearLayout ll_hotrokh, ll_dangxuat, ll_thongtintk, ll_lichsudonhang;
    FirebaseFirestore db;

    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_taikhoan, container, false);
        ll_dangxuat = view.findViewById(R.id.ll_Dangxuat);
        ll_thongtintk = view.findViewById(R.id.ll_thongtintk);
        ll_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dangxuat();
            }
        });
        ll_thongtintk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thongtintk();
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


}
