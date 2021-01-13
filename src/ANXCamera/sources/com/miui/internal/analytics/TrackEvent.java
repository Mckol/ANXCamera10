package com.miui.internal.analytics;

import android.database.Cursor;
import android.os.Parcel;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackEvent extends Event {
    private Map<String, String> mParam;
    private long mValue;

    public TrackEvent() {
        this.mPackageName = "";
        this.mType = 2;
        this.mParam = null;
        this.mValue = 0;
    }

    public TrackEvent(String str, String str2, Map<String, String> map, long j) {
        this.mPackageName = str;
        this.mType = 2;
        this.mEventId = str2;
        this.mParam = map;
        this.mValue = j;
    }

    private String buildParam(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : map.keySet()) {
            sb.append(str);
            sb.append("#ITEMSPLITTER#");
            sb.append(map.get(str));
            sb.append("#ITEMSPLITTER#");
        }
        return sb.toString();
    }

    private void parseParam(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mParam = new HashMap();
            String[] split = str.split("#ITEMSPLITTER#");
            for (int i = 0; i < split.length - 1; i += 2) {
                this.mParam.put(split[i], split[i + 1]);
            }
        }
    }

    @Override // com.miui.internal.analytics.Event
    public void dispatch() {
        List<Dispatchable> list = Event.sDispatcher;
        if (list != null) {
            for (Dispatchable dispatchable : list) {
                dispatchable.dispatchEvent(this);
            }
        }
    }

    public Map<String, String> getParam() {
        return this.mParam;
    }

    public long getValue() {
        return this.mValue;
    }

    /* access modifiers changed from: package-private */
    @Override // com.miui.internal.analytics.Event
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        parseParam(parcel.readString());
        this.mValue = parcel.readLong();
    }

    @Override // com.miui.internal.analytics.Event
    public void restore(Cursor cursor) {
        super.restore(cursor);
        if (cursor != null) {
            this.mValue = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("value")));
            parseParam(cursor.getString(cursor.getColumnIndexOrThrow(EventUtils.COLUMN_PARAM)));
        }
    }

    @Override // com.miui.internal.analytics.Event
    public void writeEvent(Storable storable) {
        if (storable != null) {
            storable.writeData(this.mPackageName, this.mType, this.mEventId, buildParam(this.mParam), this.mTrackTime + "", this.mValue + "");
        }
    }

    @Override // com.miui.internal.analytics.Event
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(buildParam(this.mParam));
        parcel.writeLong(this.mValue);
    }
}
