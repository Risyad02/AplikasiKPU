package com.hacktiv8.aplikasikpu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String database_name = "db_kpu";
    public static final String table_name = "tabel_pemilih";

    public static final String row_id = "_id";
    public static final String row_nomorNik = "NomorNik";
    public static final String row_nama = "Nama";
    public static final String row_jk = "JK";
    public static final String row_nomorhp = "NomorHp";
    public static final String row_tanggal = "Tanggal";
    public static final String row_alamat = "Alamat";
    public static final String row_foto = "Foto";

    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nomorNik + " TEXT, " + row_nama + " TEXT, " + row_jk + " TEXT, "
                + row_nomorhp + " TEXT, " + row_tanggal + " TEXT, " + row_alamat + " TEXT, " + row_foto + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    //Ambil SQLite Data
    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name, null);
        return cur;
    }

    //Ambil 1 Data By ID
    public Cursor oneData(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data ke Database
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }
}


