package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter.SanPhamAdapter;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.SanPhamDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Frag_QLSanPham extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    List<SanPhamDTO> list;
    FirebaseFirestore db ;
    SanPhamAdapter sanPhamAdapter;
    Context context;




    public Frag_QLSanPham(int contentLayoutId, List<SanPhamDTO> list, Context context) {
        super(contentLayoutId);
        this.list = list;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_san_pham, container, false);

        recyclerView = view.findViewById(R.id.re_sanPham);
        floatingActionButton = view.findViewById(R.id.float_sanPham);
        db = FirebaseFirestore.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getContext(),list);
        recyclerView.setAdapter(sanPhamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                them();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_san_pham, null, false);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void getList() {
        db.collection("sanPham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isComplete()){
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (QueryDocumentSnapshot dc : task.getResult()){
                    list.add(dc.toObject(SanPhamDTO.class));
                    sanPhamAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
