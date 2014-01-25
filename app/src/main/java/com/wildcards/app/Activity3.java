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

public class Activity3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity3);

        Button btnKnow = (Button) findViewById(R.id.btnKnow);
        OnClickListener oclBtnKnow = new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add all necessary additional actions
                Intent intActivity2 = new Intent(view.getContext(), Activity2.class);
                startActivity(intActivity2);
            }
        };
        btnKnow.setOnClickListener(oclBtnKnow);

        Button btnDontKnow = (Button) findViewById(R.id.btnDontKnow);
        OnClickListener oclBtnDontKnow = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intActivity2 = new Intent(view.getContext(), Activity2.class);
                startActivity(intActivity2);
            }
        };
        btnDontKnow.setOnClickListener(oclBtnDontKnow);

        Button btnDontShowAnymore = (Button) findViewById(R.id.btnDontShowAnymore);
        OnClickListener oclBtnDontShowAnymore = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intActivity2 = new Intent(view.getContext(), Activity2.class);
                startActivity(intActivity2);
            }
        };
        btnDontShowAnymore.setOnClickListener(oclBtnDontShowAnymore);
    }

}
