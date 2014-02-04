package com.wildcards.app;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
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
                                " FOREIGN_ARTICLE TEXT," +
                                " FOREIGN_WORD TEXT," +
                                " TYPE TEXT," +
                                " BOX INTEGER," +
                                " STATUS INTEGER)";
    final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    final String DATA_FILE_NAME = NativeLang+"_"+StudyLang+".txt";
    final String DATA_FILE_NAME_XML = NativeLang+"_"+StudyLang;

    Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG, "onCreate() создание и заполнение БД из файла "+DATA_FILE_NAME);
        db.execSQL(CREATE_TABLE);
        fillDataXML(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(DEBUG_TAG, "onUpgrade() вызвано");
    }

    private void fillDataXML(SQLiteDatabase db){
        ArrayList<Card> data = getDataXML();
//               for(Card dt:data) Log.d(DEBUG_TAG,"item="+dt.ForeignWord);

        if( db != null ){
            ContentValues values;

            for(Card card:data){
                Log.d(DEBUG_TAG,"card="+card.ForeignWord);
                values = new ContentValues();

                values.put("NATIVE_ARTICLE", card.NativeArticle);
                values.put("NATIVE_WORD", card.NativeWord);
                values.put("FOREIGN_ARTICLE", card.ForeignArticle);
                values.put("FOREIGN_WORD", card.ForeignWord);
                values.put("TYPE", card.Type);
                values.put("BOX", "1");
                values.put("STATUS", "0");

                db.insert(TABLE_NAME, null, values);
            }
        }
        else {
            Log.d(DEBUG_TAG,"db null");
        }

    }

    private ArrayList<Card> getDataXML(){
        ArrayList<Card> list = new ArrayList<Card>();
        Card card;
        try {
            XmlPullParser xpp = mContext.getResources().getXml(R.xml.ru_de); // переделать!
            Log.d(DEBUG_TAG, "файлик открыл");
            while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT){
                if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getDepth()>1){
                    card = new Card();
                    for (int i = 0; i < xpp.getAttributeCount(); i++){
                        Log.d(DEBUG_TAG, (xpp.getAttributeName(i)+ xpp.getAttributeValue(i)));
                        card.fillValueByAttrName(xpp.getAttributeName(i), xpp.getAttributeValue(i));
                    }
                    list.add(card);
                }
                xpp.next();
            }
        return list;
        } catch(IOException e){e.printStackTrace(); return null;}
          catch(XmlPullParserException e){e.printStackTrace();return null;}
    }

}

