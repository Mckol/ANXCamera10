package com.miui.internal.analytics;

import android.database.Cursor;
import android.os.Parcel;
import android.text.TextUtils;
import java.util.List;

public class LogEvent extends Event {
    private String mErrorClass;
    private String mMessage;

    public LogEvent() {
        this.mPackageName = "";
        this.mType = 1;
        this.mMessage = "";
        this.mErrorClass = "";
    }

    public LogEvent(String str, String str2, String str3, String str4) {
        this.mPackageName = str;
        this.mType = 1;
        this.mEventId = str2;
        this.mMessage = str3;
        this.mErrorClass = str4;
    }

    private String buildParam() {
        return this.mMessage + "#ITEMSPLITTER#" + this.mErrorClass;
    }

    private void parseParam(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("#ITEMSPLITTER#");
            if (split.length > 1) {
                this.mMessage = split[0];
                this.mErrorClass = split[1];
            }
        }
    }

    @Override // com.miui.internal.analytics.Event
    public void dispatch() {
        List<Dispatchable> list = Event.sDispatcher;
        if (list != null) {
            for (Dispatchable dispatchable : list) {
                dispatchable.dispatchLog(this);
            }
        }
    }

    public String getErrorClass() {
        return this.mErrorClass;
    }

    public String getMessage() {
        return this.mMessage;
    }

    /* access modifiers changed from: package-private */
    @Override // com.miui.internal.analytics.Event
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.mMessage = parcel.readString();
        this.mErrorClass = parcel.readString();
    }

    @Override // com.miui.internal.analytics.Event
    public void restore(Cursor cursor) {
        super.restore(cursor);
        if (cursor != null) {
            parseParam(cursor.getString(cursor.getColumnIndexOrThrow(EventUtils.COLUMN_PARAM)));
        }
    }

    @Override // com.miui.internal.analytics.Event
    public void writeEvent(Storable storable) {
        if (storable != null) {
            String str = this.mPackageName;
            Integer num = this.mType;
            String str2 = this.mEventId;
            String buildParam = buildParam();
            storable.writeData(str, num, str2, buildParam, this.mTrackTime + "", Boolean.toString(false));
        }
    }

    @Override // com.miui.internal.analytics.Event
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mMessage);
        parcel.writeString(this.mErrorClass);
    }
}
