package ca.bcit.ass2.tong_chang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list_continents = (ListView) findViewById(R.id.list_continents);

        String[] childInfo = getChildInfo();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, childInfo
        );

        list_continents.setAdapter(arrayAdapter);


    }


    private String[] getChildInfo() {
        DbOperator helper = new DbOperator(this);

        String[] continents = null;
        try {
            db = helper.getReadableDatabase();
            Cursor cursor= db.rawQuery("select DISTINCT ID, FIRSTNAME from NAUGHTYCHILDINFO", null);

            int count = cursor.getCount();
            continents = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    continents[ndx++] = cursor.getString(0) + " "+ cursor.getString(1);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return continents;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cursor != null)
            cursor.close();

        if (db != null)
            db.close();
    }
}
