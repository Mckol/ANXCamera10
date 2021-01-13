package com.miui.internal.analytics;

import android.os.Parcel;
import java.util.List;

public class TrackPageViewEvent extends Event {
    private static final String PAGEVIEW_EVENT = "_pageview_event_";

    public TrackPageViewEvent() {
        this.mPackageName = "";
        this.mType = 3;
        this.mEventId = PAGEVIEW_EVENT;
    }

    public TrackPageViewEvent(String str) {
        this.mPackageName = str;
    }

    @Override // com.miui.internal.analytics.Event
    public void dispatch() {
        List<Dispatchable> list = Event.sDispatcher;
        if (list != null) {
            for (Dispatchable dispatchable : list) {
                dispatchable.dispatchPageView(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.miui.internal.analytics.Event
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
    }

    @Override // com.miui.internal.analytics.Event
    public void writeEvent(Storable storable) {
        if (storable != null) {
            String str = this.mPackageName;
            Integer num = this.mType;
            String str2 = this.mEventId;
            storable.writeData(str, num, str2, "", this.mTrackTime + "", Boolean.toString(false));
        }
    }

    @Override // com.miui.internal.analytics.Event
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
