package com.airbnb.lottie.a.a;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import com.airbnb.lottie.model.content.MergePaths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@TargetApi(19)
/* compiled from: MergePathsContent */
public class n implements p, k {
    private final Path Bc = new Path();
    private final Path Cc = new Path();
    private final MergePaths Dc;
    private final String name;
    private final Path path = new Path();
    private final List<p> sc = new ArrayList();

    public n(MergePaths mergePaths) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.name = mergePaths.getName();
            this.Dc = mergePaths;
            return;
        }
        throw new IllegalStateException("Merge paths are not supported pre-KitKat.");
    }

    private void Ro() {
        for (int i = 0; i < this.sc.size(); i++) {
            this.path.addPath(this.sc.get(i).getPath());
        }
    }

    @TargetApi(19)
    private void a(Path.Op op) {
        this.Cc.reset();
        this.Bc.reset();
        for (int size = this.sc.size() - 1; size >= 1; size--) {
            p pVar = this.sc.get(size);
            if (pVar instanceof e) {
                e eVar = (e) pVar;
                List<p> pb = eVar.pb();
                for (int size2 = pb.size() - 1; size2 >= 0; size2--) {
                    Path path2 = pb.get(size2).getPath();
                    path2.transform(eVar.qb());
                    this.Cc.addPath(path2);
                }
            } else {
                this.Cc.addPath(pVar.getPath());
            }
        }
        p pVar2 = this.sc.get(0);
        if (pVar2 instanceof e) {
            e eVar2 = (e) pVar2;
            List<p> pb2 = eVar2.pb();
            for (int i = 0; i < pb2.size(); i++) {
                Path path3 = pb2.get(i).getPath();
                path3.transform(eVar2.qb());
                this.Bc.addPath(path3);
            }
        } else {
            this.Bc.set(pVar2.getPath());
        }
        this.path.op(this.Bc, this.Cc, op);
    }

    @Override // com.airbnb.lottie.a.a.d
    public void a(List<d> list, List<d> list2) {
        for (int i = 0; i < this.sc.size(); i++) {
            this.sc.get(i).a(list, list2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:2:0x0006  */
    @Override // com.airbnb.lottie.a.a.k
    public void a(ListIterator<d> listIterator) {
        while (listIterator.hasPrevious() && listIterator.previous() != this) {
            while (listIterator.hasPrevious()) {
                while (listIterator.hasPrevious()) {
                }
            }
        }
        while (listIterator.hasPrevious()) {
            d previous = listIterator.previous();
            if (previous instanceof p) {
                this.sc.add((p) previous);
                listIterator.remove();
            }
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.a.a.p
    public Path getPath() {
        this.path.reset();
        if (this.Dc.isHidden()) {
            return this.path;
        }
        int i = m.Ac[this.Dc.getMode().ordinal()];
        if (i == 1) {
            Ro();
        } else if (i == 2) {
            a(Path.Op.UNION);
        } else if (i == 3) {
            a(Path.Op.REVERSE_DIFFERENCE);
        } else if (i == 4) {
            a(Path.Op.INTERSECT);
        } else if (i == 5) {
            a(Path.Op.XOR);
        }
        return this.path;
    }
}
