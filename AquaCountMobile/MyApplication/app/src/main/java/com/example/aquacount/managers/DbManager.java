package com.example.aquacount.managers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.aquacount.models.MeasurementModel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DbManager extends SQLiteOpenHelper {

    private static final String dbname = "ClockMeasurement2.db";

    public DbManager(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table tbl_clocks (clockId bigint, newMeasurement text, timestamp1 timestamp)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_clocks");
        onCreate(db);
    }

    public String addRecord(long s1, String s2, Timestamp s3) {
        SQLiteDatabase db = getConnection();
        SQLiteStatement pstmt = null;

        try {
            String sql = "INSERT INTO tbl_clocks (clockId, newMeasurement, timestamp1) VALUES (?, ?, ?)";
            pstmt = db.compileStatement(sql);

            pstmt.bindLong(1, s1);
            pstmt.bindString(2, s2);
            // Convert milliseconds to seconds before binding the timestamp
            pstmt.bindLong(3, s3.getTime());

            pstmt.execute();

            return "Successfully inserted records";
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void deleteAllFromMeasurementsTable(){
        SQLiteDatabase db = getConnection();
        db.delete("tbl_clocks",null,null);
    }

    public List<MeasurementModel> getDataFromSQLiteDB() {
        List<MeasurementModel> measurementList = new ArrayList<>();

        // Open SQLite db connection
        SQLiteDatabase db = getConnection();

        String tableName = "tbl_clocks";
        String[] columns = {"clockId", "newMeasurement", "timestamp1"};

        Cursor cursor = db.query(tableName, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Long clockId = cursor.getLong(cursor.getColumnIndexOrThrow("clockId"));
            String newMeasurement = cursor.getString(cursor.getColumnIndexOrThrow("newMeasurement"));
            long timestampInMillis = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp1"));

            // Convert timestamp in milliseconds to Timestamp object
            Timestamp timestamp = new Timestamp(timestampInMillis);

            MeasurementModel measurement = new MeasurementModel();
            measurement.setClockId(clockId);
            measurement.setNewMeasurement(newMeasurement);
            measurement.setTimestamp(timestamp);

            measurementList.add(measurement);
        }

        cursor.close();
        db.close();

        return measurementList;
    }

    public SQLiteDatabase getConnection() {
        return getWritableDatabase();
    }
}
