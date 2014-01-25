package com.wildcards.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
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
