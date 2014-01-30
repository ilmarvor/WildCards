package com.wildcards.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CardActivity extends Activity {
    public static Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        TextView txtViewRightSide = (TextView) findViewById(R.id.txtRightSide);
        CardSet cardSet = MainActivity.cardSet;

        card = new Card();
        card = cardSet.getRandomCard();
        txtViewRightSide.setText(card.rightSide);

        Button btnTurnCard = (Button) findViewById(R.id.btnTurnCard);

        OnClickListener oclBtnTurnCard = new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add all necessary additional actions
                Intent intActivity3 = new Intent(view.getContext(), Activity3.class);
                startActivity(intActivity3);
            }
        };

        btnTurnCard.setOnClickListener(oclBtnTurnCard);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == event.KEYCODE_BACK){
            Intent inte = new Intent(this, MainActivity.class);
            startActivity(inte);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
