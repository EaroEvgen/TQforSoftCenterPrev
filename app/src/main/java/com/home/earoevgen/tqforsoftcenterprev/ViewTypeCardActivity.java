package com.home.earoevgen.tqforsoftcenterprev;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewTypeCardActivity extends AppCompatActivity {

    final String LOG_TAG = "ViewTypeCardActivity ::";

    mySQLiteHelper dbHelper;
    SQLiteDatabase db;
    ListView listTypeCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_type_card);
        Log.d(LOG_TAG, "onCreate");

        dbHelper = new mySQLiteHelper(this);
        db = dbHelper.getWritableDatabase();
        listTypeCard = (ListView) findViewById(R.id.view_type_list);

        Cursor typeTableCursor = db.query("typecardstable", null, null, null, null, null, null);
        List<String> list = new ArrayList<String>();

        if (typeTableCursor.moveToFirst()) {

            int idColIndex = typeTableCursor.getColumnIndex("id");
            int nameColIndex = typeTableCursor.getColumnIndex("name");
            int percentColIndex = typeTableCursor.getColumnIndex("percent");
//
            do {
                Log.d(LOG_TAG,  "ID = " + typeTableCursor.getInt(idColIndex)
                        + ", name = " + typeTableCursor.getString(nameColIndex)
                        + ", type = " + typeTableCursor.getString(percentColIndex) + "\n" );
                list.add(typeTableCursor.getString(nameColIndex) + "\n"
                        + typeTableCursor.getString(percentColIndex) );
            } while (typeTableCursor.moveToNext());
        } else {
            Log.d(LOG_TAG, "0 rows");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, list);
        listTypeCard.setAdapter(adapter);
        listTypeCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chouseCardListItem(position);
            }
        });

    }

    private void chouseCardListItem(int position) {
        Log.d(LOG_TAG, "chouseCardListItem");
        startActivity(new Intent(this, AddTypeCardActivity.class).putExtra(MainActivity.COMMAND, 1).putExtra(MainActivity.DATA, position));
    }

    public void onClickViewTypeAddBtn(View view) {
        Log.d(LOG_TAG, "onClickViewAdd");
        startActivity(new Intent(this, AddTypeCardActivity.class).putExtra(MainActivity.COMMAND, 0));
    }

}
