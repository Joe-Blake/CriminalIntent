package database.CrimeDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.joe.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

import static database.CrimeDbSchema.CrimeDbSchema.*;

/**
 * Created by joe on 2016/9/28.
 */

public class CrimeCursorWrapper extends CursorWrapper {


    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * 获取相关字段值
     * @return crime对象
     */
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int issolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(issolved != 0);

        return crime;
    }
}
