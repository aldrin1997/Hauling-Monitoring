package com.asdec.hauling.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AppDao _appDao;

  private volatile UserDao _userDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `batches` (`id` TEXT NOT NULL, `companyId` TEXT NOT NULL, `projectId` TEXT NOT NULL, `date` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdBy` TEXT NOT NULL, `isSynced` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trucks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `batchId` TEXT NOT NULL, `haulerName` TEXT NOT NULL, `plateNo` TEXT NOT NULL, `length` REAL NOT NULL, `width` REAL NOT NULL, `height` REAL NOT NULL, `cum` REAL NOT NULL, `timeIn` TEXT NOT NULL, `timeOut` TEXT NOT NULL, `status` TEXT NOT NULL, `isSynced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `photos` (`id` TEXT NOT NULL, `batchId` TEXT NOT NULL, `truckId` TEXT NOT NULL, `photoPath` TEXT NOT NULL, `photoType` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `username` TEXT NOT NULL, `password` TEXT NOT NULL, `fullName` TEXT NOT NULL, `role` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a171a65bb25a0ac3340824b0f304e311')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `batches`");
        db.execSQL("DROP TABLE IF EXISTS `trucks`");
        db.execSQL("DROP TABLE IF EXISTS `photos`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsBatches = new HashMap<String, TableInfo.Column>(7);
        _columnsBatches.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBatches.put("companyId", new TableInfo.Column("companyId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBatches.put("projectId", new TableInfo.Column("projectId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBatches.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBatches.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBatches.put("createdBy", new TableInfo.Column("createdBy", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBatches.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBatches = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBatches = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBatches = new TableInfo("batches", _columnsBatches, _foreignKeysBatches, _indicesBatches);
        final TableInfo _existingBatches = TableInfo.read(db, "batches");
        if (!_infoBatches.equals(_existingBatches)) {
          return new RoomOpenHelper.ValidationResult(false, "batches(com.asdec.hauling.data.Batch).\n"
                  + " Expected:\n" + _infoBatches + "\n"
                  + " Found:\n" + _existingBatches);
        }
        final HashMap<String, TableInfo.Column> _columnsTrucks = new HashMap<String, TableInfo.Column>(12);
        _columnsTrucks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("batchId", new TableInfo.Column("batchId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("haulerName", new TableInfo.Column("haulerName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("plateNo", new TableInfo.Column("plateNo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("length", new TableInfo.Column("length", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("width", new TableInfo.Column("width", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("height", new TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("cum", new TableInfo.Column("cum", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("timeIn", new TableInfo.Column("timeIn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("timeOut", new TableInfo.Column("timeOut", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrucks.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrucks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrucks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrucks = new TableInfo("trucks", _columnsTrucks, _foreignKeysTrucks, _indicesTrucks);
        final TableInfo _existingTrucks = TableInfo.read(db, "trucks");
        if (!_infoTrucks.equals(_existingTrucks)) {
          return new RoomOpenHelper.ValidationResult(false, "trucks(com.asdec.hauling.data.Truck).\n"
                  + " Expected:\n" + _infoTrucks + "\n"
                  + " Found:\n" + _existingTrucks);
        }
        final HashMap<String, TableInfo.Column> _columnsPhotos = new HashMap<String, TableInfo.Column>(7);
        _columnsPhotos.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPhotos.put("batchId", new TableInfo.Column("batchId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPhotos.put("truckId", new TableInfo.Column("truckId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPhotos.put("photoPath", new TableInfo.Column("photoPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPhotos.put("photoType", new TableInfo.Column("photoType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPhotos.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPhotos.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPhotos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPhotos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPhotos = new TableInfo("photos", _columnsPhotos, _foreignKeysPhotos, _indicesPhotos);
        final TableInfo _existingPhotos = TableInfo.read(db, "photos");
        if (!_infoPhotos.equals(_existingPhotos)) {
          return new RoomOpenHelper.ValidationResult(false, "photos(com.asdec.hauling.data.Photo).\n"
                  + " Expected:\n" + _infoPhotos + "\n"
                  + " Found:\n" + _existingPhotos);
        }
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(5);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("fullName", new TableInfo.Column("fullName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.asdec.hauling.data.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a171a65bb25a0ac3340824b0f304e311", "556208f8f2b87b33f0ceebafca3c2a43");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "batches","trucks","photos","users");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `batches`");
      _db.execSQL("DELETE FROM `trucks`");
      _db.execSQL("DELETE FROM `photos`");
      _db.execSQL("DELETE FROM `users`");
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
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AppDao.class, AppDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AppDao appDao() {
    if (_appDao != null) {
      return _appDao;
    } else {
      synchronized(this) {
        if(_appDao == null) {
          _appDao = new AppDao_Impl(this);
        }
        return _appDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
