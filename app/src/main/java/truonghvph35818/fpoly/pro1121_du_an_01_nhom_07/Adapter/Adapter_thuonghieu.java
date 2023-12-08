package truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.DTO.HangDTO;
import truonghvph35818.fpoly.pro1121_du_an_01_nhom_07.R;

public class Adapter_thuonghieu extends BaseAdapter {
    List<HangDTO> list;
    Context context;

    public Adapter_thuonghieu(List<HangDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view1 = inflater.inflate(R.layout.item_thuonghieu,parent,false);
        TextView ten = view1.findViewById(R.id.tv_ten_thuonghieu);
        ten.setText(list.get(i).getTenHang());
        return view1;
    }
}
