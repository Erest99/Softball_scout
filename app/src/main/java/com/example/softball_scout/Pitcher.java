package com.example.softball_scout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Pitcher extends AppCompatActivity {

    Button confirm;
    EditText name,side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pitcher_layout);

        confirm = findViewById(R.id.b_confirmp);
        name = findViewById(R.id.et_pn);
        side = findViewById(R.id.et_ps);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pitcher.this, MainActivity.class);
                intent.putExtra("pitchername",name.getText().toString().trim());
                intent.putExtra("pitcherside",side.getText().toString().trim());
                startActivity(intent);
            }
        });

    }
}