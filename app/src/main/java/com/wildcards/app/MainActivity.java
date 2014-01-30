package com.wildcards.app;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    public SQLiteDatabase db;
    public static  CardSet cardSet;
    final String DEBUG_TAG ="debug";
    // TODO: add possibility to list not randomly, but one by one
    // after finishing of the set return to the main menu or other activity
    public static int currCardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // create and fill db from file ru_de.txt
        DBHelper mDbHelper = new DBHelper(getApplicationContext());
        Log.d("debug", "mDbHelper.getWritableDatabase();");
        db = mDbHelper.getWritableDatabase();
        Log.d("debug", "after mDbHelper.getWritableDatabase();");

        setContentView(R.layout.activity_main);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnQuit = (Button) findViewById(R.id.btnQuit);

        OnClickListener oclBtnStart = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add all necessary additional actions
                Intent intActivity2 = new Intent(v.getContext(), CardActivity.class);
                cardSet = new CardSet(db);
                startActivity(intActivity2);
            }
        };

        btnStart.setOnClickListener(oclBtnStart);

        OnClickListener oclBtnQuit = new OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                System.exit(0);
            }
        };

        btnQuit.setOnClickListener(oclBtnQuit);
    }



//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mDbHelper.close();
//    }

}
