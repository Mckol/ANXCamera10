package com.airbnb.lottie.parser.moshi;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* access modifiers changed from: package-private */
public final class LinkedHashTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Comparator<Comparable> NATURAL_ORDER = new c();
    Comparator<? super K> comparator;
    private LinkedHashTreeMap<K, V>.c entrySet;
    final f<K, V> header;
    private LinkedHashTreeMap<K, V>.d keySet;
    int modCount;
    int size;
    f<K, V>[] table;
    int threshold;

    /* access modifiers changed from: package-private */
    public static final class a<K, V> {
        private int Wf;
        private int Xf;
        private int size;
        private f<K, V> stack;

        a() {
        }

        /* access modifiers changed from: package-private */
        public void a(f<K, V> fVar) {
            fVar.right = null;
            fVar.parent = null;
            fVar.left = null;
            fVar.height = 1;
            int i = this.Wf;
            if (i > 0) {
                int i2 = this.size;
                if ((i2 & 1) == 0) {
                    this.size = i2 + 1;
                    this.Wf = i - 1;
                    this.Xf++;
                }
            }
            fVar.parent = this.stack;
            this.stack = fVar;
            this.size++;
            int i3 = this.Wf;
            if (i3 > 0) {
                int i4 = this.size;
                if ((i4 & 1) == 0) {
                    this.size = i4 + 1;
                    this.Wf = i3 - 1;
                    this.Xf++;
                }
            }
            int i5 = 4;
            while (true) {
                int i6 = i5 - 1;
                if ((this.size & i6) == i6) {
                    int i7 = this.Xf;
                    if (i7 == 0) {
                        f<K, V> fVar2 = this.stack;
                        f<K, V> fVar3 = fVar2.parent;
                        f<K, V> fVar4 = fVar3.parent;
                        fVar3.parent = fVar4.parent;
                        this.stack = fVar3;
                        fVar3.left = fVar4;
                        fVar3.right = fVar2;
                        fVar3.height = fVar2.height + 1;
                        fVar4.parent = fVar3;
                        fVar2.parent = fVar3;
                    } else if (i7 == 1) {
                        f<K, V> fVar5 = this.stack;
                        f<K, V> fVar6 = fVar5.parent;
                        this.stack = fVar6;
                        fVar6.right = fVar5;
                        fVar6.height = fVar5.height + 1;
                        fVar5.parent = fVar6;
                        this.Xf = 0;
                    } else if (i7 == 2) {
                        this.Xf = 0;
                    }
                    i5 *= 2;
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void reset(int i) {
            this.Wf = ((Integer.highestOneBit(i) * 2) - 1) - i;
            this.size = 0;
            this.Xf = 0;
            this.stack = null;
        }

        /* access modifiers changed from: package-private */
        public f<K, V> tc() {
            f<K, V> fVar = this.stack;
            if (fVar.parent == null) {
                return fVar;
            }
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: package-private */
    public static class b<K, V> {
        private f<K, V> Yf;

        b() {
        }

        /* access modifiers changed from: package-private */
        public void b(f<K, V> fVar) {
            f<K, V> fVar2 = null;
            while (true) {
                fVar2 = fVar;
                if (fVar2 != null) {
                    fVar2.parent = fVar2;
                    fVar = fVar2.left;
                } else {
                    this.Yf = fVar2;
                    return;
                }
            }
        }

        public f<K, V> next() {
            f<K, V> fVar = this.Yf;
            if (fVar == null) {
                return null;
            }
            f<K, V> fVar2 = fVar.parent;
            fVar.parent = null;
            f<K, V> fVar3 = fVar.right;
            while (true) {
                fVar2 = fVar3;
                if (fVar2 != null) {
                    fVar2.parent = fVar2;
                    fVar3 = fVar2.left;
                } else {
                    this.Yf = fVar2;
                    return fVar;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final class c extends AbstractSet<Map.Entry<K, V>> {
        c() {
        }

        public void clear() {
            LinkedHashTreeMap.this.clear();
        }

        public boolean contains(Object obj) {
            return (obj instanceof Map.Entry) && LinkedHashTreeMap.this.findByEntry((Map.Entry) obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new d(this);
        }

        public boolean remove(Object obj) {
            f<K, V> findByEntry;
            if (!(obj instanceof Map.Entry) || (findByEntry = LinkedHashTreeMap.this.findByEntry((Map.Entry) obj)) == null) {
                return false;
            }
            LinkedHashTreeMap.this.a((f) findByEntry, true);
            return true;
        }

        public int size() {
            return LinkedHashTreeMap.this.size;
        }
    }

    /* access modifiers changed from: package-private */
    public final class d extends AbstractSet<K> {
        d() {
        }

        public void clear() {
            LinkedHashTreeMap.this.clear();
        }

        public boolean contains(Object obj) {
            return LinkedHashTreeMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set, java.lang.Iterable
        public Iterator<K> iterator() {
            return new e(this);
        }

        public boolean remove(Object obj) {
            return LinkedHashTreeMap.this.removeInternalByKey(obj) != null;
        }

        public int size() {
            return LinkedHashTreeMap.this.size;
        }
    }

    /* access modifiers changed from: package-private */
    public abstract class e<T> implements Iterator<T> {
        int expectedModCount;
        f<K, V> lastReturned = null;
        f<K, V> next;

        e() {
            LinkedHashTreeMap linkedHashTreeMap = LinkedHashTreeMap.this;
            this.next = linkedHashTreeMap.header.next;
            this.expectedModCount = linkedHashTreeMap.modCount;
        }

        public final boolean hasNext() {
            return this.next != LinkedHashTreeMap.this.header;
        }

        /* access modifiers changed from: package-private */
        public final f<K, V> nextNode() {
            f<K, V> fVar = this.next;
            LinkedHashTreeMap linkedHashTreeMap = LinkedHashTreeMap.this;
            if (fVar == linkedHashTreeMap.header) {
                throw new NoSuchElementException();
            } else if (linkedHashTreeMap.modCount == this.expectedModCount) {
                this.next = fVar.next;
                this.lastReturned = fVar;
                return fVar;
            } else {
                throw new ConcurrentModificationException();
            }
        }

        public final void remove() {
            f<K, V> fVar = this.lastReturned;
            if (fVar != null) {
                LinkedHashTreeMap.this.a((f) fVar, true);
                this.lastReturned = null;
                this.expectedModCount = LinkedHashTreeMap.this.modCount;
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: package-private */
    public static final class f<K, V> implements Map.Entry<K, V> {
        final int hash;
        int height;
        final K key;
        f<K, V> left;
        f<K, V> next;
        f<K, V> parent;
        f<K, V> prev;
        f<K, V> right;
        V value;

        f() {
            this.key = null;
            this.hash = -1;
            this.prev = this;
            this.next = this;
        }

        f(f<K, V> fVar, K k, int i, f<K, V> fVar2, f<K, V> fVar3) {
            this.parent = fVar;
            this.key = k;
            this.hash = i;
            this.height = 1;
            this.next = fVar2;
            this.prev = fVar3;
            fVar3.next = this;
            fVar2.prev = this;
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0031 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            K k = this.key;
            if (k == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!k.equals(entry.getKey())) {
                return false;
            }
            V v = this.value;
            if (v == null) {
                return entry.getValue() == null;
            }
            if (!v.equals(entry.getValue())) {
                return false;
            }
        }

        public f<K, V> first() {
            f<K, V> fVar = this.left;
            while (true) {
                this = fVar;
                if (this == null) {
                    return this;
                }
                fVar = this.left;
            }
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        public int hashCode() {
            K k = this.key;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.value;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public f<K, V> last() {
            f<K, V> fVar = this.right;
            while (true) {
                this = fVar;
                if (this == null) {
                    return this;
                }
                fVar = this.right;
            }
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public String toString() {
            return ((Object) this.key) + "=" + ((Object) this.value);
        }
    }

    LinkedHashTreeMap() {
        this(null);
    }

    LinkedHashTreeMap(Comparator<? super K> comparator2) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = comparator2 == null ? NATURAL_ORDER : comparator2;
        this.header = new f<>();
        this.table = new f[16];
        f<K, V>[] fVarArr = this.table;
        this.threshold = (fVarArr.length / 2) + (fVarArr.length / 4);
    }

    private static int F(int i) {
        int i2 = i ^ ((i >>> 20) ^ (i >>> 12));
        return (i2 >>> 4) ^ ((i2 >>> 7) ^ i2);
    }

    private void a(f<K, V> fVar, f<K, V> fVar2) {
        f<K, V> fVar3 = fVar.parent;
        fVar.parent = null;
        if (fVar2 != null) {
            fVar2.parent = fVar3;
        }
        if (fVar3 == null) {
            int i = fVar.hash;
            f<K, V>[] fVarArr = this.table;
            fVarArr[i & (fVarArr.length - 1)] = fVar2;
        } else if (fVar3.left == fVar) {
            fVar3.left = fVar2;
        } else {
            fVar3.right = fVar2;
        }
    }

    static <K, V> f<K, V>[] a(f<K, V>[] fVarArr) {
        int length = fVarArr.length;
        f<K, V>[] fVarArr2 = new f[(length * 2)];
        b bVar = new b();
        a aVar = new a();
        a aVar2 = new a();
        for (int i = 0; i < length; i++) {
            f<K, V> fVar = fVarArr[i];
            if (fVar != null) {
                bVar.b(fVar);
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    f<K, V> next = bVar.next();
                    if (next == null) {
                        break;
                    } else if ((next.hash & length) == 0) {
                        i2++;
                    } else {
                        i3++;
                    }
                }
                aVar.reset(i2);
                aVar2.reset(i3);
                bVar.b(fVar);
                while (true) {
                    f<K, V> next2 = bVar.next();
                    if (next2 == null) {
                        break;
                    } else if ((next2.hash & length) == 0) {
                        aVar.a(next2);
                    } else {
                        aVar2.a(next2);
                    }
                }
                f<K, V> fVar2 = null;
                fVarArr2[i] = i2 > 0 ? aVar.tc() : null;
                int i4 = i + length;
                if (i3 > 0) {
                    fVar2 = aVar2.tc();
                }
                fVarArr2[i4] = fVar2;
            }
        }
        return fVarArr2;
    }

    private void b(f<K, V> fVar, boolean z) {
        while (fVar != null) {
            f<K, V> fVar2 = fVar.left;
            f<K, V> fVar3 = fVar.right;
            int i = 0;
            int i2 = fVar2 != null ? fVar2.height : 0;
            int i3 = fVar3 != null ? fVar3.height : 0;
            int i4 = i2 - i3;
            if (i4 == -2) {
                f<K, V> fVar4 = fVar3.left;
                f<K, V> fVar5 = fVar3.right;
                int i5 = fVar5 != null ? fVar5.height : 0;
                if (fVar4 != null) {
                    i = fVar4.height;
                }
                int i6 = i - i5;
                if (i6 == -1 || (i6 == 0 && !z)) {
                    c(fVar);
                } else {
                    d(fVar3);
                    c(fVar);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 2) {
                f<K, V> fVar6 = fVar2.left;
                f<K, V> fVar7 = fVar2.right;
                int i7 = fVar7 != null ? fVar7.height : 0;
                if (fVar6 != null) {
                    i = fVar6.height;
                }
                int i8 = i - i7;
                if (i8 == 1 || (i8 == 0 && !z)) {
                    d(fVar);
                } else {
                    c(fVar2);
                    d(fVar);
                }
                if (z) {
                    return;
                }
            } else if (i4 == 0) {
                fVar.height = i2 + 1;
                if (z) {
                    return;
                }
            } else {
                fVar.height = Math.max(i2, i3) + 1;
                if (!z) {
                    return;
                }
            }
            fVar = fVar.parent;
        }
    }

    private void c(f<K, V> fVar) {
        f<K, V> fVar2 = fVar.left;
        f<K, V> fVar3 = fVar.right;
        f<K, V> fVar4 = fVar3.left;
        f<K, V> fVar5 = fVar3.right;
        fVar.right = fVar4;
        if (fVar4 != null) {
            fVar4.parent = fVar;
        }
        a(fVar, fVar3);
        fVar3.left = fVar;
        fVar.parent = fVar3;
        int i = 0;
        fVar.height = Math.max(fVar2 != null ? fVar2.height : 0, fVar4 != null ? fVar4.height : 0) + 1;
        int i2 = fVar.height;
        if (fVar5 != null) {
            i = fVar5.height;
        }
        fVar3.height = Math.max(i2, i) + 1;
    }

    private void d(f<K, V> fVar) {
        f<K, V> fVar2 = fVar.left;
        f<K, V> fVar3 = fVar.right;
        f<K, V> fVar4 = fVar2.left;
        f<K, V> fVar5 = fVar2.right;
        fVar.left = fVar5;
        if (fVar5 != null) {
            fVar5.parent = fVar;
        }
        a(fVar, fVar2);
        fVar2.right = fVar;
        fVar.parent = fVar2;
        int i = 0;
        fVar.height = Math.max(fVar3 != null ? fVar3.height : 0, fVar5 != null ? fVar5.height : 0) + 1;
        int i2 = fVar.height;
        if (fVar4 != null) {
            i = fVar4.height;
        }
        fVar2.height = Math.max(i2, i) + 1;
    }

    private void doubleCapacity() {
        this.table = a(this.table);
        f<K, V>[] fVarArr = this.table;
        this.threshold = (fVarArr.length / 2) + (fVarArr.length / 4);
    }

    private boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }

    /* access modifiers changed from: package-private */
    public void a(f<K, V> fVar, boolean z) {
        int i;
        if (z) {
            f<K, V> fVar2 = fVar.prev;
            fVar2.next = fVar.next;
            fVar.next.prev = fVar2;
            fVar.prev = null;
            fVar.next = null;
        }
        f<K, V> fVar3 = fVar.left;
        f<K, V> fVar4 = fVar.right;
        f<K, V> fVar5 = fVar.parent;
        int i2 = 0;
        if (fVar3 == null || fVar4 == null) {
            if (fVar3 != null) {
                a(fVar, fVar3);
                fVar.left = null;
            } else if (fVar4 != null) {
                a(fVar, fVar4);
                fVar.right = null;
            } else {
                a(fVar, (f) null);
            }
            b(fVar5, false);
            this.size--;
            this.modCount++;
            return;
        }
        f<K, V> last = fVar3.height > fVar4.height ? fVar3.last() : fVar4.first();
        a((f) last, false);
        f<K, V> fVar6 = fVar.left;
        if (fVar6 != null) {
            i = fVar6.height;
            last.left = fVar6;
            fVar6.parent = last;
            fVar.left = null;
        } else {
            i = 0;
        }
        f<K, V> fVar7 = fVar.right;
        if (fVar7 != null) {
            i2 = fVar7.height;
            last.right = fVar7;
            fVar7.parent = last;
            fVar.right = null;
        }
        last.height = Math.max(i, i2) + 1;
        a(fVar, last);
    }

    public void clear() {
        Arrays.fill(this.table, (Object) null);
        this.size = 0;
        this.modCount++;
        f<K, V> fVar = this.header;
        f<K, V> fVar2 = fVar.next;
        while (fVar2 != fVar) {
            f<K, V> fVar3 = fVar2.next;
            fVar2.prev = null;
            fVar2.next = null;
            fVar2 = fVar3;
        }
        fVar.prev = fVar;
        fVar.next = fVar;
    }

    public boolean containsKey(Object obj) {
        return findByObject(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        LinkedHashTreeMap<K, V>.c cVar = this.entrySet;
        if (cVar != null) {
            return cVar;
        }
        LinkedHashTreeMap<K, V>.c cVar2 = new c();
        this.entrySet = cVar2;
        return cVar2;
    }

    /* access modifiers changed from: package-private */
    public f<K, V> find(K k, boolean z) {
        int i;
        f<K, V> fVar;
        Comparator<? super K> comparator2 = this.comparator;
        f<K, V>[] fVarArr = this.table;
        int F = F(k.hashCode());
        int length = (fVarArr.length - 1) & F;
        f<K, V> fVar2 = fVarArr[length];
        if (fVar2 != null) {
            K k2 = comparator2 == NATURAL_ORDER ? k : null;
            while (true) {
                i = k2 != null ? k2.compareTo(fVar2.key) : comparator2.compare(k, fVar2.key);
                if (i == 0) {
                    return fVar2;
                }
                f<K, V> fVar3 = i < 0 ? fVar2.left : fVar2.right;
                if (fVar3 == null) {
                    break;
                }
                fVar2 = fVar3;
            }
        } else {
            i = 0;
        }
        if (!z) {
            return null;
        }
        f<K, V> fVar4 = this.header;
        if (fVar2 != null) {
            fVar = new f<>(fVar2, k, F, fVar4, fVar4.prev);
            if (i < 0) {
                fVar2.left = fVar;
            } else {
                fVar2.right = fVar;
            }
            b(fVar2, true);
        } else if (comparator2 != NATURAL_ORDER || (k instanceof Comparable)) {
            fVar = new f<>(fVar2, k, F, fVar4, fVar4.prev);
            fVarArr[length] = fVar;
        } else {
            throw new ClassCastException(k.getClass().getName() + " is not Comparable");
        }
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 > this.threshold) {
            doubleCapacity();
        }
        this.modCount++;
        return fVar;
    }

    /* access modifiers changed from: package-private */
    public f<K, V> findByEntry(Map.Entry<?, ?> entry) {
        f<K, V> findByObject = findByObject(entry.getKey());
        if (findByObject != null && equal(findByObject.value, entry.getValue())) {
            return findByObject;
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: package-private */
    public f<K, V> findByObject(Object obj) {
        if (obj == 0) {
            return null;
        }
        try {
            return find(obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        f<K, V> findByObject = findByObject(obj);
        if (findByObject != null) {
            return findByObject.value;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        LinkedHashTreeMap<K, V>.d dVar = this.keySet;
        if (dVar != null) {
            return dVar;
        }
        LinkedHashTreeMap<K, V>.d dVar2 = new d();
        this.keySet = dVar2;
        return dVar2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        if (k != null) {
            f<K, V> find = find(k, true);
            V v2 = find.value;
            find.value = v;
            return v2;
        }
        throw new NullPointerException("key == null");
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        f<K, V> removeInternalByKey = removeInternalByKey(obj);
        if (removeInternalByKey != null) {
            return removeInternalByKey.value;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public f<K, V> removeInternalByKey(Object obj) {
        f<K, V> findByObject = findByObject(obj);
        if (findByObject != null) {
            a((f) findByObject, true);
        }
        return findByObject;
    }

    public int size() {
        return this.size;
    }
}
