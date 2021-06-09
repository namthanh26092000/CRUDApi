package com.example.mau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangKi extends AppCompatActivity {
    EditText name,emailDK,passDK;
    Button dangKi;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.tenDK);
        emailDK=findViewById(R.id.emailDK);
        passDK=findViewById(R.id.passDK);
        dangKi=findViewById(R.id.dangKi);
        dangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e,p;
                e=emailDK.getText().toString();
                p=passDK.getText().toString();
                if(TextUtils.isEmpty(e)){
                    Toast.makeText(getApplicationContext(),"Chưa nhập mật Email!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(p)){
                    Toast.makeText(getApplicationContext(),"Chưa nhập mật Password!",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Đăng kí thành công!",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(DangKi.this,MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Đăng kí không thành công!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });
    }
}