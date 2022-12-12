package com.android.log.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.log.dao.DaoMaster;
import com.android.log.dao.DaoSession;
import com.android.log.dao.DeviceBleOperaTimeEntityDao;
import com.android.log.entity.DeviceBleOperaTimeEntity;

import java.util.ArrayList;
import java.util.List;

public class BleHistoryTimeController {
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context context;
    private static BleHistoryTimeController instance;
    //
    private DeviceBleOperaTimeEntityDao historyDao;

    //
    public BleHistoryTimeController(Context context) {
        this.context = context;
        this.mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.HISTORY_DB, null);
        this.mDaoMaster = new DaoMaster(getBleLogDb());
        this.mDaoSession = mDaoMaster.newSession();
        this.historyDao = mDaoSession.getDeviceBleOperaTimeEntityDao();
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new BleHistoryTimeController(context);
        }
    }

    public static BleHistoryTimeController getInstance() {
        return instance;
    }

    private SQLiteDatabase getBleLogDb() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DataBaseConstant.HISTORY_DB, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    public void insertOrReplace(DeviceBleOperaTimeEntity history) {
        historyDao.insertOrReplace(history);
    }

    public long insert(DeviceBleOperaTimeEntity history) {
        return historyDao.insert(history);
    }

    public void insertBatch(final ArrayList<DeviceBleOperaTimeEntity> historys) {
        if (historyDao == null || historys.isEmpty()) {
            return;
        }
        historyDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < historys.size(); i++) {
                    DeviceBleOperaTimeEntity history = historys.get(i);
                    insert(history);
                }
            }
        });
    }

    public ArrayList<DeviceBleOperaTimeEntity> searchByBatchId(String wherecluse) {
        List<DeviceBleOperaTimeEntity> historys =
                (List<DeviceBleOperaTimeEntity>) historyDao.queryBuilder()
                        .where(DeviceBleOperaTimeEntityDao.Properties.Batch_uuid.eq(wherecluse))
                        .orderDesc(DeviceBleOperaTimeEntityDao.Properties.Id).build().list();
        return new ArrayList<>(historys);
    }

    public ArrayList<DeviceBleOperaTimeEntity> searchByUserIdLimit(String wherecluse, int offset,
                                                                      int limit) {
        List<DeviceBleOperaTimeEntity> historys = (List<DeviceBleOperaTimeEntity>) historyDao.queryBuilder().
                where(DeviceBleOperaTimeEntityDao.Properties.Id.eq(wherecluse))
                .orderDesc(DeviceBleOperaTimeEntityDao.Properties.Id)
                .offset(offset * limit)
                .limit(limit)
                .build().list();
        return new ArrayList<>(historys);
    }

    public ArrayList<DeviceBleOperaTimeEntity> searchAll() {
        ArrayList<DeviceBleOperaTimeEntity> historys = new ArrayList<>(historyDao.queryBuilder()
                .orderDesc(DeviceBleOperaTimeEntityDao.Properties.Id).list());
        return historys;
    }

    public void deleteById(String wherecluse) {
        historyDao.queryBuilder().where(DeviceBleOperaTimeEntityDao.Properties.Batch_uuid.
                eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public void clearAll() {
        historyDao.getSession().deleteAll(DeviceBleOperaTimeEntity.class);
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
