package b.b.a;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;

/* compiled from: IMarketInstallerService */
public interface a extends IInterface {

    /* renamed from: b.b.a.a$a  reason: collision with other inner class name */
    /* compiled from: IMarketInstallerService */
    public static class C0002a implements a {
        @Override // b.b.a.a
        public void a(Uri uri, ResultReceiver resultReceiver, Bundle bundle) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    /* compiled from: IMarketInstallerService */
    public static abstract class b extends Binder implements a {
        private static final String DESCRIPTOR = "com.market.pm.IMarketInstallerService";
        static final int TRANSACTION_installPackage = 1;

        /* access modifiers changed from: private */
        /* renamed from: b.b.a.a$b$a  reason: collision with other inner class name */
        /* compiled from: IMarketInstallerService */
        public static class C0003a implements a {
            public static a sDefaultImpl;
            private IBinder mRemote;

            C0003a(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // b.b.a.a
            public void a(Uri uri, ResultReceiver resultReceiver, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (resultReceiver != null) {
                        obtain.writeInt(1);
                        resultReceiver.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (this.mRemote.transact(1, obtain, obtain2, 0) || b.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    b.getDefaultImpl().a(uri, resultReceiver, bundle);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return b.DESCRIPTOR;
            }
        }

        public b() {
            attachInterface(this, DESCRIPTOR);
        }

        public static boolean a(a aVar) {
            if (C0003a.sDefaultImpl != null || aVar == null) {
                return false;
            }
            C0003a.sDefaultImpl = aVar;
            return true;
        }

        public static a asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0003a(iBinder) : (a) queryLocalInterface;
        }

        public static a getDefaultImpl() {
            return C0003a.sDefaultImpl;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                Bundle bundle = null;
                Uri uri = parcel.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcel) : null;
                ResultReceiver resultReceiver = parcel.readInt() != 0 ? (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel) : null;
                if (parcel.readInt() != 0) {
                    bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                }
                a(uri, resultReceiver, bundle);
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

    void a(Uri uri, ResultReceiver resultReceiver, Bundle bundle) throws RemoteException;
}
