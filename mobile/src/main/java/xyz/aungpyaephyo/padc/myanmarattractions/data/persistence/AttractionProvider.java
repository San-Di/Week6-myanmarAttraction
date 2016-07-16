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
 * Created by aung on 7/10/16.
 */
public class AttractionProvider extends ContentProvider {

    public static final int ATTRACTION = 100;
    public static final int ATTRACTION_IMAGE = 200;

    private static final String sAttractionTitleSelection = AttractionsContract.AttractionEntry.COLUMN_TITLE + " = ?";
    private static final String sAttractionImageSelectionWithTitle = AttractionsContract.AttractionImageEntry.COLUMN_ATTRACTION_TITLE + " = ?";

    private static final UriMatcher sUriMatcher = buildUriMatcher();    // Step 1.
    private AttractionDBHelper mAttractionDBHelper;

    @Override
    public boolean onCreate() {     // Step 2. since DB is under provider , it needs to be initialized in onCreate callback of provider
        mAttractionDBHelper = new AttractionDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) { //selection - columnName/key , selectionArgument - value , sortOrder - return pyan mae dataSet ko manage lote chin tae order (Which column you wanna sort, and which order you wanna sort thaat column)
        Cursor queryCursor;

        int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case ATTRACTION:
                String attractionTitle = AttractionsContract.AttractionEntry.getTitleFromParam(uri);
                if (!TextUtils.isEmpty(attractionTitle)) {
                    selection = sAttractionTitleSelection;
                    selectionArgs = new String[]{attractionTitle};
                }
                queryCursor = mAttractionDBHelper.getReadableDatabase().query(AttractionsContract.AttractionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case ATTRACTION_IMAGE:
                String title = AttractionsContract.AttractionImageEntry.getAttractionTitleFromParam(uri);
                if (title != null) {
                    selection = sAttractionImageSelectionWithTitle;
                    selectionArgs = new String[]{title};
                }
                queryCursor = mAttractionDBHelper.getReadableDatabase().query(AttractionsContract.AttractionImageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
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
    public String getType(Uri uri) { // According to Uri, getType method returns MANY or SINGLE
        final int matchUri = sUriMatcher.match(uri); // return constants of 100 or 200 means that since sUriMatch
        switch (matchUri) {
            case ATTRACTION:
                return AttractionsContract.AttractionEntry.DIR_TYPE;
            case ATTRACTION_IMAGE:
                return AttractionsContract.AttractionImageEntry.DIR_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri); // just in case we forgot to match integer value wit uri
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase(); //To insert to database, Take a writable Database
        final int matchUri = sUriMatcher.match(uri);
        Uri insertedUri;

        switch (matchUri) {
            case ATTRACTION: { //contentValues is a namevalue pair format of column name and value
                long _id = db.insert(AttractionsContract.AttractionEntry.TABLE_NAME, null, contentValues); //insert method return id of an entry
                if (_id > 0) { //if insert process was success, the id always greater than 0 since we go the id with autoincrease
                    insertedUri = AttractionsContract.AttractionEntry.buildAttractionUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ATTRACTION_IMAGE: {
                long _id = db.insert(AttractionsContract.AttractionImageEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = AttractionsContract.AttractionImageEntry.buildAttractionImageUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);   //automatic notify by ContentResolver//table 1 khu lone ko point lote hter tae URI //from parameter****************Why we use uri from parameter**** notifyChange method is checking updates that whether something different with parameter uri
        }

        return insertedUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) { //not necessary, very useful //
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();  // all the processings are sucessfull
        } finally {
            db.endTransaction();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;   // how many database entry to Dataabase
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mAttractionDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {       // ko mhar shi shi tae path(table) 1 khu chin see twat that sine yar constant nat Match lote lite tar
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AttractionsContract.CONTENT_AUTHORITY, AttractionsContract.PATH_ATTRACTIONS, ATTRACTION);
        uriMatcher.addURI(AttractionsContract.CONTENT_AUTHORITY, AttractionsContract.PATH_ATTRACTION_IMAGES, ATTRACTION_IMAGE);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);

        switch (matchUri) {
            case ATTRACTION:
                return AttractionsContract.AttractionEntry.TABLE_NAME;
            case ATTRACTION_IMAGE:
                return AttractionsContract.AttractionImageEntry.TABLE_NAME;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }
}
