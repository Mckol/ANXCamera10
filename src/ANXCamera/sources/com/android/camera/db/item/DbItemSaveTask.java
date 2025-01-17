package com.android.camera.db.item;

import android.content.Context;
import com.android.camera.CameraAppImpl;
import com.android.camera.db.element.SaveTask;
import com.android.camera.db.greendao.SaveTaskDao;
import com.android.camera.log.Log;
import com.xiaomi.camera.parallelservice.util.ParallelUtil;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

public class DbItemSaveTask extends DbItemBase<SaveTask, Long> {
    private static final int PROCESS_MAX_TIME_OUT = 10000;
    private static final String TAG = "DbItemSaveTask";

    /* access modifiers changed from: protected */
    @Override // com.android.camera.db.item.DbItemBase
    public SaveTask createItem(long j) {
        SaveTask saveTask;
        synchronized (this.mLock) {
            saveTask = new SaveTask();
            saveTask.setStartTime(Long.valueOf(j));
        }
        return saveTask;
    }

    /* access modifiers changed from: protected */
    public SaveTask endItem(SaveTask saveTask, long j) {
        return saveTask;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.db.item.DbItemBase
    public AbstractDao<SaveTask, Long> getDao() {
        return getDaoSession().getSaveTaskDao();
    }

    @Override // com.android.camera.db.item.DbItemBase, com.android.camera.db.provider.DbProvider.providerDb
    public SaveTask getItemByMediaId(Long l) {
        synchronized (this.mLock) {
            List<SaveTask> list = getDao().queryBuilder().where(SaveTaskDao.Properties.MediaStoreId.eq(l), new WhereCondition[0]).limit(1).build().list();
            if (list != null) {
                if (!list.isEmpty()) {
                    return list.get(0);
                }
            }
            return null;
        }
    }

    @Override // com.android.camera.db.item.DbItemBase, com.android.camera.db.provider.DbProvider.providerDb
    public SaveTask getItemByPath(String str) {
        synchronized (this.mLock) {
            List<SaveTask> list = getDao().queryBuilder().where(SaveTaskDao.Properties.Path.eq(str), new WhereCondition[0]).limit(1).build().list();
            if (list != null) {
                if (!list.isEmpty()) {
                    return list.get(0);
                }
            }
            return null;
        }
    }

    @Override // com.android.camera.db.item.DbItemBase, com.android.camera.db.provider.DbProvider.providerDb
    public SaveTask getItemWithExistedQuery(Query query, String str) {
        synchronized (this.mLock) {
            query.setParameter(0, (Object) str);
            List<SaveTask> list = getDao().queryBuilder().where(SaveTaskDao.Properties.Path.eq(str), new WhereCondition[0]).limit(1).build().list();
            if (list != null) {
                if (!list.isEmpty()) {
                    return list.get(0);
                }
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.db.item.DbItemBase
    public Property getOrderProperty() {
        return SaveTaskDao.Properties.Id;
    }

    public void markAllDepartedTask() {
        synchronized (this.mLock) {
            List<SaveTask> allItems = getAllItems();
            if (allItems != null) {
                if (allItems.size() != 0) {
                    Context androidContext = CameraAppImpl.getAndroidContext();
                    long currentTimeMillis = System.currentTimeMillis();
                    for (SaveTask saveTask : allItems) {
                        if (saveTask.isDeparted(currentTimeMillis)) {
                            if (!saveTask.isValid()) {
                                Log.e(TAG, "not valid, remove:" + saveTask.getPath());
                                removeItem(saveTask);
                            } else {
                                Log.e(TAG, "mark departed:" + saveTask.getPath());
                                ParallelUtil.markTaskFinish(androidContext, saveTask, false);
                            }
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.db.item.DbItemBase
    public String provideTableName() {
        return SaveTaskDao.TABLENAME;
    }
}
