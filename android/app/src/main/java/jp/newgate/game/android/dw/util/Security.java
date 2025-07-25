package jp.newgate.game.android.dw.util;

import android.text.TextUtils;
import android.util.Log;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class Security {
    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String TAG = "IABUtil/Security";

    public static PublicKey generatePublicKey(String str) {
        try {
            return KeyFactory.getInstance(KEY_FACTORY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e2) {
            Log.e(TAG, "Invalid key specification.");
            throw new IllegalArgumentException(e2);
        } catch (Base64DecoderException e3) {
            Log.e(TAG, "Base64 decoding failed.");
            throw new IllegalArgumentException(e3);
        }
    }

    public static boolean verify(PublicKey publicKey, String str, String str2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(str.getBytes());
            if (signature.verify(Base64.decode(str2))) {
                return true;
            }
            Log.e(TAG, "Signature verification failed.");
            return false;
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key specification.");
            return false;
        } catch (NoSuchAlgorithmException e2) {
            Log.e(TAG, "NoSuchAlgorithmException.");
            return false;
        } catch (SignatureException e3) {
            Log.e(TAG, "Signature exception.");
            return false;
        } catch (Base64DecoderException e4) {
            Log.e(TAG, "Base64 decoding failed.");
            return false;
        }
    }

    public static boolean verifyPurchase(String str, String str2, String str3) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3)) {
            return verify(generatePublicKey(str), str2, str3);
        }
        Log.e(TAG, "Purchase verification failed: missing data.");
        return false;
    }
}
