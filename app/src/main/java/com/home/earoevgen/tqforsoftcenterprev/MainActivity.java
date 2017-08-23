package com.home.earoevgen.tqforsoftcenterprev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "MainActivityLog ::";
    public final static String COMMAND = "TQforSoftCenterPrev.COMMAND";
    public final static String DATA = "TQforSoftCenterPrev.DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate");
    }

    public void onClickViewTypeBtn(View view) {
        Log.d(LOG_TAG, "onClickViewTypeBtn");
        startActivity(new Intent(this, ViewTypeCardActivity.class));
    }

    public void onClickViewCartBtn(View view) {
        Log.d(LOG_TAG, "onClickViewCartBtn");
        startActivity(new Intent(this, ViewCardActivity.class));
    }
}
