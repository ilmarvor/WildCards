package com.wildcards.app;

/**
 * Created by Denis on 28.01.14.
 */
public class Card {
    public String rightSide;
    public String backSide;
// TODO: Add additional fields, such as ID, currentBox, etc.

    public Card(){
        this.rightSide = "";
        this.backSide  = "";
    }

    public Card(String rightSide, String backSide){
        this.rightSide = rightSide;
        this.backSide  = backSide;
    }

}
