package com.wildcards.app;

import java.util.Random;

/**
 * Created by Denis on 28.01.14.
 */
public class CardSet extends Card {
    Card set []; // set of cards to show

    public CardSet(){
        this.set = new Card[5];
        this.set[0] = new Card();
        this.set[1] = new Card();
        this.set[2] = new Card();
        this.set[3] = new Card();
        this.set[4] = new Card();
    }

    public void getCardSet(){
        this.set = new Card[5];

        this.set[0] = new Card("die Katze","кот");
        this.set[1] = new Card("der Hund", "собака");
        this.set[2] = new Card("warten", "ждать");
        this.set[3] = new Card("das Dach", "крыша");
        this.set[4] = new Card("der Bahnhof", "станция");
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
