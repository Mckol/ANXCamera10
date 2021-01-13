package com.android.camera.ui.drawable.snap;

import com.android.camera.Util;

public class CameraSnapPaintSecondPatternFast extends PaintPattern<CameraSnapPaintSecond> {
    public CameraSnapPaintSecondPatternFast(CameraSnapPaintSecond cameraSnapPaintSecond) {
        super(cameraSnapPaintSecond);
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void directlyResult() {
        P p = this.paintBase;
        ((CameraSnapPaintSecond) p).motionLineWidthCurrent = ((CameraSnapPaintSecond) p).motionLineWidthDst;
        ((CameraSnapPaintSecond) p).commonLineWidthCurrent = ((CameraSnapPaintSecond) p).baseLineWidth;
        ((CameraSnapPaintSecond) p).commonLineWidthDst = (float) Util.dpToPixel(3.5f);
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public boolean interceptDraw() {
        return false;
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void prepareTargetPattern() {
        P p = this.paintBase;
        ((CameraSnapPaintSecond) p).quarterAngelSrc = ((CameraSnapPaintSecond) p).quarterAngelCurrent;
        ((CameraSnapPaintSecond) p).squashAngelSrc = ((CameraSnapPaintSecond) p).squashAngelCurrent;
        ((CameraSnapPaintSecond) p).motionLineWidthSrc = ((CameraSnapPaintSecond) p).motionLineWidthCurrent;
        ((CameraSnapPaintSecond) p).commonLineWidthSrc = ((CameraSnapPaintSecond) p).commonLineWidthCurrent;
        ((CameraSnapPaintSecond) p).quarterAngelDst = ((CameraSnapPaintSecond) p).baseAngel;
        ((CameraSnapPaintSecond) p).squashAngelDst = ((CameraSnapPaintSecond) p).baseAngel;
        ((CameraSnapPaintSecond) p).motionLineWidthDst = (float) Util.dpToPixel(6.0f);
        ((CameraSnapPaintSecond) this.paintBase).commonLineWidthDst = (float) Util.dpToPixel(3.5f);
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void updateValue(float f) {
        P p = this.paintBase;
        ((CameraSnapPaintSecond) p).quarterAngelCurrent = calculateCurrentValue(((CameraSnapPaintSecond) p).quarterAngelSrc, ((CameraSnapPaintSecond) p).quarterAngelDst, f);
        P p2 = this.paintBase;
        ((CameraSnapPaintSecond) p2).squashAngelCurrent = calculateCurrentValue(((CameraSnapPaintSecond) p2).squashAngelSrc, ((CameraSnapPaintSecond) p2).squashAngelDst, f);
        P p3 = this.paintBase;
        ((CameraSnapPaintSecond) p3).motionLineWidthCurrent = calculateCurrentValue(((CameraSnapPaintSecond) p3).motionLineWidthSrc, ((CameraSnapPaintSecond) p3).motionLineWidthDst, f);
        P p4 = this.paintBase;
        ((CameraSnapPaintSecond) p4).commonLineWidthCurrent = calculateCurrentValue(((CameraSnapPaintSecond) p4).commonLineWidthSrc, ((CameraSnapPaintSecond) p4).commonLineWidthDst, f);
    }
}
