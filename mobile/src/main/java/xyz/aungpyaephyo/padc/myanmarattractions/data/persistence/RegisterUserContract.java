package xyz.aungpyaephyo.padc.myanmarattractions.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class RegisterUserContract {
    public static final String CONTENT_AUTHORITY = MyanmarAttractionsApp.class.getPackage().getName(); //default (Step 1.)
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);//default

    public static final String PATH_USERS = "users";
//    public static final String PATH_ATTRACTION_IMAGES = "attraction_images";

    public static final class RegistrationUserEntry implements BaseColumns {  //step 2. create entry objects for each data type //AttractionEntry class for attractions path and implement BaseColumns
        public static final Uri CONTENT_URI =                           //needed***************
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build(); //contents//xyz.aungpyaephyo.padc.myanmarattractions/attractions

        public static final String DIR_TYPE =                        //needed*************** DIR = directory
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS; //When return row is more than one***********

        public static final String ITEM_TYPE =                           //needed***************
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String TABLE_NAME = "users";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_BIRTH_DATE = "date_of_birth";
        public static final String COLUMN_COUNTRY = "country_of_origin";

        public static Uri buildAttractionUri(long id) {  // HelperMethods That is needed for every entry *********   // Search with Id ,,, If you wanna find with title, the parameter with be Title
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions/1
            return ContentUris.withAppendedId(CONTENT_URI, id); //insert lote tine ID return pyan
        }

        public static Uri buildRegisteractionUserUriWithEmail(String email) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_EMAIL, email)
                    .build();
        }

        public static String getEmailFromParam(Uri uri) {   // ya lar tae Uri ko thone Pee, Title value ko return pyan
            return uri.getQueryParameter(COLUMN_EMAIL);
        }
    }
}
