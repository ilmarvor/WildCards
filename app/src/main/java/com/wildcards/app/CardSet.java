package com.wildcards.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

public class CardSet extends Card {
    Card set []; // set of cards to show

    // пока читаю всю таблицу в cardSet, для ускорения можно читать все изучаемые слова
    // и штук 50, которые скоро будут изучаться
    public CardSet(SQLiteDatabase db){
        Cursor c = db.query(DBHelper.TABLE_NAME,
                      new String[] { "_id", "NATIVE_ARTICLE", "NATIVE_WORD", "FOREIGN_ARTICLE", "FOREIGN_WORD"},
                 null, null, null, null, null);
        this.set = new Card[c.getCount()];
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int nativeArticleColIndex = c.getColumnIndex("NATIVE_ARTICLE");
            int nativeWordColIndex = c.getColumnIndex("NATIVE_WORD");
            int foreignArticleColIndex = c.getColumnIndex("FOREIGN_ARTICLE");
            int foreignWordColIndex = c.getColumnIndex("FOREIGN_WORD");
            int i=0;
            do {
                // получаем значения по номерам столбцов
                this.set[i++] = new Card(c.getString(nativeArticleColIndex), c.getString(nativeWordColIndex),
                        c.getString(foreignArticleColIndex), c.getString(foreignWordColIndex)
                 );
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
