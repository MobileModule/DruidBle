package com.android.log.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.android.log.entity.DeviceBleOperaHistoryEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEVICE_BLE_OPERA_HISTORY_ENTITY".
*/
public class DeviceBleOperaHistoryEntityDao extends AbstractDao<DeviceBleOperaHistoryEntity, Long> {

    public static final String TABLENAME = "DEVICE_BLE_OPERA_HISTORY_ENTITY";

    /**
     * Properties of entity DeviceBleOperaHistoryEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Batch_uuid = new Property(1, String.class, "batch_uuid", false, "BATCH_UUID");
        public final static Property Device_id = new Property(2, String.class, "device_id", false, "DEVICE_ID");
        public final static Property Mac = new Property(3, String.class, "mac", false, "MAC");
        public final static Property Uuid = new Property(4, String.class, "uuid", false, "UUID");
        public final static Property Sn = new Property(5, String.class, "sn", false, "SN");
        public final static Property Mark = new Property(6, int.class, "mark", false, "MARK");
        public final static Property Device_type = new Property(7, int.class, "device_type", false, "DEVICE_TYPE");
        public final static Property Device_type_enum = new Property(8, int.class, "device_type_enum", false, "DEVICE_TYPE_ENUM");
        public final static Property Firmware_version = new Property(9, int.class, "firmware_version", false, "FIRMWARE_VERSION");
        public final static Property Battery_voltage = new Property(10, double.class, "battery_voltage", false, "BATTERY_VOLTAGE");
        public final static Property Device_status = new Property(11, int.class, "device_status", false, "DEVICE_STATUS");
        public final static Property Timestamp_start = new Property(12, long.class, "timestamp_start", false, "TIMESTAMP_START");
        public final static Property Timestamp_end = new Property(13, long.class, "timestamp_end", false, "TIMESTAMP_END");
        public final static Property Opera_success_type = new Property(14, int.class, "opera_success_type", false, "OPERA_SUCCESS_TYPE");
        public final static Property Opera_type = new Property(15, int.class, "opera_type", false, "OPERA_TYPE");
        public final static Property Hardware_version = new Property(16, int.class, "hardware_version", false, "HARDWARE_VERSION");
        public final static Property Result_details = new Property(17, String.class, "result_details", false, "RESULT_DETAILS");
    }


    public DeviceBleOperaHistoryEntityDao(DaoConfig config) {
        super(config);
    }
    
    public DeviceBleOperaHistoryEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEVICE_BLE_OPERA_HISTORY_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"BATCH_UUID\" TEXT," + // 1: batch_uuid
                "\"DEVICE_ID\" TEXT," + // 2: device_id
                "\"MAC\" TEXT," + // 3: mac
                "\"UUID\" TEXT," + // 4: uuid
                "\"SN\" TEXT," + // 5: sn
                "\"MARK\" INTEGER NOT NULL ," + // 6: mark
                "\"DEVICE_TYPE\" INTEGER NOT NULL ," + // 7: device_type
                "\"DEVICE_TYPE_ENUM\" INTEGER NOT NULL ," + // 8: device_type_enum
                "\"FIRMWARE_VERSION\" INTEGER NOT NULL ," + // 9: firmware_version
                "\"BATTERY_VOLTAGE\" REAL NOT NULL ," + // 10: battery_voltage
                "\"DEVICE_STATUS\" INTEGER NOT NULL ," + // 11: device_status
                "\"TIMESTAMP_START\" INTEGER NOT NULL ," + // 12: timestamp_start
                "\"TIMESTAMP_END\" INTEGER NOT NULL ," + // 13: timestamp_end
                "\"OPERA_SUCCESS_TYPE\" INTEGER NOT NULL ," + // 14: opera_success_type
                "\"OPERA_TYPE\" INTEGER NOT NULL ," + // 15: opera_type
                "\"HARDWARE_VERSION\" INTEGER NOT NULL ," + // 16: hardware_version
                "\"RESULT_DETAILS\" TEXT);"); // 17: result_details
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEVICE_BLE_OPERA_HISTORY_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DeviceBleOperaHistoryEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String batch_uuid = entity.getBatch_uuid();
        if (batch_uuid != null) {
            stmt.bindString(2, batch_uuid);
        }
 
        String device_id = entity.getDevice_id();
        if (device_id != null) {
            stmt.bindString(3, device_id);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(4, mac);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(5, uuid);
        }
 
        String sn = entity.getSn();
        if (sn != null) {
            stmt.bindString(6, sn);
        }
        stmt.bindLong(7, entity.getMark());
        stmt.bindLong(8, entity.getDevice_type());
        stmt.bindLong(9, entity.getDevice_type_enum());
        stmt.bindLong(10, entity.getFirmware_version());
        stmt.bindDouble(11, entity.getBattery_voltage());
        stmt.bindLong(12, entity.getDevice_status());
        stmt.bindLong(13, entity.getTimestamp_start());
        stmt.bindLong(14, entity.getTimestamp_end());
        stmt.bindLong(15, entity.getOpera_success_type());
        stmt.bindLong(16, entity.getOpera_type());
        stmt.bindLong(17, entity.getHardware_version());
 
        String result_details = entity.getResult_details();
        if (result_details != null) {
            stmt.bindString(18, result_details);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DeviceBleOperaHistoryEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String batch_uuid = entity.getBatch_uuid();
        if (batch_uuid != null) {
            stmt.bindString(2, batch_uuid);
        }
 
        String device_id = entity.getDevice_id();
        if (device_id != null) {
            stmt.bindString(3, device_id);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(4, mac);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(5, uuid);
        }
 
        String sn = entity.getSn();
        if (sn != null) {
            stmt.bindString(6, sn);
        }
        stmt.bindLong(7, entity.getMark());
        stmt.bindLong(8, entity.getDevice_type());
        stmt.bindLong(9, entity.getDevice_type_enum());
        stmt.bindLong(10, entity.getFirmware_version());
        stmt.bindDouble(11, entity.getBattery_voltage());
        stmt.bindLong(12, entity.getDevice_status());
        stmt.bindLong(13, entity.getTimestamp_start());
        stmt.bindLong(14, entity.getTimestamp_end());
        stmt.bindLong(15, entity.getOpera_success_type());
        stmt.bindLong(16, entity.getOpera_type());
        stmt.bindLong(17, entity.getHardware_version());
 
        String result_details = entity.getResult_details();
        if (result_details != null) {
            stmt.bindString(18, result_details);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DeviceBleOperaHistoryEntity readEntity(Cursor cursor, int offset) {
        DeviceBleOperaHistoryEntity entity = new DeviceBleOperaHistoryEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // batch_uuid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // device_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mac
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // uuid
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sn
            cursor.getInt(offset + 6), // mark
            cursor.getInt(offset + 7), // device_type
            cursor.getInt(offset + 8), // device_type_enum
            cursor.getInt(offset + 9), // firmware_version
            cursor.getDouble(offset + 10), // battery_voltage
            cursor.getInt(offset + 11), // device_status
            cursor.getLong(offset + 12), // timestamp_start
            cursor.getLong(offset + 13), // timestamp_end
            cursor.getInt(offset + 14), // opera_success_type
            cursor.getInt(offset + 15), // opera_type
            cursor.getInt(offset + 16), // hardware_version
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17) // result_details
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DeviceBleOperaHistoryEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBatch_uuid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDevice_id(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMac(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUuid(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSn(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMark(cursor.getInt(offset + 6));
        entity.setDevice_type(cursor.getInt(offset + 7));
        entity.setDevice_type_enum(cursor.getInt(offset + 8));
        entity.setFirmware_version(cursor.getInt(offset + 9));
        entity.setBattery_voltage(cursor.getDouble(offset + 10));
        entity.setDevice_status(cursor.getInt(offset + 11));
        entity.setTimestamp_start(cursor.getLong(offset + 12));
        entity.setTimestamp_end(cursor.getLong(offset + 13));
        entity.setOpera_success_type(cursor.getInt(offset + 14));
        entity.setOpera_type(cursor.getInt(offset + 15));
        entity.setHardware_version(cursor.getInt(offset + 16));
        entity.setResult_details(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DeviceBleOperaHistoryEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DeviceBleOperaHistoryEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DeviceBleOperaHistoryEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
