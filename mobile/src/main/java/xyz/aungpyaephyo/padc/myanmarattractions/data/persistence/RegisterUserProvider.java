package xyz.aungpyaephyo.padc.myanmarattractions.data.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class RegisterUserProvider extends ContentProvider {

    public static final int USERS = 1;

    private static final String sRegisterUserSelection = RegisterUserContract.RegistrationUserEntry.COLUMN_EMAIL + " = ?";
    private RegisterUserDBHelper mRegisterUserDBHelper;
    private static final UriMatcher sUriMatcherForRegisterUser = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(RegisterUserContract.CONTENT_AUTHORITY, AttractionsContract.PATH_ATTRACTIONS, USERS);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mRegisterUserDBHelper = new RegisterUserDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor queryCursor;

        int matchUri = sUriMatcherForRegisterUser.match(uri);
        switch (matchUri) {
            case USERS:
                String userEmail = RegisterUserContract.RegistrationUserEntry.getEmailFromParam(uri);
                if (!TextUtils.isEmpty(userEmail)) {
                    selection = sRegisterUserSelection;
                    selectionArgs = new String[]{userEmail};
                }
                queryCursor = mRegisterUserDBHelper.getReadableDatabase().query(RegisterUserContract.RegistrationUserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mRegisterUserDBHelper.getWritableDatabase(); //To insert to database, Take a writable Database

        Uri insertedUri;
        long _id = db.insert(AttractionsContract.AttractionEntry.TABLE_NAME, null, contentValues); //insert method return id of an entry
        if (_id > 0) { //if insert process was success, the id always greater than 0 since we go the id with autoincrease
           insertedUri = AttractionsContract.AttractionEntry.buildAttractionUri(_id);
        }
        else {
           throw new SQLException("Failed to insert row into " + uri);
            }


        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);   //automatic notify by ContentResolver//table 1 khu lone ko point lote hter tae URI //from parameter****************Why we use uri from parameter**** notifyChange method is checking updates that whether something different with parameter uri
        }

        return insertedUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = mRegisterUserDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = RegisterUserContract.RegistrationUserEntry.TABLE_NAME;

        rowDeleted = db.delete(tableName, s, strings);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final SQLiteDatabase db = mRegisterUserDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = RegisterUserContract.RegistrationUserEntry.TABLE_NAME;

        rowUpdated = db.update(tableName, contentValues, s, strings);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }
}
