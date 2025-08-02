package com.example.personaltravel.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.personaltravel.entities.Excursion;
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
public final class ExcursionDAO_Impl implements ExcursionDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Excursion> __insertionAdapterOfExcursion;

  private final EntityDeletionOrUpdateAdapter<Excursion> __deletionAdapterOfExcursion;

  private final EntityDeletionOrUpdateAdapter<Excursion> __updateAdapterOfExcursion;

  public ExcursionDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExcursion = new EntityInsertionAdapter<Excursion>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `excursions` (`excursionID`,`excursionName`,`excursionDate`,`vacationID`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Excursion value) {
        stmt.bindLong(1, value.getExcursionID());
        if (value.getExcursionName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getExcursionName());
        }
        if (value.getExcursionDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getExcursionDate());
        }
        stmt.bindLong(4, value.getVacationID());
      }
    };
    this.__deletionAdapterOfExcursion = new EntityDeletionOrUpdateAdapter<Excursion>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `excursions` WHERE `excursionID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Excursion value) {
        stmt.bindLong(1, value.getExcursionID());
      }
    };
    this.__updateAdapterOfExcursion = new EntityDeletionOrUpdateAdapter<Excursion>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `excursions` SET `excursionID` = ?,`excursionName` = ?,`excursionDate` = ?,`vacationID` = ? WHERE `excursionID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Excursion value) {
        stmt.bindLong(1, value.getExcursionID());
        if (value.getExcursionName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getExcursionName());
        }
        if (value.getExcursionDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getExcursionDate());
        }
        stmt.bindLong(4, value.getVacationID());
        stmt.bindLong(5, value.getExcursionID());
      }
    };
  }

  @Override
  public void insert(final Excursion excursion) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExcursion.insert(excursion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Excursion excursion) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfExcursion.handle(excursion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Excursion excursion) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfExcursion.handle(excursion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Excursion> getAllExcursions() {
    final String _sql = "SELECT * FROM EXCURSIONS ORDER BY excursionID ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfExcursionID = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionID");
      final int _cursorIndexOfExcursionName = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionName");
      final int _cursorIndexOfExcursionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionDate");
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final List<Excursion> _result = new ArrayList<Excursion>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Excursion _item;
        final int _tmpExcursionID;
        _tmpExcursionID = _cursor.getInt(_cursorIndexOfExcursionID);
        final String _tmpExcursionName;
        if (_cursor.isNull(_cursorIndexOfExcursionName)) {
          _tmpExcursionName = null;
        } else {
          _tmpExcursionName = _cursor.getString(_cursorIndexOfExcursionName);
        }
        final String _tmpExcursionDate;
        if (_cursor.isNull(_cursorIndexOfExcursionDate)) {
          _tmpExcursionDate = null;
        } else {
          _tmpExcursionDate = _cursor.getString(_cursorIndexOfExcursionDate);
        }
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        _item = new Excursion(_tmpExcursionID,_tmpExcursionName,_tmpExcursionDate,_tmpVacationID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Excursion> getAssociatedExcursions(final int vacationID) {
    final String _sql = "SELECT * FROM EXCURSIONS WHERE vacationID=? ORDER BY excursionID ASC ;";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, vacationID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfExcursionID = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionID");
      final int _cursorIndexOfExcursionName = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionName");
      final int _cursorIndexOfExcursionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "excursionDate");
      final int _cursorIndexOfVacationID = CursorUtil.getColumnIndexOrThrow(_cursor, "vacationID");
      final List<Excursion> _result = new ArrayList<Excursion>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Excursion _item;
        final int _tmpExcursionID;
        _tmpExcursionID = _cursor.getInt(_cursorIndexOfExcursionID);
        final String _tmpExcursionName;
        if (_cursor.isNull(_cursorIndexOfExcursionName)) {
          _tmpExcursionName = null;
        } else {
          _tmpExcursionName = _cursor.getString(_cursorIndexOfExcursionName);
        }
        final String _tmpExcursionDate;
        if (_cursor.isNull(_cursorIndexOfExcursionDate)) {
          _tmpExcursionDate = null;
        } else {
          _tmpExcursionDate = _cursor.getString(_cursorIndexOfExcursionDate);
        }
        final int _tmpVacationID;
        _tmpVacationID = _cursor.getInt(_cursorIndexOfVacationID);
        _item = new Excursion(_tmpExcursionID,_tmpExcursionName,_tmpExcursionDate,_tmpVacationID);
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
