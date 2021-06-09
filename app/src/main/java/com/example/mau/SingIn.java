package com.example.mau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingIn extends AppCompatActivity {
    private List<Nhanvien> list = new ArrayList<>();
    private ListView listView;
    private NhanVienAdapter nhanVienAdapter;
    Button them;
    EditText id;
    EditText name;
    EditText age;
    String url="https://60b594c0fe923b0017c843bf.mockapi.io/NhanVien";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        //Lấy DL
        GetArrayJson(url);

        //Thêm DL
        them=findViewById(R.id.them);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostApi(url);
                Intent intent = new Intent(SingIn.this,SingIn.class);
                startActivity(intent);
            }
        });

    }

    //Lấy DL
    private void GetArrayJson(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++){
                    try{
                        JSONObject object = (JSONObject) response.get(i);
                        String id =object.getString("id");
                        String name =object.getString("name");
                        String age =object.getString("age");
                        Nhanvien nhanvien = new Nhanvien(id, name, age);
                        list.add(nhanvien);
                        nhanVienAdapter = new NhanVienAdapter(list,getApplicationContext(),R.layout.item);
                        listView = findViewById(R.id.listnv);
                        listView.setAdapter(nhanVienAdapter);
                    } catch (JSONException e ){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SingIn.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //Thêm DL
    private void PostApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SingIn.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SingIn.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                id=findViewById(R.id.nhapIDNV);
                name=findViewById(R.id.nhapTenNV);
                age=findViewById(R.id.nhapTuoiNV);
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id.getText().toString());
                params.put("name", name.getText().toString());
                params.put("age", age.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}