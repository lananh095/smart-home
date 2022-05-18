package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Temp extends AppCompatActivity {
    private TextView humi, temp;
    private DatabaseReference databaseReference1; //humi and temp
    String hum,tem;
    FloatingActionButton mfab;
    Boolean show_hide = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        humi=findViewById(R.id.humi);
        temp=findViewById(R.id.temp);
        mfab = (FloatingActionButton) findViewById(R.id.fab);

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_hide == false) {
                    Intent intent_fab= new Intent(Temp.this, MainActivity.class);
                    startActivity(intent_fab);
                    finish();
                    show_hide = true;
                }else{
                    show_hide = false;
                }
            }
        });

        databaseReference1 = FirebaseDatabase.getInstance().getReference("DATA");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    hum=snapshot.child("HUMI").getValue().toString();
                    tem=snapshot.child("TEMP").getValue().toString();

                    humi.setText("HUMI:"+hum+"%");
                    temp.setText("TEMP:"+tem+"*C");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}