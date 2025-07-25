package com.facebook.internal;

import com.facebook.FacebookRequestError;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FacebookRequestErrorClassification {
    public static final int EC_APP_TOO_MANY_CALLS = 4;
    public static final int EC_INVALID_SESSION = 102;
    public static final int EC_INVALID_TOKEN = 190;
    public static final int EC_RATE = 9;
    public static final int EC_SERVICE_UNAVAILABLE = 2;
    public static final int EC_TOO_MANY_USER_ACTION_CALLS = 341;
    public static final int EC_USER_TOO_MANY_CALLS = 17;
    public static final String KEY_LOGIN_RECOVERABLE = "login_recoverable";
    public static final String KEY_NAME = "name";
    public static final String KEY_OTHER = "other";
    public static final String KEY_RECOVERY_MESSAGE = "recovery_message";
    public static final String KEY_TRANSIENT = "transient";
    private static FacebookRequestErrorClassification defaultInstance;
    private final Map<Integer, Set<Integer>> loginRecoverableErrors;
    private final String loginRecoverableRecoveryMessage;
    private final Map<Integer, Set<Integer>> otherErrors;
    private final String otherRecoveryMessage;
    private final Map<Integer, Set<Integer>> transientErrors;
    private final String transientRecoveryMessage;

    FacebookRequestErrorClassification(Map<Integer, Set<Integer>> map, Map<Integer, Set<Integer>> map2, Map<Integer, Set<Integer>> map3, String str, String str2, String str3) {
        this.otherErrors = map;
        this.transientErrors = map2;
        this.loginRecoverableErrors = map3;
        this.otherRecoveryMessage = str;
        this.transientRecoveryMessage = str2;
        this.loginRecoverableRecoveryMessage = str3;
    }

    public static FacebookRequestErrorClassification createFromJSON(JSONArray jSONArray) {
        String optString;
        if (jSONArray == null) {
            return null;
        }
        Map<Integer, Set<Integer>> map = null;
        Map<Integer, Set<Integer>> map2 = null;
        Map<Integer, Set<Integer>> map3 = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (!(optJSONObject == null || (optString = optJSONObject.optString("name")) == null)) {
                if (optString.equalsIgnoreCase(KEY_OTHER)) {
                    str = optJSONObject.optString(KEY_RECOVERY_MESSAGE, (String) null);
                    map = parseJSONDefinition(optJSONObject);
                } else if (optString.equalsIgnoreCase(KEY_TRANSIENT)) {
                    str2 = optJSONObject.optString(KEY_RECOVERY_MESSAGE, (String) null);
                    map2 = parseJSONDefinition(optJSONObject);
                } else if (optString.equalsIgnoreCase(KEY_LOGIN_RECOVERABLE)) {
                    str3 = optJSONObject.optString(KEY_RECOVERY_MESSAGE, (String) null);
                    map3 = parseJSONDefinition(optJSONObject);
                }
            }
        }
        return new FacebookRequestErrorClassification(map, map2, map3, str, str2, str3);
    }

    public static synchronized FacebookRequestErrorClassification getDefaultErrorClassification() {
        FacebookRequestErrorClassification facebookRequestErrorClassification;
        synchronized (FacebookRequestErrorClassification.class) {
            if (defaultInstance == null) {
                defaultInstance = getDefaultErrorClassificationImpl();
            }
            facebookRequestErrorClassification = defaultInstance;
        }
        return facebookRequestErrorClassification;
    }

    private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl() {
        return new FacebookRequestErrorClassification((Map<Integer, Set<Integer>>) null, new HashMap<Integer, Set<Integer>>() {
            {
                put(2, (Object) null);
                put(4, (Object) null);
                put(9, (Object) null);
                put(17, (Object) null);
                put(Integer.valueOf(FacebookRequestErrorClassification.EC_TOO_MANY_USER_ACTION_CALLS), (Object) null);
            }
        }, new HashMap<Integer, Set<Integer>>() {
            {
                put(102, (Object) null);
                put(Integer.valueOf(FacebookRequestErrorClassification.EC_INVALID_TOKEN), (Object) null);
            }
        }, (String) null, (String) null, (String) null);
    }

    private static Map<Integer, Set<Integer>> parseJSONDefinition(JSONObject jSONObject) {
        int optInt;
        JSONArray optJSONArray = jSONObject.optJSONArray("items");
        if (optJSONArray.length() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (!(optJSONObject == null || (optInt = optJSONObject.optInt("code")) == 0)) {
                HashSet hashSet = null;
                JSONArray optJSONArray2 = optJSONObject.optJSONArray("subcodes");
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    hashSet = new HashSet();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        int optInt2 = optJSONArray2.optInt(i2);
                        if (optInt2 != 0) {
                            hashSet.add(Integer.valueOf(optInt2));
                        }
                    }
                }
                hashMap.put(Integer.valueOf(optInt), hashSet);
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006b, code lost:
        r0 = r3.transientErrors.get(java.lang.Integer.valueOf(r4));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.facebook.FacebookRequestError.Category classify(int r4, int r5, boolean r6) {
        /*
            r3 = this;
            if (r6 == 0) goto L_0x0005
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.TRANSIENT
        L_0x0004:
            return r1
        L_0x0005:
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.otherErrors
            if (r1 == 0) goto L_0x0030
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.otherErrors
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            boolean r1 = r1.containsKey(r2)
            if (r1 == 0) goto L_0x0030
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.otherErrors
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.Object r0 = r1.get(r2)
            java.util.Set r0 = (java.util.Set) r0
            if (r0 == 0) goto L_0x002d
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            boolean r1 = r0.contains(r1)
            if (r1 == 0) goto L_0x0030
        L_0x002d:
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.OTHER
            goto L_0x0004
        L_0x0030:
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.loginRecoverableErrors
            if (r1 == 0) goto L_0x005b
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.loginRecoverableErrors
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            boolean r1 = r1.containsKey(r2)
            if (r1 == 0) goto L_0x005b
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.loginRecoverableErrors
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.Object r0 = r1.get(r2)
            java.util.Set r0 = (java.util.Set) r0
            if (r0 == 0) goto L_0x0058
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            boolean r1 = r0.contains(r1)
            if (r1 == 0) goto L_0x005b
        L_0x0058:
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.LOGIN_RECOVERABLE
            goto L_0x0004
        L_0x005b:
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.transientErrors
            if (r1 == 0) goto L_0x0087
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.transientErrors
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            boolean r1 = r1.containsKey(r2)
            if (r1 == 0) goto L_0x0087
            java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> r1 = r3.transientErrors
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            java.lang.Object r0 = r1.get(r2)
            java.util.Set r0 = (java.util.Set) r0
            if (r0 == 0) goto L_0x0083
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            boolean r1 = r0.contains(r1)
            if (r1 == 0) goto L_0x0087
        L_0x0083:
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.TRANSIENT
            goto L_0x0004
        L_0x0087:
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.OTHER
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.FacebookRequestErrorClassification.classify(int, int, boolean):com.facebook.FacebookRequestError$Category");
    }

    public Map<Integer, Set<Integer>> getLoginRecoverableErrors() {
        return this.loginRecoverableErrors;
    }

    public Map<Integer, Set<Integer>> getOtherErrors() {
        return this.otherErrors;
    }

    public String getRecoveryMessage(FacebookRequestError.Category category) {
        switch (category) {
            case OTHER:
                return this.otherRecoveryMessage;
            case LOGIN_RECOVERABLE:
                return this.loginRecoverableRecoveryMessage;
            case TRANSIENT:
                return this.transientRecoveryMessage;
            default:
                return null;
        }
    }

    public Map<Integer, Set<Integer>> getTransientErrors() {
        return this.transientErrors;
    }
}
