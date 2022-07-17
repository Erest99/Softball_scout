package com.example.softball_scout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Scout.db";
    private static int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "records";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_ACTION = "event";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE "+ TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_POSITION + " TEXT, " +
                COLUMN_DURATION + " TEXT, " +
                COLUMN_ACTION + " TEXT);";



        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addItem(Record record, Context context)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,record.getName());
        cv.put(COLUMN_POSITION,record.getPosition());
        cv.put(COLUMN_DURATION,record.getDuration());
        cv.put(COLUMN_ACTION,record.getAction());

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1)
        {
            Toast.makeText(context,context.getResources().getString(R.string.save_failed),Toast.LENGTH_SHORT).show();
            Log.e("db error","failed to insert " + result + " into database");
        }
        else
        {
            Toast.makeText(context,context.getResources().getString(R.string.save_succes),Toast.LENGTH_SHORT).show();
        }

    }

    public void updateData(Record record, Context context )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,record.getName());
        cv.put(COLUMN_POSITION,record.getPosition());
        cv.put(COLUMN_DURATION,record.getDuration());
        cv.put(COLUMN_ACTION,record.getAction());

        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{record.getId().toString()});
        if (result ==-1)
        {
            Toast.makeText(context,context.getResources().getString(R.string.save_failed), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,context.getResources().getString(R.string.save_succes), Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }



    public void deleteOneRow(String row_id,Context context)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if (result == -1)
        {
            Toast.makeText(context,context.getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,context.getResources().getString(R.string.delete_succes), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


    public Cursor findById(Integer id)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + String.valueOf(id);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;

    }

}
