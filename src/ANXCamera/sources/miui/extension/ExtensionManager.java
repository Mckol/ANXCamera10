package miui.extension;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.miui.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import miui.util.AppConstants;
import miui.util.ResourceHelper;
import org.xmlpull.v1.XmlPullParserException;

public class ExtensionManager {
    private static final String ADDITIONAL_PACKAGE_NAME = "miui.system";
    private static final String EXTENSION_CONFIG_NAME = "miui_extension";
    private static final String EXTENSION_TAG_EXTENSION = "extension";
    private static final String META_KEY_EXTENSION = "com.miui.sdk.extension";
    private static final String TAG = "ExtensionManager";
    private HashMap<String, ArrayList<Extension>> mExtensionMap;

    private static class Holder {
        static final ExtensionManager INSTANCE = new ExtensionManager(AppConstants.getCurrentApplication());

        private Holder() {
        }
    }

    private ExtensionManager(Context context) {
        this.mExtensionMap = new HashMap<>();
        loadConfig(context);
    }

    public static ExtensionManager getInstance() {
        return Holder.INSTANCE;
    }

    private void loadConfig(Context context) {
        XmlResourceParser loadXml = ResourceHelper.loadXml(context, META_KEY_EXTENSION, EXTENSION_CONFIG_NAME, ADDITIONAL_PACKAGE_NAME);
        if (loadXml != null) {
            AttributeSet asAttributeSet = Xml.asAttributeSet(loadXml);
            try {
                for (int next = loadXml.next(); next != 1; next = loadXml.next()) {
                    if (next == 2 && EXTENSION_TAG_EXTENSION.equals(loadXml.getName())) {
                        Extension parseExtension = parseExtension(context, asAttributeSet);
                        String target = parseExtension.getTarget();
                        ArrayList<Extension> arrayList = this.mExtensionMap.get(target);
                        if (arrayList == null) {
                            arrayList = new ArrayList<>();
                            this.mExtensionMap.put(target, arrayList);
                        }
                        arrayList.add(parseExtension);
                    }
                }
            } catch (XmlPullParserException e2) {
                Log.e(TAG, "Fail to parse CTA config", e2);
            } catch (IOException e3) {
                Log.e(TAG, "Fail to parse CTA config", e3);
            } catch (Throwable th) {
                loadXml.close();
                throw th;
            }
            loadXml.close();
        }
    }

    private Extension parseExtension(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DynamicExtension);
        String string = obtainStyledAttributes.getString(R.styleable.DynamicExtension_extensionTarget);
        String string2 = obtainStyledAttributes.getString(R.styleable.DynamicExtension_extensionAction);
        String string3 = obtainStyledAttributes.getString(R.styleable.DynamicExtension_extensionInvoker);
        obtainStyledAttributes.recycle();
        return new Extension(string, string2, string3);
    }

    public void invoke(String str, String str2, Object... objArr) {
        ArrayList<Extension> arrayList = this.mExtensionMap.get(str);
        if (arrayList != null) {
            Iterator<Extension> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().invoke(str2, objArr);
            }
        }
    }
}
