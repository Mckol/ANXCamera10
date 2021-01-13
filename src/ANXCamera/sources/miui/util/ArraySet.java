package miui.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E> implements Collection<E>, Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArraySet";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    MapCollections<E, E> mCollections;
    int[] mHashes;
    int mSize;

    public ArraySet() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    public ArraySet(int i) {
        if (i == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    public ArraySet(ArraySet arraySet) {
        this();
        if (arraySet != null) {
            addAll(arraySet);
        }
    }

    private void allocArrays(int i) {
        if (i == 8) {
            synchronized (ArraySet.class) {
                if (mTwiceBaseCache != null) {
                    Object[] objArr = mTwiceBaseCache;
                    this.mArray = objArr;
                    mTwiceBaseCache = (Object[]) objArr[0];
                    this.mHashes = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    mTwiceBaseCacheSize--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (ArraySet.class) {
                if (mBaseCache != null) {
                    Object[] objArr2 = mBaseCache;
                    this.mArray = objArr2;
                    mBaseCache = (Object[]) objArr2[0];
                    this.mHashes = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    mBaseCacheSize--;
                    return;
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new Object[i];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (ArraySet.class) {
                if (mTwiceBaseCacheSize < 10) {
                    objArr[0] = mTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    mTwiceBaseCache = objArr;
                    mTwiceBaseCacheSize++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (ArraySet.class) {
                if (mBaseCacheSize < 10) {
                    objArr[0] = mBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    mBaseCache = objArr;
                    mBaseCacheSize++;
                }
            }
        }
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<E, E>() {
                /* class miui.util.ArraySet.AnonymousClass1 */

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public void colClear() {
                    ArraySet.this.clear();
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public Object colGetEntry(int i, int i2) {
                    return ArraySet.this.mArray[i];
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public Map<E, E> colGetMap() {
                    throw new UnsupportedOperationException("not a map");
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public int colGetSize() {
                    return ArraySet.this.mSize;
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public int colIndexOfKey(Object obj) {
                    ArraySet arraySet = ArraySet.this;
                    return obj == null ? arraySet.indexOfNull() : arraySet.indexOf(obj, obj.hashCode());
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public int colIndexOfValue(Object obj) {
                    ArraySet arraySet = ArraySet.this;
                    return obj == null ? arraySet.indexOfNull() : arraySet.indexOf(obj, obj.hashCode());
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public void colPut(E e2, E e3) {
                    ArraySet.this.add(e2);
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public void colRemoveAt(int i) {
                    ArraySet.this.removeAt(i);
                }

                /* access modifiers changed from: protected */
                @Override // miui.util.MapCollections
                public E colSetValue(int i, E e2) {
                    throw new UnsupportedOperationException("not a map");
                }
            };
        }
        return this.mCollections;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpers.binarySearch(this.mHashes, i2, i);
        if (binarySearch < 0 || obj.equals(this.mArray[binarySearch])) {
            return binarySearch;
        }
        int i3 = binarySearch + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3])) {
                return i3;
            }
            i3++;
        }
        int i4 = binarySearch - 1;
        while (i4 >= 0 && this.mHashes[i4] == i) {
            if (obj.equals(this.mArray[i4])) {
                return i4;
            }
            i4--;
        }
        return ~i3;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpers.binarySearch(this.mHashes, i, 0);
        if (binarySearch < 0 || this.mArray[binarySearch] == null) {
            return binarySearch;
        }
        int i2 = binarySearch + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2] == null) {
                return i2;
            }
            i2++;
        }
        int i3 = binarySearch - 1;
        while (i3 >= 0 && this.mHashes[i3] == 0) {
            if (this.mArray[i3] == null) {
                return i3;
            }
            i3--;
        }
        return ~i2;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(E e2) {
        int i;
        int i2;
        if (e2 == null) {
            i2 = indexOfNull();
            i = 0;
        } else {
            int hashCode = e2.hashCode();
            i = hashCode;
            i2 = indexOf(e2, hashCode);
        }
        if (i2 >= 0) {
            return false;
        }
        int i3 = ~i2;
        int i4 = this.mSize;
        if (i4 >= this.mHashes.length) {
            int i5 = 4;
            if (i4 >= 8) {
                i5 = (i4 >> 1) + i4;
            } else if (i4 >= 4) {
                i5 = 8;
            }
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            allocArrays(i5);
            int[] iArr2 = this.mHashes;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
            }
            freeArrays(iArr, objArr, this.mSize);
        }
        int i6 = this.mSize;
        if (i3 < i6) {
            int[] iArr3 = this.mHashes;
            int i7 = i3 + 1;
            System.arraycopy(iArr3, i3, iArr3, i7, i6 - i3);
            Object[] objArr2 = this.mArray;
            System.arraycopy(objArr2, i3, objArr2, i7, this.mSize - i3);
        }
        this.mHashes[i3] = i;
        this.mArray[i3] = e2;
        this.mSize++;
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: miui.util.ArraySet<E> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends E> collection) {
        ensureCapacity(this.mSize + collection.size());
        Iterator<? extends E> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= add(it.next());
        }
        return z;
    }

    public void clear() {
        int i = this.mSize;
        if (i != 0) {
            freeArrays(this.mHashes, this.mArray, i);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
        }
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            if (indexOfNull() >= 0) {
                return true;
            }
        } else if (indexOf(obj, obj.hashCode()) >= 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public void ensureCapacity(int i) {
        int[] iArr = this.mHashes;
        if (iArr.length < i) {
            Object[] objArr = this.mArray;
            allocArrays(i);
            int i2 = this.mSize;
            if (i2 > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, i2);
                System.arraycopy(objArr, 0, this.mArray, 0, this.mSize);
            }
            freeArrays(iArr, objArr, this.mSize);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (size() != set.size()) {
                return false;
            }
            for (int i = 0; i < this.mSize; i++) {
                try {
                    if (!set.contains(valueAt(i))) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int[] iArr = this.mHashes;
        int i = this.mSize;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Collection, java.util.Set, java.lang.Iterable
    public Iterator<E> iterator() {
        return getCollection().getKeySet().iterator();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: miui.util.ArraySet<E> */
    /* JADX WARN: Multi-variable type inference failed */
    public void putAll(ArraySet<? extends E> arraySet) {
        int i = arraySet.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                add(arraySet.valueAt(i2));
            }
        } else if (i > 0) {
            System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, i);
            System.arraycopy(arraySet.mArray, 0, this.mArray, 0, i);
            this.mSize = i;
        }
    }

    public boolean remove(Object obj) {
        int indexOfNull = obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
        if (indexOfNull < 0) {
            return false;
        }
        removeAt(indexOfNull);
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    public E removeAt(int i) {
        Object[] objArr = this.mArray;
        E e2 = (E) objArr[i];
        int i2 = this.mSize;
        if (i2 <= 1) {
            freeArrays(this.mHashes, objArr, i2);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
        } else {
            int[] iArr = this.mHashes;
            int i3 = 8;
            if (iArr.length <= 8 || i2 >= iArr.length / 3) {
                this.mSize--;
                int i4 = this.mSize;
                if (i < i4) {
                    int[] iArr2 = this.mHashes;
                    int i5 = i + 1;
                    System.arraycopy(iArr2, i5, iArr2, i, i4 - i);
                    Object[] objArr2 = this.mArray;
                    System.arraycopy(objArr2, i5, objArr2, i, this.mSize - i);
                }
                this.mArray[this.mSize] = null;
            } else {
                if (i2 > 8) {
                    i3 = i2 + (i2 >> 1);
                }
                int[] iArr3 = this.mHashes;
                Object[] objArr3 = this.mArray;
                allocArrays(i3);
                this.mSize--;
                if (i > 0) {
                    System.arraycopy(iArr3, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr3, 0, this.mArray, 0, i);
                }
                int i6 = this.mSize;
                if (i < i6) {
                    int i7 = i + 1;
                    System.arraycopy(iArr3, i7, this.mHashes, i, i6 - i);
                    System.arraycopy(objArr3, i7, this.mArray, i, this.mSize - i);
                }
            }
        }
        return e2;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.mArray[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }

    public int size() {
        return this.mSize;
    }

    public Object[] toArray() {
        int i = this.mSize;
        Object[] objArr = new Object[i];
        System.arraycopy(this.mArray, 0, objArr, 0, i);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        if (tArr.length < this.mSize) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.mSize));
        }
        System.arraycopy(this.mArray, 0, tArr, 0, this.mSize);
        int length = tArr.length;
        int i = this.mSize;
        if (length > i) {
            tArr[i] = null;
        }
        return tArr;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 14);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append((Object) valueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public E valueAt(int i) {
        return (E) this.mArray[i];
    }
}