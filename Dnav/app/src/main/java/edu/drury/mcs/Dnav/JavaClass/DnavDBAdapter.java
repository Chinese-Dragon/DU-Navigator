package edu.drury.mcs.Dnav.JavaClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mark93 on 4/17/2016.
 */
public class DnavDBAdapter {

    DnavDBHelper helper;
    long id;

    public DnavDBAdapter(Context context) {
        helper = new DnavDBHelper(context);
    }

    public SQLiteDatabase createDB(){

        return helper.getWritableDatabase();
    }


    class DnavDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "dnav";
        private static final String TABLE_RESOURCES = "resources";
        private static final String TABLE_CONTACTS = "contacts";
        private static final String TABLE_LANDMARKS = "landmarks";
        private static final int DATABASE_VERSION = 7;
        private Context context;

        //Landmark table info
        private static final String LID = "landmark_id";
        private static final String LNAME = "name";
        private static final String LDESCRIPTION = "description";
        private static final String LTYPE = "type";
        private static final String LLAT = "lat";
        private static final String LLNG = "lng";
        private static final String CREATE_TABLE_LANDMARKS = "CREATE TABLE " + TABLE_LANDMARKS
                + " ("
                + LID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LNAME + " VARCHAR(255) NOT NULL,"
                + LDESCRIPTION + " VARCHAR(512) NOT NULL,"
                + LTYPE + " VARCHAR(255),"
                + LLAT + " REAL,"
                + LLNG + " REAL"
                + ");";

        //Resources table info
        private static final String RID = "resource_id";
        private static final String RNAME = "name";
        private static final String RDESCRIPTION = "description";
        private static final String RLANDMARK_ID = "landmark_id";
        private static final String CREATE_TABLE_RESOURCES = "CREATE TABLE " + TABLE_RESOURCES
                + " ("
                + RID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RNAME + " VARCHAR(255) NOT NULL,"
                + RDESCRIPTION + " VARCHAR(512) NOT NULL,"
                + RLANDMARK_ID + " INTEGER,"
                + "FOREIGN KEY (" + RLANDMARK_ID + ") REFERENCES " + TABLE_LANDMARKS + " (" + LID + ")"
                + ");";

        //Contacts table info
        private static final String CID = "contact_id";
        private static final String CNAME = "name";
        private static final String CPHONE = "phone";
        private static final String CEMAIL = "email";
        private static final String CADDRESS = "address";
        private static final String CRESOURCE_ID = "resource_id";
        private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS
                + " ("
                + CID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CNAME + " VARCHAR(255) NOT NULL,"
                + CPHONE + " VARCHAR(255),"
                + CEMAIL + " VARCHAR(255),"
                + CADDRESS + " VARCHAR(255),"
                + CRESOURCE_ID + " INTEGER,"
                + "FOREIGN KEY (" + CRESOURCE_ID + ") REFERENCES " + TABLE_RESOURCES + " (" + RID + ")"
                + ");";

        private static final String DROP_TABLE_CONTACTS = "DROP TABLE IF EXISTS " + TABLE_CONTACTS;
        private static final String DROP_TABLE_RESOURCES = "DROP TABLE IF EXISTS " + TABLE_RESOURCES;
        private static final String DROP_TABLE_LANDMARKS = "DROP TABLE IF EXISTS " + TABLE_LANDMARKS;

        public DnavDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Message.message(context, "constructor called");

        }

        private long insertLandmarksData(SQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DnavDBHelper.LNAME, "Findlay Student Center");
            contentValues.put(DnavDBHelper.LDESCRIPTION, "Student Affairs");
            contentValues.put(DnavDBHelper.LTYPE, "I dont know");
            contentValues.put(DnavDBHelper.LLAT, 37.221062);
            contentValues.put(DnavDBHelper.LLNG, -93.284961);
            id = db.insert(DnavDBHelper.TABLE_LANDMARKS, null, contentValues);
            return id;
        }

        private long insertResourcesData(SQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DnavDBHelper.RNAME, "Greek Life");
            contentValues.put(DnavDBHelper.RDESCRIPTION, "Anything about greek life");
            contentValues.put(DnavDBHelper.RLANDMARK_ID, 1);
            id = db.insert(DnavDBHelper.TABLE_RESOURCES, null, contentValues);
            return id;
        }

        private long insertContactsData(SQLiteDatabase db) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DnavDBHelper.CNAME, "General Contact Info");
            contentValues.put(DnavDBHelper.CPHONE, "(417)873-3061");
            contentValues.put(DnavDBHelper.CEMAIL, "greeks@drury.edu");
            contentValues.put(DnavDBHelper.CADDRESS, "Findlay Student Center 124");
            contentValues.put(DnavDBHelper.CRESOURCE_ID, 1);
            id = db.insert(DnavDBHelper.TABLE_CONTACTS, null, contentValues);
            return id;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_LANDMARKS);
                Message.message(context, "onCreate Landmarks called");

                if (insertLandmarksData(sqLiteDatabase) < 0) {
                    Message.message(context,"insertLandmark Unsuccessful");
                } else {
                    Message.message(context,"Successfully inserted into Landmark");
                }


            } catch (SQLException e) {

                Message.message(context, "" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_RESOURCES);
                Message.message(context, "onCreate resources called");

                if (insertResourcesData(sqLiteDatabase) < 0) {
                    Message.message(context,"insertResource Unsuccessful");
                } else {
                    Message.message(context,"Successfully inserted into Resource");
                }

            } catch (SQLException e) {
                Message.message(context, "" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_CONTACTS);
                Message.message(context, "onCreate contacts called");

                if (insertContactsData(sqLiteDatabase) < 0) {
                    Message.message(context,"insertContact Unsuccessful");
                } else {
                    Message.message(context,"Successfully inserted into Contact");
                }

            } catch (SQLException e) {
                Message.message(context, "" + e);
                e.printStackTrace();
            }



        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            try {
                Message.message(context, "onUpgrade called");
                sqLiteDatabase.execSQL(DROP_TABLE_CONTACTS);
                sqLiteDatabase.execSQL(DROP_TABLE_LANDMARKS);
                sqLiteDatabase.execSQL(DROP_TABLE_RESOURCES);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Message.message(context, "" + e);
                e.printStackTrace();

            }

        }

    }

}
