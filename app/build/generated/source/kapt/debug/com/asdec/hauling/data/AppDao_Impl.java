package com.asdec.hauling.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Batch> __insertionAdapterOfBatch;

  private final EntityInsertionAdapter<Truck> __insertionAdapterOfTruck;

  private final EntityInsertionAdapter<Photo> __insertionAdapterOfPhoto;

  private final EntityDeletionOrUpdateAdapter<Batch> __updateAdapterOfBatch;

  private final EntityDeletionOrUpdateAdapter<Truck> __updateAdapterOfTruck;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePhotoPath;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBatchSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTruckSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePhotoSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTruckById;

  private final SharedSQLiteStatement __preparedStmtOfDeletePhotosByTruckId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteBatchById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTrucksByBatchId;

  private final SharedSQLiteStatement __preparedStmtOfDeletePhotosByBatchId;

  public AppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBatch = new EntityInsertionAdapter<Batch>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `batches` (`id`,`companyId`,`projectId`,`date`,`status`,`createdBy`,`isSynced`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Batch entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getCompanyId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCompanyId());
        }
        if (entity.getProjectId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProjectId());
        }
        statement.bindLong(4, entity.getDate());
        if (entity.getStatus() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStatus());
        }
        if (entity.getCreatedBy() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCreatedBy());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__insertionAdapterOfTruck = new EntityInsertionAdapter<Truck>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trucks` (`id`,`batchId`,`haulerName`,`plateNo`,`length`,`width`,`height`,`cum`,`timeIn`,`timeOut`,`status`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Truck entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getBatchId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBatchId());
        }
        if (entity.getHaulerName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getHaulerName());
        }
        if (entity.getPlateNo() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPlateNo());
        }
        statement.bindDouble(5, entity.getLength());
        statement.bindDouble(6, entity.getWidth());
        statement.bindDouble(7, entity.getHeight());
        statement.bindDouble(8, entity.getCum());
        if (entity.getTimeIn() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTimeIn());
        }
        if (entity.getTimeOut() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getTimeOut());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getStatus());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(12, _tmp);
      }
    };
    this.__insertionAdapterOfPhoto = new EntityInsertionAdapter<Photo>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `photos` (`id`,`batchId`,`truckId`,`photoPath`,`photoType`,`timestamp`,`isSynced`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Photo entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getBatchId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBatchId());
        }
        if (entity.getTruckId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTruckId());
        }
        if (entity.getPhotoPath() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoPath());
        }
        if (entity.getPhotoType() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPhotoType());
        }
        statement.bindLong(6, entity.getTimestamp());
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__updateAdapterOfBatch = new EntityDeletionOrUpdateAdapter<Batch>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `batches` SET `id` = ?,`companyId` = ?,`projectId` = ?,`date` = ?,`status` = ?,`createdBy` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Batch entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getCompanyId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCompanyId());
        }
        if (entity.getProjectId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProjectId());
        }
        statement.bindLong(4, entity.getDate());
        if (entity.getStatus() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStatus());
        }
        if (entity.getCreatedBy() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCreatedBy());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getId());
        }
      }
    };
    this.__updateAdapterOfTruck = new EntityDeletionOrUpdateAdapter<Truck>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `trucks` SET `id` = ?,`batchId` = ?,`haulerName` = ?,`plateNo` = ?,`length` = ?,`width` = ?,`height` = ?,`cum` = ?,`timeIn` = ?,`timeOut` = ?,`status` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Truck entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getBatchId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBatchId());
        }
        if (entity.getHaulerName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getHaulerName());
        }
        if (entity.getPlateNo() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPlateNo());
        }
        statement.bindDouble(5, entity.getLength());
        statement.bindDouble(6, entity.getWidth());
        statement.bindDouble(7, entity.getHeight());
        statement.bindDouble(8, entity.getCum());
        if (entity.getTimeIn() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTimeIn());
        }
        if (entity.getTimeOut() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getTimeOut());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getStatus());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfUpdatePhotoPath = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE photos SET photoPath = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateBatchSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE batches SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTruckSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE trucks SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePhotoSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE photos SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTruckById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM trucks WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePhotosByTruckId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM photos WHERE truckId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteBatchById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM batches WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTrucksByBatchId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM trucks WHERE batchId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePhotosByBatchId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM photos WHERE batchId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertBatch(final Batch batch, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBatch.insert(batch);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTruck(final Truck truck, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTruck.insertAndReturnId(truck);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPhoto(final Photo photo, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPhoto.insert(photo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBatch(final Batch batch, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBatch.handle(batch);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTruck(final Truck truck, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTruck.handle(truck);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePhotoPath(final String photoId, final String newPath,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePhotoPath.acquire();
        int _argIndex = 1;
        if (newPath == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, newPath);
        }
        _argIndex = 2;
        if (photoId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, photoId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePhotoPath.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBatchSyncStatus(final String id, final boolean status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBatchSyncStatus.acquire();
        int _argIndex = 1;
        final int _tmp = status ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateBatchSyncStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTruckSyncStatus(final long id, final boolean status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTruckSyncStatus.acquire();
        int _argIndex = 1;
        final int _tmp = status ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateTruckSyncStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePhotoSyncStatus(final String id, final boolean status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePhotoSyncStatus.acquire();
        int _argIndex = 1;
        final int _tmp = status ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdatePhotoSyncStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTruckById(final long truckId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTruckById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, truckId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTruckById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePhotosByTruckId(final String truckId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePhotosByTruckId.acquire();
        int _argIndex = 1;
        if (truckId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, truckId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePhotosByTruckId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBatchById(final String batchId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteBatchById.acquire();
        int _argIndex = 1;
        if (batchId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, batchId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteBatchById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTrucksByBatchId(final String batchId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTrucksByBatchId.acquire();
        int _argIndex = 1;
        if (batchId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, batchId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTrucksByBatchId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePhotosByBatchId(final String batchId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePhotosByBatchId.acquire();
        int _argIndex = 1;
        if (batchId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, batchId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePhotosByBatchId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Batch>> getAllBatches() {
    final String _sql = "SELECT * FROM batches ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"batches"}, new Callable<List<Batch>>() {
      @Override
      @NonNull
      public List<Batch> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCompanyId = CursorUtil.getColumnIndexOrThrow(_cursor, "companyId");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "createdBy");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Batch> _result = new ArrayList<Batch>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Batch _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCompanyId;
            if (_cursor.isNull(_cursorIndexOfCompanyId)) {
              _tmpCompanyId = null;
            } else {
              _tmpCompanyId = _cursor.getString(_cursorIndexOfCompanyId);
            }
            final String _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            }
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpCreatedBy;
            if (_cursor.isNull(_cursorIndexOfCreatedBy)) {
              _tmpCreatedBy = null;
            } else {
              _tmpCreatedBy = _cursor.getString(_cursorIndexOfCreatedBy);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Batch(_tmpId,_tmpCompanyId,_tmpProjectId,_tmpDate,_tmpStatus,_tmpCreatedBy,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getBatchById(final String batchId, final Continuation<? super Batch> $completion) {
    final String _sql = "SELECT * FROM batches WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (batchId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batchId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Batch>() {
      @Override
      @Nullable
      public Batch call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCompanyId = CursorUtil.getColumnIndexOrThrow(_cursor, "companyId");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "createdBy");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final Batch _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCompanyId;
            if (_cursor.isNull(_cursorIndexOfCompanyId)) {
              _tmpCompanyId = null;
            } else {
              _tmpCompanyId = _cursor.getString(_cursorIndexOfCompanyId);
            }
            final String _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            }
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpCreatedBy;
            if (_cursor.isNull(_cursorIndexOfCreatedBy)) {
              _tmpCreatedBy = null;
            } else {
              _tmpCreatedBy = _cursor.getString(_cursorIndexOfCreatedBy);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _result = new Batch(_tmpId,_tmpCompanyId,_tmpProjectId,_tmpDate,_tmpStatus,_tmpCreatedBy,_tmpIsSynced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Truck>> getTrucksForBatch(final String batchId) {
    final String _sql = "SELECT * FROM trucks WHERE batchId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (batchId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batchId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trucks"}, new Callable<List<Truck>>() {
      @Override
      @NonNull
      public List<Truck> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfHaulerName = CursorUtil.getColumnIndexOrThrow(_cursor, "haulerName");
          final int _cursorIndexOfPlateNo = CursorUtil.getColumnIndexOrThrow(_cursor, "plateNo");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfCum = CursorUtil.getColumnIndexOrThrow(_cursor, "cum");
          final int _cursorIndexOfTimeIn = CursorUtil.getColumnIndexOrThrow(_cursor, "timeIn");
          final int _cursorIndexOfTimeOut = CursorUtil.getColumnIndexOrThrow(_cursor, "timeOut");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Truck> _result = new ArrayList<Truck>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Truck _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpBatchId;
            if (_cursor.isNull(_cursorIndexOfBatchId)) {
              _tmpBatchId = null;
            } else {
              _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            }
            final String _tmpHaulerName;
            if (_cursor.isNull(_cursorIndexOfHaulerName)) {
              _tmpHaulerName = null;
            } else {
              _tmpHaulerName = _cursor.getString(_cursorIndexOfHaulerName);
            }
            final String _tmpPlateNo;
            if (_cursor.isNull(_cursorIndexOfPlateNo)) {
              _tmpPlateNo = null;
            } else {
              _tmpPlateNo = _cursor.getString(_cursorIndexOfPlateNo);
            }
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpCum;
            _tmpCum = _cursor.getDouble(_cursorIndexOfCum);
            final String _tmpTimeIn;
            if (_cursor.isNull(_cursorIndexOfTimeIn)) {
              _tmpTimeIn = null;
            } else {
              _tmpTimeIn = _cursor.getString(_cursorIndexOfTimeIn);
            }
            final String _tmpTimeOut;
            if (_cursor.isNull(_cursorIndexOfTimeOut)) {
              _tmpTimeOut = null;
            } else {
              _tmpTimeOut = _cursor.getString(_cursorIndexOfTimeOut);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Truck(_tmpId,_tmpBatchId,_tmpHaulerName,_tmpPlateNo,_tmpLength,_tmpWidth,_tmpHeight,_tmpCum,_tmpTimeIn,_tmpTimeOut,_tmpStatus,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTrucksForBatchSync(final String batchId,
      final Continuation<? super List<Truck>> $completion) {
    final String _sql = "SELECT * FROM trucks WHERE batchId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (batchId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batchId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Truck>>() {
      @Override
      @NonNull
      public List<Truck> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfHaulerName = CursorUtil.getColumnIndexOrThrow(_cursor, "haulerName");
          final int _cursorIndexOfPlateNo = CursorUtil.getColumnIndexOrThrow(_cursor, "plateNo");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfCum = CursorUtil.getColumnIndexOrThrow(_cursor, "cum");
          final int _cursorIndexOfTimeIn = CursorUtil.getColumnIndexOrThrow(_cursor, "timeIn");
          final int _cursorIndexOfTimeOut = CursorUtil.getColumnIndexOrThrow(_cursor, "timeOut");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Truck> _result = new ArrayList<Truck>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Truck _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpBatchId;
            if (_cursor.isNull(_cursorIndexOfBatchId)) {
              _tmpBatchId = null;
            } else {
              _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            }
            final String _tmpHaulerName;
            if (_cursor.isNull(_cursorIndexOfHaulerName)) {
              _tmpHaulerName = null;
            } else {
              _tmpHaulerName = _cursor.getString(_cursorIndexOfHaulerName);
            }
            final String _tmpPlateNo;
            if (_cursor.isNull(_cursorIndexOfPlateNo)) {
              _tmpPlateNo = null;
            } else {
              _tmpPlateNo = _cursor.getString(_cursorIndexOfPlateNo);
            }
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpCum;
            _tmpCum = _cursor.getDouble(_cursorIndexOfCum);
            final String _tmpTimeIn;
            if (_cursor.isNull(_cursorIndexOfTimeIn)) {
              _tmpTimeIn = null;
            } else {
              _tmpTimeIn = _cursor.getString(_cursorIndexOfTimeIn);
            }
            final String _tmpTimeOut;
            if (_cursor.isNull(_cursorIndexOfTimeOut)) {
              _tmpTimeOut = null;
            } else {
              _tmpTimeOut = _cursor.getString(_cursorIndexOfTimeOut);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Truck(_tmpId,_tmpBatchId,_tmpHaulerName,_tmpPlateNo,_tmpLength,_tmpWidth,_tmpHeight,_tmpCum,_tmpTimeIn,_tmpTimeOut,_tmpStatus,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTruckCountForBatch(final String batchId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM trucks WHERE batchId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (batchId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batchId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPhotosForBatch(final String batchId,
      final Continuation<? super List<Photo>> $completion) {
    final String _sql = "SELECT * FROM photos WHERE batchId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (batchId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batchId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Photo>>() {
      @Override
      @NonNull
      public List<Photo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfTruckId = CursorUtil.getColumnIndexOrThrow(_cursor, "truckId");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfPhotoType = CursorUtil.getColumnIndexOrThrow(_cursor, "photoType");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Photo _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpBatchId;
            if (_cursor.isNull(_cursorIndexOfBatchId)) {
              _tmpBatchId = null;
            } else {
              _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            }
            final String _tmpTruckId;
            if (_cursor.isNull(_cursorIndexOfTruckId)) {
              _tmpTruckId = null;
            } else {
              _tmpTruckId = _cursor.getString(_cursorIndexOfTruckId);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final String _tmpPhotoType;
            if (_cursor.isNull(_cursorIndexOfPhotoType)) {
              _tmpPhotoType = null;
            } else {
              _tmpPhotoType = _cursor.getString(_cursorIndexOfPhotoType);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Photo(_tmpId,_tmpBatchId,_tmpTruckId,_tmpPhotoPath,_tmpPhotoType,_tmpTimestamp,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedBatches(final Continuation<? super List<Batch>> $completion) {
    final String _sql = "SELECT * FROM batches WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Batch>>() {
      @Override
      @NonNull
      public List<Batch> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCompanyId = CursorUtil.getColumnIndexOrThrow(_cursor, "companyId");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "createdBy");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Batch> _result = new ArrayList<Batch>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Batch _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCompanyId;
            if (_cursor.isNull(_cursorIndexOfCompanyId)) {
              _tmpCompanyId = null;
            } else {
              _tmpCompanyId = _cursor.getString(_cursorIndexOfCompanyId);
            }
            final String _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            }
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpCreatedBy;
            if (_cursor.isNull(_cursorIndexOfCreatedBy)) {
              _tmpCreatedBy = null;
            } else {
              _tmpCreatedBy = _cursor.getString(_cursorIndexOfCreatedBy);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Batch(_tmpId,_tmpCompanyId,_tmpProjectId,_tmpDate,_tmpStatus,_tmpCreatedBy,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedTrucks(final Continuation<? super List<Truck>> $completion) {
    final String _sql = "SELECT * FROM trucks WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Truck>>() {
      @Override
      @NonNull
      public List<Truck> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfHaulerName = CursorUtil.getColumnIndexOrThrow(_cursor, "haulerName");
          final int _cursorIndexOfPlateNo = CursorUtil.getColumnIndexOrThrow(_cursor, "plateNo");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfCum = CursorUtil.getColumnIndexOrThrow(_cursor, "cum");
          final int _cursorIndexOfTimeIn = CursorUtil.getColumnIndexOrThrow(_cursor, "timeIn");
          final int _cursorIndexOfTimeOut = CursorUtil.getColumnIndexOrThrow(_cursor, "timeOut");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Truck> _result = new ArrayList<Truck>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Truck _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpBatchId;
            if (_cursor.isNull(_cursorIndexOfBatchId)) {
              _tmpBatchId = null;
            } else {
              _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            }
            final String _tmpHaulerName;
            if (_cursor.isNull(_cursorIndexOfHaulerName)) {
              _tmpHaulerName = null;
            } else {
              _tmpHaulerName = _cursor.getString(_cursorIndexOfHaulerName);
            }
            final String _tmpPlateNo;
            if (_cursor.isNull(_cursorIndexOfPlateNo)) {
              _tmpPlateNo = null;
            } else {
              _tmpPlateNo = _cursor.getString(_cursorIndexOfPlateNo);
            }
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpCum;
            _tmpCum = _cursor.getDouble(_cursorIndexOfCum);
            final String _tmpTimeIn;
            if (_cursor.isNull(_cursorIndexOfTimeIn)) {
              _tmpTimeIn = null;
            } else {
              _tmpTimeIn = _cursor.getString(_cursorIndexOfTimeIn);
            }
            final String _tmpTimeOut;
            if (_cursor.isNull(_cursorIndexOfTimeOut)) {
              _tmpTimeOut = null;
            } else {
              _tmpTimeOut = _cursor.getString(_cursorIndexOfTimeOut);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Truck(_tmpId,_tmpBatchId,_tmpHaulerName,_tmpPlateNo,_tmpLength,_tmpWidth,_tmpHeight,_tmpCum,_tmpTimeIn,_tmpTimeOut,_tmpStatus,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedPhotos(final Continuation<? super List<Photo>> $completion) {
    final String _sql = "SELECT * FROM photos WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Photo>>() {
      @Override
      @NonNull
      public List<Photo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfTruckId = CursorUtil.getColumnIndexOrThrow(_cursor, "truckId");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfPhotoType = CursorUtil.getColumnIndexOrThrow(_cursor, "photoType");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Photo _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpBatchId;
            if (_cursor.isNull(_cursorIndexOfBatchId)) {
              _tmpBatchId = null;
            } else {
              _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            }
            final String _tmpTruckId;
            if (_cursor.isNull(_cursorIndexOfTruckId)) {
              _tmpTruckId = null;
            } else {
              _tmpTruckId = _cursor.getString(_cursorIndexOfTruckId);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final String _tmpPhotoType;
            if (_cursor.isNull(_cursorIndexOfPhotoType)) {
              _tmpPhotoType = null;
            } else {
              _tmpPhotoType = _cursor.getString(_cursorIndexOfPhotoType);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Photo(_tmpId,_tmpBatchId,_tmpTruckId,_tmpPhotoPath,_tmpPhotoType,_tmpTimestamp,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllPhotosSync(final Continuation<? super List<Photo>> $completion) {
    final String _sql = "SELECT * FROM photos";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Photo>>() {
      @Override
      @NonNull
      public List<Photo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfTruckId = CursorUtil.getColumnIndexOrThrow(_cursor, "truckId");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfPhotoType = CursorUtil.getColumnIndexOrThrow(_cursor, "photoType");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Photo _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpBatchId;
            if (_cursor.isNull(_cursorIndexOfBatchId)) {
              _tmpBatchId = null;
            } else {
              _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            }
            final String _tmpTruckId;
            if (_cursor.isNull(_cursorIndexOfTruckId)) {
              _tmpTruckId = null;
            } else {
              _tmpTruckId = _cursor.getString(_cursorIndexOfTruckId);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final String _tmpPhotoType;
            if (_cursor.isNull(_cursorIndexOfPhotoType)) {
              _tmpPhotoType = null;
            } else {
              _tmpPhotoType = _cursor.getString(_cursorIndexOfPhotoType);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new Photo(_tmpId,_tmpBatchId,_tmpTruckId,_tmpPhotoPath,_tmpPhotoType,_tmpTimestamp,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTruckById(final long truckId, final Continuation<? super Truck> $completion) {
    final String _sql = "SELECT * FROM trucks WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, truckId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Truck>() {
      @Override
      @Nullable
      public Truck call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfHaulerName = CursorUtil.getColumnIndexOrThrow(_cursor, "haulerName");
          final int _cursorIndexOfPlateNo = CursorUtil.getColumnIndexOrThrow(_cursor, "plateNo");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "length");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfCum = CursorUtil.getColumnIndexOrThrow(_cursor, "cum");
          final int _cursorIndexOfTimeIn = CursorUtil.getColumnIndexOrThrow(_cursor, "timeIn");
          final int _cursorIndexOfTimeOut = CursorUtil.getColumnIndexOrThrow(_cursor, "timeOut");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final Truck _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpBatchId;
            if (_cursor.isNull(_cursorIndexOfBatchId)) {
              _tmpBatchId = null;
            } else {
              _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            }
            final String _tmpHaulerName;
            if (_cursor.isNull(_cursorIndexOfHaulerName)) {
              _tmpHaulerName = null;
            } else {
              _tmpHaulerName = _cursor.getString(_cursorIndexOfHaulerName);
            }
            final String _tmpPlateNo;
            if (_cursor.isNull(_cursorIndexOfPlateNo)) {
              _tmpPlateNo = null;
            } else {
              _tmpPlateNo = _cursor.getString(_cursorIndexOfPlateNo);
            }
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            final double _tmpWidth;
            _tmpWidth = _cursor.getDouble(_cursorIndexOfWidth);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpCum;
            _tmpCum = _cursor.getDouble(_cursorIndexOfCum);
            final String _tmpTimeIn;
            if (_cursor.isNull(_cursorIndexOfTimeIn)) {
              _tmpTimeIn = null;
            } else {
              _tmpTimeIn = _cursor.getString(_cursorIndexOfTimeIn);
            }
            final String _tmpTimeOut;
            if (_cursor.isNull(_cursorIndexOfTimeOut)) {
              _tmpTimeOut = null;
            } else {
              _tmpTimeOut = _cursor.getString(_cursorIndexOfTimeOut);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _result = new Truck(_tmpId,_tmpBatchId,_tmpHaulerName,_tmpPlateNo,_tmpLength,_tmpWidth,_tmpHeight,_tmpCum,_tmpTimeIn,_tmpTimeOut,_tmpStatus,_tmpIsSynced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
