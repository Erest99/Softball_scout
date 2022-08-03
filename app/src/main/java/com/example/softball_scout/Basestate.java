package com.example.softball_scout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Basestate extends AppCompatActivity {

    Button B1,B2,B3, SAFE,OUT,DOUBLE,TRIPLE;
    boolean b1,b2,b3,p1,p2,p3,newHitter;
    int out;
    ConstraintLayout L1,L2,L3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        B1 = findViewById(R.id.base1);
        B2 = findViewById(R.id.base2);
        B3 = findViewById(R.id.base3);
        L1 = findViewById(R.id.l1);
        L2 = findViewById(R.id.l2);
        L3 = findViewById(R.id.l3);
        SAFE = findViewById(R.id.b_safe);
        OUT = findViewById(R.id.b_out);
        DOUBLE = findViewById(R.id.b_double);
        TRIPLE = findViewById(R.id.b_triple);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            b1 = extras.getBoolean("1B",false);
            b2 = extras.getBoolean("2B",false);
            b3 = extras.getBoolean("3B",false);

            p1 = extras.getBoolean("1B",false);
            p2 = extras.getBoolean("2B",false);
            p3 = extras.getBoolean("3B",false);

            newHitter = extras.getBoolean("newHitter",true);
        }

        out = extras.getInt("out");

        if(b1)L1.setBackgroundColor(getResources().getColor(R.color.red));
        else L1.setBackgroundColor(getResources().getColor(R.color.lime));
        if(b2)L2.setBackgroundColor(getResources().getColor(R.color.red));
        else L2.setBackgroundColor(getResources().getColor(R.color.lime));
        if(b3)L3.setBackgroundColor(getResources().getColor(R.color.red));
        else L3.setBackgroundColor(getResources().getColor(R.color.lime));

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b1){
                    L1.setBackgroundColor(getResources().getColor(R.color.lime));
                    b1 = false;
                }
                else
                {
                    L1.setBackgroundColor(getResources().getColor(R.color.red));
                    b1 = true;
                }
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b2){
                    L2.setBackgroundColor(getResources().getColor(R.color.lime));
                    b2 = false;
                }
                else
                {
                    L2.setBackgroundColor(getResources().getColor(R.color.red));
                    b2 = true;
                }
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b3){
                    L3.setBackgroundColor(getResources().getColor(R.color.lime));
                    b3 = false;
                }
                else
                {
                    L3.setBackgroundColor(getResources().getColor(R.color.red));
                    b3 = true;
                }
            }
        });

        SAFE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Basestate.this, MainActivity.class);
                intent.putExtra("1B",b1);
                intent.putExtra("2B",b2);
                intent.putExtra("3B",b3);
                intent.putExtra("out",out);
                intent.putExtra("newHitter",newHitter);
                startActivity(intent);
            }
        });

        OUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Basestate.this, MainActivity.class);

                out++;
                if(out >= 3 ){
                    out =0;
                    b1 = false;
                    b2 = false;
                    b3 = false;
                }
                intent.putExtra("1B",b1);
                intent.putExtra("2B",b2);
                intent.putExtra("3B",b3);
                intent.putExtra("out",out);
                intent.putExtra("newHitter",newHitter);
                startActivity(intent);
            }
        });

        DOUBLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Basestate.this, MainActivity.class);

                out+=2;
                if(out >= 3 ){
                    out =0;
                    b1 = false;
                    b2 = false;
                    b3 = false;
                }
                intent.putExtra("1B",b1);
                intent.putExtra("2B",b2);
                intent.putExtra("3B",b3);
                intent.putExtra("out",out);
                intent.putExtra("newHitter",newHitter);
                startActivity(intent);
            }
        });

        TRIPLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Basestate.this, MainActivity.class);

                out+=3;
                if(out >= 3 ){
                    out =0;
                    b1 = false;
                    b2 = false;
                    b3 = false;
                }
                intent.putExtra("1B",b1);
                intent.putExtra("2B",b2);
                intent.putExtra("3B",b3);
                intent.putExtra("out",out);
                intent.putExtra("newHitter",newHitter);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Basestate.this, MainActivity.class);
        intent.putExtra("1B",p1);
        intent.putExtra("2B",p2);
        intent.putExtra("3B",p3);
        intent.putExtra("out",out);
        intent.putExtra("resetbs",false);
        intent.putExtra("newHitter",false);
        startActivity(intent);

    }
}