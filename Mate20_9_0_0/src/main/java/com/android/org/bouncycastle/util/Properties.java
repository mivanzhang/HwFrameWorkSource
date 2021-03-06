package com.android.org.bouncycastle.util;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Properties {
    public static boolean isOverrideSet(final String propertyName) {
        try {
            return "true".equals(AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    String value = System.getProperty(propertyName);
                    if (value == null) {
                        return null;
                    }
                    return Strings.toLowerCase(value);
                }
            }));
        } catch (AccessControlException e) {
            return false;
        }
    }

    public static Set<String> asKeySet(String propertyName) {
        Set<String> set = new HashSet();
        String p = System.getProperty(propertyName);
        if (p != null) {
            StringTokenizer sTok = new StringTokenizer(p, ",");
            while (sTok.hasMoreElements()) {
                set.add(Strings.toLowerCase(sTok.nextToken()).trim());
            }
        }
        return Collections.unmodifiableSet(set);
    }
}
