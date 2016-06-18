package bazinac.pichackanakafe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bazinac on 18.6.16.
 */
public class DBhelper extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "pichacka";

    private static final String TABLE_COFFEES = "coffees";
    private static final String TABLE_CENS = "cens";

    private static final String COL_ID = "id";
    private static final String COL_DRINKER_ID = "drinker_id";
    private static final String COL_DRINKER_CEN = "drinker_cen";
    private static final String COL_TIMESTAMP = "timestamp";
    private static final String COL_TIME = "time";


    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_COFFEES = "CREATE TABLE " + TABLE_COFFEES + "("
                + COL_ID + " INTEGER PRIMARY KEY," + COL_DRINKER_ID + " TEXT,"
                + COL_DRINKER_CEN + " TEXT" + COL_TIMESTAMP + " TEXT" + COL_TIME + " INT" + ")";
        db.execSQL(CREATE_TABLE_COFFEES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEES);

        onCreate(db);
    }
}
