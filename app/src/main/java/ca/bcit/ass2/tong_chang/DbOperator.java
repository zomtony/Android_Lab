package ca.bcit.ass2.tong_chang;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DbOperator extends SQLiteOpenHelper {
    private static final String DB_NAME = "Christmas.sqlite";
    private static final int DB_VERSION = 3;
    private Context context;

    public DbOperator(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private String getCreateCountryTableSql() {
        String sql = "";
        sql += "CREATE TABLE NAUGHTYCHILDINFO (";
        sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "FIRSTNAME TEXT, ";
        sql += "LASTNAME TEXT, ";
        sql += "BIRTHDATE TEXT, ";
        sql += "STREET TEXT, ";
        sql += "CITY TEXT, ";
        sql += "PROVINCE TEXT, ";
        sql += "POSTALCODE TEXT, ";
        sql += "COUNTRY TEXT, ";
        sql += "LATITUDE INTEGER, ";
        sql += "LONGITUDE INTEGER, ";
        sql += "ISNAUGHTY INTEGER, ";
        sql += "DATECREATED TEXT);";
        return sql;
    }

    private void insertNaughtyChild(SQLiteDatabase db, NaughtyChild naughtyChild) {
        ContentValues values = new ContentValues();
        values.put("FIRSTNAME", naughtyChild.getFirstName());
        values.put("LASTNAME", naughtyChild.getLastName());
        values.put("BIRTHDATE", naughtyChild.getBirthday());
        values.put("STREET", naughtyChild.getStreet());
        values.put("CITY", naughtyChild.getCity());
        values.put("PROVINCE", naughtyChild.getProvince());
        values.put("POSTALCODE", naughtyChild.getPostalCode());
        values.put("COUNTRY", naughtyChild.getCountry());
        values.put("LATITUDE", naughtyChild.getLatitude());
        values.put("LONGITUDE", naughtyChild.getLongitude());
        values.put("ISNAUGHTY", naughtyChild.getIsNaughty());
        values.put("DATECREATED", naughtyChild.getDateCreated());
        db.insert("NAUGHTYCHILDINFO", null, values);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                Log.i("teg", "1111111111111111111111111");
                db.execSQL(getCreateCountryTableSql());
                for (NaughtyChild c : NaughtyChild.NaughtyChilds) {
                    insertNaughtyChild(db, c);
                }
            }
            if (oldVersion < 2)
                db.execSQL("ALTER TABLE COUNTRY ADD COLUMN POPULATION NUMERIC;");
        } catch (SQLException sqle) {
            String msg = "[MyPlanetDbHelper/updateMyDatabase/insertCountry] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }
}
