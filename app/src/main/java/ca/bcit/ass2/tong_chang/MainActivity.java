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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    public static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list_naughty = (ListView) findViewById(R.id.list_naughty);

        final String[] childInfo = getChildInfo();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, childInfo
        );
        list_naughty.setAdapter(arrayAdapter);

        list_naughty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = Integer.parseInt(childInfo[i].split(" ")[0]);
                Intent intent = new Intent(MainActivity.this, NaughtyDetailActivity.class);
                startActivity(intent);
            }
        });
    }


    private String[] getChildInfo() {
        DbOperator helper = new DbOperator(this);

        String[] naughty = null;
        try {
            db = helper.getReadableDatabase();
            Cursor cursor= db.rawQuery("select DISTINCT ID, FIRSTNAME from NAUGHTYCHILDINFO", null);

            int count = cursor.getCount();
            naughty = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    naughty[ndx++] = cursor.getString(0) + " "+ cursor.getString(1);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return naughty;
    }

    private String[] searchChildInfo(String key) {
        DbOperator helper = new DbOperator(this);

        String[] naughty = null;
        try {
            db = helper.getReadableDatabase();
            Cursor cursor= db.rawQuery("select * from NAUGHTYCHILDINFO where FIRSTNAME LIKE '%" + key + "%' OR LASTNAME LIKE '%" + key + "%'", null);

            int count = cursor.getCount();
            naughty = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    naughty[ndx++] = cursor.getString(0) + " "+ cursor.getString(1);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return naughty;
    }

    public void SearchNaughty(View v){
        ListView list_naughty = (ListView) findViewById(R.id.list_naughty);

        EditText keyWord = findViewById(R.id.keyWord);
        final String[] childInfo = searchChildInfo(keyWord.getText().toString());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, childInfo
        );
        list_naughty.setAdapter(arrayAdapter);

        list_naughty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = Integer.parseInt(childInfo[i].split(" ")[0]);
                Intent intent = new Intent(MainActivity.this, NaughtyDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createNaughty(View v){
        Intent intent = new Intent(MainActivity.this, EditNaughtyActivity.class);
        startActivity(intent);
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
