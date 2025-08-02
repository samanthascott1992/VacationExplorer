package com.example.personaltravel.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.personaltravel.entities.Vacation;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VacationDAO_Impl implements VacationDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Vacation> __insertionAdapterOfVacation;

  private final EntityDeletionOrUpdateAdapter<Vacation> __deletionAdapterOfVacation;

  private final EntityDeletionOrUpdateAdapter<Vacation> __updateAdapterOfVacation;

  public VacationDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVacation = new EntityInsertionAdapter<Vacation>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `vacations` (`vacationID`,`vacationName`,`hotelName`,`startDate`,`endDate`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vacation value) {
        stmt.bindLong(1, value.getVacationID());
        if (value.getVacationName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVacationName());
        }
        if (value.getHotelName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getHotelName());
        }
        if (value.getStartDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStartDate());
        }
        if (value.getEndDate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEndDate());
        }
      }
    };
    this.__deletionAdapterOfVacation = new EntityDeletionOrUpdateAdapter<Vacation>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `vacations` WHERE `vacationID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vacation value) {
        stmt.bindLong(1, value.getVacationID());
      }
    };
    this.__updateAdapterOfVacation = new EntityDeletionOrUpdateAdapter<Vacation>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `vacations` SET `vacationID` = ?,`vacationName` = ?,`hotelName` = ?,`startDate` = ?,`endDate` = ? WHERE `vacationID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vacation value) {
        stmt.bindLong(1, value.getVacationID());
        if (value.getVacationName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVacationName());
        }
        if (value.getHotelName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getHotelName());
        }
        if (value.getStartDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStartDate());
        }
        if (value.getEndDate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEndDate());
        }
        stmt.bindLong(6, value.getVacationID());
      }
    };
  }

  @Override
  public void insert(final Vacation vacation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfVacation.insert(vacation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Vacation vacation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfVacation.handle(vacation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Vacation vacation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfVacation.handle(vacation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Vacation> getmAllVacations() {
    final String _sql = "SELECT * FROM VACATIONS ORDER BY vacationID ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final int _cursorIndexOfVacationName = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationName");
      final int _cursorIndexOfHotelName = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelName");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final List<Vacation> _result = new ArrayList<Vacation>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Vacation _item;
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        final String _tmpVacationName;
        if (_cursor.isNull(_cursorIndexOfVacationName)) {
          _tmpVacationName = null;
        } else {
          _tmpVacationName = _cursor.getString(_cursorIndexOfVacationName);
        }
        final String _tmpHotelName;
        if (_cursor.isNull(_cursorIndexOfHotelName)) {
          _tmpHotelName = null;
        } else {
          _tmpHotelName = _cursor.getString(_cursorIndexOfHotelName);
        }
        final String _tmpStartDate;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmpStartDate = null;
        } else {
          _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
        }
        final String _tmpEndDate;
        if (_cursor.isNull(_cursorIndexOfEndDate)) {
          _tmpEndDate = null;
        } else {
          _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
        }
        _item = new Vacation(_tmpVacationID,_tmpVacationName,_tmpHotelName,_tmpStartDate,_tmpEndDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
