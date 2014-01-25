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
import android.view.ViewGroup;
import android.os.Build;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnQuit = (Button) findViewById(R.id.btnQuit);

        OnClickListener oclBtnStart = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add all necessary additional actions
                Intent intActivity2 = new Intent(v.getContext(), Activity2.class);
                startActivity(intActivity2);
            }
        };

        btnStart.setOnClickListener(oclBtnStart);

        OnClickListener oclBtnQuit = new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        };

        btnQuit.setOnClickListener(oclBtnQuit);
    }

}
