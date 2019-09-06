package f.a.a.a.b;

import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwBinder.DeathRecipient;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: IMiSys */
public interface b extends a.a.a.a.b {
    public static final String kInterfaceName = "vendor.xiaomi.hardware.misys@2.0::IMiSys";

    /* compiled from: IMiSys */
    public static final class a implements b {
        private IHwBinder mRemote;

        public a(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        public int a(String str, String str2, ArrayList<Byte> arrayList, long j) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(b.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeInt8Vector(arrayList);
            hwParcel.writeInt64(j);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public a g(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(b.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                a aVar = new a();
                aVar.readFromParcel(hwParcel2);
                return aVar;
            } finally {
                hwParcel2.release();
            }
        }

        public a.a.a.a.a getDebugInfo() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                a.a.a.a.a aVar = new a.a.a.a.a();
                aVar.readFromParcel(hwParcel2);
                return aVar;
            } finally {
                hwParcel2.release();
            }
        }

        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList<byte[]> arrayList = new ArrayList<>();
                HwBlob readBuffer = hwParcel2.readBuffer(16);
                int int32 = readBuffer.getInt32(8);
                HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer((long) (int32 * 32), readBuffer.handle(), 0, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array((long) (i * 32), bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        public int h(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(b.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        public String interfaceDescriptor() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        public boolean j(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(b.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        public boolean linkToDeath(DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        public void notifySyspropsChanged() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public void ping() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public void setHALInstrumentation() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.a.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public String toString() {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(interfaceDescriptor());
                sb.append("@Proxy");
                return sb.toString();
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.xiaomi.hardware.misys@2.0::IMiSys]@Proxy";
            }
        }

        public boolean unlinkToDeath(DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    /* renamed from: f.a.a.a.b.b$b reason: collision with other inner class name */
    /* compiled from: IMiSys */
    public static abstract class C0017b extends HwBinder implements b {
        public IHwBinder asBinder() {
            return this;
        }

        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        public final a.a.a.a.a getDebugInfo() {
            a.a.a.a.a aVar = new a.a.a.a.a();
            aVar.pid = HidlSupport.getPidIfSharable();
            aVar.ptr = 0;
            aVar.arch = 0;
            return aVar;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{36, 71, 63, 51, 7, -77, -59, -27, 109, 14, 90, 75, 103, 25, 109, -69, -85, 100, 117, -54, 74, -89, -75, -126, 107, -29, 109, -11, -76, -32, -37, -41}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{b.kInterfaceName, a.a.a.a.b.kInterfaceName}));
        }

        public final String interfaceDescriptor() {
            return b.kInterfaceName;
        }

        public final boolean linkToDeath(DeathRecipient deathRecipient, long j) {
            return true;
        }

        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) throws RemoteException {
            String str = b.kInterfaceName;
            int i3 = 0;
            boolean z = true;
            if (i == 1) {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(str);
                int h = h(hwParcel.readString(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(h);
                hwParcel2.send();
            } else if (i == 2) {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(str);
                int a2 = a(hwParcel.readString(), hwParcel.readString(), hwParcel.readInt8Vector(), hwParcel.readInt64());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(a2);
                hwParcel2.send();
            } else if (i == 3) {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(str);
                a g = g(hwParcel.readString(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                g.writeToParcel(hwParcel2);
                hwParcel2.send();
            } else if (i != 4) {
                String str2 = a.a.a.a.b.kInterfaceName;
                switch (i) {
                    case 256067662:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        ArrayList interfaceChain = interfaceChain();
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeStringVector(interfaceChain);
                        hwParcel2.send();
                        return;
                    case 256131655:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 256136003:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        String interfaceDescriptor = interfaceDescriptor();
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeString(interfaceDescriptor);
                        hwParcel2.send();
                        return;
                    case 256398152:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        ArrayList hashChain = getHashChain();
                        hwParcel2.writeStatus(0);
                        HwBlob hwBlob = new HwBlob(16);
                        int size = hashChain.size();
                        hwBlob.putInt32(8, size);
                        hwBlob.putBool(12, false);
                        HwBlob hwBlob2 = new HwBlob(size * 32);
                        while (i3 < size) {
                            long j = (long) (i3 * 32);
                            byte[] bArr = (byte[]) hashChain.get(i3);
                            if (bArr == null || bArr.length != 32) {
                                throw new IllegalArgumentException("Array element is not of the expected length");
                            }
                            hwBlob2.putInt8Array(j, bArr);
                            i3++;
                        }
                        hwBlob.putBlob(0, hwBlob2);
                        hwParcel2.writeBuffer(hwBlob);
                        hwParcel2.send();
                        return;
                    case 256462420:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 1) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        setHALInstrumentation();
                        return;
                    case 256660548:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 0) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        return;
                    case 256921159:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        ping();
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 257049926:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        a.a.a.a.a debugInfo = getDebugInfo();
                        hwParcel2.writeStatus(0);
                        debugInfo.writeToParcel(hwParcel2);
                        hwParcel2.send();
                        return;
                    case 257120595:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 1) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(str2);
                        notifySyspropsChanged();
                        return;
                    case 257250372:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 0) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            } else {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(str);
                boolean j2 = j(hwParcel.readString(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                hwParcel2.writeBool(j2);
                hwParcel2.send();
            }
        }

        public final void ping() {
        }

        public IHwInterface queryLocalInterface(String str) {
            if (b.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String str) throws RemoteException {
            registerService(str);
        }

        public final void setHALInstrumentation() {
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(interfaceDescriptor());
            sb.append("@Stub");
            return sb.toString();
        }

        public final boolean unlinkToDeath(DeathRecipient deathRecipient) {
            return true;
        }
    }

    static b asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        String str = kInterfaceName;
        b queryLocalInterface = iHwBinder.queryLocalInterface(str);
        if (queryLocalInterface != null && (queryLocalInterface instanceof b)) {
            return queryLocalInterface;
        }
        a aVar = new a(iHwBinder);
        try {
            Iterator it = aVar.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals(str)) {
                    return aVar;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    static b castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static b getService() throws RemoteException {
        return getService("default");
    }

    static b getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    static b getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static b getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    int a(String str, String str2, ArrayList<Byte> arrayList, long j) throws RemoteException;

    IHwBinder asBinder();

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    a g(String str, String str2) throws RemoteException;

    a.a.a.a.a getDebugInfo() throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    int h(String str, String str2) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean j(String str, String str2) throws RemoteException;

    boolean linkToDeath(DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void ping() throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    boolean unlinkToDeath(DeathRecipient deathRecipient) throws RemoteException;
}
