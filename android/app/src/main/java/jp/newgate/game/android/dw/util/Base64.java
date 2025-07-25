package jp.newgate.game.android.dw.util;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final byte[] ALPHABET;
    private static final byte[] DECODABET;
    public static final boolean DECODE = false;
    public static final boolean ENCODE = true;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    private static final byte NEW_LINE = 10;
    private static final byte[] WEBSAFE_ALPHABET;
    private static final byte[] WEBSAFE_DECODABET;
    private static final byte WHITE_SPACE_ENC = -5;

    static {
        $assertionsDisabled = !Base64.class.desiredAssertionStatus();
        ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        WEBSAFE_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NEW_LINE, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
        WEBSAFE_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NEW_LINE, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
    }

    private Base64() {
    }

    public static byte[] decode(String str) throws Base64DecoderException {
        byte[] bytes = str.getBytes();
        return decode(bytes, 0, bytes.length);
    }

    public static byte[] decode(byte[] bArr) throws Base64DecoderException {
        return decode(bArr, 0, bArr.length);
    }

    public static byte[] decode(byte[] bArr, int i, int i2) throws Base64DecoderException {
        return decode(bArr, i, i2, DECODABET);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, byte[] bArr2) throws Base64DecoderException {
        int i3;
        byte[] bArr3 = new byte[((i2 * 3) / 4) + 2];
        int iDecode4to3 = 0;
        byte[] bArr4 = new byte[4];
        int i4 = 0;
        int i5 = 0;
        while (true) {
            i3 = i4;
            if (i5 >= i2) {
                break;
            }
            byte b = (byte) (bArr[i5 + i] & Byte.MAX_VALUE);
            byte b2 = bArr2[b];
            if (b2 < -5) {
                throw new Base64DecoderException("Bad Base64 input character at " + i5 + ": " + ((int) bArr[i5 + i]) + "(decimal)");
            }
            if (b2 < -1) {
                i4 = i3;
            } else if (b == 61) {
                int i6 = i2 - i5;
                byte b3 = (byte) (bArr[(i2 - 1) + i] & Byte.MAX_VALUE);
                if (i3 == 0 || i3 == 1) {
                    throw new Base64DecoderException("invalid padding byte '=' at byte offset " + i5);
                }
                if ((i3 == 3 && i6 > 2) || (i3 == 4 && i6 > 1)) {
                    throw new Base64DecoderException("padding byte '=' falsely signals end of encoded value at offset " + i5);
                }
                if (b3 != 61 && b3 != 10) {
                    throw new Base64DecoderException("encoded value has invalid trailing byte");
                }
            } else {
                i4 = i3 + 1;
                bArr4[i3] = b;
                if (i4 == 4) {
                    iDecode4to3 += decode4to3(bArr4, 0, bArr3, iDecode4to3, bArr2);
                    i4 = 0;
                }
            }
            i5++;
        }
        if (i3 != 0) {
            if (i3 == 1) {
                throw new Base64DecoderException("single trailing character at offset " + (i2 - 1));
            }
            int i7 = i3 + 1;
            bArr4[i3] = EQUALS_SIGN;
            iDecode4to3 += decode4to3(bArr4, 0, bArr3, iDecode4to3, bArr2);
        }
        byte[] bArr5 = new byte[iDecode4to3];
        System.arraycopy(bArr3, 0, bArr5, 0, iDecode4to3);
        return bArr5;
    }

    private static int decode4to3(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3) {
        if (bArr[i + 2] == 61) {
            bArr2[i2] = (byte) ((((bArr3[bArr[i]] << 24) >>> 6) | ((bArr3[bArr[i + 1]] << 24) >>> 12)) >>> 16);
            return 1;
        }
        if (bArr[i + 3] == 61) {
            int i3 = ((bArr3[bArr[i]] << 24) >>> 6) | ((bArr3[bArr[i + 1]] << 24) >>> 12) | ((bArr3[bArr[i + 2]] << 24) >>> 18);
            bArr2[i2] = (byte) (i3 >>> 16);
            bArr2[i2 + 1] = (byte) (i3 >>> 8);
            return 2;
        }
        int i4 = ((bArr3[bArr[i]] << 24) >>> 6) | ((bArr3[bArr[i + 1]] << 24) >>> 12) | ((bArr3[bArr[i + 2]] << 24) >>> 18) | ((bArr3[bArr[i + 3]] << 24) >>> 24);
        bArr2[i2] = (byte) (i4 >> 16);
        bArr2[i2 + 1] = (byte) (i4 >> 8);
        bArr2[i2 + 2] = (byte) i4;
        return 3;
    }

    public static byte[] decodeWebSafe(String str) throws Base64DecoderException {
        byte[] bytes = str.getBytes();
        return decodeWebSafe(bytes, 0, bytes.length);
    }

    public static byte[] decodeWebSafe(byte[] bArr) throws Base64DecoderException {
        return decodeWebSafe(bArr, 0, bArr.length);
    }

    public static byte[] decodeWebSafe(byte[] bArr, int i, int i2) throws Base64DecoderException {
        return decode(bArr, i, i2, WEBSAFE_DECODABET);
    }

    public static String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length, ALPHABET, true);
    }

    public static String encode(byte[] bArr, int i, int i2, byte[] bArr2, boolean z) {
        byte[] bArrEncode = encode(bArr, i, i2, bArr2, Integer.MAX_VALUE);
        int length = bArrEncode.length;
        while (!z && length > 0 && bArrEncode[length - 1] == 61) {
            length--;
        }
        return new String(bArrEncode, 0, length);
    }

    public static byte[] encode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = ((i2 + 2) / 3) * 4;
        byte[] bArr3 = new byte[(i4 / i3) + i4];
        int i5 = 0;
        int i6 = 0;
        int i7 = i2 - 2;
        int i8 = 0;
        while (i5 < i7) {
            int i9 = ((bArr[i5 + i] << 24) >>> 8) | ((bArr[(i5 + 1) + i] << 24) >>> 16) | ((bArr[(i5 + 2) + i] << 24) >>> 24);
            bArr3[i6] = bArr2[i9 >>> 18];
            bArr3[i6 + 1] = bArr2[(i9 >>> 12) & 63];
            bArr3[i6 + 2] = bArr2[(i9 >>> 6) & 63];
            bArr3[i6 + 3] = bArr2[i9 & 63];
            i8 += 4;
            if (i8 == i3) {
                bArr3[i6 + 4] = NEW_LINE;
                i6++;
                i8 = 0;
            }
            i5 += 3;
            i6 += 4;
        }
        if (i5 < i2) {
            encode3to4(bArr, i5 + i, i2 - i5, bArr3, i6, bArr2);
            if (i8 + 4 == i3) {
                bArr3[i6 + 4] = NEW_LINE;
                i6++;
            }
            i6 += 4;
        }
        if ($assertionsDisabled || i6 == bArr3.length) {
            return bArr3;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static byte[] encode3to4(byte[] bArr, int i, int i2, byte[] bArr2, int i3, byte[] bArr3) {
        int i4 = (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0) | (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0) | (i2 > 2 ? (bArr[i + 2] << 24) >>> 24 : 0);
        switch (i2) {
            case 1:
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = EQUALS_SIGN;
                bArr2[i3 + 3] = EQUALS_SIGN;
                return bArr2;
            case 2:
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                bArr2[i3 + 3] = EQUALS_SIGN;
                return bArr2;
            case 3:
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                bArr2[i3 + 3] = bArr3[i4 & 63];
                return bArr2;
            default:
                return bArr2;
        }
    }

    public static String encodeWebSafe(byte[] bArr, boolean z) {
        return encode(bArr, 0, bArr.length, WEBSAFE_ALPHABET, z);
    }
}
