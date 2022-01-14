package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class myDbAdapter {

    // Deklarasikan class myDbHelper
//    myDbHelper myhelper;
    myDbHelper myhelper;

    // Constructor
    public myDbAdapter(Context context) {
        myhelper = new myDbHelper(context);
    }

    // Digunakan untuk memasukkan value username dan password
    // ke dalam table di SQLite
    public long insertData(String name, String pass) {

        // Panggil DB
        SQLiteDatabase dbb = myhelper.getWritableDatabase();

        // Kita gunakan untuk menyimpan nama kolom dan value yang ingin disimpan
        ContentValues contentValues = new ContentValues();
        // Parameter pertama adalah nama kolom table
        // Parameter kedua adalah value yang ingin disimpan
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);

        // Lakukan fungsi insert
        // Kemudian simpan id user yang baru berhasil diinsert
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);

        return id;
    }



//    // Buat Constructor
//    public myDbAdapter(Context context) {
//        myhelper = new myDbHelper(context);
//    }
//
//    // Fungsi untuk memasukkan data ke dalam SQLite
//    public long insertData(String name, String pass) {
//
//        // Panggil database myDatabase
//        SQLiteDatabase dbb = myhelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        // Masukkan variable name ke dalam Kolom NAME di database
//        contentValues.put(myDbHelper.NAME, name);
//        // Masukkan variable password ke dalam Kolom MyPASSWORD di database
//        contentValues.put(myDbHelper.MyPASSWORD, pass);
//
//        // Masukkan contentValues ke dalam table myTable
//        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
//
//        return id;
//    }

    public String getData() {

        // Panggil database dengan nama myDatabase
        SQLiteDatabase db = myhelper.getWritableDatabase();

        // Panggil 3 kolom berikut
        String[] columns = {myDbHelper.UID, myDbHelper.NAME, myDbHelper.MyPASSWORD};

        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns,null,null,
                null,null,null);

        // Panggil StringBuffer
        // Mengonversi suatu jenis data menjadi tipe data string
        StringBuffer buffer = new StringBuffer();

        // Perulangan untuk mengambil semua data myDbHelper.UID, myDbHelper.NAME, myDbHelper.MyPASSWORD
        // dari table
        while (cursor.moveToNext()) {

            // Ambil data ID
            int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            // Ambil data NAME
            String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            // Ambil data PASSWORD
            String password = cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));

            // Buat struktur string outputnya
            buffer.append(cid + "   " + name + "   " + password +" \n");
        }

        return buffer.toString();
    }

    // Konfigurasi SQLite
    // implementasi dari class SQLiteOpenHelper
    static class myDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String MyPASSWORD= "Password";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
                " ("+ UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME +" VARCHAR(255) ,"+ MyPASSWORD +" VARCHAR(225));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context,"" + e,
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context,"OnUpgrade",
                        Toast.LENGTH_LONG).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Toast.makeText(context,"" + e,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
