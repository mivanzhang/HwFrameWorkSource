package com.huawei.g11n.tmr.address.jni;

import java.io.PrintStream;

public class DicSearch {
    private static final String TAG = "TMRManager";

    public static native int dicsearch(int i, String str);

    static {
        try {
            System.loadLibrary("dicsearch");
        } catch (UnsatisfiedLinkError e) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder("loadLibrary tmr has an error  >>>> ");
            stringBuilder.append(e);
            printStream.println(stringBuilder.toString());
        }
    }
}
