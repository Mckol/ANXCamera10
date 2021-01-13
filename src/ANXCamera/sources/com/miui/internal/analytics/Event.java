package com.miui.internal.analytics;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.List;

public abstract class Event implements Parcelable {
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        /* class com.miui.internal.analytics.Event.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public Event createFromParcel(Parcel parcel) {
            try {
                Event event = (Event) Class.forName(parcel.readString()).newInstance();
                event.readFromParcel(parcel);
                return event;
            } catch (InstantiationException e2) {
                Log.e(Event.TAG, "InstantiationException catched when create event from parcel", e2);
                return null;
            } catch (IllegalAccessException e3) {
                Log.e(Event.TAG, "IllegalAccessException catched when create event from parcel", e3);
                return null;
            } catch (ClassNotFoundException e4) {
                Log.e(Event.TAG, "ClassNotFoundException catched when create event from parcel", e4);
                return null;
            }
        }

        @Override // android.os.Parcelable.Creator
        public Event[] newArray(int i) {
            return new Event[i];
        }
    };
    protected static final String SPLITTER = "#ITEMSPLITTER#";
    private static final String TAG = "AnalyticsEvent";
    protected static List<Dispatchable> sDispatcher;
    protected String mEventId;
    protected String mPackageName;
    protected String mPolicy;
    protected long mTrackTime;
    protected Integer mType;

    public Event() {
        setTime(System.currentTimeMillis());
    }

    public static void setDispatcher(List<Dispatchable> list) {
        sDispatcher = list;
    }

    public int describeContents() {
        return 0;
    }

    public abstract void dispatch();

    public String getEventId() {
        return this.mEventId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getPolicy() {
        return this.mPolicy;
    }

    public long getTrackTime() {
        return this.mTrackTime;
    }

    public Integer getType() {
        return this.mType;
    }

    /* access modifiers changed from: package-private */
    public void readFromParcel(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mType = Integer.valueOf(parcel.readInt());
        this.mEventId = parcel.readString();
        this.mPolicy = parcel.readString();
        this.mTrackTime = parcel.readLong();
    }

    public void restore(Cursor cursor) {
        if (cursor != null) {
            this.mPackageName = cursor.getString(cursor.getColumnIndexOrThrow(EventUtils.COLUMN_PACKAGE_NAME));
            this.mEventId = cursor.getString(cursor.getColumnIndexOrThrow(EventUtils.COLUMN_ID));
            this.mTrackTime = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("time")));
        }
    }

    public void setPolicy(String str) {
        this.mPolicy = str;
    }

    public void setTime(long j) {
        this.mTrackTime = j;
    }

    public abstract void writeEvent(Storable storable);

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getClass().getName());
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mType.intValue());
        parcel.writeString(this.mEventId);
        parcel.writeString(this.mPolicy);
        parcel.writeLong(this.mTrackTime);
    }
}
