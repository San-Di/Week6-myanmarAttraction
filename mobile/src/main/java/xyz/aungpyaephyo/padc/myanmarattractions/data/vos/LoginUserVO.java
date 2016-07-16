package xyz.aungpyaephyo.padc.myanmarattractions.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.AttractionsContract;
import xyz.aungpyaephyo.padc.myanmarattractions.data.persistence.RegisterUserContract;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class LoginUserVO {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("country_of_origin")
    private String countryOrigin;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public static void saveUser(LoginUserVO loginUser) {
        Context context = MyanmarAttractionsApp.getContext();
        ContentValues loginUserCV = new ContentValues();

            loginUserCV = loginUser.parseToContentValues();
        //Bulk insert into attractions.
//        int insertedCount = context.getContentResolver().bulkInsert(AttractionsContract.AttractionEntry.CONTENT_URI, attractionCVs);
//
//        Log.d(MyanmarAttractionsApp.TAG, "Bulk inserted into attraction table : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(RegisterUserContract.RegistrationUserEntry.COLUMN_TITLE, title);
        cv.put(AttractionsContract.AttractionEntry.COLUMN_DESC, desc);
        return cv;
    }

    public static AttractionVO parseFromCursor(Cursor data) {
        AttractionVO attraction = new AttractionVO();
        attraction.title = data.getString(data.getColumnIndex(AttractionsContract.AttractionEntry.COLUMN_TITLE));
        attraction.desc = data.getString(data.getColumnIndex(AttractionsContract.AttractionEntry.COLUMN_DESC));
        return attraction;
    }

    public static String[] loadAttractionImagesByTitle(String title) {
        Context context = MyanmarAttractionsApp.getContext();
        ArrayList<String> images = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(AttractionsContract.AttractionImageEntry.buildAttractionImageUriWithAttractionTitle(title),
                null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            do {
                images.add(cursor.getString(cursor.getColumnIndex(AttractionsContract.AttractionImageEntry.COLUMN_IMAGE)));
            } while (cursor.moveToNext());
        }

        String[] imageArray = new String[images.size()];
        images.toArray(imageArray);
        return imageArray;
    }
}
