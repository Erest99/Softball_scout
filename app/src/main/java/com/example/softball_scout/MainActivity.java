package com.example.softball_scout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Button swing, look, ball, foul, hit, play, illegal, intentional, wild, release, send, reset, bpitcher, bhitter, dlr;
    boolean released,match_start,firstbase,secondbase,thirdbase;
    int order = 1;
    int outs;
    int pstrike;
    int pball;
    int inning;
    long start_time, release_time;
    String pitcher,hitter, hitterSide, pitcherSide, baseSituation, position, duration,event;
    Integer strike, balls;
    ArrayList<Record> records = new ArrayList<>();
    RecyclerView recyclerView;

    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swing = findViewById(R.id.b_swing);
        look = findViewById(R.id.b_look);
        ball = findViewById(R.id.b_ball);
        foul = findViewById(R.id.b_foul);
        hit = findViewById(R.id.b_hit);
        play = findViewById(R.id.b_play);
        illegal = findViewById(R.id.b_illegal);
        intentional = findViewById(R.id.b_walk);
        wild = findViewById(R.id.b_wild);
        release = findViewById(R.id.b_release);
        send = findViewById(R.id.b_send);
        reset = findViewById(R.id.b_new);
        recyclerView = findViewById(R.id.recyclerView);
        bpitcher = findViewById(R.id.b_pitcher);
        bhitter = findViewById(R.id.b_hitter);
        dlr = findViewById(R.id.b_dlr);

        myDB = new MyDatabaseHelper(this);

        match_start = false;

        released = false;
        allowButtons(released);

        SharedPreferences sharedPref = getApplication().getSharedPreferences("Scout",Context.MODE_PRIVATE);
        start_time = sharedPref.getLong("start",0);
        if(start_time>0)match_start = true;
        position = sharedPref.getString("position","0");
        duration = sharedPref.getString("duration","0");
        event = sharedPref.getString("event","");
        order = sharedPref.getInt("order",1);
        strike = sharedPref.getInt("strike",0);
        balls = sharedPref.getInt("ball",0);
        pball = sharedPref.getInt("pball",0);
        pstrike = sharedPref.getInt("pstrike",0);
        outs = sharedPref.getInt("outs",0);
        inning = sharedPref.getInt("inning",1);
        baseSituation = sharedPref.getString("basesituation","");
        pitcher = sharedPref.getString("pitcher","");
        pitcherSide = sharedPref.getString("pitcherside","");
        hitter = sharedPref.getString("hitter","");
        hitterSide = sharedPref.getString("hitterside","");
        firstbase = sharedPref.getBoolean("1B",false);
        secondbase = sharedPref.getBoolean("2B",false);
        thirdbase = sharedPref.getBoolean("3B",false);

        Bundle extras = getIntent().getExtras();
        Intent i = getIntent();
        if(extras!=null)
        {
            if(i.hasExtra("pitchername"))
            pitcher = extras.getString("pitchername");

            if(i.hasExtra("pitcherside"))
            pitcherSide = extras.getString("pitcherside");

            if(i.hasExtra("hittername"))
            hitter = extras.getString("hittername");

            if(i.hasExtra("hitterside"))
            hitterSide = extras.getString("hitterside");

            if(i.hasExtra("1B"))
            {
                firstbase = extras.getBoolean("1B");
                secondbase = extras.getBoolean("2B");
                thirdbase = extras.getBoolean("3B");
                outs = extras.getInt("out");
                getBaseState(firstbase,secondbase,thirdbase);
                Record record = new Record("Pitch "+ order,position, duration,event,strike,balls,pstrike,pball,outs,inning,baseSituation,pitcher,pitcherSide,hitter,hitterSide);
                myDB.addItem(record,MainActivity.this);
                records.add(record);
                order++;
                refresh();
                released = false;
                allowButtons(released);
                if(extras.getBoolean("newHitter"))askForHitter();
                saveData();
            }

            if(i.hasExtra("strike"))
            {
                strike = extras.getInt("strike");
                balls = extras.getInt("ball");
                inning = extras.getInt("inning");
                outs = extras.getInt("out");
                saveData();
            }

        }


        records = storeData();
        records.add(0,new Record(0,"name","position","duration","event",0,0,0,0,0,1,"base situation","pitcher name","pitcher side","hitter name","hitter side"));
        refresh();

        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //zapni timer
                if(!match_start)
                {
                    start_time = System.nanoTime()/1000000000;
                    match_start = true;
                }
                release_time = System.nanoTime()/1000000000;
                released = true;
                allowButtons(released);
            }
        });

        swing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "strike swinging";
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                if(strike<2)strike++;
                else
                {
                    strike=0;
                    balls = 0;
                    addOut();
                    askForHitter();
                }
                Record record = new Record("Pitch "+ order,position, duration,event,strike,balls,pstrike,pball,outs,inning,baseSituation,pitcher,pitcherSide,hitter,hitterSide);
                myDB.addItem(record,MainActivity.this);
                records.add(record);
                order++;
                refresh();
                released = false;
                allowButtons(released);
                saveData();
            }
        });

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "strike looking";
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                if(strike<2)strike++;
                else
                {
                    strike=0;
                    balls = 0;
                    addOut();
                    askForHitter();
                }
                Record record = new Record("Pitch "+ order,position, duration,event,strike,balls,pstrike,pball,outs,inning,baseSituation,pitcher,pitcherSide,hitter,hitterSide);
                myDB.addItem(record,MainActivity.this);
                records.add(record);
                order++;
                refresh();
                released = false;
                allowButtons(released);
                saveData();
            }
        });

        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "ball";
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                if(balls<3)
                {
                    balls++;
                    Record record = new Record("Pitch "+ order,position, duration,event,strike,balls,pstrike,pball,outs,inning,baseSituation,pitcher,pitcherSide,hitter,hitterSide);
                    myDB.addItem(record,MainActivity.this);
                    records.add(record);
                    order++;
                    refresh();
                    released = false;
                    allowButtons(released);
                    saveData();
                }
                else
                {
                    strike=0;
                    balls = 0;
                    callBaseState(firstbase,secondbase,thirdbase,true);
                    saveData();
                }
            }
        });

        foul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "foul ball";
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                if(strike<2)strike++;
                Record record = new Record("Pitch "+ order,position, duration,event,strike,balls,pstrike,pball,outs,inning,baseSituation,pitcher,pitcherSide,hitter,hitterSide);
                myDB.addItem(record,MainActivity.this);
                records.add(record);
                order++;
                refresh();
                released = false;
                allowButtons(released);
                saveData();
            }
        });

        intentional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "intentional walk";
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                callBaseState(firstbase,secondbase,thirdbase,true);
                strike=0;
                balls = 0;
                saveData();
            }
        });

        illegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "illegal pitch";
                boolean hitr = false;
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                if(balls<3)balls++;
                else
                {
                    strike=0;
                    balls = 0;
                    hitr = true;
                }
                callBaseState(firstbase,secondbase,thirdbase,hitr);
                saveData();
            }
        });

        wild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "wild pitch/passed ball";
                boolean hitr = false;
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                if(balls<3)balls++;
                else
                {
                    strike=0;
                    balls = 0;
                    hitr = true;

                }
                callBaseState(firstbase,secondbase,thirdbase,hitr);
                saveData();
            }
        });

        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "hit by pitch";
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                callBaseState(firstbase,secondbase,thirdbase,true);
                strike=0;
                balls = 0;
                saveData();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vypni timer
                event = "in play";
                Long pos = System.nanoTime()/1000000000 - start_time;
                Long dur = System.nanoTime()/1000000000 - release_time;
                position = toTime(pos);
                duration = toTime(dur);
                pball = balls;
                pstrike = strike;
                callBaseState(firstbase,secondbase,thirdbase,true);
                strike=0;
                balls = 0;
                saveData();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
                Uri file = Uri.fromFile(new File(sdcard+"/Scout"));
                createFile(file,"Record" + Calendar.getInstance().getTime() + ".csv");

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.deleteAllData();
                records = new ArrayList<>();
                storeData();
                records.add(0,new Record(0,"name","postion","duration","event",0,0,0,0,0,1,"base situation","pitcher name","pitcher side","hitter name","hitter side"));
                order = 1;
                strike = 0;
                balls = 0;
                pball = 0;
                pstrike = 0;
                inning = 1;
                outs = 0;
                pitcher = "";
                pitcherSide = "";
                hitter = "";
                hitterSide = "";
                firstbase = false;
                secondbase = false;
                thirdbase = false;
                baseSituation = "";
                start_time = System.nanoTime()/1000000000;
                SharedPreferences sharedPref = getApplication().getSharedPreferences("Scout", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong("start", start_time);
                editor.putInt("order",order);
                editor.putInt("strike", strike);
                editor.putInt("ball",balls);
                editor.putInt("pball", pball);
                editor.putInt("pstrike",pstrike);
                editor.putInt("outs", outs);
                editor.putInt("inning",inning);
                editor.putString("pitcher","");
                editor.putString("pitcherside","");
                editor.putString("hitter","");
                editor.putString("hitterside","");
                editor.putBoolean("1B",false);
                editor.putBoolean("2B",false);
                editor.putBoolean("3B",false);
                editor.apply();
                Intent i = getIntent().replaceExtras(new Bundle());
                refresh();



            }
        });

        bpitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
                Intent intent = new Intent(MainActivity.this, Pitcher.class);
                startActivity(intent);

            }
        });

        bhitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(MainActivity.this, Hitter.class);
                startActivity(intent);

            }

        });

        dlr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(MainActivity.this, DeleteLastRow.class);
                startActivity(intent);
            }
        });


    }

    void allowButtons(boolean released)
    {
            release.setEnabled(!released);
            swing.setEnabled(released);
            look.setEnabled(released);
            ball.setEnabled(released);
            foul.setEnabled(released);
            hit.setEnabled(released);
            play.setEnabled(released);
            illegal.setEnabled(released);
            intentional.setEnabled(released);
            wild.setEnabled(released);

    }

    void addOut()
    {
        outs++;
        if(outs>2)
        {
            inning++;
            outs = 0;
            baseSituation = "";
            firstbase = false;
            secondbase = false;
            thirdbase = false;
            saveData();
        }
    }


    void refresh()
    {
        ArrayList<Record> copy = new ArrayList<>(records);
        Record record = copy.get(0);
        copy.remove(0);
        Collections.reverse(copy);
        copy.add(0,record);
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,copy);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    String toTime(Long time)
    {
        Long hours = time/3600;
        Long minutes = (time-hours*3600)/60;
        Long seconds = (time-hours*3600-minutes*60);
        return hours+"h :" +minutes + "m :"+ seconds+"s";
    }

    ArrayList<Record> storeData()
    {
        ArrayList<Record> loaded = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
            while(cursor.moveToNext())
            {
                Record record = new Record(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getInt(9),cursor.getInt(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15));
                loaded.add(record);
            }


        return loaded;
    }

    void saveData()
    {
        SharedPreferences sharedPref = this.getSharedPreferences("Scout", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("start", start_time);
        editor.putString("position",position);
        editor.putString("duration",duration);
        editor.putString("event",event);
        editor.putInt("order",order);
        editor.putInt("strike",strike);
        editor.putInt("ball",balls);
        editor.putInt("pball", pball);
        editor.putInt("pstrike",pstrike);
        editor.putInt("outs", outs);
        editor.putInt("inning",inning);
        editor.putString("basesituation",baseSituation); //TODO vymyslet
        editor.putString("pitcher",pitcher);
        editor.putString("pitcherside",pitcherSide);
        editor.putString("hitter",hitter);
        editor.putString("hitterside",hitterSide);
        editor.putBoolean("1B",firstbase);
        editor.putBoolean("2B",secondbase);
        editor.putBoolean("3B",thirdbase);
        editor.apply();



    }

    private void createFile(Uri pickerInitialUri, String filename) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_TITLE, filename);

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                alterDocument(uri,"Záznam");
            }
        }
    }

    private void alterDocument(Uri uri,String title) {
        try {
            ParcelFileDescriptor pfd = MainActivity.this.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());



            fileOutputStream.write((title+"\n").getBytes());

            for (Record i: records)
            {
                fileOutputStream.write((i.getId().toString() + ",").getBytes());
                fileOutputStream.write((i.getName() + ",").getBytes());
                fileOutputStream.write((i.getPosition() + ",").getBytes());
                fileOutputStream.write((i.getDuration() + ",").getBytes());
                fileOutputStream.write((i.getAction() + ",").getBytes());
                fileOutputStream.write((i.getState() + ",").getBytes());
                fileOutputStream.write((i.getOut() + ",").getBytes());
                fileOutputStream.write((i.getInning() + ",").getBytes());
                fileOutputStream.write((i.getPstate() + ",").getBytes());
                fileOutputStream.write((i.getPitcherName() + ",").getBytes());
                fileOutputStream.write((i.getPitcherSide() + ",").getBytes());
                fileOutputStream.write((i.getHitterName() + ",").getBytes());
                fileOutputStream.write((i.getHitterSide() + "\n").getBytes());

            }
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendEmail(uri);
    }

    void sendEmail(Uri uri)
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"jaroslavmaron9@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Match record from: " + new Date());
        i.putExtra(Intent.EXTRA_TEXT   , "Match record from: " + new Date());
        // the attachment
        i .putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    void callBaseState(boolean B1, boolean B2, boolean B3, boolean newHitter)
    {
        saveData();
        Intent intent = new Intent(MainActivity.this, Basestate.class);
        intent.putExtra("1B",B1);
        intent.putExtra("2B",B2);
        intent.putExtra("3B",B3);
        intent.putExtra("out",outs);
        intent.putExtra("newHitter",newHitter);
        startActivity(intent);
    }

    void getBaseState(boolean B1, boolean B2, boolean B3)
    {
        baseSituation = "";
        if(B3)baseSituation += "B3 ";
        if(B2)baseSituation += "B2 ";
        if(B1)baseSituation += "B1";
    }

    void askForHitter()
    {
        saveData();
        Intent intent = new Intent(MainActivity.this, Hitter.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

}