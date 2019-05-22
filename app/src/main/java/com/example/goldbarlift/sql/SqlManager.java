package com.example.goldbarlift.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.goldbarlift.pubcrawl.Pubcrawl;

public class SqlManager extends SQLiteOpenHelper {

    private static final String TAG = "SQLMANAGER";
    private static final String TABLE_NAME = "event_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "TAG";
    private static final String COL3 = "ADRESSE";
    private static final String COL4 = "MEETING";



    public SqlManager(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Pubcrawl item){
        //Convert Item to uploadable String
        String converted = this.convertToString(item);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, converted);

        Log.d(TAG, "addData: Adding '" + item.getId() + "' to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //If -1 then no item was uploaded
        if(result == -1){
            return false;
        }

        return true;

    }

    private String convertToString(Pubcrawl item){

        return "test";
    }
}
