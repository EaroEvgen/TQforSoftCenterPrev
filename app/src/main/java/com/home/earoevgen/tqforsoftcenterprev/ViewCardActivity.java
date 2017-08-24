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

public class ViewCardActivity extends AppCompatActivity {

    final String LOG_TAG = "ViewCardActivity ::";

    mySQLiteHelper dbHelper;
    SQLiteDatabase db;
    ListView listCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        Log.d(LOG_TAG, "onCreate");

        dbHelper = new mySQLiteHelper(this);
        // FIXME: 24.08.2017 Хорошим тоном является закрывать подключение к БД и курсоры после использования, в противном случае может возникнуть утечка ресурсов, а в многопоточном приложении все и вовсе перестанет работать
        db = dbHelper.getWritableDatabase();
        Cursor cardTableCursor = db.query("cardstable", null, null, null, null, null, null);
        Cursor typeTableCursor = db.query("typecardstable", null, null, null, null, null, null);

        listCard = (ListView) findViewById(R.id.view_card_list);
        List<String> list = new ArrayList<String>();

        if (cardTableCursor.moveToFirst()) {

            int idColIndex = cardTableCursor.getColumnIndex("id");
            int nameColIndex = cardTableCursor.getColumnIndex("name");
            int typeColIndex = cardTableCursor.getColumnIndex("type");
            int numberColIndex = cardTableCursor.getColumnIndex("number");
            do {
                Log.d(LOG_TAG,  "ID = " + cardTableCursor.getInt(idColIndex) +
                        ", name = " + cardTableCursor.getString(nameColIndex) +
                        ", type = " + cardTableCursor.getString(typeColIndex) +
                        ", number = " + cardTableCursor.getString(numberColIndex) );
                int erh = cardTableCursor.getInt(cardTableCursor.getColumnIndex("type"));
                // FIXME: 24.08.2017 Позиция внутри курсора в общем случае никогда не будет совпадать с ID, тем более что в курсоре не задана сортировка
                typeTableCursor.moveToPosition(erh);
                list.add(cardTableCursor.getString(nameColIndex) + "\n" +
                        typeTableCursor.getString(typeTableCursor.getColumnIndex("name")) + "\n" +
                        cardTableCursor.getString(numberColIndex) + "\n");
            } while (cardTableCursor.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        // FIXME: 24.08.2017 Обновлять список стоит в onResume, в противном случае мы не увидим изменения возвращаясь с других экранов
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, list);
        listCard.setAdapter(adapter);
        listCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chouseCardListItem(position);
            }
        });
    }

    private void chouseCardListItem(int position) {
        Log.d(LOG_TAG, "chouseCardListItem");
        // FIXME: 24.08.2017 Вместо extra command лучше использовать setAction
        // FIXME: 24.08.2017 position и ID в общем случае совпадать не будут, необходимо передавать ID элемента
        startActivity(new Intent(this, AddCardActivity.class).putExtra(MainActivity.COMMAND, 1).putExtra(MainActivity.DATA, position));
    }

    public void onClickViewCardAddBtn(View view) {
        Log.d(LOG_TAG, "onClickViewCardAddBtn");
        startActivity(new Intent(this, AddCardActivity.class).putExtra(MainActivity.COMMAND, 0));
    }

}
