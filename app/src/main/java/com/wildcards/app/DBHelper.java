package com.wildcards.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Lenovo on 29.01.14.
 */
public class DBHelper extends SQLiteOpenHelper {
    final static String DEBUG_TAG="debug";
    final static String NativeLang="ru"; // родной язык - нужно вынести в преференсис
    final static String StudyLang="de";  // изучаемый язык

    final static int DB_VER = 1; // версия БД
    final static String DB_NAME = "words.db";
    final static String TABLE_NAME = NativeLang+"_"+StudyLang;
    final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                                "( _id INTEGER primary key autoincrement, "+
                                " NATIVE_ARTICLE TEXT," +
                                " NATIVE_WORD TEXT," +
                                " STUDY_ARTICLE TEXT," +
                                " STUDY_WORD TEXT," +
                                " TYPE TEXT," +
                                " BOX INTEGER," +
                                " STATUS INTEGER)";
    final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    final String DATA_FILE_NAME = NativeLang+"_"+StudyLang+".txt";

    Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG, "onCreate() создание и заполнение БД из файла "+DATA_FILE_NAME);
        db.execSQL(CREATE_TABLE);
        fillData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(DEBUG_TAG, "onUpgrade() вызвано");
    }

    private ArrayList<String> getData() {
        InputStream stream = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            stream = mContext.getAssets().open(DATA_FILE_NAME);
        }
        catch (IOException e) {
            Log.d(DEBUG_TAG,e.getMessage());
        }
// исправить работу с кодировкой
        DataInputStream dataStream = new DataInputStream(stream);
        String data = "";
        try {
            while( (data=dataStream.readLine()) != null ) {
                list.add(data);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

   private void fillData(SQLiteDatabase db){
        ArrayList<String> data = getData();
 //       for(String dt:data) Log.d(DEBUG_TAG,"item="+dt);

        if( db != null ){
            ContentValues values;

            for(String dat:data){
               Log.d(DEBUG_TAG,"dat="+dat);
               String vals[] = dat.split(",");
               values = new ContentValues();

               values.put("NATIVE_WORD", vals[0]);
               values.put("STUDY_WORD", vals[1]);
               values.put("TYPE", vals[2]);
               values.put("BOX", "1");
               values.put("STATUS", "0");

               db.insert(TABLE_NAME, null, values);
            }
        }
        else {
            Log.d(DEBUG_TAG,"db null");
        }
    }
}

