package com.example.softball_scout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Hitter extends AppCompatActivity {

    Button confirm;
    EditText name,side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitter_layout);

        confirm = findViewById(R.id.b_confirmh);
        name = findViewById(R.id.et_hn);
        side = findViewById(R.id.et_hs);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitter.this, MainActivity.class);
                intent.putExtra("hittername",name.getText().toString().trim());
                intent.putExtra("hitterside",side.getText().toString().trim());
                startActivity(intent);
            }
        });

    }
}