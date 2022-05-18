package com.example.smarthome;

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

public class LoginActivity extends AppCompatActivity {
    private Button btnlogin, btnregis;
    private EditText emailed,passed;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        emailed=findViewById(R.id.email);
        passed=findViewById(R.id.password);
        btnlogin=findViewById(R.id.btnlogin);
        btnregis=findViewById(R.id.btnregis);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void login() {
        String email;
        String pass;
        email=emailed.getText().toString();
        pass=passed.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Vui lòng nhập email!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Vui lòng nhập password!!",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Đăng nhập thành công!!",Toast.LENGTH_SHORT).show();
                    Intent  i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this,"Đăng nhập không thành công vui lòng kiểm tra lại email và password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register() {
        Intent  i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
    }
}