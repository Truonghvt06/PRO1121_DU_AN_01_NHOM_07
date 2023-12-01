package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.UUID;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.Adapter_Kichco;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.giohang;

public class ChiTietSPActivity extends AppCompatActivity {
    RecyclerView Rcy_list;
    SanPhamDTO sanPhamDTO = new SanPhamDTO();
    List<String> list_co;
    Button cong, tru, themgh, mua;
    EditText soluong;
    ImageView anh, back;

    TextView tenSP, gia, mota;
    int so = 0;
    String kichco = "";
    private Adapter_Kichco adapterKichco;
    FirebaseFirestore db;


    public void setKickco(String kichco) {
        this.kichco = kichco;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_spactivity);

        Intent intent = getIntent();
        String s = intent.getStringExtra("sanpham");


        Rcy_list = findViewById(R.id.rc_kichCo);
        cong = findViewById(R.id.bnt_cong_soluong);
        tru = findViewById(R.id.bnt_tru_soluong);
        themgh = findViewById(R.id.btn_themgh);
        mua = findViewById(R.id.btn_mua);
        anh = findViewById(R.id.imv_anh_sp_lon);
        back = findViewById(R.id.img_back_chi_tiet);
        tenSP = findViewById(R.id.tv_tensp_show);
        gia = findViewById(R.id.tv_giasp_show);
        mota = findViewById(R.id.tv_moTa);
        soluong =findViewById(R.id.edt_soluong_show);
        soluong.setText(so+"");

        themgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (so==0){
                    Toast.makeText(ChiTietSPActivity.this, "Bạn phải chọn ít nhất 1 sản phẩm ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (kichco.isEmpty()){
                    Toast.makeText(ChiTietSPActivity.this, "Hãy chọn kích cỡ", Toast.LENGTH_SHORT).show();
                    return;
                }
                themGio();
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinh("-");
            }
        });
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinh("+");
            }
        });

    }

    private void themGio() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String maGio = UUID.randomUUID()+"";
        db.collection("gioHang").document(maGio).
                set(new giohang(maGio, user.getUid(), sanPhamDTO.getMaSp(),kichco, Long.parseLong(so + "")))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(ChiTietSPActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChiTietSPActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void tinh(String dau) {
        if ("-".equals(dau)) {
            so -= 1;
            if (so == -1) {
                so = 0;
            }
        } else {
            so += 1;
        }
       soluong.setText(so + "");
    }


}