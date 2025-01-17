package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.impl.xpath.XMPPath;
import com.adobe.xmp.impl.xpath.XMPPathParser;
import com.adobe.xmp.options.PropertyOptions;
import com.adobe.xmp.properties.XMPAliasInfo;
import java.util.Iterator;

public class XMPUtilsImpl implements XMPConst {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String COMMAS = ",，､﹐﹑、،՝";
    private static final String CONTROLS = "  ";
    private static final String QUOTES = "\"[]«»〝〞〟―‹›";
    private static final String SEMICOLA = ";；﹔؛;";
    private static final String SPACES = " 　〿";
    private static final int UCK_COMMA = 2;
    private static final int UCK_CONTROL = 5;
    private static final int UCK_NORMAL = 0;
    private static final int UCK_QUOTE = 4;
    private static final int UCK_SEMICOLON = 3;
    private static final int UCK_SPACE = 1;

    private XMPUtilsImpl() {
    }

    public static void appendProperties(XMPMeta xMPMeta, XMPMeta xMPMeta2, boolean z, boolean z2, boolean z3) throws XMPException {
        ParameterAsserts.assertImplementation(xMPMeta);
        ParameterAsserts.assertImplementation(xMPMeta2);
        XMPMetaImpl xMPMetaImpl = (XMPMetaImpl) xMPMeta2;
        Iterator iterateChildren = ((XMPMetaImpl) xMPMeta).getRoot().iterateChildren();
        while (iterateChildren.hasNext()) {
            XMPNode xMPNode = (XMPNode) iterateChildren.next();
            XMPNode findSchemaNode = XMPNodeUtils.findSchemaNode(xMPMetaImpl.getRoot(), xMPNode.getName(), false);
            boolean z4 = true;
            if (findSchemaNode == null) {
                findSchemaNode = new XMPNode(xMPNode.getName(), xMPNode.getValue(), new PropertyOptions().setSchemaNode(true));
                xMPMetaImpl.getRoot().addChild(findSchemaNode);
            } else {
                z4 = false;
            }
            Iterator iterateChildren2 = xMPNode.iterateChildren();
            while (iterateChildren2.hasNext()) {
                XMPNode xMPNode2 = (XMPNode) iterateChildren2.next();
                if (z || !Utils.isInternalProperty(xMPNode.getName(), xMPNode2.getName())) {
                    appendSubtree(xMPMetaImpl, xMPNode2, findSchemaNode, z2, z3);
                }
            }
            if (!findSchemaNode.hasChildren() && (z4 || z3)) {
                xMPMetaImpl.getRoot().removeChild(findSchemaNode);
            }
        }
    }

    private static void appendSubtree(XMPMetaImpl xMPMetaImpl, XMPNode xMPNode, XMPNode xMPNode2, boolean z, boolean z2) throws XMPException {
        XMPNode findChildNode = XMPNodeUtils.findChildNode(xMPNode2, xMPNode.getName(), false);
        boolean z3 = z2 && (!xMPNode.getOptions().isSimple() ? !xMPNode.hasChildren() : xMPNode.getValue() == null || xMPNode.getValue().length() == 0);
        if (!z2 || !z3) {
            if (findChildNode == null) {
                xMPNode2.addChild((XMPNode) xMPNode.clone());
            } else if (z) {
                xMPMetaImpl.setNode(findChildNode, xMPNode.getValue(), xMPNode.getOptions(), true);
                xMPNode2.removeChild(findChildNode);
                xMPNode2.addChild((XMPNode) xMPNode.clone());
            } else {
                PropertyOptions options = xMPNode.getOptions();
                if (options == findChildNode.getOptions()) {
                    if (options.isStruct()) {
                        Iterator iterateChildren = xMPNode.iterateChildren();
                        while (iterateChildren.hasNext()) {
                            appendSubtree(xMPMetaImpl, (XMPNode) iterateChildren.next(), findChildNode, z, z2);
                            if (z2 && !findChildNode.hasChildren()) {
                                xMPNode2.removeChild(findChildNode);
                            }
                        }
                    } else if (options.isArrayAltText()) {
                        Iterator iterateChildren2 = xMPNode.iterateChildren();
                        while (iterateChildren2.hasNext()) {
                            XMPNode xMPNode3 = (XMPNode) iterateChildren2.next();
                            if (xMPNode3.hasQualifier() && XMPConst.XML_LANG.equals(xMPNode3.getQualifier(1).getName())) {
                                int lookupLanguageItem = XMPNodeUtils.lookupLanguageItem(findChildNode, xMPNode3.getQualifier(1).getValue());
                                if (!z2 || !(xMPNode3.getValue() == null || xMPNode3.getValue().length() == 0)) {
                                    if (lookupLanguageItem == -1) {
                                        if (!XMPConst.X_DEFAULT.equals(xMPNode3.getQualifier(1).getValue()) || !findChildNode.hasChildren()) {
                                            xMPNode3.cloneSubtree(findChildNode);
                                        } else {
                                            XMPNode xMPNode4 = new XMPNode(xMPNode3.getName(), xMPNode3.getValue(), xMPNode3.getOptions());
                                            xMPNode3.cloneSubtree(xMPNode4);
                                            findChildNode.addChild(1, xMPNode4);
                                        }
                                    }
                                } else if (lookupLanguageItem != -1) {
                                    findChildNode.removeChild(lookupLanguageItem);
                                    if (!findChildNode.hasChildren()) {
                                        xMPNode2.removeChild(findChildNode);
                                    }
                                }
                            }
                        }
                    } else if (options.isArray()) {
                        Iterator iterateChildren3 = xMPNode.iterateChildren();
                        while (iterateChildren3.hasNext()) {
                            XMPNode xMPNode5 = (XMPNode) iterateChildren3.next();
                            Iterator iterateChildren4 = findChildNode.iterateChildren();
                            boolean z4 = false;
                            while (iterateChildren4.hasNext()) {
                                if (itemValuesMatch(xMPNode5, (XMPNode) iterateChildren4.next())) {
                                    z4 = true;
                                }
                            }
                            if (!z4) {
                                XMPNode xMPNode6 = (XMPNode) xMPNode5.clone();
                                xMPNode2.addChild(xMPNode6);
                                findChildNode = xMPNode6;
                            }
                        }
                    }
                }
            }
        } else if (findChildNode != null) {
            xMPNode2.removeChild(findChildNode);
        }
    }

    private static String applyQuotes(String str, char c2, char c3, boolean z) {
        if (str == null) {
            str = "";
        }
        int i = 0;
        boolean z2 = false;
        while (i < str.length()) {
            int classifyCharacter = classifyCharacter(str.charAt(i));
            if (i == 0 && classifyCharacter == 4) {
                break;
            }
            if (classifyCharacter != 1) {
                if (classifyCharacter == 3 || classifyCharacter == 5 || (classifyCharacter == 2 && !z)) {
                    break;
                }
                z2 = false;
            } else if (z2) {
                break;
            } else {
                z2 = true;
            }
            i++;
        }
        if (i >= str.length()) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length() + 2);
        int i2 = 0;
        while (i2 <= i && classifyCharacter(str.charAt(i)) != 4) {
            i2++;
        }
        stringBuffer.append(c2);
        stringBuffer.append(str.substring(0, i2));
        while (i2 < str.length()) {
            stringBuffer.append(str.charAt(i2));
            if (classifyCharacter(str.charAt(i2)) == 4 && isSurroundingQuote(str.charAt(i2), c2, c3)) {
                stringBuffer.append(str.charAt(i2));
            }
            i2++;
        }
        stringBuffer.append(c3);
        return stringBuffer.toString();
    }

    public static String catenateArrayItems(XMPMeta xMPMeta, String str, String str2, String str3, String str4, boolean z) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        ParameterAsserts.assertImplementation(xMPMeta);
        if (str3 == null || str3.length() == 0) {
            str3 = "; ";
        }
        if (str4 == null || str4.length() == 0) {
            str4 = "\"";
        }
        XMPNode findNode = XMPNodeUtils.findNode(((XMPMetaImpl) xMPMeta).getRoot(), XMPPathParser.expandXPath(str, str2), false, null);
        if (findNode == null) {
            return "";
        }
        if (!findNode.getOptions().isArray() || findNode.getOptions().isArrayAlternate()) {
            throw new XMPException("Named property must be non-alternate array", 4);
        }
        checkSeparator(str3);
        char charAt = str4.charAt(0);
        char checkQuotes = checkQuotes(str4, charAt);
        StringBuffer stringBuffer = new StringBuffer();
        Iterator iterateChildren = findNode.iterateChildren();
        while (iterateChildren.hasNext()) {
            XMPNode xMPNode = (XMPNode) iterateChildren.next();
            if (!xMPNode.getOptions().isCompositeProperty()) {
                stringBuffer.append(applyQuotes(xMPNode.getValue(), charAt, checkQuotes, z));
                if (iterateChildren.hasNext()) {
                    stringBuffer.append(str3);
                }
            } else {
                throw new XMPException("Array items must be simple", 4);
            }
        }
        return stringBuffer.toString();
    }

    private static char checkQuotes(String str, char c2) throws XMPException {
        char c3;
        if (classifyCharacter(c2) == 4) {
            if (str.length() == 1) {
                c3 = c2;
            } else {
                c3 = str.charAt(1);
                if (classifyCharacter(c3) != 4) {
                    throw new XMPException("Invalid quoting character", 4);
                }
            }
            if (c3 == getClosingQuote(c2)) {
                return c3;
            }
            throw new XMPException("Mismatched quote pair", 4);
        }
        throw new XMPException("Invalid quoting character", 4);
    }

    private static void checkSeparator(String str) throws XMPException {
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            int classifyCharacter = classifyCharacter(str.charAt(i));
            if (classifyCharacter == 3) {
                if (!z) {
                    z = true;
                } else {
                    throw new XMPException("Separator can have only one semicolon", 4);
                }
            } else if (classifyCharacter != 1) {
                throw new XMPException("Separator can have only spaces and one semicolon", 4);
            }
        }
        if (!z) {
            throw new XMPException("Separator must have one semicolon", 4);
        }
    }

    private static int classifyCharacter(char c2) {
        if (SPACES.indexOf(c2) >= 0) {
            return 1;
        }
        if (8192 <= c2 && c2 <= 8203) {
            return 1;
        }
        if (COMMAS.indexOf(c2) >= 0) {
            return 2;
        }
        if (SEMICOLA.indexOf(c2) >= 0) {
            return 3;
        }
        if (QUOTES.indexOf(c2) >= 0) {
            return 4;
        }
        if (12296 <= c2 && c2 <= 12303) {
            return 4;
        }
        if (8216 > c2 || c2 > 8223) {
            return (c2 < ' ' || CONTROLS.indexOf(c2) >= 0) ? 5 : 0;
        }
        return 4;
    }

    private static char getClosingQuote(char c2) {
        switch (c2) {
            case '\"':
                return '\"';
            case '[':
                return ']';
            case 171:
                return 187;
            case 187:
                return 171;
            case 8213:
                return 8213;
            case 8216:
                return 8217;
            case 8218:
                return 8219;
            case 8220:
                return 8221;
            case 8222:
                return 8223;
            case 8249:
                return 8250;
            case 8250:
                return 8249;
            case 12296:
                return 12297;
            case 12298:
                return 12299;
            case 12300:
                return 12301;
            case 12302:
                return 12303;
            case 12317:
                return 12319;
            default:
                return 0;
        }
    }

    private static boolean isClosingingQuote(char c2, char c3, char c4) {
        return c2 == c4 || (c3 == 12317 && c2 == 12318) || c2 == 12319;
    }

    private static boolean isSurroundingQuote(char c2, char c3, char c4) {
        return c2 == c3 || isClosingingQuote(c2, c3, c4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0075  */
    private static boolean itemValuesMatch(XMPNode xMPNode, XMPNode xMPNode2) throws XMPException {
        boolean z;
        PropertyOptions options = xMPNode.getOptions();
        if (options.equals(xMPNode2.getOptions())) {
            return false;
        }
        if (options.getOptions() == 0) {
            if (xMPNode.getValue().equals(xMPNode2.getValue()) && xMPNode.getOptions().getHasLanguage() == xMPNode2.getOptions().getHasLanguage()) {
                return !xMPNode.getOptions().getHasLanguage() || xMPNode.getQualifier(1).getValue().equals(xMPNode2.getQualifier(1).getValue());
            }
            return false;
        } else if (!options.isStruct()) {
            Iterator iterateChildren = xMPNode.iterateChildren();
            while (iterateChildren.hasNext()) {
                XMPNode xMPNode3 = (XMPNode) iterateChildren.next();
                Iterator iterateChildren2 = xMPNode2.iterateChildren();
                while (true) {
                    if (iterateChildren2.hasNext()) {
                        if (itemValuesMatch(xMPNode3, (XMPNode) iterateChildren2.next())) {
                            z = true;
                            continue;
                            break;
                        }
                    } else {
                        z = false;
                        continue;
                        break;
                    }
                }
                if (!z) {
                    return false;
                }
            }
        } else if (xMPNode.getChildrenLength() != xMPNode2.getChildrenLength()) {
            return false;
        } else {
            Iterator iterateChildren3 = xMPNode.iterateChildren();
            while (iterateChildren3.hasNext()) {
                XMPNode xMPNode4 = (XMPNode) iterateChildren3.next();
                XMPNode findChildNode = XMPNodeUtils.findChildNode(xMPNode2, xMPNode4.getName(), false);
                if (findChildNode == null || !itemValuesMatch(xMPNode4, findChildNode)) {
                    return false;
                }
                while (iterateChildren3.hasNext()) {
                }
            }
        }
    }

    public static void removeProperties(XMPMeta xMPMeta, String str, String str2, boolean z, boolean z2) throws XMPException {
        ParameterAsserts.assertImplementation(xMPMeta);
        XMPMetaImpl xMPMetaImpl = (XMPMetaImpl) xMPMeta;
        if (str2 == null || str2.length() <= 0) {
            if (str == null || str.length() <= 0) {
                Iterator iterateChildren = xMPMetaImpl.getRoot().iterateChildren();
                while (iterateChildren.hasNext()) {
                    if (removeSchemaChildren((XMPNode) iterateChildren.next(), z)) {
                        iterateChildren.remove();
                    }
                }
                return;
            }
            XMPNode findSchemaNode = XMPNodeUtils.findSchemaNode(xMPMetaImpl.getRoot(), str, false);
            if (findSchemaNode != null && removeSchemaChildren(findSchemaNode, z)) {
                xMPMetaImpl.getRoot().removeChild(findSchemaNode);
            }
            if (z2) {
                XMPAliasInfo[] findAliases = XMPMetaFactory.getSchemaRegistry().findAliases(str);
                for (XMPAliasInfo xMPAliasInfo : findAliases) {
                    XMPNode findNode = XMPNodeUtils.findNode(xMPMetaImpl.getRoot(), XMPPathParser.expandXPath(xMPAliasInfo.getNamespace(), xMPAliasInfo.getPropName()), false, null);
                    if (findNode != null) {
                        findNode.getParent().removeChild(findNode);
                    }
                }
            }
        } else if (str == null || str.length() == 0) {
            throw new XMPException("Property name requires schema namespace", 4);
        } else {
            XMPPath expandXPath = XMPPathParser.expandXPath(str, str2);
            XMPNode findNode2 = XMPNodeUtils.findNode(xMPMetaImpl.getRoot(), expandXPath, false, null);
            if (findNode2 == null) {
                return;
            }
            if (z || !Utils.isInternalProperty(expandXPath.getSegment(0).getName(), expandXPath.getSegment(1).getName())) {
                XMPNode parent = findNode2.getParent();
                parent.removeChild(findNode2);
                if (parent.getOptions().isSchemaNode() && !parent.hasChildren()) {
                    parent.getParent().removeChild(parent);
                }
            }
        }
    }

    private static boolean removeSchemaChildren(XMPNode xMPNode, boolean z) {
        Iterator iterateChildren = xMPNode.iterateChildren();
        while (iterateChildren.hasNext()) {
            XMPNode xMPNode2 = (XMPNode) iterateChildren.next();
            if (z || !Utils.isInternalProperty(xMPNode.getName(), xMPNode2.getName())) {
                iterateChildren.remove();
            }
        }
        return !xMPNode.hasChildren();
    }

    public static void separateArrayItems(XMPMeta xMPMeta, String str, String str2, String str3, PropertyOptions propertyOptions, boolean z) throws XMPException {
        String str4;
        char c2;
        int i;
        int classifyCharacter;
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertArrayName(str2);
        if (str3 != null) {
            ParameterAsserts.assertImplementation(xMPMeta);
            XMPNode separateFindCreateArray = separateFindCreateArray(str, str2, propertyOptions, (XMPMetaImpl) xMPMeta);
            int length = str3.length();
            int i2 = 0;
            int i3 = 0;
            char c3 = 0;
            while (i2 < length) {
                while (i2 < length) {
                    c3 = str3.charAt(i2);
                    i3 = classifyCharacter(c3);
                    if (i3 == 0 || i3 == 4) {
                        break;
                    }
                    i2++;
                }
                if (i2 < length) {
                    int i4 = 1;
                    if (i3 != 4) {
                        char c4 = c3;
                        int i5 = i3;
                        int i6 = i2;
                        while (i6 < length) {
                            c4 = str3.charAt(i6);
                            i5 = classifyCharacter(c4);
                            if (i5 != 0 && i5 != 4 && ((i5 != 2 || !z) && (i5 != 1 || (i = i6 + 1) >= length || ((classifyCharacter = classifyCharacter((c4 = str3.charAt(i)))) != 0 && classifyCharacter != 4 && (classifyCharacter != 2 || !z))))) {
                                break;
                            }
                            i6++;
                        }
                        str4 = str3.substring(i2, i6);
                        i2 = i6;
                        i3 = i5;
                        c3 = c4;
                    } else {
                        char closingQuote = getClosingQuote(c3);
                        i2++;
                        str4 = "";
                        char c5 = c3;
                        while (true) {
                            if (i2 >= length) {
                                c3 = c5;
                                break;
                            }
                            c5 = str3.charAt(i2);
                            i3 = classifyCharacter(c5);
                            if (i3 == 4 && isSurroundingQuote(c5, c3, closingQuote)) {
                                int i7 = i2 + 1;
                                if (i7 < length) {
                                    c2 = str3.charAt(i7);
                                    classifyCharacter(c2);
                                } else {
                                    c2 = ';';
                                }
                                if (c5 != c2) {
                                    if (isClosingingQuote(c5, c3, closingQuote)) {
                                        c3 = c5;
                                        i2 = i7;
                                        break;
                                    }
                                    str4 = str4 + c5;
                                } else {
                                    str4 = str4 + c5;
                                    i2 = i7;
                                }
                            } else {
                                str4 = str4 + c5;
                            }
                            i2++;
                        }
                    }
                    while (true) {
                        if (i4 > separateFindCreateArray.getChildrenLength()) {
                            i4 = -1;
                            break;
                        } else if (str4.equals(separateFindCreateArray.getChild(i4).getValue())) {
                            break;
                        } else {
                            i4++;
                        }
                    }
                    if (i4 < 0) {
                        separateFindCreateArray.addChild(new XMPNode(XMPConst.ARRAY_ITEM_NAME, str4, null));
                    }
                } else {
                    return;
                }
            }
            return;
        }
        throw new XMPException("Parameter must not be null", 4);
    }

    private static XMPNode separateFindCreateArray(String str, String str2, PropertyOptions propertyOptions, XMPMetaImpl xMPMetaImpl) throws XMPException {
        PropertyOptions verifySetOptions = XMPNodeUtils.verifySetOptions(propertyOptions, null);
        if (verifySetOptions.isOnlyArrayOptions()) {
            XMPPath expandXPath = XMPPathParser.expandXPath(str, str2);
            XMPNode findNode = XMPNodeUtils.findNode(xMPMetaImpl.getRoot(), expandXPath, false, null);
            if (findNode != null) {
                PropertyOptions options = findNode.getOptions();
                if (!options.isArray() || options.isArrayAlternate()) {
                    throw new XMPException("Named property must be non-alternate array", 102);
                } else if (verifySetOptions.equalArrayTypes(options)) {
                    throw new XMPException("Mismatch of specified and existing array form", 102);
                }
            } else {
                findNode = XMPNodeUtils.findNode(xMPMetaImpl.getRoot(), expandXPath, true, verifySetOptions.setArray(true));
                if (findNode == null) {
                    throw new XMPException("Failed to create named array", 102);
                }
            }
            return findNode;
        }
        throw new XMPException("Options can only provide array form", 103);
    }
}
