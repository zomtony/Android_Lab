package ca.bcit.ass2.tong_chang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNaughtyActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_naughty);
        if(NaughtyDetailActivity.TAG == 1){
            NaughtyDetailActivity.TAG = 0;

            TextView idN = findViewById(R.id.idNaughty);
            EditText firstName =  findViewById(R.id.firstName);
            EditText lastName =  findViewById(R.id.lastName);
            EditText birthday =  findViewById(R.id.birthday);
            EditText street =  findViewById(R.id.street);
            EditText city =  findViewById(R.id.cityName);
            EditText province =  findViewById(R.id.province);
            EditText postalCode =  findViewById(R.id.postalCode);
            EditText country =  findViewById(R.id.country);
            EditText latitude =  findViewById(R.id.latitude);
            EditText longitude =  findViewById(R.id.longitude);
            EditText isNaughty =  findViewById(R.id.isNaughty);
            EditText dateCreated =  findViewById(R.id.dateCreated);

            idN.setText(Integer.toString(NaughtyDetailActivity.naughtyChildInfo.getId()));
            firstName.setText(NaughtyDetailActivity.naughtyChildInfo.getFirstName());
            lastName.setText(NaughtyDetailActivity.naughtyChildInfo.getLastName());
            birthday.setText(NaughtyDetailActivity.naughtyChildInfo.getBirthday());
            street.setText(NaughtyDetailActivity.naughtyChildInfo.getStreet());
            city.setText(NaughtyDetailActivity.naughtyChildInfo.getCity());
            province.setText(NaughtyDetailActivity.naughtyChildInfo.getProvince());
            postalCode.setText(NaughtyDetailActivity.naughtyChildInfo.getPostalCode());
            country.setText(NaughtyDetailActivity.naughtyChildInfo.getCountry());
            latitude.setText(Double.toString(NaughtyDetailActivity.naughtyChildInfo.getLatitude()));
            longitude.setText(Double.toString(NaughtyDetailActivity.naughtyChildInfo.getLongitude()));
            isNaughty.setText(Integer.toString(NaughtyDetailActivity.naughtyChildInfo.getIsNaughty()));
            dateCreated.setText(NaughtyDetailActivity.naughtyChildInfo.getDateCreated());

        }
    }

    public void submitInfo(View v){
        DbOperator helper = new DbOperator(this);
        TextView idN =  findViewById(R.id.idNaughty);
        EditText firstName =  findViewById(R.id.firstName);
        EditText lastName =  findViewById(R.id.lastName);
        EditText birthday =  findViewById(R.id.birthday);
        EditText street =  findViewById(R.id.street);
        EditText city =  findViewById(R.id.cityName);
        EditText province =  findViewById(R.id.province);
        EditText postalCode =  findViewById(R.id.postalCode);
        EditText country =  findViewById(R.id.country);
        EditText latitude =  findViewById(R.id.latitude);
        EditText longitude =  findViewById(R.id.longitude);
        EditText isNaughty =  findViewById(R.id.isNaughty);
        EditText dateCreated =  findViewById(R.id.dateCreated);

        try {
            db = helper.getReadableDatabase();
            Cursor cursor= db.rawQuery("select ID from NAUGHTYCHILDINFO where ID = '"+ idN.getText() + "'", null);
            int count = cursor.getCount();
            if (count != 0){
                db = helper.getReadableDatabase();
                db.delete("NAUGHTYCHILDINFO", "ID = '" + idN.getText() + "'", null);


            }
            NaughtyChild naughtyChild = new NaughtyChild(firstName.getText().toString(),
                    lastName.getText().toString(), birthday.getText().toString(), street.getText().toString(),
                    city.getText().toString(), province.getText().toString(), postalCode.getText().toString(),
                    country.getText().toString(),  Double.parseDouble(latitude.getText().toString()),
                    Double.parseDouble(longitude.getText().toString()),
                    Integer.parseInt(isNaughty.getText().toString()),
                    dateCreated.getText().toString());
            helper.insertNaughtyChild(db, naughtyChild);
            db.close();
            Intent intent = new Intent(EditNaughtyActivity.this, MainActivity.class);
            startActivity(intent);

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }


    }
}
