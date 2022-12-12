package com.android.log.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.android.log.entity.LocalSaveLogEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL_SAVE_LOG_ENTITY".
*/
public class LocalSaveLogEntityDao extends AbstractDao<LocalSaveLogEntity, Long> {

    public static final String TABLENAME = "LOCAL_SAVE_LOG_ENTITY";

    /**
     * Properties of entity LocalSaveLogEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Mac = new Property(1, String.class, "mac", false, "MAC");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property Timestamp = new Property(3, long.class, "timestamp", false, "TIMESTAMP");
    }


    public LocalSaveLogEntityDao(DaoConfig config) {
        super(config);
    }
    
    public LocalSaveLogEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL_SAVE_LOG_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MAC\" TEXT," + // 1: mac
                "\"CONTENT\" TEXT," + // 2: content
                "\"TIMESTAMP\" INTEGER NOT NULL );"); // 3: timestamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL_SAVE_LOG_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocalSaveLogEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(2, mac);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getTimestamp());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocalSaveLogEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(2, mac);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getTimestamp());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocalSaveLogEntity readEntity(Cursor cursor, int offset) {
        LocalSaveLogEntity entity = new LocalSaveLogEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mac
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.getLong(offset + 3) // timestamp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocalSaveLogEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMac(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTimestamp(cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocalSaveLogEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocalSaveLogEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocalSaveLogEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
