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
    private Card card;
    private Intent intActivity3;
    private Intent intMain;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        intActivity3 = new Intent(this, Activity3.class);
        intMain = new Intent(this, MainActivity.class);
        setContentView(R.layout.activity2);
        Cards.init();

        card = Cards.getNext();

        Button btnTurnCard = (Button) findViewById(R.id.btnTurnCard);
        btnTurnCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add all necessary additional actions
                startActivity(intActivity3);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView txtViewRightSide = (TextView) findViewById(R.id.txtRightSide);
        txtViewRightSide.setText((card.nativeArticle==null?"":card.nativeArticle+" ")+card.nativeWord);
        TextView txtRightSideDebug = (TextView) findViewById(R.id.txtRightSideDebug);
        txtRightSideDebug.setText("id= " + card.id + "     type= "+ card.type + "    deck= "+Cards.current);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            startActivity(intMain);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
