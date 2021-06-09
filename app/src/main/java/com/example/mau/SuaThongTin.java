package com.example.mau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SuaThongTin extends AppCompatActivity {
    TextView eid;
    EditText eten,etuoi;
    Button sua;
    String url="https://60b594c0fe923b0017c843bf.mockapi.io/NhanVien";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editt);
        eid=findViewById(R.id.editID);
        eten=findViewById(R.id.emailDK);
        etuoi=findViewById(R.id.passDK);
        Intent intent = getIntent();
        eid.setText(intent.getStringExtra("ma"));
        eten.setText(intent.getStringExtra("ten"));
        etuoi.setText(intent.getStringExtra("tuoi"));


        sua=findViewById(R.id.dangKi);
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutApi(url,eid.getText().toString().trim());
                Intent intent  = new Intent(getApplicationContext(),SingIn.class);
                startActivity(intent);
            }
        });

    }
    private void PutApi(String url,String id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + '/' + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SuaThongTin.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuaThongTin.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("name", eten.getText().toString());
                params.put("age", etuoi.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}