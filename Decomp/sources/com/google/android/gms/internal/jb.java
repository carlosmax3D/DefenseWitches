package com.google.android.gms.internal;

import java.io.IOException;

public final class jb {
    static final int HA = g(3, 2);
    public static final int[] HB = new int[0];
    public static final long[] HC = new long[0];
    public static final float[] HD = new float[0];
    public static final double[] HE = new double[0];
    public static final boolean[] HF = new boolean[0];
    public static final String[] HG = new String[0];
    public static final byte[][] HH = new byte[0][];
    public static final byte[] HI = new byte[0];
    static final int Hx = g(1, 3);
    static final int Hy = g(1, 4);
    static final int Hz = g(2, 0);

    public static boolean a(iw iwVar, int i) throws IOException {
        return iwVar.bJ(i);
    }

    static int bS(int i) {
        return i & 7;
    }

    public static int bT(int i) {
        return i >>> 3;
    }

    static int g(int i, int i2) {
        return (i << 3) | i2;
    }
}
