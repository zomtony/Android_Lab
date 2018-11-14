package ca.bcit.ass2.tong_chang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NaughtyDetailActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    public static int TAG = 0;

    public static NaughtyChild naughtyChildInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naughty_detail);
        showDetails();
    }

    public void showDetails() {
        DbOperator helper = new DbOperator(this);
        TextView idN = findViewById(R.id.idNaughty);
        TextView firstName = findViewById(R.id.firstName);
        TextView lastName = findViewById(R.id.lastName);
        TextView birthday = findViewById(R.id.birthday);
        TextView street = findViewById(R.id.street);
        TextView city = findViewById(R.id.cityName);
        TextView province = findViewById(R.id.province);
        TextView postalCode = findViewById(R.id.postalCode);
        TextView country = findViewById(R.id.country);
        TextView latitude = findViewById(R.id.latitude);
        TextView longitude = findViewById(R.id.longitude);
        TextView isNaughty = findViewById(R.id.isNaughty);
        TextView dateCreated = findViewById(R.id.dateCreated);

        try {
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select * from NAUGHTYCHILDINFO where ID = '" + MainActivity.id + "'", null);
            int count = cursor.getCount();
            Log.i("t====================", " "+count);
            if (cursor.moveToFirst()) {

                idN.setText(Integer.toString(cursor.getInt(0)));
                firstName.setText(cursor.getString(1));
                lastName.setText(cursor.getString(2));
                birthday.setText(cursor.getString(3));
                street.setText(cursor.getString(4));
                city.setText(cursor.getString(5));
                province.setText(cursor.getString(6));
                postalCode.setText(cursor.getString(7));
                country.setText(cursor.getString(8));
                latitude.setText(Double.toString(cursor.getDouble(9)));
                longitude.setText(Double.toString(cursor.getDouble(10)));
                isNaughty.setText(Integer.toString(cursor.getInt(11)));
                dateCreated.setText(cursor.getString(12));

                naughtyChildInfo = new NaughtyChild(firstName.getText().toString(),
                        lastName.getText().toString(), birthday.getText().toString(), street.getText().toString(),
                        city.getText().toString(), province.getText().toString(), postalCode.getText().toString(),
                        country.getText().toString(),  Double.parseDouble(latitude.getText().toString()),
                        Double.parseDouble(longitude.getText().toString()),
                        Integer.parseInt(isNaughty.getText().toString()),
                        dateCreated.getText().toString());
                naughtyChildInfo.setId(cursor.getInt(0));
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void deleteInfo(View v) {
        DbOperator helper = new DbOperator(this);
        TextView idN = findViewById(R.id.idNaughty);
        int id = Integer.parseInt(idN.getText().toString());
        try {
            db = helper.getReadableDatabase();

            db.delete("NAUGHTYCHILDINFO", "ID = '" + id + "'", null);
            db.close();

            Intent intent = new Intent(NaughtyDetailActivity.this, MainActivity.class);
            startActivity(intent);
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void editInfo(View v){
        TAG = 1;
        Intent intent = new Intent(NaughtyDetailActivity.this, EditNaughtyActivity.class);
        startActivity(intent);
    }
}
