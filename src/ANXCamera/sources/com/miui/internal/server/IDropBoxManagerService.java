package com.miui.internal.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import miui.os.DropBoxManager;

public interface IDropBoxManagerService extends IInterface {

    public static class Default implements IDropBoxManagerService {
        @Override // com.miui.internal.server.IDropBoxManagerService
        public void add(DropBoxManager.Entry entry) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.internal.server.IDropBoxManagerService
        public DropBoxManager.Entry getNextEntry(String str, long j) throws RemoteException {
            return null;
        }

        @Override // com.miui.internal.server.IDropBoxManagerService
        public boolean isTagEnabled(String str) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub extends Binder implements IDropBoxManagerService {
        private static final String DESCRIPTOR = "com.miui.internal.server.IDropBoxManagerService";
        static final int TRANSACTION_add = 1;
        static final int TRANSACTION_getNextEntry = 3;
        static final int TRANSACTION_isTagEnabled = 2;

        /* access modifiers changed from: private */
        public static class Proxy implements IDropBoxManagerService {
            public static IDropBoxManagerService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.miui.internal.server.IDropBoxManagerService
            public void add(DropBoxManager.Entry entry) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (entry != null) {
                        obtain.writeInt(1);
                        entry.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().add(entry);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.miui.internal.server.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntry(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNextEntry(str, j);
                    }
                    obtain2.readException();
                    DropBoxManager.Entry createFromParcel = obtain2.readInt() != 0 ? DropBoxManager.Entry.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.miui.internal.server.IDropBoxManagerService
            public boolean isTagEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    boolean z = false;
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTagEnabled(str);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDropBoxManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDropBoxManagerService)) ? new Proxy(iBinder) : (IDropBoxManagerService) queryLocalInterface;
        }

        public static IDropBoxManagerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IDropBoxManagerService iDropBoxManagerService) {
            if (Proxy.sDefaultImpl != null || iDropBoxManagerService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iDropBoxManagerService;
            return true;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                int i3 = 0;
                if (i == 2) {
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isTagEnabled = isTagEnabled(parcel.readString());
                    parcel2.writeNoException();
                    if (isTagEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                } else if (i == 3) {
                    parcel.enforceInterface(DESCRIPTOR);
                    DropBoxManager.Entry nextEntry = getNextEntry(parcel.readString(), parcel.readLong());
                    parcel2.writeNoException();
                    if (nextEntry != null) {
                        parcel2.writeInt(1);
                        nextEntry.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                } else if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                } else {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
            } else {
                parcel.enforceInterface(DESCRIPTOR);
                add(parcel.readInt() != 0 ? DropBoxManager.Entry.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
        }
    }

    void add(DropBoxManager.Entry entry) throws RemoteException;

    DropBoxManager.Entry getNextEntry(String str, long j) throws RemoteException;

    boolean isTagEnabled(String str) throws RemoteException;
}
