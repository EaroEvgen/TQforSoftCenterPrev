package com.home.earoevgen.tqforsoftcenterprev;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddTypeCardActivity extends AppCompatActivity {

    final String LOG_TAG = "AddTypeCardActivity::";

    TextView nameType;
    TextView percentType;
    Button btnAdd;
    Button btnCancel;
    mySQLiteHelper dbHelper;
    SQLiteDatabase db;
    Intent recIntent;
    int recDATA;
    int recCOM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type_card);
        Log.d(LOG_TAG, "onCreate ");

        nameType = (TextView)findViewById(R.id.add_type_edit_text_name);
        percentType = (TextView)findViewById(R.id.add_type_edit_text_percent);
        btnAdd = (Button)findViewById(R.id.add_type_add);
        btnCancel = (Button)findViewById(R.id.add_type_cancel);

        dbHelper = new mySQLiteHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor typeTableCursor = db.query("typecardstable", null, null, null, null, null, null);
        recIntent = getIntent();
        recCOM = recIntent.getIntExtra(MainActivity.COMMAND, 2);
        recDATA = recIntent.getIntExtra(MainActivity.DATA, 0);

        nameType.setText("");
        percentType.setText("");
        nameType.setHint(R.string.text_view_hint_name);
        percentType.setHint(R.string.text_view_hint_percent);

        if (recCOM == 0) {
            btnAdd.setText(R.string.btn_add);
            btnCancel.setText(R.string.btn_cancel);

        } if (recCOM == 1) {
            typeTableCursor.moveToPosition(recDATA);
            int nameColIndex = typeTableCursor.getColumnIndex("name");
            int percentColIndex = typeTableCursor.getColumnIndex("percent");

            nameType.setText(typeTableCursor.getString(nameColIndex));
            percentType.setText(typeTableCursor.getString(percentColIndex));
            btnAdd.setText(R.string.btn_change);
            btnCancel.setText(R.string.btn_delete);
        } //else ошибка
    }

    public void onClickAddTypeBtn(View view) {
        Log.d(LOG_TAG, "onClickAddTypeBtn ");
        ContentValues cv = new ContentValues();
        db = dbHelper.getWritableDatabase();
        long t;
        if (recCOM == 0) {
            cv.put("name", nameType.getText().toString());
            cv.put("percent", percentType.getText().toString());
            t = db.insert("typecardstable", null, cv);
            Log.d(LOG_TAG, "Add " + t + " rows");

        } if (recCOM == 1) {
            cv.put("name", nameType.getText().toString());
            cv.put("percent", percentType.getText().toString());
            Cursor typeTableCursor = db.query("typecardstable", null, null, null, null, null, null);
            typeTableCursor.moveToPosition(recDATA);
            int iid = typeTableCursor.getInt(typeTableCursor.getColumnIndex("id"));
            String sid = String.valueOf(iid);
            t = db.update("typecardstable", cv, "id = ?", new String[] {sid});
            Log.d(LOG_TAG, "Update " + t + " rows");


        } //else ошибка
        dbHelper.close();
        startActivity(new Intent(this, ViewTypeCardActivity.class));
    }

    public void onClickCancelAddTypeBtn(View view) {
        Log.d(LOG_TAG, "onClickCancelAddTypeBtn ");
        if (recCOM == 0) {
            recDATA=0;
            dbHelper.close();
            startActivity(new Intent(this, MainActivity.class));

        } if (recCOM == 1) {
            Cursor typeTableCursor = db.query("typecardstable", null, null, null, null, null, null);
            typeTableCursor.moveToPosition(recDATA);
            int iid = typeTableCursor.getInt(typeTableCursor.getColumnIndex("id"));
            String sid = String.valueOf(iid);
            int t = db.delete("typecardstable", "id = ?", new String[] {sid});
            Log.d(LOG_TAG, "Delete " + t + " rows");
            dbHelper.close();
            recDATA=0;
            startActivity(new Intent(this, ViewTypeCardActivity.class));
        } //else ошибка
    }
}
