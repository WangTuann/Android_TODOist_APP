package com.example.myapp_todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {

    private MainActivity context;
    private  int layout;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    private List<CongViec> congViecList;


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
    private class ViewHolder{
        TextView txtTen;
        ImageView imgDelet,imgEdit;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        
        if (view==null){
            holder =new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtTen   =(TextView) view.findViewById(R.id.textviewTen);
            holder.imgDelet =(ImageView) view.findViewById(R.id.imgdelete);
            holder.imgEdit  =(ImageView) view.findViewById(R.id.imgdEditt);
            //goi lai anh xa
            view.setTag(holder);
        }
        else {
            holder=(ViewHolder) view.getTag();
        }
        final CongViec congViec=congViecList.get(position);
        holder.txtTen.setText(congViec.getTenCV());

        //bat su kien xoa&sua
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Sua" + congViec.getTenCV(),Toast.LENGTH_LONG).show();
                context.DialogSuaCV(congViec.getTenCV(),congViec.getIdCV());
            }
        });
        holder.imgDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            context.DialogXoaCV(congViec.getTenCV(),congViec.getIdCV());
            }
        });
        return view;
    }
}
