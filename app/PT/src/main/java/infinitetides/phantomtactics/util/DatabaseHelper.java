package infinitetides.phantomtactics.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Phantom Tactics";
    private static final int DB_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ACTION ("
                + "_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "DESCRIPTION TEXT"
                + "TYPE INTEGER");

        insertAction(db, "Draw", 1);
    }

    private static void insertAction(SQLiteDatabase db, String description, int type) {
        ContentValues actionValues = new ContentValues();

        actionValues.put("DESCRIPTION", description);
        actionValues.put("TYPE", type);

        db.insert("ACTION", null, actionValues);
    }
}
