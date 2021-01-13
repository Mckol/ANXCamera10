package com.miui.internal.analytics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAnalytics extends IInterface {

    public static class Default implements IAnalytics {
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.internal.analytics.IAnalytics
        public void track(Event[] eventArr) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements IAnalytics {
        private static final String DESCRIPTOR = "com.miui.internal.analytics.IAnalytics";
        static final int TRANSACTION_track = 1;

        /* access modifiers changed from: private */
        public static class Proxy implements IAnalytics {
            public static IAnalytics sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.miui.internal.analytics.IAnalytics
            public void track(Event[] eventArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(eventArr, 0);
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().track(eventArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAnalytics asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IAnalytics)) ? new Proxy(iBinder) : (IAnalytics) queryLocalInterface;
        }

        public static IAnalytics getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IAnalytics iAnalytics) {
            if (Proxy.sDefaultImpl != null || iAnalytics == null) {
                return false;
            }
            Proxy.sDefaultImpl = iAnalytics;
            return true;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                track((Event[]) parcel.createTypedArray(Event.CREATOR));
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }
    }

    void track(Event[] eventArr) throws RemoteException;
}
