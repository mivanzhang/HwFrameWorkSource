package java.util;

import java.security.AccessController;
import sun.util.logging.PlatformLogger;

final class Tripwire {
    static final boolean ENABLED = ((Boolean) AccessController.doPrivileged(-$$Lambda$Tripwire$03Zb3z-rd6SqpmwW72AFPa8slaw.INSTANCE)).booleanValue();
    private static final String TRIPWIRE_PROPERTY = "org.openjdk.java.util.stream.tripwire";

    private Tripwire() {
    }

    static void trip(Class<?> trippingClass, String msg) {
        PlatformLogger.getLogger(trippingClass.getName()).warning(msg, trippingClass.getName());
    }
}
