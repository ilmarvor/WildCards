package com.wildcards.app;

import android.text.TextUtils;

/**
 * Created by Denis on 28.01.14.
 */
public class Card {
    int id;
    String NativeArticle;
    String NativeWord;
    String ForeignArticle;
    String ForeignWord;
    String Type;
    public String rightSide;
    public String backSide;
// TODO: Add additional fields, such as ID, currentBox, etc.

    public Card(){}

    public Card(String NativeArticle, String NativeWord, String ForeignArticle, String ForeignWord){
        this.rightSide = (NativeArticle==null?"":NativeArticle+" ")   +NativeWord;
        this.backSide  = (ForeignArticle==null?"":ForeignArticle+" ") +ForeignWord;
    }

    // используется при парсинге файла для записи в БД, но мало ли еще куда понадобится
    public void fillValueByAttrName(String attrName, String attrValue){
        if (!TextUtils.isEmpty(attrValue)){
            if ("W".equalsIgnoreCase(attrName))
                NativeWord=attrValue;
            else if ("A".equalsIgnoreCase(attrName))
                NativeArticle=attrValue;
            else if ("FA".equalsIgnoreCase(attrName))
                ForeignArticle=attrValue;
            else if ("FW".equalsIgnoreCase(attrName))
                ForeignWord=attrValue;
            else if ("P".equalsIgnoreCase(attrName))
                Type=attrValue;
        }
    }

}
