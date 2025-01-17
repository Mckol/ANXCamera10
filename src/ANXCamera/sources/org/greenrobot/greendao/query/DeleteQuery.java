package org.greenrobot.greendao.query;

import java.util.Date;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

public class DeleteQuery<T> extends AbstractQuery<T> {
    private final QueryData<T> queryData;

    /* access modifiers changed from: private */
    public static final class QueryData<T2> extends AbstractQueryData<T2, DeleteQuery<T2>> {
        private QueryData(AbstractDao<T2, ?> abstractDao, String str, String[] strArr) {
            super(abstractDao, str, strArr);
        }

        /* access modifiers changed from: protected */
        @Override // org.greenrobot.greendao.query.AbstractQueryData
        public DeleteQuery<T2> createQuery() {
            return new DeleteQuery<>(this, this.dao, this.sql, (String[]) this.initialValues.clone());
        }
    }

    private DeleteQuery(QueryData<T> queryData2, AbstractDao<T, ?> abstractDao, String str, String[] strArr) {
        super(abstractDao, str, strArr);
        this.queryData = queryData2;
    }

    static <T2> DeleteQuery<T2> create(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return (DeleteQuery) new QueryData(abstractDao, str, AbstractQuery.toStringArray(objArr)).forCurrentThread();
    }

    public void executeDeleteWithoutDetachingEntities() {
        checkThread();
        Database database = this.dao.getDatabase();
        if (database.isDbLockedByCurrentThread()) {
            this.dao.getDatabase().execSQL(this.sql, this.parameters);
            return;
        }
        database.beginTransaction();
        try {
            this.dao.getDatabase().execSQL(this.sql, this.parameters);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public DeleteQuery<T> forCurrentThread() {
        return (DeleteQuery) this.queryData.forCurrentThread(this);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public DeleteQuery<T> setParameter(int i, Boolean bool) {
        return (DeleteQuery) super.setParameter(i, bool);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public DeleteQuery<T> setParameter(int i, Object obj) {
        super.setParameter(i, obj);
        return this;
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public DeleteQuery<T> setParameter(int i, Date date) {
        return (DeleteQuery) super.setParameter(i, date);
    }
}
