package edu.drury.mcs.Dnav.JavaClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mark93 on 4/17/2016.
 */
public class DnavDBAdapter {

    public static final String DATABASE_NAME = "dnav";
    public static final String TABLE_RESOURCES = "resources";
    public static final String TABLE_RESOURCE_ALIAS = "resource_alias";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_LANDMARKS = "landmarks";
    public static final String TABLE_LANDMARK_ALIAS = "landmark_alias";
    public static final String TABLE_TIMESTAMPS = "table_timestamps";
    public static final String TABLE_FAQS = "faqs";
    public static final int DATABASE_VERSION = 7;

    //faq table info
    public static final String FID = "faqs_id";
    public static final String FNAME = "name";
    public static final String FURL = "url";
    public static final String CREATE_TABLE_FAQS = "CREATE TABLE " + TABLE_FAQS
            + " ("
            + FID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FNAME + " VARCHAR(255) NOT NULL,"
            + FURL + " VARCHAR(512) NOT NULL"
            + ");";

    //Landmark table info
    public static final String LID = "landmark_id";
    public static final String LNAME = "name";
    public static final String LDESCRIPTION = "description";
    public static final String LTYPE = "type";
    public static final String LLAT = "lat";
    public static final String LLNG = "lng";
    public static final String CREATE_TABLE_LANDMARKS = "CREATE TABLE " + TABLE_LANDMARKS
            + " ("
            + LID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LNAME + " VARCHAR(255) NOT NULL,"
            + LDESCRIPTION + " VARCHAR(512) NOT NULL,"
            + LTYPE + " INTEGER,"
            + LLAT + " REAL,"
            + LLNG + " REAL"
            + ");";

    //Landmark alias table info
    public static final String LA_LANDMARK_ID = "landmark_id";
    public static final String LA_ALIAS_ID  = "alias_id";
    public static final String LA_ALIAS = "alias";
    public static final String CREATE_TABLE_LANDMARK_ALIAS = "CREATE TABLE " + TABLE_LANDMARK_ALIAS
            + " ("
            + LA_ALIAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LA_ALIAS + " VARCHAR(255),"
            + LA_LANDMARK_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + LA_LANDMARK_ID + ") REFERENCES "  + TABLE_LANDMARKS + " (" + LID + ")"
            + ");";

    //Resources table info
    public static final String RID = "resource_id";
    public static final String RNAME = "name";
    public static final String RDESCRIPTION = "description";
    public static final String RLANDMARK_ID = "landmark_id";
    public static final String CREATE_TABLE_RESOURCES = "CREATE TABLE " + TABLE_RESOURCES
            + " ("
            + RID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + RNAME + " VARCHAR(255) NOT NULL,"
            + RDESCRIPTION + " VARCHAR(512) NOT NULL,"
            + RLANDMARK_ID + " INTEGER,"
            + "FOREIGN KEY (" + RLANDMARK_ID + ") REFERENCES " + TABLE_LANDMARKS + " (" + LID + ")"
            + ");";

    //Resource alias table info
    public static final String RA_LANDMARK_ID = "resource_id";
    public static final String RA_ALIAS_ID  = "alias_id";
    public static final String RA_ALIAS = "alias";
    public static final String CREATE_TABLE_RESOURCE_ALIAS = "CREATE TABLE " + TABLE_RESOURCE_ALIAS
            + " ("
            + RA_ALIAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + RA_ALIAS + " VARCHAR(255),"
            + RA_LANDMARK_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + RA_LANDMARK_ID + ") REFERENCES "  + TABLE_RESOURCES + " (" + RID + ")"
            + ");";

    //Contacts table info
    public static final String CID = "contact_id";
    public static final String CNAME = "name";
    public static final String CPHONE = "phone";
    public static final String CEMAIL = "email";
    public static final String CADDRESS = "address";
    public static final String CRESOURCE_ID = "resource_id";
    public static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS
            + " ("
            + CID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CNAME + " VARCHAR(255) NOT NULL,"
            + CPHONE + " VARCHAR(255),"
            + CEMAIL + " VARCHAR(255),"
            + CADDRESS + " VARCHAR(255),"
            + CRESOURCE_ID + " INTEGER,"
            + "FOREIGN KEY (" + CRESOURCE_ID + ") REFERENCES " + TABLE_RESOURCES + " (" + RID + ")"
            + ");";

    //Table_Timestamps table info
    public static final String TNAME = "table_name";
    public static final String TCOUNTER = "update_counter";
    public static final String TTIME = "latest_timestamp";
    public static final String CREATE_TABLE_TIMESTAMPS = "CREATE TABLE " + TABLE_TIMESTAMPS
            + " ("
            + TNAME + " VARCHAR(255) PRIMARY KEY,"
            + TCOUNTER + " INTEGER,"
            + TTIME + " TIMESTAMP"
            + ");";

    public static final String DROP_TABLE_CONTACTS = "DROP TABLE IF EXISTS " + TABLE_CONTACTS;
    public static final String DROP_TABLE_RESOURCES = "DROP TABLE IF EXISTS " + TABLE_RESOURCES;
    public static final String DROP_TABLE_LANDMARKS = "DROP TABLE IF EXISTS " + TABLE_LANDMARKS;

    DnavDBHelper helper;
    //long id;

    public DnavDBAdapter(Context context) {
        helper = new DnavDBHelper(context);
    }

    public SQLiteDatabase getWritableDB(){

        return helper.getWritableDatabase();
    }

    public SQLiteDatabase getReadOnlyDB(){
        return helper.getReadableDatabase();
    }

    class DnavDBHelper extends SQLiteOpenHelper {

        private Context context;

        public DnavDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            System.out.println("constructor called");

        }

//        private long insertLandmarksData(SQLiteDatabase db) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(LNAME, "Findlay Student Center");
//            contentValues.put(LDESCRIPTION, "Student Affairs");
//            contentValues.put(LTYPE, "I dont know");
//            contentValues.put(LLAT, 37.221062);
//            contentValues.put(LLNG, -93.284961);
//            id = db.insert(TABLE_LANDMARKS, null, contentValues);
//            return id;
//        }
//
//        private long insertResourcesData(SQLiteDatabase db) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(RNAME, "Greek Life");
//            contentValues.put(RDESCRIPTION, "Anything about greek life");
//            contentValues.put(RLANDMARK_ID, 1);
//            id = db.insert(TABLE_RESOURCES, null, contentValues);
//            return id;
//        }
//
//        private long insertContactsData(SQLiteDatabase db) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(CNAME, "General Contact Info");
//            contentValues.put(CPHONE, "(417)873-3061");
//            contentValues.put(CEMAIL, "greeks@drury.edu");
//            contentValues.put(CADDRESS, "Findlay Student Center 124");
//            contentValues.put(CRESOURCE_ID, 1);
//            id = db.insert(TABLE_CONTACTS, null, contentValues);
//            return id;
//        }

        private ArrayList<Long> insertInitialTimeStampData(SQLiteDatabase db) {

            ArrayList<Long> errorCodeList = new ArrayList<>();
            long id;

            ContentValues contentValues = new ContentValues();
            contentValues.put(TNAME, TABLE_LANDMARKS);
            contentValues.put(TCOUNTER, 0);
            contentValues.put(TTIME, "");
            id = db.insert(TABLE_TIMESTAMPS, null, contentValues);
            errorCodeList.add(id);

            contentValues = new ContentValues();
            contentValues.put(TNAME, TABLE_LANDMARK_ALIAS);
            contentValues.put(TCOUNTER, 0);
            contentValues.put(TTIME, "");
            id = db.insert(TABLE_TIMESTAMPS, null, contentValues);
            errorCodeList.add(id);

            contentValues = new ContentValues();
            contentValues.put(TNAME, TABLE_RESOURCES);
            contentValues.put(TCOUNTER, 0);
            contentValues.put(TTIME, "");
            id = db.insert(TABLE_TIMESTAMPS, null, contentValues);
            errorCodeList.add(id);

            contentValues = new ContentValues();
            contentValues.put(TNAME, TABLE_RESOURCE_ALIAS);
            contentValues.put(TCOUNTER, 0);
            contentValues.put(TTIME, "");
            id = db.insert(TABLE_TIMESTAMPS, null, contentValues);
            errorCodeList.add(id);

            contentValues = new ContentValues();
            contentValues.put(TNAME, TABLE_CONTACTS);
            contentValues.put(TCOUNTER, 0);
            contentValues.put(TTIME, "");
            id = db.insert(TABLE_TIMESTAMPS, null, contentValues);
            errorCodeList.add(id);

            contentValues = new ContentValues();
            contentValues.put(TNAME, TABLE_FAQS);
            contentValues.put(TCOUNTER, 0);
            contentValues.put(TTIME, "");
            id = db.insert(TABLE_TIMESTAMPS, null, contentValues);
            errorCodeList.add(id);

            return errorCodeList;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_LANDMARKS);
                System.out.println("onCreate Landmarks called");

//                if (insertLandmarksData(sqLiteDatabase) < 0) {
//                    System.out.println(insertLandmark Unsuccessful");
//                } else {
//                    System.out.println(Successfully inserted into Landmark");
//                }


            } catch (SQLException e) {

                System.out.println("" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_RESOURCES);
                System.out.println("onCreate resources called");

//                if (insertResourcesData(sqLiteDatabase) < 0) {
//                    System.out.println(insertResource Unsuccessful");
//                } else {
//                    System.out.println(Successfully inserted into Resource");
//                }

            } catch (SQLException e) {
                System.out.println("" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_CONTACTS);
                System.out.println("onCreate contacts called");

//                if (insertContactsData(sqLiteDatabase) < 0) {
//                    System.out.println(insertContact Unsuccessful");
//                } else {
//                    System.out.println(Successfully inserted into Contact");
//                }

            } catch (SQLException e) {
                System.out.println("" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_FAQS);
                System.out.println("onCreate contacts called");

//                if (insertContactsData(sqLiteDatabase) < 0) {
//                    System.out.println(insertContact Unsuccessful");
//                } else {
//                    System.out.println(Successfully inserted into Contact");
//                }

            } catch (SQLException e) {
                System.out.println("" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_RESOURCE_ALIAS);
                System.out.println("onCreate contacts called");

//                if (insertContactsData(sqLiteDatabase) < 0) {
//                    System.out.println(insertContact Unsuccessful");
//                } else {
//                    System.out.println(Successfully inserted into Contact");
//                }

            } catch (SQLException e) {
                System.out.println("" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_LANDMARK_ALIAS);
                System.out.println("onCreate contacts called");

//                if (insertContactsData(sqLiteDatabase) < 0) {
//                    System.out.println(insertContact Unsuccessful");
//                } else {
//                    System.out.println(Successfully inserted into Contact");
//                }

            } catch (SQLException e) {
                System.out.println("" + e);
                e.printStackTrace();
            }

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_TIMESTAMPS);
                System.out.println("onCreate contacts called");

                for(Long id : insertInitialTimeStampData(sqLiteDatabase)){
                    if (id < 0) {
                        System.out.println("Initialize timestamp table unsuccessful: " + id);
                    } else {
                        System.out.println("Successfully inserted into Contact");
                    }
                }

            } catch (SQLException e) {
                System.out.println("" + e);
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            try {
                System.out.println("onUpgrade called");
//                sqLiteDatabase.execSQL(DROP_TABLE_CONTACTS);
//                sqLiteDatabase.execSQL(DROP_TABLE_LANDMARKS);
//                sqLiteDatabase.execSQL(DROP_TABLE_RESOURCES);
//                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                System.out.println("" + e);
                e.printStackTrace();

            }

        }

    }

}
