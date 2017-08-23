package com.home.earoevgen.tqforsoftcenterprev;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddCardActivity extends AppCompatActivity {

    final String LOG_TAG = "AddCardActivity ::";

    TextView nameCard;
    TextView numberCard;
    Spinner typeCard;
    Button btnAdd;
    Button btnCancel;
    mySQLiteHelper dbHelper;
    SQLiteDatabase db;
    Intent recIntent;
    int recDATA;
    int recCOM;
    String tempStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Log.d(LOG_TAG, "onCreate ");

        nameCard = (TextView)findViewById(R.id.add_card_edit_text_name);
        numberCard = (TextView)findViewById(R.id.add_card_edit_text_number_card);
        typeCard = (Spinner) findViewById(R.id.add_card_spinner_type_card);
        btnAdd = (Button)findViewById(R.id.add_card_btn_add);
        btnCancel = (Button)findViewById(R.id.add_card_btn_cancel);

        dbHelper = new mySQLiteHelper(this);
        db = dbHelper.getWritableDatabase();
        recIntent = getIntent();
        recCOM = recIntent.getIntExtra(MainActivity.COMMAND, 2);
        recDATA = recIntent.getIntExtra(MainActivity.DATA, 0);

        nameCard.setText("");
        numberCard.setText("");
        nameCard.setHint(R.string.text_view_hint_name);
        numberCard.setHint(R.string.text_view_hint_number);

        typeCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected,
                                       int selectedItemPosition,
                                       long selectedId) {
                tempStr = String.valueOf(selectedItemPosition);
                Log.d(LOG_TAG, "Result type" + recDATA + " = " + tempStr);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (recCOM == 0) {
            Cursor typeTableCursor = db.query("typecardstable", null, null, null, null, null, null);
            int nameTypeColIndex = typeTableCursor.getColumnIndex("name");

            List<String> list = new ArrayList<String>();

            if (typeTableCursor.moveToFirst()) {
                do {
                    list.add(typeTableCursor.getString(nameTypeColIndex));
                } while (typeTableCursor.moveToNext());
            } else
                Log.d(LOG_TAG, "Type Card 0 rows");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeCard.setAdapter(adapter);
            btnAdd.setText(R.string.btn_add);
            btnCancel.setText(R.string.btn_cancel);

        } if (recCOM == 1) {
            Cursor cardTableCursor = db.query("cardstable", null, null, null, null, null, null);

            cardTableCursor.moveToPosition(recDATA);
            int nameColIndex = cardTableCursor.getColumnIndex("name");
            int numberColIndex = cardTableCursor.getColumnIndex("number");

            nameCard.setText(cardTableCursor.getString(nameColIndex));
            numberCard.setText(cardTableCursor.getString(numberColIndex));
            btnAdd.setText(R.string.btn_change);
            btnCancel.setText(R.string.btn_delete);
        } //else ошибка
    }

    public void onClickAddCardBtn(View view) {
        ContentValues cv = new ContentValues();

        if (recCOM == 0) {
            cv.put("name", nameCard.getText().toString());
            cv.put("number", numberCard.getText().toString());
            cv.put("type", tempStr);
            db.insert("cardstable", null, cv);

        } if (recCOM == 1) {
            cv.put("name", nameCard.getText().toString());
            cv.put("number", numberCard.getText().toString());
            Cursor cardTableCursor = db.query("cardstable", null, null, null, null, null, null);
            cardTableCursor.moveToPosition(recDATA);
            int iid = cardTableCursor.getInt(cardTableCursor.getColumnIndex("id"));
            String sid = String.valueOf(iid);
            int t = db.update("cardstable", cv, "id = ?", new String[] {sid});
            Log.d(LOG_TAG, "Update " + t + " rows");
            recDATA=0;


        } //else ошибка
        dbHelper.close();
        startActivity(new Intent(this, ViewCardActivity.class));
    }

    public void onClickCancelAddCardBtn(View view) {
        if (recCOM == 0) {
            dbHelper.close();
            startActivity(new Intent(this, MainActivity.class));

        } if (recCOM == 1) {

            db = dbHelper.getWritableDatabase();
            Cursor cardTableCursor = db.query("cardstable", null, null, null, null, null, null);
            cardTableCursor.moveToPosition(recDATA);
            int iid = cardTableCursor.getInt(cardTableCursor.getColumnIndex("id"));
            String sid = String.valueOf(iid);
            int t = db.delete("cardstable", "id = ?", new String[] {sid});
            Log.d(LOG_TAG, "Delete " + t + " rows");
            db.close();
            dbHelper.close();
            recDATA=0;
            startActivity(new Intent(this, ViewCardActivity.class));
        } //else ошибка
    }
}
