package com.android.log.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.log.dao.DaoMaster;
import com.android.log.dao.DaoSession;
import com.android.log.dao.LocalSaveLogEntityDao;
import com.android.log.entity.LocalSaveLogEntity;

import java.util.ArrayList;
import java.util.List;

public class BleLocalLogController {
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context context;
    private static BleLocalLogController instance;
    //
    private LocalSaveLogEntityDao bleLogEntityDao;

    //
    public BleLocalLogController(Context context) {
        this.context = context;
        this.mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.LOG_DB, null);
        this.mDaoMaster = new DaoMaster(getBleLogDb());
        this.mDaoSession = mDaoMaster.newSession();
        this.bleLogEntityDao = mDaoSession.getLocalSaveLogEntityDao();
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new BleLocalLogController(context);
        }
    }

    public static BleLocalLogController getInstance() {
        return instance;
    }

    private SQLiteDatabase getBleLogDb() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.LOG_DB, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    public void insertOrReplace(LocalSaveLogEntity bleLogEntity) {
        bleLogEntityDao.insertOrReplace(bleLogEntity);
    }

    public long insert(LocalSaveLogEntity bleLogEntity) {
        return bleLogEntityDao.insert(bleLogEntity);
    }

    public ArrayList<LocalSaveLogEntity> searchByUserId(String wherecluse) {
        List<LocalSaveLogEntity> bleLogEntitys = (List<LocalSaveLogEntity>) bleLogEntityDao.queryBuilder().
                where(LocalSaveLogEntityDao.Properties.Id.eq(wherecluse))
                .orderDesc(LocalSaveLogEntityDao.Properties.Id).build().list();
        return new ArrayList<>(bleLogEntitys);
    }

    public ArrayList<LocalSaveLogEntity> searchByUserIdLimit(String wherecluse, int offset, int limit) {
        List<LocalSaveLogEntity> bleLogEntitys = (List<LocalSaveLogEntity>) bleLogEntityDao.queryBuilder().
                where(LocalSaveLogEntityDao.Properties.Id.eq(wherecluse))
                .orderDesc(LocalSaveLogEntityDao.Properties.Id)
                .offset(offset * limit)
                .limit(limit)
                .build().list();
        return new ArrayList<>(bleLogEntitys);
    }

    public ArrayList<LocalSaveLogEntity> searchAll() {
        ArrayList<LocalSaveLogEntity> bleLogEntitys = new ArrayList<>(bleLogEntityDao.queryBuilder()
                .orderAsc(LocalSaveLogEntityDao.Properties.Id).list());
        return bleLogEntitys;
    }

    public void deleteById(String wherecluse) {
        bleLogEntityDao.queryBuilder().where(LocalSaveLogEntityDao.Properties.Id.
                eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public void clearAll() {
        bleLogEntityDao.getSession().deleteAll(LocalSaveLogEntity.class);
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
