package com.example.sqlite_first;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {

    private MainActivity context;
    private int Layout;
    private List<CongViec> congViecList;


    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        Layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class viewHolder{
        TextView txtname;
        ImageView imgDelete;
        ImageView imgedit;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder holder;
        if(convertView == null){
            holder = new viewHolder();
            LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutinflater.inflate(Layout, null);
            holder.txtname = (TextView) convertView.findViewById(R.id.tvTen);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgdelete);
//            holder.imgedit = (ImageView) convertView.findViewById(R.id.imgEdit);
            convertView.setTag(holder);
        }else{
            holder = (viewHolder) convertView.getTag();
        }
        final CongViec congViec = congViecList.get(position);
        holder.txtname.setText(congViec.getTieuDe());
        holder.txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.Sua_GhiChu(congViec.getID(), congViec.getTieuDe(), congViec.getNoiDung());
            }
        });
        //bat su kien xoa va sua
//        holder.imgedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.Sua_GhiChu(congViec.getID(), congViec.getTieuDe(), congViec.getNoiDung());
//            }
//        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.Xoa_GhiChu(congViec.getTieuDe(), congViec.getID());
            }
        });
        return convertView;
    }
}
