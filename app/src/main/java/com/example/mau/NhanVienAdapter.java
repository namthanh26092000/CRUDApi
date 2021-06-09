package com.example.mau;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class NhanVienAdapter extends BaseAdapter {
    List<Nhanvien> list;
    Context context;
    int layout;
    String url="https://60b594c0fe923b0017c843bf.mockapi.io/NhanVien";

    public NhanVienAdapter(List<Nhanvien> list, Context context, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView ma,ten,tuoi;
        Button sua,xoa;
        //Thêm DL
        convertView= LayoutInflater.from(context).inflate(layout,parent,false);
        ma=convertView.findViewById(R.id.manv);
        ten=convertView.findViewById(R.id.tennv);
        tuoi=convertView.findViewById(R.id.tuoinv);
        ma.setText(list.get(position).getId());
        ten.setText(list.get(position).getName());
        tuoi.setText(list.get(position).getAge());

        //Sủa DL
        sua=convertView.findViewById(R.id.btnEdit);
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nhanvien nhanvien = list.get(position);
                Intent intent = new Intent(context,SuaThongTin.class);
                intent.putExtra("ma",nhanvien.getId());
                intent.putExtra("ten",nhanvien.getName());
                intent.putExtra("tuoi",nhanvien.getAge());
                context.startActivity(intent);
            }
        });

        //Xóa DL
        xoa=convertView.findViewById(R.id.btnDelete);
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteApi(url,ma.getText().toString().trim());
                Intent intent  = new Intent(context.getApplicationContext(),SingIn.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    //Xóa DL
    private void DeleteApi(String url,String id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context.getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
