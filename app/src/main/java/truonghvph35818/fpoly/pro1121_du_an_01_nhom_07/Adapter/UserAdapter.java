package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.User;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    private final Context context;
    private final List<User> list;
    FirebaseFirestore db;

    public UserAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_nhan_vien, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = list.get(position);

        Glide.with(context).load(list.get(position).getAnh()).error(R.drawable.anh_sp).into(holder.img_anh);//ảnh
        holder.tv_Ten.setText( list.get(position).getHoTen());
        holder.tv_Email.setText(list.get(position).getEmail());
//        holder.tv_TrangThai.setText(user.getNgaySinh());

        if (user.getTrangThai() == 0) {
            holder.tv_TrangThai.setText("Không hoạt động");
            holder.tv_TrangThai.setTextColor(Color.RED);
        } else if (user.getTrangThai() == 1) {
            holder.tv_TrangThai.setText("Đang hoạt động");
            holder.tv_TrangThai.setTextColor(Color.BLUE);
        }
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.canh_bao);
                builder.setTitle("Cảnh báo !");
                builder.setMessage("Bạn có chắc chắn muốn xóa dữ liệu của nhân viên " + user.getHoTen() + " không ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("user").document(list.get(position).getMaUser()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()){
                                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.canh_bao);
                builder.setTitle("Cánh bảo !");
                builder.setMessage("Bạn có muốn dừng hoạt động nhân viên " + user.getHoTen() + " không ?");
                builder.setPositiveButton("Tắt trạng thái", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeTT(0,list.get(position));
                    }
                });
                builder.setNegativeButton("Mở trạng thái", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeTT(1,list.get(position));
                    }
                });
                builder.create().show();

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }
    private void changeTT(int i, User user) {
        user.setTrangThai(i);
        db.collection("user").document(user.getMaUser()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_Ten, tv_Email, tv_TrangThai;
        ImageView img_delete, img_anh;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Ten = itemView.findViewById(R.id.tv_tenNV);
            tv_Email = itemView.findViewById(R.id.tv_emailNV);
            tv_TrangThai = itemView.findViewById(R.id.tv_trangThai);
            img_delete = itemView.findViewById(R.id.delete_nv);
            img_anh = itemView.findViewById(R.id.img_anh);
        }
    }

}
