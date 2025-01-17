package org.greenrobot.greendao.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScope;
import org.greenrobot.greendao.identityscope.IdentityScopeLong;
import org.greenrobot.greendao.identityscope.IdentityScopeObject;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

public final class DaoConfig implements Cloneable {
    public final String[] allColumns;
    public final Database db;
    private IdentityScope<?, ?> identityScope;
    public final boolean keyIsNumeric;
    public final String[] nonPkColumns;
    public final String[] pkColumns;
    public final Property pkProperty;
    public final Property[] properties;
    public final TableStatements statements;
    public final String tablename;

    public DaoConfig(Database database, Class<? extends AbstractDao<?, ?>> cls) {
        this.db = database;
        try {
            Property property = null;
            this.tablename = (String) cls.getField("TABLENAME").get(null);
            Property[] reflectProperties = reflectProperties(cls);
            this.properties = reflectProperties;
            this.allColumns = new String[reflectProperties.length];
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Property property2 = null;
            for (int i = 0; i < reflectProperties.length; i++) {
                Property property3 = reflectProperties[i];
                String str = property3.columnName;
                this.allColumns[i] = str;
                if (property3.primaryKey) {
                    arrayList.add(str);
                    property2 = property3;
                } else {
                    arrayList2.add(str);
                }
            }
            this.nonPkColumns = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
            this.pkColumns = (String[]) arrayList.toArray(new String[arrayList.size()]);
            boolean z = true;
            this.pkProperty = this.pkColumns.length == 1 ? property2 : property;
            this.statements = new TableStatements(database, this.tablename, this.allColumns, this.pkColumns);
            if (this.pkProperty != null) {
                Class<?> cls2 = this.pkProperty.type;
                if (!cls2.equals(Long.TYPE) && !cls2.equals(Long.class) && !cls2.equals(Integer.TYPE) && !cls2.equals(Integer.class) && !cls2.equals(Short.TYPE) && !cls2.equals(Short.class) && !cls2.equals(Byte.TYPE)) {
                    if (!cls2.equals(Byte.class)) {
                        z = false;
                    }
                }
                this.keyIsNumeric = z;
                return;
            }
            this.keyIsNumeric = false;
        } catch (Exception e2) {
            throw new DaoException("Could not init DAOConfig", e2);
        }
    }

    public DaoConfig(DaoConfig daoConfig) {
        this.db = daoConfig.db;
        this.tablename = daoConfig.tablename;
        this.properties = daoConfig.properties;
        this.allColumns = daoConfig.allColumns;
        this.pkColumns = daoConfig.pkColumns;
        this.nonPkColumns = daoConfig.nonPkColumns;
        this.pkProperty = daoConfig.pkProperty;
        this.statements = daoConfig.statements;
        this.keyIsNumeric = daoConfig.keyIsNumeric;
    }

    private static Property[] reflectProperties(Class<? extends AbstractDao<?, ?>> cls) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        Field[] declaredFields = Class.forName(cls.getName() + "$Properties").getDeclaredFields();
        ArrayList arrayList = new ArrayList();
        for (Field field : declaredFields) {
            if ((field.getModifiers() & 9) == 9) {
                Object obj = field.get(null);
                if (obj instanceof Property) {
                    arrayList.add((Property) obj);
                }
            }
        }
        Property[] propertyArr = new Property[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Property property = (Property) it.next();
            int i = property.ordinal;
            if (propertyArr[i] == null) {
                propertyArr[i] = property;
            } else {
                throw new DaoException("Duplicate property ordinals");
            }
        }
        return propertyArr;
    }

    public void clearIdentityScope() {
        IdentityScope<?, ?> identityScope2 = this.identityScope;
        if (identityScope2 != null) {
            identityScope2.clear();
        }
    }

    @Override // java.lang.Object
    public DaoConfig clone() {
        return new DaoConfig(this);
    }

    public IdentityScope<?, ?> getIdentityScope() {
        return this.identityScope;
    }

    public void initIdentityScope(IdentityScopeType identityScopeType) {
        if (identityScopeType == IdentityScopeType.None) {
            this.identityScope = null;
        } else if (identityScopeType != IdentityScopeType.Session) {
            throw new IllegalArgumentException("Unsupported type: " + identityScopeType);
        } else if (this.keyIsNumeric) {
            this.identityScope = new IdentityScopeLong();
        } else {
            this.identityScope = new IdentityScopeObject();
        }
    }

    public void setIdentityScope(IdentityScope<?, ?> identityScope2) {
        this.identityScope = identityScope2;
    }
}
