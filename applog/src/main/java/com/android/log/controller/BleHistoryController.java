package com.android.log.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.log.dao.DaoMaster;
import com.android.log.dao.DaoSession;
import com.android.log.dao.DeviceBleOperaHistoryEntityDao;
import com.android.log.entity.DeviceBleOperaHistoryEntity;

import java.util.ArrayList;
import java.util.List;

public class BleHistoryController {
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context context;
    private static BleHistoryController instance;
    //
    private DeviceBleOperaHistoryEntityDao historyDao;

    //
    public BleHistoryController(Context context) {
        this.context = context;
        this.mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.HISTORY_DB, null);
        this.mDaoMaster = new DaoMaster(getBleLogDb());
        this.mDaoSession = mDaoMaster.newSession();
        this.historyDao = mDaoSession.getDeviceBleOperaHistoryEntityDao();
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new BleHistoryController(context);
        }
    }

    public static BleHistoryController getInstance() {
        return instance;
    }

    private SQLiteDatabase getBleLogDb() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.HISTORY_DB, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    public void insertOrReplace(DeviceBleOperaHistoryEntity history) {
        historyDao.insertOrReplace(history);
    }

    public long insert(DeviceBleOperaHistoryEntity history) {
        return historyDao.insert(history);
    }

    public void insertBatch(final ArrayList<DeviceBleOperaHistoryEntity> historys) {
        if (historyDao == null || historys.isEmpty()) {
            return;
        }
        historyDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < historys.size(); i++) {
                    DeviceBleOperaHistoryEntity history = historys.get(i);
                    insert(history);
                }
            }
        });
    }

    public ArrayList<DeviceBleOperaHistoryEntity> searchByBatchId(String wherecluse) {
        List<DeviceBleOperaHistoryEntity> historys =
                (List<DeviceBleOperaHistoryEntity>) historyDao.queryBuilder()
                        .where(DeviceBleOperaHistoryEntityDao.Properties.Batch_uuid.eq(wherecluse))
                        .orderDesc(DeviceBleOperaHistoryEntityDao.Properties.Id).build().list();
        return new ArrayList<>(historys);
    }

    public ArrayList<DeviceBleOperaHistoryEntity> searchByUserIdLimit(String wherecluse, int offset,
                                                                      int limit) {
        List<DeviceBleOperaHistoryEntity> historys = (List<DeviceBleOperaHistoryEntity>) historyDao.queryBuilder().
                where(DeviceBleOperaHistoryEntityDao.Properties.Id.eq(wherecluse))
                .orderDesc(DeviceBleOperaHistoryEntityDao.Properties.Id)
                .offset(offset * limit)
                .limit(limit)
                .build().list();
        return new ArrayList<>(historys);
    }

    public ArrayList<DeviceBleOperaHistoryEntity> searchAll() {
        ArrayList<DeviceBleOperaHistoryEntity> historys = new ArrayList<>(historyDao.queryBuilder()
                .orderDesc(DeviceBleOperaHistoryEntityDao.Properties.Id).list());
        return historys;
    }

    public void deleteById(String wherecluse) {
        historyDao.queryBuilder().where(DeviceBleOperaHistoryEntityDao.Properties.Batch_uuid.
                eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public void clearAll() {
        historyDao.getSession().deleteAll(DeviceBleOperaHistoryEntity.class);
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
