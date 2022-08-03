package com.example.softball_scout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DeleteLastRow extends AppCompatActivity {

    EditText inning;
    Button confirm;
    Spinner strike, ball, out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_last_row);

        strike = findViewById(R.id.s_strike);
        ball = findViewById(R.id.s_ball);
        out = findViewById(R.id.s_out);
        inning = findViewById(R.id.et_inning);
        confirm = findViewById(R.id.b_drconfirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(getApplicationContext());
                myDB.deleteLastRow();
                Intent intent = new Intent(DeleteLastRow.this, MainActivity.class);
                intent.putExtra("strike",Integer.valueOf(strike.getSelectedItem().toString().trim()));
                intent.putExtra("ball",Integer.valueOf(ball.getSelectedItem().toString().trim()));
                if(inning.getText().toString().isEmpty()) intent.putExtra("inning",1);
                else intent.putExtra("inning",Integer.valueOf(inning.getText().toString().trim()));
                intent.putExtra("out",Integer.valueOf(out.getSelectedItem().toString().trim()));
                startActivity(intent);
            }
        });
    }
}