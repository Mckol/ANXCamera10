package com.xiaomi.a.a.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

public interface a extends IInterface {

    /* renamed from: com.xiaomi.a.a.a.a$a  reason: collision with other inner class name */
    public static abstract class AbstractBinderC0006a extends Binder implements a {

        /* renamed from: a  reason: collision with root package name */
        static final int f382a = 1;

        /* renamed from: b  reason: collision with root package name */
        static final int f383b = 2;

        /* renamed from: c  reason: collision with root package name */
        private static final String f384c = "com.xiaomi.xmsf.push.service.IHttpService";

        /* renamed from: com.xiaomi.a.a.a.a$a$a  reason: collision with other inner class name */
        private static class C0007a implements a {

            /* renamed from: a  reason: collision with root package name */
            private IBinder f385a;

            C0007a(IBinder iBinder) {
                this.f385a = iBinder;
            }

            public String a() {
                return AbstractBinderC0006a.f384c;
            }

            @Override // com.xiaomi.a.a.a.a
            public String a(String str, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(AbstractBinderC0006a.f384c);
                    obtain.writeString(str);
                    obtain.writeMap(map);
                    this.f385a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f385a;
            }

            @Override // com.xiaomi.a.a.a.a
            public String b(String str, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(AbstractBinderC0006a.f384c);
                    obtain.writeString(str);
                    obtain.writeMap(map);
                    this.f385a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public AbstractBinderC0006a() {
            attachInterface(this, f384c);
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(f384c);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0007a(iBinder) : (a) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(f384c);
                String a2 = a(parcel.readString(), parcel.readHashMap(AbstractBinderC0006a.class.getClassLoader()));
                parcel2.writeNoException();
                parcel2.writeString(a2);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(f384c);
                String b2 = b(parcel.readString(), parcel.readHashMap(AbstractBinderC0006a.class.getClassLoader()));
                parcel2.writeNoException();
                parcel2.writeString(b2);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(f384c);
                return true;
            }
        }
    }

    String a(String str, Map map) throws RemoteException;

    String b(String str, Map map) throws RemoteException;
}
