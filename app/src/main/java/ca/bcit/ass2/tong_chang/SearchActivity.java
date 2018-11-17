package ca.bcit.ass2.tong_chang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity  extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
                    naughty[ndx++] = cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[SearchActivity / searchChildInfo] DB unavailable";
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
                int id = Integer.parseInt(childInfo[i].split(" ")[0]);
                Intent intent = new Intent(SearchActivity.this, NaughtyDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }



}
