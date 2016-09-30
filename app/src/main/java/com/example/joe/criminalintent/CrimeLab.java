package com.example.joe.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.UUID;

import database.CrimeDbSchema.CrimeBaseHelper;
import database.CrimeDbSchema.CrimeCursorWrapper;


import static database.CrimeDbSchema.CrimeDbSchema.*;

/**
 * Created by joe on 16/9/20.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {

        mCrimes = new ArrayList<>();
//        mContext = context.getApplicationContext();
//        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    /**
     * 便利查询crimes
     * @return crimes数组
     */
    public List<Crime> getCrimes() {
        return mCrimes;
//        List<Crime> crimes = new ArrayList<>();
//
//        CrimeCursorWrapper cursor = queryCrimes(null, null); //查询所有
//        try {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                crimes.add(cursor.getCrime());
//                cursor.moveToNext();
//            }
//        } finally {
//            cursor.close();
//        }
//        return crimes;

    }

    /**
     * 添加
     * @param crime
     */
    public void addCrime(Crime crime) {
        mCrimes.add(crime);
//        ContentValues values = getContentValues(crime);
//        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    /**
     * 删除
     */
    public void deleteCrime(Crime crime) {
        mCrimes.remove(crime);
//        String uuidString = crimeId.toString();
//        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?", new String[] {uuidString});
    }


    /**
     * 读取首条记录
     */
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
//        CrimeCursorWrapper cursor = queryCrimes(
//                CrimeTable.Cols.UUID + " = ?",
//                new String[]{id.toString()}
//        );
//
//        try{
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//
//            cursor.moveToFirst();
//            return cursor.getCrime();
//        }finally {
//            cursor.close();
//        }



    }

    /**
     * 更新
     * @param crime
     */
//    public void updateCcrime(Crime crime) {
//        String uuidString = crime.getId().toString();
//        ContentValues values = getContentValues(crime);
//
//        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new
//                String[]{uuidString});
//    }

    /**
     * 封装ContentValues包装操作
     * @param crime
     * @return
     */
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().toString());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    /**
     * 查询
     * @param whereClause
     * @param whereArgs
     * @return
     */
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, //group by
                null, //having
                null //order by
        );
        return new CrimeCursorWrapper(cursor);
    }
}
