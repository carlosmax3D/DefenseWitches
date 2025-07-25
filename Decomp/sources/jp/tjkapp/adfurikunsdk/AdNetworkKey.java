package jp.tjkapp.adfurikunsdk;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class AdNetworkKey {
    public static final String ADLANTIS = "1001";
    public static final String APPLI_PROMOTION = "1041";
    public static final String DEFAULT = "default";
    public static final String DEFAULT_AD = "-";
    public static final String GAMELOGIC = "1067";
    public static final String XMAX = "1037";
    public static final String ADCROPS = "1043";
    public static final String INMOBI = "1003";
    public static final String MMEDIA = "1060";
    public static final String AARKI = "1073";
    public static final String GAMEFEAT = "1065";
    public static final String SCALEOUT = "1075";
    public static final String ZUCKS = "1035";
    public static final String ZUCKS_ICON = "1078";
    public static final String AMOAD = "1006";
    public static final String AST_ICON = "1077";
    public static final String GMOSSP = "1052";
    public static final String TJK = "8888";
    public static final String[][] ADNETWORK_API = {new String[]{ADCROPS, "jp.tjkapp.adfurikunsdk.API_AdCrops"}, new String[]{INMOBI, "jp.tjkapp.adfurikunsdk.API_InMobi"}, new String[]{MMEDIA, "jp.tjkapp.adfurikunsdk.API_mMedia"}, new String[]{AARKI, "jp.tjkapp.adfurikunsdk.API_Aarki"}, new String[]{GAMEFEAT, "jp.tjkapp.adfurikunsdk.API_GameFeat"}, new String[]{SCALEOUT, "jp.tjkapp.adfurikunsdk.API_ScaleOut"}, new String[]{ZUCKS, "jp.tjkapp.adfurikunsdk.API_Zucks"}, new String[]{ZUCKS_ICON, "jp.tjkapp.adfurikunsdk.API_ZucksIcon"}, new String[]{AMOAD, "jp.tjkapp.adfurikunsdk.API_AMoAd"}, new String[]{AST_ICON, "jp.tjkapp.adfurikunsdk.API_AstIcon"}, new String[]{GMOSSP, "jp.tjkapp.adfurikunsdk.API_GMOSSP"}, new String[]{TJK, "jp.tjkapp.adfurikunsdk.API_TJK"}};

    private AdNetworkKey() {
    }

    public static String getClassName(String str) {
        try {
            int length = ADNETWORK_API.length;
            for (int i = 0; i < length; i++) {
                if (str.equals(ADNETWORK_API[i][0])) {
                    return ADNETWORK_API[i][1];
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
