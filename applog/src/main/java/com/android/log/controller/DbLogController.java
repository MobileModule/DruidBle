package com.android.log.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.log.dao.BleLogEntityDao;
import com.android.log.dao.DaoMaster;
import com.android.log.dao.DaoSession;
import com.android.log.entity.BleLogEntity;

import java.util.ArrayList;
import java.util.List;

public class DbLogController {
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context context;
    private static DbLogController instance;
    //
    private BleLogEntityDao bleLogEntityDao;

    //
    public DbLogController(Context context) {
        this.context = context;
        this.mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.LOG_DB, null);
        this.mDaoMaster = new DaoMaster(getBleLogDb());
        this.mDaoSession = mDaoMaster.newSession();
        this.bleLogEntityDao = mDaoSession.getBleLogEntityDao();
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new DbLogController(context);
        }
    }

    public static DbLogController getInstance() {
        return instance;
    }

    private SQLiteDatabase getBleLogDb() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.LOG_DB, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    public void insertOrReplace(BleLogEntity bleLogEntity) {
        bleLogEntityDao.insertOrReplace(bleLogEntity);
    }

    public long insert(BleLogEntity bleLogEntity) {
        return bleLogEntityDao.insert(bleLogEntity);
    }

    public void updateById(BleLogEntity bleLogEntity) {
        BleLogEntity mOldBleLogEntity = bleLogEntityDao.queryBuilder().
                where(BleLogEntityDao.Properties.Id.eq(bleLogEntity.timestamp)).build().unique();
        if (mOldBleLogEntity != null) {
            bleLogEntityDao.update(mOldBleLogEntity);
        }
    }

    public ArrayList<BleLogEntity> searchByUserId(String wherecluse) {
        List<BleLogEntity> bleLogEntitys = (List<BleLogEntity>) bleLogEntityDao.queryBuilder().
                where(BleLogEntityDao.Properties.User_id.eq(wherecluse))
                .orderDesc(BleLogEntityDao.Properties.Id).build().list();
        return new ArrayList<>(bleLogEntitys);
    }

    public ArrayList<BleLogEntity> searchByUserIdLimit(String wherecluse, int offset, int limit) {
        List<BleLogEntity> bleLogEntitys = (List<BleLogEntity>) bleLogEntityDao.queryBuilder().
                where(BleLogEntityDao.Properties.User_id.eq(wherecluse))
                .orderDesc(BleLogEntityDao.Properties.Id)
                .offset(offset * limit)
                .limit(limit)
                .build().list();
        return new ArrayList<>(bleLogEntitys);
    }

    public ArrayList<BleLogEntity> searchAll() {
        ArrayList<BleLogEntity> bleLogEntitys = new ArrayList<>(bleLogEntityDao.queryBuilder()
                .orderDesc(BleLogEntityDao.Properties.Id).list());
        return bleLogEntitys;
    }

    public void deleteById(String wherecluse) {
        bleLogEntityDao.queryBuilder().where(BleLogEntityDao.Properties.Id.
                eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    public void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }
}
