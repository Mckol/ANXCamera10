package com.android.camera.db;

import android.content.Context;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraApplicationDelegate;
import com.android.camera.db.greendao.DaoMaster;
import com.android.camera.db.greendao.DaoSession;

public class DbContainer {
    private static DbContainer dbContainer;
    private Context context;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DbContainer(Context context2) {
        this.context = context2;
    }

    private void clear() {
        DaoSession daoSession2 = this.daoSession;
        if (daoSession2 != null) {
            daoSession2.clear();
            this.daoSession = null;
        }
        if (this.daoMaster != null) {
            this.daoMaster = null;
        }
    }

    public static DbContainer getInstance() {
        if (dbContainer == null) {
            init(CameraApplicationDelegate.getAndroidContext());
        }
        return dbContainer;
    }

    public static final void init(Context context2) {
        if (dbContainer == null) {
            dbContainer = new DbContainer(context2);
        }
    }

    public static void release() {
        DbContainer dbContainer2 = dbContainer;
        if (dbContainer2 != null) {
            dbContainer2.clear();
            dbContainer = null;
        }
    }

    public DaoMaster getDaoMaster() {
        if (this.daoMaster == null) {
            if (this.context == null) {
                this.context = CameraAppImpl.getAndroidContext();
            }
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this.context, "camera1.db", null);
            devOpenHelper.setLoadSQLCipherNativeLibs(false);
            this.daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        }
        return this.daoMaster;
    }

    public DaoSession getDaoSession() {
        if (this.daoSession == null) {
            this.daoMaster = getDaoMaster();
            this.daoSession = this.daoMaster.newSession();
        }
        return this.daoSession;
    }
}
