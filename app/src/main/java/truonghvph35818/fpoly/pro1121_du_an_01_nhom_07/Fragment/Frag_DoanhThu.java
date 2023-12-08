package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_DoanhThu extends Fragment {
    Button btn_tuNgay, btn_denNgay, btn_timKiem;
    EditText ed_tuNgay, ed_denNgay;
    TextView tv_doanhThu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_doanh_thu, container, false);
        ed_tuNgay = view.findViewById(R.id.ed_TuNgay);
        ed_denNgay = view.findViewById(R.id.ed_DenNgay);
        btn_tuNgay = view.findViewById(R.id.btn_TuNgay);
        btn_denNgay = view.findViewById(R.id.btn_DenNgay);
        btn_timKiem = view.findViewById(R.id.btn_TimKiem);

        tv_doanhThu = view.findViewById(R.id.tv_DoanhThu);

        Calendar calendar = Calendar.getInstance();

        btn_tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if (dayOfMonth < 10) {
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if ((month + 1) < 10) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf(month + 1);
                        }
                        ed_tuNgay.setText(year + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONDAY),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        btn_denNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if (dayOfMonth < 10) {
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }
                        if ((month + 1) < 10) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf(month + 1);
                        }
                        ed_denNgay.setText(year + "/" + thang + "/" + ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONDAY),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        btn_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String ngayStart = ed_tuNgay.getText().toString();
                String ngayEnd = ed_denNgay.getText().toString();

                db.collection("DonHangDaDuyet")
                        .whereGreaterThanOrEqualTo("ngayMua", ngayStart)
                        .whereLessThanOrEqualTo("ngayMua", ngayEnd)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isComplete()) {
                                    Long tong = 0l;
                                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                        Long price = snapshot.getLong("giaDon");
                                        tong += price;
                                    }
                                    tv_doanhThu.setText(tong + " VND");
                                } else {
                                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return view;
    }
}
