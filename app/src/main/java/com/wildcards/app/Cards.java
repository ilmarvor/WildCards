package com.wildcards.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by YuraZ on 05.02.14.
 */
public class Cards {
    // пока все static - потом посмотрим
    // массив колод, первая с постоянным размером, нулевая - с запасом слов для первой
    private static ArrayList<Deck> decks;
    private SQLiteDatabase db;
    static final int DefaultDeckSize = 20; // Количество слов первой колоде, остальные создаются с таким количеством, но оно динамическое
    private static final int ZeroDeckSize = 100; // Количество слов в нулевой колоде
    private static final int DntShow = -1;
    private static Cards cards;
    static int current;
    private static int counter;
    static void init(){
        if (cards==null){
            cards = new Cards();
            current = 1;
            counter = 1; // Количество просмотренных карт
            decks = new ArrayList<Deck>();
            // В нулевой колоде лежат карты, которые скоро войдут в первую, когда закончатся - надо пополнять из БД
            decks.add(0, new Deck(ZeroDeckSize));
            decks.add(1, new Deck(DefaultDeckSize));
        }
    }

    static Deck getDeck(int deckNum){
            for (int i = decks.size(); i<=deckNum ; i++) // Добавляю колоды
                decks.add(new Deck(DefaultDeckSize));
            return decks.get(deckNum);
    }

    static Card getNext(){ // нету смысла допиливать, пока не будет алгоритма
        if (counter>=20)
            counter = 1;
        if (counter++%5 == 0)
            current = 2;
        else
            current = 1;
        return getDeck(current).getNextCard();
    }

    static Card getCurrent(){
        return getDeck(current).getCurrentCard();
    }
    static void boostCurrentCard(){
        Card movedCard = getDeck(current).removeCurrentCard();
        getDeck(current+1).addCard(movedCard);
        //movedCard.rightSide += String.valueOf(current+1);
        // TODO: записывать в БД (желательно асинхронно)

        ContentValues cv = new ContentValues();
        cv.put("DECK", current+1);
        cards.db.update(DBHelper.TABLE_NAME, cv, "_id = ?",
                new String[]{String.valueOf(movedCard.id)} // id Карты
        );

        // Если прокачиваем карту из первой колоды - нужно пополнить колоду из нулевой
        // Для поддержания стабильного количества
        if (current==1){
            getDeck(0).resetIndex();
            try{
                movedCard = getDeck(0).removeCurrentCard();
                getDeck(1).addCard(movedCard);
                // И сразу в БД обновим
                cv = new ContentValues();
                cv.put("DECK", 1);
                cards.db.update(DBHelper.TABLE_NAME, cv, "_id = ?",
                        new String[]{String.valueOf(movedCard.id)} // id Карты
                );
            }catch(Exception e){ // В нулевой колоде закончились карты нетокуда пополнять
                Log.d(DBHelper.DEBUG_TAG, " В нулевой колоде закончились карты нетокуда пополнять " );
            }
        }

    }

    static void loadFromDb(SQLiteDatabase db){
         init();
         cards.db=db; // Запоминаем БД, потом пригодится
         Cursor c = db.query(DBHelper.TABLE_NAME,
                     new String[] {"_id", "NATIVE_ARTICLE", "NATIVE_WORD", "FOREIGN_ARTICLE", "FOREIGN_WORD", "DECK", "TYPE"},
                  /* where */   null,
                   /* where */  null,
                  /*group by*/ null,
                 /* having */ null,
               /*order by */  "_id"
         );
         if (c.moveToFirst()) {
             int idColIndex = c.getColumnIndex("_id");
             int nativeArticleColIndex = c.getColumnIndex("NATIVE_ARTICLE");
             int nativeWordColIndex = c.getColumnIndex("NATIVE_WORD");
             int foreignArticleColIndex = c.getColumnIndex("FOREIGN_ARTICLE");
             int foreignWordColIndex = c.getColumnIndex("FOREIGN_WORD");
             int deckIndex = c.getColumnIndex("DECK");
             int typeIndex = c.getColumnIndex("TYPE");
             do {
               // читаю и расталкиваю сразу по колодам, как в БД
               getDeck(c.getInt(deckIndex))
                  .addCard(new Card(c.getInt(idColIndex),
                          c.getString(nativeArticleColIndex),
                          c.getString(nativeWordColIndex),
                          c.getString(foreignArticleColIndex),
                          c.getString(foreignWordColIndex),
                          c.getString(typeIndex)
                  ));
             } while (c.moveToNext());
         }
    }
}


class Card {
    int id;
    String nativeArticle, nativeWord, foreignArticle, foreignWord, type; //, rightSide, backSide;

    public Card(){
        foreignWord = "";
        nativeWord = "";
    }

    public Card(int id,
                String NativeArticle, String NativeWord,
                String ForeignArticle, String ForeignWord,
                String type){
        this.id=id;
        this.nativeArticle = NativeArticle;
        this.nativeWord = NativeWord;
        this.foreignArticle = ForeignArticle;
        this.foreignWord = ForeignWord;
        this.type = type;
    }

}

class Deck extends ArrayList{
    ArrayList<Card> cards;
    private int index=-1;
    // конструктор
    public Deck(int size){
        cards = new ArrayList<Card>(size);
    }

    Card getNextCard(){
        return cards.get(++index);
    }
    
    Card getCurrentCard(){
        return cards.get(index);
    }

    Card removeCurrentCard(){
        return cards.remove(index--);
    }

    void addCard(Card card){
        cards.add(card);
    }

    void resetIndex(){ // Для инициализации нулевой колоды
        if (index<0)
            index=0;
    }

}