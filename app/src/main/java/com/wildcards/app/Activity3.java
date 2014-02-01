package com.wildcards.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Activity3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity3);

        TextView txtViewBackSide = (TextView) findViewById(R.id.txtBackSide);
        txtViewBackSide.setText(CardActivity.card.backSide);

        Button btnKnow = (Button) findViewById(R.id.btnKnow);
        OnClickListener oclBtnKnow = new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add all necessary additional actions
                Intent intActivity2 = new Intent(view.getContext(), CardActivity.class);
                startActivity(intActivity2);
            }
        };
        btnKnow.setOnClickListener(oclBtnKnow);

        Button btnDontKnow = (Button) findViewById(R.id.btnDontKnow);
        OnClickListener oclBtnDontKnow = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intActivity2 = new Intent(view.getContext(), CardActivity.class);
                startActivity(intActivity2);
            }
        };
        btnDontKnow.setOnClickListener(oclBtnDontKnow);

        Button btnDontShowAnymore = (Button) findViewById(R.id.btnDontShowAnymore);
        OnClickListener oclBtnDontShowAnymore = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intActivity2 = new Intent(view.getContext(), CardActivity.class);
                startActivity(intActivity2);
            }
        };
        btnDontShowAnymore.setOnClickListener(oclBtnDontShowAnymore);
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
