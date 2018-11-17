package ca.bcit.ass2.tong_chang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNaughtyActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private boolean isEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_naughty);
        int edit = (Integer) getIntent().getExtras().get("edit");
        isEdit = (edit == 1);

        if(isEdit){
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
            isNaughty.setText(Boolean.toString(NaughtyDetailActivity.naughtyChildInfo.getIsNaughty()));

        }
    }

    public void submitInfo(View v){
        DbOperator helper = new DbOperator(this);
        TextView idN =  findViewById(R.id.idNaughty);
        EditText firstName_et =  findViewById(R.id.firstName);
        EditText lastName_et =  findViewById(R.id.lastName);
        EditText birthday_et =  findViewById(R.id.birthday);
        EditText street_et =  findViewById(R.id.street);
        EditText city_et =  findViewById(R.id.cityName);
        EditText province_et =  findViewById(R.id.province);
        EditText postalCode_et =  findViewById(R.id.postalCode);
        EditText country_et =  findViewById(R.id.country);
        EditText latitude_et =  findViewById(R.id.latitude);
        EditText longitude_et =  findViewById(R.id.longitude);
        EditText isNaughty_et =  findViewById(R.id.isNaughty);

        try {
            db = helper.getReadableDatabase();

            int id = Integer.parseInt(idN.getText().toString());
            String firstName = firstName_et.getText().toString();
            String lastName = lastName_et.getText().toString();
            String birthday = birthday_et.getText().toString();
            String street = street_et.getText().toString();
            String city = city_et.getText().toString();
            String province = province_et.getText().toString();
            String postalCode = postalCode_et.getText().toString();
            String country = country_et.getText().toString();
            double latitude;
            if(latitude_et.getText().toString().isEmpty()){
                latitude = 0.0;
            } else {
                latitude = Double.parseDouble(latitude_et.getText().toString());
            }
            double longitude;
            if(longitude_et.getText().toString().isEmpty()){
                longitude = 0.0;
            } else {
                longitude = Double.parseDouble(longitude_et.getText().toString());
            }
            boolean isNaughty;
            if(isNaughty_et.getText().toString().equals("false")){
                isNaughty = false;
            } else {
                isNaughty = true;
            }

            NaughtyChild naughtyChild = new NaughtyChild(
                    firstName, lastName, birthday,
                    street, city, province, postalCode, country,
                    latitude, longitude,
                    isNaughty
                    );
            naughtyChild.setId(id);
            if(isEdit){
                helper.updateNaughtyChild(db, naughtyChild);
            }else{
                helper.insertNaughtyChild(db, naughtyChild);
            }

            db.close();
            Intent intent = new Intent(EditNaughtyActivity.this, MainActivity.class);
            startActivity(intent);

        } catch (SQLiteException sqlex) {
            String msg = "[EditNaughtyActivity / submitInfo] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }
}
