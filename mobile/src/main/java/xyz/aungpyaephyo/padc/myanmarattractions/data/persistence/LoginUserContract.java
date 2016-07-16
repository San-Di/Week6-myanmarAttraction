package xyz.aungpyaephyo.padc.myanmarattractions.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by UNiQUE on 7/16/2016.
 */
public class LoginUserContract {
    public static final String CONTENT_AUTHORITY = MyanmarAttractionsApp.class.getPackage().getName(); //default (Step 1.)
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);//default

    public static final String PATH_ATTRACTIONS = "attractions";
    public static final String PATH_ATTRACTION_IMAGES = "attraction_images";

    public static final class AttractionEntry implements BaseColumns {  //step 2. create entry objects for each data type //AttractionEntry class for attractions path and implement BaseColumns
        public static final Uri CONTENT_URI =                           //needed***************
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ATTRACTIONS).build(); //contents//xyz.aungpyaephyo.padc.myanmarattractions/attractions

        public static final String DIR_TYPE =                        //needed*************** DIR = directory
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATTRACTIONS; //When return row is more than one***********

        public static final String ITEM_TYPE =                           //needed***************
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATTRACTIONS;

        public static final String TABLE_NAME = "attractions";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "desc";

        public static Uri buildAttractionUri(long id) {  // HelperMethods That is needed for every entry *********   // Search with Id ,,, If you wanna find with title, the parameter with be Title
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions/1
            return ContentUris.withAppendedId(CONTENT_URI, id); //insert lote tine ID return pyan
        }

        public static Uri buildAttractionUriWithTitle(String attractionTitle) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attractions?title="Yangon"
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TITLE, attractionTitle)
                    .build();
        }

        public static String getTitleFromParam(Uri uri) {   // ya lar tae Uri ko thone Pee, Title value ko return pyan
            return uri.getQueryParameter(COLUMN_TITLE);
        }
    }

    public static final class AttractionImageEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ATTRACTION_IMAGES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATTRACTION_IMAGES;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ATTRACTION_IMAGES;

        public static final String TABLE_NAME = "attraction_images";

        public static final String COLUMN_ATTRACTION_TITLE = "attraction_title";
        public static final String COLUMN_IMAGE = "image";

        public static Uri buildAttractionImageUri(long id) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildAttractionImageUriWithAttractionTitle(String attractionTitle) {
            //content://xyz.aungpyaephyo.padc.myanmarattractions/attraction_images?attraction_title=Yangon
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_ATTRACTION_TITLE, attractionTitle)
                    .build();
        }

        public static String getAttractionTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_ATTRACTION_TITLE);
        }
    }
}
