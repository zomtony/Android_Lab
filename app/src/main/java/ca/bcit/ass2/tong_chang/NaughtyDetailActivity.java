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
    int id;

    public static NaughtyChild naughtyChildInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naughty_detail);
        id = (Integer) getIntent().getExtras().get("id");
        showDetails();
    }

    public void showDetails() {
        DbOperator helper = new DbOperator(this);
        TextView idN_tv = findViewById(R.id.idNaughty);
        TextView firstName_tv = findViewById(R.id.firstName);
        TextView lastName_tv = findViewById(R.id.lastName);
        TextView birthday_tv = findViewById(R.id.birthday);
        TextView street_tv = findViewById(R.id.street);
        TextView city_tv = findViewById(R.id.cityName);
        TextView province_tv = findViewById(R.id.province);
        TextView postalCode_tv = findViewById(R.id.postalCode);
        TextView country_tv = findViewById(R.id.country);
        TextView latitude_tv = findViewById(R.id.latitude);
        TextView longitude_tv = findViewById(R.id.longitude);
        TextView isNaughty_tv = findViewById(R.id.isNaughty);
        TextView dateCreated_tv = findViewById(R.id.dateCreated);

        try {
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select * from NAUGHTYCHILDINFO where ID = '" + id + "'", null);
            int count = cursor.getCount();
            Log.i("t====================", " "+count);
            if (cursor.moveToFirst()) {

                idN_tv.setText(Integer.toString(cursor.getInt(0)));
                firstName_tv.setText(cursor.getString(1));
                lastName_tv.setText(cursor.getString(2));
                birthday_tv.setText(cursor.getString(3));
                street_tv.setText(cursor.getString(4));
                city_tv.setText(cursor.getString(5));
                province_tv.setText(cursor.getString(6));
                postalCode_tv.setText(cursor.getString(7));
                country_tv.setText(cursor.getString(8));
                latitude_tv.setText(Double.toString(cursor.getDouble(9)));
                longitude_tv.setText(Double.toString(cursor.getDouble(10)));
                isNaughty_tv.setText(Boolean.toString(cursor.getInt(11)!=0));
                dateCreated_tv.setText(cursor.getString(12));

                String firstName = firstName_tv.getText().toString();
                String lastName = lastName_tv.getText().toString();
                String birthday = birthday_tv.getText().toString();
                String street = street_tv.getText().toString();
                String city = city_tv.getText().toString();
                String province = province_tv.getText().toString();
                String postalCode = postalCode_tv.getText().toString();
                String country = country_tv.getText().toString();
                double latitude;
                if(latitude_tv.getText().toString().isEmpty()){
                    latitude = 0.0;
                } else {
                    latitude = Double.parseDouble(latitude_tv.getText().toString());
                }
                double longitude;
                if(longitude_tv.getText().toString().isEmpty()){
                    longitude = 0.0;
                } else {
                    longitude = Double.parseDouble(longitude_tv.getText().toString());
                }
                boolean isNaughty;
                if(isNaughty_tv.getText().toString().equals("false")){
                    isNaughty = false;
                } else {
                    isNaughty = true;
                }

                naughtyChildInfo = new NaughtyChild(
                        firstName, lastName, birthday,
                        street, city, province, postalCode, country,
                        latitude, longitude,
                        isNaughty
                );

                naughtyChildInfo.setId(cursor.getInt(0));
            }
        } catch (SQLiteException sqlex) {
            String msg = "[NaughtyDetailActivity / showDetails] DB unavailable";
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
            String msg = "[NaughtyDetailActivity / deleteInfo] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void editInfo(View v){
        Intent intent = new Intent(NaughtyDetailActivity.this, EditNaughtyActivity.class);
        intent.putExtra("edit", 1);
        startActivity(intent);
    }
}
