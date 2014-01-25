package com.wildcards.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

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

}
