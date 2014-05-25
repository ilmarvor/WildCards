package com.wildcards.app;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    private SQLiteDatabase db;
//    public static CardSet cardSet;
    private Intent intCardActivity;
    final String DEBUG_TAG ="debug";
    // TODO: add possibility to list not randomly, but one by one
    // after finishing of the set return to the main menu or other activity

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        DBHelper mDbHelper = new DBHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();
        intCardActivity = new Intent(this, CardActivity.class);
        Cards.init();
        setContentView(R.layout.activity_main);

        // кнопка "Приступить к изучению"
        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add all necessary additional actions
//                if (Cards.isEmpty())
                    Cards.loadFromDb(db);
                startActivity(intCardActivity);
            }
        });

        // Кнопка "Выход"
        OnClickListener oclBtnQuit = new OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                System.exit(0);
            }
        };
        Button btnQuit = (Button) findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(oclBtnQuit);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    moveTaskToBack(true);
                    System.exit(0);
                    return true;
                }
                return super.onKeyDown(keyCode, event);
    }


//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mDbHelper.close();
//    }

}
