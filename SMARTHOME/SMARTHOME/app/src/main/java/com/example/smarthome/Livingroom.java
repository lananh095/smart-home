package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Livingroom extends AppCompatActivity {
    private Button btnOn1, btnOff1, btnOn2, btnOff2, btnOn3, btnOff3, btnOn4, btnOff4 ;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private DatabaseReference databaseReference2; //device
    FloatingActionButton mfab;
    Boolean show_hide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livingroom);

        imageView1=findViewById(R.id.device1);
        btnOn1=findViewById(R.id.btnOn1);
        btnOff1=findViewById(R.id.btnOff1);

        imageView2=findViewById(R.id.device2);
        btnOn2=findViewById(R.id.btnOn2);
        btnOff2=findViewById(R.id.btnOff2);

        imageView3=findViewById(R.id.device3);
        btnOn3=findViewById(R.id.btnOn3);
        btnOff3=findViewById(R.id.btnOff3);

        imageView4=findViewById(R.id.device4);
        btnOn4=findViewById(R.id.btnOn4);
        btnOff4=findViewById(R.id.btnOff4);

        imageView1.setImageResource(R.drawable.light_off);
        imageView2.setImageResource(R.drawable.light_off);
        imageView3.setImageResource(R.drawable.light_off);
        imageView4.setImageResource(R.drawable.fan_off);

        databaseReference2 = FirebaseDatabase.getInstance().getReference("CONTROL");

        mfab = (FloatingActionButton) findViewById(R.id.fab);

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_hide == false) {
                    Intent intent_fab= new Intent(Livingroom.this, MainActivity.class);
                    startActivity(intent_fab);
                    finish();
                    show_hide = true;
                }else{
                    show_hide = false;
                }
            }
        });


        btnOff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 1").setValue(0);
                Toast.makeText(v.getContext(),"DEVICE 1 IS OFF",Toast.LENGTH_SHORT).show();
                imageView1.setImageResource(R.drawable.light_off);
            }
        });
        btnOn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 1").setValue(1);
                Toast.makeText(v.getContext(),"DEVICE 1 IS ON",Toast.LENGTH_SHORT).show();
                imageView1.setImageResource(R.drawable.light_on);
            }
        });
        btnOff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 2").setValue(0);
                Toast.makeText(v.getContext(),"DEVICE 2 IS OFF",Toast.LENGTH_SHORT).show();
                imageView2.setImageResource(R.drawable.light_off);
            }
        });
        btnOn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 2").setValue(1);
                Toast.makeText(v.getContext(), "DEVICE 2 IS ON", Toast.LENGTH_SHORT).show();
                imageView2.setImageResource(R.drawable.light_on);
            }
        });
        btnOff3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 3").setValue(0);
                Toast.makeText(v.getContext(),"DEVICE 3 IS CLOSE",Toast.LENGTH_SHORT).show();
                imageView3.setImageResource(R.drawable.light_off);
            }
        });
        btnOn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 3").setValue(1);
                Toast.makeText(v.getContext(),"DEVICE 3 IS OPEN",Toast.LENGTH_SHORT).show();
                imageView3.setImageResource(R.drawable.light_on);
            }
        });
        btnOff4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 4").setValue(0);
                Toast.makeText(v.getContext(),"DEVICE 4 IS CLOSE",Toast.LENGTH_SHORT).show();
                imageView4.setImageResource(R.drawable.fan_off);
            }
        });
        btnOn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.child("DEVICE 4").setValue(1);
                Toast.makeText(v.getContext(), "DEVICE 4 IS OPEN", Toast.LENGTH_SHORT).show();
                imageView4.setImageResource(R.drawable.fan_on);
            }
        });
    }
}