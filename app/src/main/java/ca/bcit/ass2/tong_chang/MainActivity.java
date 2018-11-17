package ca.bcit.ass2.tong_chang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        ListView list_naughty = (ListView) findViewById(R.id.list_naughty);

        final String[] childInfo = getChildInfo();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, childInfo
        );
        list_naughty.setAdapter(arrayAdapter);

        list_naughty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = Integer.parseInt(childInfo[i].split(" ")[0]);
                Intent intent = new Intent(MainActivity.this, NaughtyDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, EditNaughtyActivity.class);
                startActivity(intent);
                return true;
            case R.id.search:
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String[] getChildInfo() {
        DbOperator helper = new DbOperator(this);

        String[] naughty = null;
        try {
            db = helper.getReadableDatabase();
            Cursor cursor= db.rawQuery("select DISTINCT ID, FIRSTNAME, LASTNAME from NAUGHTYCHILDINFO", null);

            int count = cursor.getCount();
            naughty = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    naughty[ndx++] = cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getChildInfo] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return naughty;
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
