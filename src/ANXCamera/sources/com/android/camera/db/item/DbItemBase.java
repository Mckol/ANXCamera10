package com.android.camera.db.item;

import com.android.camera.db.DbContainer;
import com.android.camera.db.greendao.DaoMaster;
import com.android.camera.db.greendao.DaoSession;
import com.android.camera.db.provider.DbProvider;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Query;

public abstract class DbItemBase<T, K> implements DbProvider.providerDb<T, K> {
    protected final Object mLock = new Object();

    /* access modifiers changed from: protected */
    public abstract T createItem(long j);

    /* access modifiers changed from: protected */
    public abstract T endItem(T t, long j);

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public long endItemAndInsert(T t, long j) {
        long insertOrReplace;
        synchronized (this.mLock) {
            insertOrReplace = insertOrReplace(endItem(t, j));
        }
        return insertOrReplace;
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public T generateItem(long j) {
        T createItem;
        synchronized (this.mLock) {
            createItem = createItem(j);
        }
        return createItem;
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public List<T> getAllItems() {
        List<T> loadAll;
        synchronized (this.mLock) {
            loadAll = getDao().loadAll();
        }
        return loadAll;
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public long getCount() {
        long count;
        synchronized (this.mLock) {
            count = getDao().queryBuilder().buildCount().count();
        }
        return count;
    }

    /* access modifiers changed from: protected */
    public abstract AbstractDao<T, K> getDao();

    /* access modifiers changed from: protected */
    public final DaoMaster getDaoMaser() {
        return DbContainer.getInstance().getDaoMaster();
    }

    /* access modifiers changed from: protected */
    public final DaoSession getDaoSession() {
        return DbContainer.getInstance().getDaoSession();
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public T getItemByMediaId(Long l) {
        throw new RuntimeException("todo");
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public T getItemByPath(String str) {
        throw new RuntimeException("todo");
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public T getItemWithExistedQuery(Query query, String str) {
        throw new RuntimeException("todo");
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public T getLastItem() {
        synchronized (this.mLock) {
            List<T> list = getDao().queryBuilder().limit(1).orderDesc(getOrderProperty()).list();
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        }
    }

    /* access modifiers changed from: protected */
    public abstract Property getOrderProperty();

    /* access modifiers changed from: protected */
    public final long insertOrReplace(T t) {
        return getDaoSession().insertOrReplace(t);
    }

    /* access modifiers changed from: protected */
    public abstract String provideTableName();

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public void removeItem(T t) {
        synchronized (this.mLock) {
            getDaoSession().delete(t);
        }
    }

    @Override // com.android.camera.db.provider.DbProvider.providerDb
    public void updateItem(T t) {
        synchronized (this.mLock) {
            updateItemThroughDb(t);
        }
    }

    /* access modifiers changed from: protected */
    public final void updateItemThroughDb(T t) {
        getDaoSession().update(t);
    }
}
