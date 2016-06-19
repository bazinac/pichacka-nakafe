package bazinac.pichackanakafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bazinac on 18.6.16.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "pichackaFinal";

    private static final String TABLE_COFFEES = "coffees";
    private static final String TABLE_CENS = "cens";

    private static final String COL_ID = "id";
    private static final String COL_DRINKER_ID = "drinker_id";
    private static final String COL_DRINKER_CEN = "drinker_cen";
    private static final String COL_TIMESTAMP = "timestamp";
    private static final String COL_TIME = "time";


    public DBhelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("DBhelper: ","onCreate fired");
        String CREATE_TABLE_COFFEES = "CREATE TABLE IF NOT EXISTS " + TABLE_COFFEES
                + "("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_DRINKER_ID + " TEXT,"
                + COL_DRINKER_CEN + " TEXT,"
                + COL_TIMESTAMP + " TEXT,"
                + COL_TIME + " INT"
                + ")";

        db.execSQL(CREATE_TABLE_COFFEES);
        Log.i("DBhelper: ","CREATE executed ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBhelper: ","onUpgrade fired");

        onCreate(db);
    }

    //CRUD metody

    public void addCoffee(Coffee coffee) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_DRINKER_ID, coffee.getDrinkerId());
        values.put(COL_DRINKER_CEN, coffee.getCen());
        values.put(COL_TIMESTAMP, coffee.getTimeStamp());
        values.put(COL_TIME, coffee.getTime());

        db.insert(TABLE_COFFEES, null, values);
        db.close();
    }

    public List<Coffee> getAllCoffees() {
        List<Coffee> coffeeList = new ArrayList<Coffee>();

        String selectQuery = "SELECT " + COL_DRINKER_ID + "," +  COL_DRINKER_CEN + "," + COL_TIMESTAMP + "," + COL_TIME + " FROM " + TABLE_COFFEES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Coffee coffee = new Coffee();
                coffee.setDrinkerId(cursor.getString(0));
                coffee.setCen(cursor.getString(1));
                coffee.setTimeStamp(cursor.getString(2));
                coffee.setTime(Long.parseLong(cursor.getString(3)));


                coffeeList.add(coffee);
            } while (cursor.moveToNext());
        }

        return coffeeList;
    }


    public List<String> getCoffeeStats() {
        List<String> coffeeList = new ArrayList<String>();

        String selectQuery = "SELECT " + COL_DRINKER_CEN +  ", COUNT(*) FROM " + TABLE_COFFEES + " GROUP BY " + COL_DRINKER_CEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String s = new String();
                s = "User " + cursor.getString(0) + " vypil " + Long.parseLong(cursor.getString(1)) + " porcí kávy.";


                coffeeList.add(s);
            } while (cursor.moveToNext());
        }

        return coffeeList;
    }

    public int getCoffeesCount() {
        String countQuery = "SELECT * FROM " + TABLE_COFFEES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int i = cursor.getCount();

        cursor.close();

        return i;
    }




    public void truncateCoffeeLog(){
        Log.i("DBhelper: ","TRUNCATE (jakoby) executed ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_COFFEES);

    }





}
