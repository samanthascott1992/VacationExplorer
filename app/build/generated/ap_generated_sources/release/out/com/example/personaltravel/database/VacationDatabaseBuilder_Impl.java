package com.example.personaltravel.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.personaltravel.dao.ExcursionDAO;
import com.example.personaltravel.dao.ExcursionDAO_Impl;
import com.example.personaltravel.dao.VacationDAO;
import com.example.personaltravel.dao.VacationDAO_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VacationDatabaseBuilder_Impl extends VacationDatabaseBuilder {
  private volatile VacationDAO _vacationDAO;

  private volatile ExcursionDAO _excursionDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `vacations` (`vacationID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `vacationName` TEXT, `hotelName` TEXT, `startDate` TEXT, `endDate` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `excursions` (`excursionID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `excursionName` TEXT, `excursionDate` TEXT, `vacationID` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '191d2b67d3f5cb3951666069c1deab5c')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `vacations`");
        _db.execSQL("DROP TABLE IF EXISTS `excursions`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsVacations = new HashMap<String, TableInfo.Column>(5);
        _columnsVacations.put("vacationID", new TableInfo.Column("vacationID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVacations.put("vacationName", new TableInfo.Column("vacationName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVacations.put("hotelName", new TableInfo.Column("hotelName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVacations.put("startDate", new TableInfo.Column("startDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVacations.put("endDate", new TableInfo.Column("endDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVacations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVacations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVacations = new TableInfo("vacations", _columnsVacations, _foreignKeysVacations, _indicesVacations);
        final TableInfo _existingVacations = TableInfo.read(_db, "vacations");
        if (! _infoVacations.equals(_existingVacations)) {
          return new RoomOpenHelper.ValidationResult(false, "vacations(com.example.personaltravel.entities.Vacation).\n"
                  + " Expected:\n" + _infoVacations + "\n"
                  + " Found:\n" + _existingVacations);
        }
        final HashMap<String, TableInfo.Column> _columnsExcursions = new HashMap<String, TableInfo.Column>(4);
        _columnsExcursions.put("excursionID", new TableInfo.Column("excursionID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExcursions.put("excursionName", new TableInfo.Column("excursionName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExcursions.put("excursionDate", new TableInfo.Column("excursionDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExcursions.put("vacationID", new TableInfo.Column("vacationID", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExcursions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExcursions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExcursions = new TableInfo("excursions", _columnsExcursions, _foreignKeysExcursions, _indicesExcursions);
        final TableInfo _existingExcursions = TableInfo.read(_db, "excursions");
        if (! _infoExcursions.equals(_existingExcursions)) {
          return new RoomOpenHelper.ValidationResult(false, "excursions(com.example.personaltravel.entities.Excursion).\n"
                  + " Expected:\n" + _infoExcursions + "\n"
                  + " Found:\n" + _existingExcursions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "191d2b67d3f5cb3951666069c1deab5c", "1f55975fe31d4c39e84bbee9ccd0ecce");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "vacations","excursions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `vacations`");
      _db.execSQL("DELETE FROM `excursions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(VacationDAO.class, VacationDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExcursionDAO.class, ExcursionDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public VacationDAO vacationDAO() {
    if (_vacationDAO != null) {
      return _vacationDAO;
    } else {
      synchronized(this) {
        if(_vacationDAO == null) {
          _vacationDAO = new VacationDAO_Impl(this);
        }
        return _vacationDAO;
      }
    }
  }

  @Override
  public ExcursionDAO excursionDAO() {
    if (_excursionDAO != null) {
      return _excursionDAO;
    } else {
      synchronized(this) {
        if(_excursionDAO == null) {
          _excursionDAO = new ExcursionDAO_Impl(this);
        }
        return _excursionDAO;
      }
    }
  }
}
