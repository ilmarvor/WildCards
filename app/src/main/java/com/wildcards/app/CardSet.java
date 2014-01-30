package com.wildcards.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

public class CardSet extends Card {
    Card set []; // set of cards to show

    public CardSet(SQLiteDatabase db){
        Cursor c = db.query(DBHelper.TABLE_NAME,
                             new String[] { "_id", "NATIVE_WORD", "STUDY_WORD"},
                            null, null, null, null, null);
        this.set = new Card[c.getCount()];
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int nativeColIndex = c.getColumnIndex("NATIVE_WORD");
            int studyColIndex = c.getColumnIndex("STUDY_WORD");
            int i=0;
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("debug",
                        "NATIVE_WORD = " + c.getString(nativeColIndex) +
                                ", id = " + c.getString(idColIndex)
                            );
                this.set[i++] = new Card(c.getString(nativeColIndex), c.getString(studyColIndex));
            } while (c.moveToNext());
        }
    }

    public Card getRandomCard(){
        int len = this.set.length;
        Random rand = new Random();
        int i = rand.nextInt(len);
        return this.set[i];
    }

    public int getLength(){
        return this.set.length;
    }
}
