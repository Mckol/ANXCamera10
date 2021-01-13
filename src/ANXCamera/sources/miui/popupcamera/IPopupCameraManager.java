package miui.popupcamera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPopupCameraManager extends IInterface {

    public static class Default implements IPopupCameraManager {
        public IBinder asBinder() {
            return null;
        }

        @Override // miui.popupcamera.IPopupCameraManager
        public void calibrationMotor() throws RemoteException {
        }

        @Override // miui.popupcamera.IPopupCameraManager
        public int getMotorStatus() throws RemoteException {
            return 0;
        }

        @Override // miui.popupcamera.IPopupCameraManager
        public boolean notifyCameraStatus(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // miui.popupcamera.IPopupCameraManager
        public boolean popupMotor() throws RemoteException {
            return false;
        }

        @Override // miui.popupcamera.IPopupCameraManager
        public boolean takebackMotor() throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub extends Binder implements IPopupCameraManager {
        private static final String DESCRIPTOR = "miui.popupcamera.IPopupCameraManager";
        static final int TRANSACTION_calibrationMotor = 5;
        static final int TRANSACTION_getMotorStatus = 4;
        static final int TRANSACTION_notifyCameraStatus = 1;
        static final int TRANSACTION_popupMotor = 2;
        static final int TRANSACTION_takebackMotor = 3;

        /* access modifiers changed from: private */
        public static class Proxy implements IPopupCameraManager {
            public static IPopupCameraManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // miui.popupcamera.IPopupCameraManager
            public void calibrationMotor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, obtain, obtain2, 0) || Stub.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().calibrationMotor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // miui.popupcamera.IPopupCameraManager
            public int getMotorStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMotorStatus();
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // miui.popupcamera.IPopupCameraManager
            public boolean notifyCameraStatus(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    boolean z = false;
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().notifyCameraStatus(i, i2, str);
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

            @Override // miui.popupcamera.IPopupCameraManager
            public boolean popupMotor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().popupMotor();
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

            @Override // miui.popupcamera.IPopupCameraManager
            public boolean takebackMotor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().takebackMotor();
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

        public static IPopupCameraManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPopupCameraManager)) ? new Proxy(iBinder) : (IPopupCameraManager) queryLocalInterface;
        }

        public static IPopupCameraManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IPopupCameraManager iPopupCameraManager) {
            if (Proxy.sDefaultImpl != null || iPopupCameraManager == null) {
                return false;
            }
            Proxy.sDefaultImpl = iPopupCameraManager;
            return true;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int i3 = 0;
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean notifyCameraStatus = notifyCameraStatus(parcel.readInt(), parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                if (notifyCameraStatus) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean popupMotor = popupMotor();
                parcel2.writeNoException();
                if (popupMotor) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean takebackMotor = takebackMotor();
                parcel2.writeNoException();
                if (takebackMotor) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                int motorStatus = getMotorStatus();
                parcel2.writeNoException();
                parcel2.writeInt(motorStatus);
                return true;
            } else if (i == 5) {
                parcel.enforceInterface(DESCRIPTOR);
                calibrationMotor();
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

    void calibrationMotor() throws RemoteException;

    int getMotorStatus() throws RemoteException;

    boolean notifyCameraStatus(int i, int i2, String str) throws RemoteException;

    boolean popupMotor() throws RemoteException;

    boolean takebackMotor() throws RemoteException;
}
