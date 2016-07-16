package xyz.aungpyaephyo.padc.myanmarattractions.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class RegisterUserDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "users.db";

    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + RegisterUserContract.RegistrationUserEntry.TABLE_NAME + " (" +
            RegisterUserContract.RegistrationUserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RegisterUserContract.RegistrationUserEntry.COLUMN_NAME + " TEXT NOT NULL, "+
            RegisterUserContract.RegistrationUserEntry.COLUMN_EMAIL + " TEXT NOT NULL, "+
            RegisterUserContract.RegistrationUserEntry.COLUMN_PASSWORD + " TEXT NOT NULL, "+
            RegisterUserContract.RegistrationUserEntry.COLUMN_BIRTH_DATE + " TEXT NOT NULL, "+
            RegisterUserContract.RegistrationUserEntry.COLUMN_COUNTRY + " TEXT NOT NULL, "+

            " UNIQUE (" + RegisterUserContract.RegistrationUserEntry.COLUMN_EMAIL + ") ON CONFLICT IGNORE" + // ******IGNORE or REPLACE ***********// unique constraints affects for whole table (Eg. each Entry must be unique in table by checking their title // the old data is replaced by new one since REPLACE command is used, or you also can ignore the new entry
            " );";

    public RegisterUserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AttractionsContract.AttractionImageEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
