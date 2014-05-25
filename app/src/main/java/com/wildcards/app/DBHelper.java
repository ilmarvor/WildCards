package com.wildcards.app;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by YuraZ on 29.01.14.
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
                                " DECK INTEGER," +
                                " STATUS INTEGER)";
    final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    final String DATA_FILE_NAME_XML = NativeLang+"_"+StudyLang;

    Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        fillDataXML(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(DEBUG_TAG, "onUpgrade() для БД вызвано, но пока не придумано =)");
    }

    private void fillDataXML(SQLiteDatabase db){
        ArrayList<Card> data = getDataXML();
        if (data.isEmpty())
            Log.d(DEBUG_TAG, "ВНИМАНИЕ!!! БД не заполнена!");
//               for(Card dt:data) Log.d(DEBUG_TAG,"item="+dt.ForeignWord);

        if( db != null ){
            ContentValues values;
            int i=0;
            for(Card card:data){
                values = new ContentValues();
                values.put("NATIVE_ARTICLE", card.nativeArticle);
                values.put("NATIVE_WORD", card.nativeWord);
                values.put("FOREIGN_ARTICLE", card.foreignArticle);
                values.put("FOREIGN_WORD", card.foreignWord);
                values.put("TYPE", card.type);
                values.put("DECK", i++<Cards.DefaultDeckSize?"1":"0"); // первых DefaultDeckSize помещаю в первую колоду, остальные - в нулевую
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
            // TODO: переделать на использование DATA_FILE_NAME_XML!
            XmlPullParser xpp = mContext.getResources().getXml(R.xml.ru_de);
            while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT){
                if(xpp.getEventType()==XmlPullParser.START_TAG && xpp.getDepth()>1){
                    card = new Card();
                    for (int i = 0; i < xpp.getAttributeCount(); i++)
                        fillValueByAttrName(card, xpp.getAttributeName(i), xpp.getAttributeValue(i));
                    list.add(card);
                }
                xpp.next();
            }
        return list;
        } catch(IOException e){e.printStackTrace(); return null;}
          catch(XmlPullParserException e){e.printStackTrace();return null;}
    }

    // используется при парсинге файла для записи в БД, но мало ли еще куда понадобится
    private void fillValueByAttrName(Card card, String attrName, String attrValue){
        if (!TextUtils.isEmpty(attrValue)){
            if ("W".equalsIgnoreCase(attrName))
                card.nativeWord=attrValue;
            else if ("A".equalsIgnoreCase(attrName))
                card.nativeArticle=attrValue;
            else if ("FA".equalsIgnoreCase(attrName))
                card.foreignArticle=attrValue;
            else if ("FW".equalsIgnoreCase(attrName))
                card.foreignWord=attrValue;
            else if ("P".equalsIgnoreCase(attrName))
                card.type=attrValue;
        }
    }

}

