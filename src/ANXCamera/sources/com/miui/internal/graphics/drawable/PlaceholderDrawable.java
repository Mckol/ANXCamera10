package com.miui.internal.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import com.miui.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class PlaceholderDrawable extends Drawable {
    public int mHeight = -1;
    private Rect mPadding;
    public int mWidth = -1;

    public void draw(Canvas canvas) {
    }

    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    public int getOpacity() {
        return 0;
    }

    public boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2 == null) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 < depth && next == 3) {
                    return;
                }
                if (next == 2 && depth2 <= depth) {
                    String name = xmlPullParser.getName();
                    if (name.equals("size")) {
                        TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.PlaceholderDrawableSize);
                        this.mWidth = obtainAttributes.getDimensionPixelSize(R.styleable.PlaceholderDrawableSize_android_width, -1);
                        this.mHeight = obtainAttributes.getDimensionPixelSize(R.styleable.PlaceholderDrawableSize_android_height, -1);
                        obtainAttributes.recycle();
                    } else if (name.equals("padding")) {
                        TypedArray obtainAttributes2 = resources.obtainAttributes(attributeSet, R.styleable.PlaceholderDrawablePadding);
                        this.mPadding = new Rect(obtainAttributes2.getDimensionPixelOffset(R.styleable.PlaceholderDrawablePadding_android_left, 0), obtainAttributes2.getDimensionPixelOffset(R.styleable.PlaceholderDrawablePadding_android_top, 0), obtainAttributes2.getDimensionPixelOffset(R.styleable.PlaceholderDrawablePadding_android_right, 0), obtainAttributes2.getDimensionPixelOffset(R.styleable.PlaceholderDrawablePadding_android_bottom, 0));
                        obtainAttributes2.recycle();
                    } else {
                        Log.w("drawable", "Bad element under <placeholder>: " + name);
                    }
                }
            } else {
                return;
            }
        }
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
