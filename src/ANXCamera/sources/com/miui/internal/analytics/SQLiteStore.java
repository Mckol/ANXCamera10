package com.miui.internal.analytics;

import android.content.Context;
import android.database.Cursor;

/* access modifiers changed from: package-private */
public class SQLiteStore implements Storable {
    public static final String TAG = "ANALYTICS.SQLITESTORE";
    private DatabaseHelper mDatabaseHelper;

    SQLiteStore() {
    }

    @Override // com.miui.internal.analytics.Storable
    public void close() {
    }

    @Override // com.miui.internal.analytics.Storable
    public void create(Context context) {
        this.mDatabaseHelper = DatabaseHelper.getInstance();
    }

    @Override // com.miui.internal.analytics.Storable
    public Cursor readDataset(String str) {
        DatabaseHelper databaseHelper = this.mDatabaseHelper;
        if (databaseHelper != null) {
            return databaseHelper.query(str);
        }
        return null;
    }

    @Override // com.miui.internal.analytics.Storable
    public void writeData(String str, Integer num, String str2, String str3, String str4, String str5) {
        DatabaseHelper databaseHelper = this.mDatabaseHelper;
        if (databaseHelper != null) {
            databaseHelper.insert(str, num, str2, str3, str4, str5);
        }
    }
}
