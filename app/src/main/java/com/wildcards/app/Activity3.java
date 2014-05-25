package com.wildcards.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// Активити после переворота карты
public class Activity3 extends Activity {
    private Intent intCardActivity;

    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        intCardActivity = new Intent(this, CardActivity.class);
        setContentView(R.layout.activity3);
        Cards.init();

        // Кнопка "Знаю"
        Button btnKnow = (Button) findViewById(R.id.btnKnow);
        btnKnow.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // TODO: повысить уровень колоды
                Cards.boostCurrentCard();
                startActivity(intCardActivity);
            }
        });

        // Кнопка "Не знаю"
        Button btnDontKnow = (Button) findViewById(R.id.btnDontKnow);
        btnDontKnow.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(intCardActivity);
            }
        });

        // Кнопка "Больше не показывать"
        Button btnDontShowAnymore = (Button) findViewById(R.id.btnDontShowAnymore);
        btnDontShowAnymore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intCardActivity);
            }
        });
    }

    @Override
    // The activity is about to become visible.
    protected void onStart() {
        super.onStart();
        TextView txtViewBackSide = (TextView) findViewById(R.id.txtBackSide);
        Card card = Cards.getCurrent();
        txtViewBackSide.setText((card.foreignArticle==null?"":card.foreignArticle+" ") +card.foreignWord);
    }

}
