package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static int gauge;
    FloatingActionButton mfab, mfab_livingroom, mfab_Temp, mfab_gas;
    Boolean show_hide = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfab = (FloatingActionButton) findViewById(R.id.fab);
        mfab_livingroom = (FloatingActionButton) findViewById(R.id.fab_livingroom);
        mfab_Temp = (FloatingActionButton) findViewById(R.id.fab_Temp);
        mfab_gas =(FloatingActionButton) findViewById(R.id.fab_Gar);

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_hide == false) {
                    show_fab_layout();
                    show_hide = true;
                }else{
                    hide_fab_layout();
                    show_hide = false;
                }
            }
        });
    }

    private void show_fab_layout(){
        mfab_livingroom.show();
        mfab_livingroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_livingroom = new Intent(MainActivity.this, Livingroom.class);
                startActivity(intent_livingroom);
                finish();
            }
        });
        mfab_Temp.show();
        mfab_Temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Temp = new Intent(MainActivity.this, Temp.class);
                startActivity(intent_Temp);
                finish();
            }
        });
        mfab_gas.show();
        mfab_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_gas = new Intent(MainActivity.this, activity_gar.class);
                startActivity(intent_gas);
                finish();
            }
        });
    }

    private void hide_fab_layout(){
        mfab_livingroom.hide();
        mfab_Temp.hide();
        mfab_gas.hide();
    }
}