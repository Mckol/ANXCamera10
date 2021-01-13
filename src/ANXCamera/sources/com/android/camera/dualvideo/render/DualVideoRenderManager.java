package com.android.camera.dualvideo.render;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.opengl.Matrix;
import android.util.SparseArray;
import android.view.MotionEvent;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningDualVideo;
import com.android.camera.dualvideo.CameraIDManager;
import com.android.camera.dualvideo.UserSelectData;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.ExtTexture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DualVideoRenderManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "DualVideoRenderManager";
    private DrawExtTexAttribute mMainDrawAttribute;
    private RegionHelper mRegionHelper;
    private Object mRenderLocker;
    private ArrayList<IRenderable> mRenderableList = new ArrayList<>();
    private ExtTexture mSubExtTexture;
    private SurfaceTexture mSubSurfaceTexture;

    public DualVideoRenderManager(SurfaceTexture surfaceTexture, DrawExtTexAttribute drawExtTexAttribute, ExtTexture extTexture, Object obj) {
        this.mRenderLocker = obj;
        this.mSubSurfaceTexture = surfaceTexture;
        this.mMainDrawAttribute = drawExtTexAttribute;
        this.mSubExtTexture = extTexture;
        Rect displayRect = Util.getDisplayRect(1);
        this.mRegionHelper = new RegionHelper(displayRect.left, displayRect.top, displayRect.height(), displayRect.width());
        initRenderableList();
    }

    static /* synthetic */ boolean B(IRenderable iRenderable) {
        return !iRenderable.isVisible();
    }

    static /* synthetic */ boolean D(IRenderable iRenderable) {
        return iRenderable.getSelectedType() == 0;
    }

    static /* synthetic */ boolean G(IRenderable iRenderable) {
        return iRenderable.getSelectedType() != 0;
    }

    static /* synthetic */ void a(IRenderable iRenderable, ConcurrentHashMap concurrentHashMap, CameraIDManager cameraIDManager, UserSelectData userSelectData) {
        userSelectData.m6PatchType = iRenderable.get6patchComposeType();
        int index = userSelectData.getIndex();
        if (index == 1) {
            concurrentHashMap.put(100, cameraIDManager.get6Patch2CameraId().get(iRenderable.get6patchComposeType()));
        } else if (index == 2) {
            concurrentHashMap.put(101, cameraIDManager.get6Patch2CameraId().get(iRenderable.get6patchComposeType()));
        }
    }

    static /* synthetic */ void a(ArrayList arrayList, IRenderable iRenderable) {
        if (iRenderable.isVisible()) {
            arrayList.add(iRenderable);
        }
    }

    static /* synthetic */ boolean a(int i, UserSelectData userSelectData) {
        return userSelectData.m6PatchType == i;
    }

    static /* synthetic */ boolean a(SparseArray sparseArray, int i, IRenderable iRenderable) {
        if (iRenderable.getSelectedType() == 0 || ((Integer) sparseArray.get(iRenderable.getRenderComposeType())).intValue() != i) {
            return false;
        }
        iRenderable.setSelectedtypeWithAnim(0, true);
        return true;
    }

    static /* synthetic */ boolean c(IRenderable iRenderable, UserSelectData userSelectData) {
        return userSelectData.m6PatchType == iRenderable.getRenderComposeType();
    }

    static /* synthetic */ boolean c(ConcurrentHashMap concurrentHashMap, UserSelectData userSelectData) {
        return userSelectData.mCameraID == ((Integer) concurrentHashMap.get(100)).intValue();
    }

    static /* synthetic */ boolean d(ConcurrentHashMap concurrentHashMap, UserSelectData userSelectData) {
        return userSelectData.mCameraID == ((Integer) concurrentHashMap.get(101)).intValue();
    }

    static /* synthetic */ boolean e(IRenderable iRenderable, UserSelectData userSelectData) {
        return userSelectData.getIndex() == iRenderable.getSelectedType();
    }

    private void init6PatchTag() {
        this.mRenderableList.forEach(G.INSTANCE);
    }

    private void initPreviewAttris() {
        Iterator<IRenderable> it = this.mRenderableList.iterator();
        while (it.hasNext()) {
            IRenderable next = it.next();
            Rect renderAreaFor = this.mRegionHelper.getRenderAreaFor(next.getRenderComposeType());
            float[] fArr = new float[16];
            this.mSubSurfaceTexture.getTransformMatrix(fArr);
            next.setBasicPreviewTransMatrix((float[]) fArr.clone());
            if (next.isFacingFront()) {
                ExtTexture extTexture = this.mSubExtTexture;
                DualVideoUtil.centerScaleMatrix(fArr, next.getRenderComposeType());
                next.setRenderAttri(new DrawExtTexAttribute(extTexture, fArr, renderAreaFor.left, renderAreaFor.top, renderAreaFor.width(), renderAreaFor.height()));
            } else {
                Matrix.translateM(fArr, 0, 0.0f, 1.0f, 0.0f);
                Matrix.scaleM(fArr, 0, 1.0f, -1.0f, 1.0f);
                ExtTexture extTexture2 = this.mMainDrawAttribute.mExtTexture;
                DualVideoUtil.centerScaleMatrix(fArr, next.getRenderComposeType());
                next.setRenderAttri(new DrawExtTexAttribute(extTexture2, fArr, renderAreaFor.left, renderAreaFor.top, renderAreaFor.width(), renderAreaFor.height()));
            }
        }
    }

    private void initSelected() {
        this.mRenderableList.stream().forEach(new H(DataRepository.dataItemRunning().getComponentRunningDualVideo().getSelectedData()));
        Log.d(TAG, "initSelected: ");
        printRenderList();
    }

    static /* synthetic */ boolean n(IRenderable iRenderable) {
        return iRenderable.getRenderComposeType() == 10;
    }

    private boolean onTouched(int i, int i2) {
        SparseArray<Integer> sparseArray = new CameraIDManager().get6Patch2CameraId();
        Iterator<IRenderable> it = this.mRenderableList.iterator();
        while (it.hasNext()) {
            IRenderable next = it.next();
            if (this.mRegionHelper.getRenderAreaFor(next.getRenderComposeType()).contains(i, i2)) {
                if (next.getSelectedType() == 1) {
                    this.mRenderableList.stream().filter(J.INSTANCE).forEach(C0068u.INSTANCE);
                    next.setSelectedtypeWithAnim(2, true);
                } else if (next.getSelectedType() == 0) {
                    if (this.mRenderableList.stream().anyMatch(new C0067t(sparseArray, sparseArray.get(next.getRenderComposeType()).intValue()))) {
                        this.mRenderableList.stream().forEach(L.INSTANCE);
                    } else {
                        this.mRenderableList.stream().forEach(C0058j.INSTANCE);
                    }
                    next.setSelectedtypeWithAnim(2, true);
                }
                saveSelectedStatus();
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ boolean p(IRenderable iRenderable) {
        return iRenderable.getRenderComposeType() == 20 && iRenderable.isVisible();
    }

    static /* synthetic */ boolean r(IRenderable iRenderable) {
        return iRenderable.getSelectedType() == 2;
    }

    private void saveSelectedStatus() {
        CameraIDManager cameraIDManager = new CameraIDManager();
        ComponentRunningDualVideo componentRunningDualVideo = DataRepository.dataItemRunning().getComponentRunningDualVideo();
        ArrayList<UserSelectData> selectedData = componentRunningDualVideo.getSelectedData();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        this.mRenderableList.stream().forEach(new C0057i(selectedData, concurrentHashMap, cameraIDManager));
        componentRunningDualVideo.setSelectedData(selectedData);
        componentRunningDualVideo.setIds(concurrentHashMap);
    }

    static /* synthetic */ void t(IRenderable iRenderable) {
        if (iRenderable.getSelectedType() != 0) {
            iRenderable.setSelectedtypeWithAnim(1, true);
        }
    }

    static /* synthetic */ void u(IRenderable iRenderable) {
        if (iRenderable.getSelectedType() == 1) {
            iRenderable.setSelectedtypeWithAnim(0, true);
        } else if (iRenderable.getSelectedType() == 2) {
            iRenderable.setSelectedtypeWithAnim(1, true);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: updateRenderableList */
    public void Ec() {
        synchronized (this.mRenderLocker) {
            Iterator<IRenderable> it = this.mRenderableList.iterator();
            while (it.hasNext()) {
                it.next().updateRenderAttri(this.mRegionHelper);
            }
        }
    }

    public /* synthetic */ void E(IRenderable iRenderable) {
        iRenderable.setComposeTypeWithAnimation(iRenderable.getRenderComposeType(), this.mRegionHelper, false);
    }

    public /* synthetic */ void F(IRenderable iRenderable) {
        if (iRenderable.getSelectedType() == 1) {
            iRenderable.setComposeTypeWithAnimation(iRenderable.get6patchComposeType(), this.mRegionHelper, true);
        } else if (iRenderable.getSelectedType() == 2) {
            iRenderable.setComposeTypeWithAnimation(iRenderable.get6patchComposeType(), this.mRegionHelper, true);
        }
    }

    public /* synthetic */ void a(SparseArray sparseArray, IRenderable iRenderable) {
        if (DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch()) {
            switch (iRenderable.get6patchComposeType()) {
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                    iRenderable.getRenderAttri().mExtTexture = this.mMainDrawAttribute.mExtTexture;
                    return;
                case 25:
                    iRenderable.getRenderAttri().mExtTexture = this.mSubExtTexture;
                    return;
                default:
                    return;
            }
        } else {
            Integer num = (Integer) sparseArray.get(iRenderable.get6patchComposeType());
            if (num != null) {
                int intValue = DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(100).intValue();
                int intValue2 = DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(101).intValue();
                Log.d(TAG, "changeTexture: " + num + " main: " + intValue + " sub " + intValue2);
                if (num.intValue() == intValue) {
                    iRenderable.getRenderAttri().mExtTexture = this.mMainDrawAttribute.mExtTexture;
                } else if (num.intValue() == intValue2) {
                    iRenderable.getRenderAttri().mExtTexture = this.mSubExtTexture;
                }
            }
        }
    }

    public /* synthetic */ void a(List list, IRenderable iRenderable) {
        DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
        list.add(new DrawExtTexAttribute(renderAttri.mExtTexture, renderAttri.mTextureTransform, renderAttri.mX, renderAttri.mY - this.mRegionHelper.mY, renderAttri.mWidth, renderAttri.mHeight));
    }

    public void changeTexture(SparseArray<Integer> sparseArray) {
        Log.d(TAG, "changeTexture: ");
        this.mRenderableList.stream().filter(C0055g.INSTANCE).forEach(new C0063o(this, sparseArray));
    }

    public boolean expandBottom() {
        Log.d(TAG, "expandBottom: ");
        if (isAnimating()) {
            return false;
        }
        Iterator<IRenderable> it = this.mRenderableList.iterator();
        while (it.hasNext()) {
            IRenderable next = it.next();
            if (next.isVisible()) {
                int renderComposeType = next.getRenderComposeType();
                if (renderComposeType == 11) {
                    next.setComposeTypeWithAnimation(10, this.mRegionHelper, true);
                } else if (renderComposeType == 12) {
                    next.setComposeTypeWithAnimation(13, this.mRegionHelper, true);
                }
            }
        }
        return true;
    }

    public boolean expandOrShrinkTop() {
        Log.d(TAG, "expandOrShrinkTop: ");
        if (isAnimating()) {
            return false;
        }
        Iterator<IRenderable> it = this.mRenderableList.iterator();
        while (it.hasNext()) {
            IRenderable next = it.next();
            if (next.isVisible()) {
                int lastRenderComposeType = next.getLastRenderComposeType();
                switch (next.getRenderComposeType()) {
                    case 10:
                        if (lastRenderComposeType == 10 || lastRenderComposeType == 13 || lastRenderComposeType >= 20) {
                            next.setComposeTypeWithAnimation(11, this.mRegionHelper, true);
                            break;
                        } else {
                            next.setComposeTypeWithAnimation(lastRenderComposeType, this.mRegionHelper, true);
                            continue;
                        }
                    case 11:
                        next.setComposeTypeWithAnimation(13, this.mRegionHelper, true);
                        continue;
                    case 12:
                        next.setComposeTypeWithAnimation(10, this.mRegionHelper, true);
                        continue;
                    case 13:
                        if (lastRenderComposeType == 10 || lastRenderComposeType == 13 || lastRenderComposeType >= 20) {
                            next.setComposeTypeWithAnimation(12, this.mRegionHelper, true);
                            break;
                        } else {
                            next.setComposeTypeWithAnimation(lastRenderComposeType, this.mRegionHelper, true);
                            continue;
                        }
                }
            }
        }
        return true;
    }

    public int get6PatchNextComposeType(int i) {
        return ((Integer) DataRepository.dataItemRunning().getComponentRunningDualVideo().getmSelectedData().stream().filter(new F(i)).findFirst().map(C0053e.INSTANCE).orElse(-1)).intValue();
    }

    public ArrayList<IRenderable> getRenderableList() {
        return this.mRenderableList;
    }

    public List<DrawAttribute> getRenderableListForRecord() {
        ArrayList arrayList = new ArrayList();
        getRenderableList().stream().filter(x.INSTANCE).sorted(U.INSTANCE).forEachOrdered(new C0065q(this, arrayList));
        return arrayList;
    }

    public ArrayList<IRenderable> getVisibileRenderList() {
        ArrayList<IRenderable> arrayList = new ArrayList<>();
        this.mRenderableList.stream().forEachOrdered(new B(arrayList));
        return arrayList;
    }

    public boolean hasMiniPreview() {
        return this.mRenderableList.stream().anyMatch(I.INSTANCE);
    }

    public void initRenderableList() {
        if (this.mRenderableList.isEmpty()) {
            if (DataRepository.dataItemFeature().tj()) {
                this.mRenderableList.add(new RenderItem(20, false));
                this.mRenderableList.add(new RenderItem(21, false));
                this.mRenderableList.add(new RenderItem(22, false));
                this.mRenderableList.add(new RenderItem(23, false));
                this.mRenderableList.add(new RenderItem(24, false));
                this.mRenderableList.add(new RenderItem(25, true));
            } else {
                ComponentRunningDualVideo componentRunningDualVideo = DataRepository.dataItemRunning().getComponentRunningDualVideo();
                ConcurrentHashMap<Integer, Integer> ids = componentRunningDualVideo.getIds();
                ArrayList<UserSelectData> arrayList = componentRunningDualVideo.getmSelectedData();
                int intValue = ((Integer) arrayList.stream().filter(new E(ids)).findFirst().map(C0050b.INSTANCE).get()).intValue();
                int intValue2 = ((Integer) arrayList.stream().filter(new C0061m(ids)).findFirst().map(A.INSTANCE).get()).intValue();
                this.mRenderableList.add(new RenderItem(intValue, false));
                this.mRenderableList.add(new RenderItem(intValue2, true));
            }
        }
        initPreviewAttris();
        initSelected();
        init6PatchTag();
    }

    public boolean is6PatchWindow() {
        return this.mRenderableList.stream().anyMatch(D.INSTANCE);
    }

    public boolean isAnimating() {
        ArrayList<IRenderable> arrayList = this.mRenderableList;
        return arrayList != null && arrayList.stream().anyMatch(r.INSTANCE);
    }

    public void printRenderList() {
        Log.d(TAG, "printRenderList: start");
        if (!this.mRenderableList.isEmpty()) {
            this.mRenderableList.stream().forEachOrdered(C0064p.INSTANCE);
        }
    }

    public boolean selectItem(MotionEvent motionEvent) {
        Log.d(TAG, "selectItem: " + motionEvent.getAction());
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            Iterator<IRenderable> it = this.mRenderableList.iterator();
            while (it.hasNext()) {
                IRenderable next = it.next();
                if (this.mRegionHelper.getRenderAreaFor(next.getRenderComposeType()).contains(x, y)) {
                    next.onKeyDown();
                }
            }
        } else if (actionMasked == 1 || actionMasked == 3) {
            onTouched((int) motionEvent.getX(), (int) motionEvent.getY());
            this.mRenderableList.stream().filter(C0052d.INSTANCE).forEachOrdered(C0056h.INSTANCE);
        }
        return true;
    }

    public void switch6patch2preview() {
        if (!isAnimating()) {
            saveSelectedStatus();
            this.mRenderableList.stream().forEach(new K(this));
        }
    }

    public void switchPreviewTo6Patch() {
        if (!isAnimating()) {
            Log.d(TAG, "switchPreviewTo6Patch: ");
            this.mRenderableList.stream().filter(C.INSTANCE).forEach(C0051c.INSTANCE);
            this.mRenderableList.stream().forEach(y.INSTANCE);
            this.mRenderableList.stream().filter(z.INSTANCE).forEach(C0062n.INSTANCE);
            this.mRenderableList.stream().filter(M.INSTANCE).forEach(new C0066s(this));
            this.mRenderableList.stream().forEach(new C0054f(this));
        }
    }

    public boolean switchTopBottom() {
        Log.d(TAG, "switchTopBottom ");
        if (isAnimating()) {
            return false;
        }
        Iterator<IRenderable> it = this.mRenderableList.iterator();
        while (it.hasNext()) {
            IRenderable next = it.next();
            if (next.isVisible()) {
                switch (next.getRenderComposeType()) {
                    case 10:
                        next.setComposeTypeWithAnimation(13, this.mRegionHelper, false);
                        continue;
                    case 11:
                        next.setComposeTypeWithAnimation(12, this.mRegionHelper, false);
                        continue;
                    case 12:
                        next.setComposeTypeWithAnimation(11, this.mRegionHelper, false);
                        continue;
                    case 13:
                        next.setComposeTypeWithAnimation(10, this.mRegionHelper, false);
                        continue;
                }
            }
        }
        return true;
    }

    public boolean updateMiniWindowLocation(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        return false;
                    }
                } else if (!this.mRegionHelper.mIsHovering) {
                    return false;
                } else {
                    float y = motionEvent.getY();
                    RegionHelper regionHelper = this.mRegionHelper;
                    regionHelper.setStartPosition(motionEvent.getX(), motionEvent.getY());
                    this.mRegionHelper.updateMarginOffset((int) (motionEvent.getX() - this.mRegionHelper.mStartX), (int) (y - regionHelper.mStartY));
                    return true;
                }
            }
            RegionHelper regionHelper2 = this.mRegionHelper;
            if (!regionHelper2.mIsHovering) {
                return false;
            }
            regionHelper2.mIsHovering = false;
            regionHelper2.moveToEdge();
            return true;
        }
        float x = motionEvent.getX();
        float y2 = motionEvent.getY();
        Rect renderAreaFor = this.mRegionHelper.getRenderAreaFor(10);
        int i = renderAreaFor.left;
        boolean z = x > ((float) i) && x < ((float) (i + renderAreaFor.width()));
        int i2 = renderAreaFor.top;
        boolean z2 = y2 > ((float) i2) && y2 < ((float) (i2 + renderAreaFor.height()));
        if (!z || !z2) {
            return false;
        }
        RegionHelper regionHelper3 = this.mRegionHelper;
        regionHelper3.mIsHovering = true;
        regionHelper3.setListener(new C0059k(this));
        this.mRegionHelper.setStartPosition(x, y2);
        return true;
    }

    public /* synthetic */ void y(IRenderable iRenderable) {
        iRenderable.alphaIn6PatchTag(false);
        int selectedType = iRenderable.getSelectedType();
        if (selectedType == 1 || selectedType == 2) {
            iRenderable.alphaInSelectedFrame(false);
            iRenderable.setComposeTypeWithAnimation(get6PatchNextComposeType(iRenderable.get6patchComposeType()), this.mRegionHelper, true);
            return;
        }
        iRenderable.setVisibilityWithAnim(false, true);
    }
}
