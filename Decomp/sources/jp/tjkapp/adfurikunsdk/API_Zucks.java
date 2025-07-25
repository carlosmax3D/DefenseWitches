package jp.tjkapp.adfurikunsdk;

import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_Zucks extends API_Base {
    private static final String ZUCKS_HTML1 = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><style>img{width:320px;height: auto;}@media screen and (min-width: 640px) and orientation: portrait){img{width:640px;height:auto;}}@media screen and (min-width:768px) and (orientation:landscape){img{width:640px;height: auto;}}</style></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\"><a href=\"";
    private static final String ZUCKS_HTML11 = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"text-align:center;\"><a href=\"";
    private static final String ZUCKS_HTML12 = "\"><img src=\"";
    private static final String ZUCKS_HTML13 = "\" width=\"100%%\"></a></div></body></html>";
    private static final String ZUCKS_HTML2 = "\"><img src=\"";
    private static final String ZUCKS_HTML3 = "\"></a></div></body></html>";
    private static final String ZUCKS_URL = "http://sh.zucks.net/opt/api/v2";

    API_Zucks() {
    }

    private void setResultParam(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, int i, LogUtil logUtil) {
        String valueFromJSON = getValueFromJSON(str, "status");
        String valueFromJSON2 = getValueFromJSON(str, "imp_url");
        String valueFromJSON3 = getValueFromJSON(str, "type");
        if (!"ok".equals(valueFromJSON)) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (valueFromJSON3 == null || valueFromJSON3.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (!"img".equals(valueFromJSON3)) {
            if (!"html".equals(valueFromJSON3)) {
                aPI_ResultParam.err = -7;
                return;
            } else {
                aPI_ResultParam.html = getValueFromJSON(str, "html");
                aPI_ResultParam.imp_url = valueFromJSON2;
                return;
            }
        }
        String valueFromJSON4 = getValueFromJSON(str, "img_src");
        String valueFromJSON5 = getValueFromJSON(str, "landing_url");
        StringBuilder sb = new StringBuilder();
        if (i == 9) {
            sb.append(ZUCKS_HTML11);
            sb.append(valueFromJSON5);
            sb.append("\"><img src=\"");
            sb.append(valueFromJSON4);
            sb.append(ZUCKS_HTML13);
        } else {
            sb.append(ZUCKS_HTML1);
            sb.append(valueFromJSON5);
            sb.append("\"><img src=\"");
            sb.append(valueFromJSON4);
            sb.append(ZUCKS_HTML3);
        }
        aPI_ResultParam.html = sb.toString();
        aPI_ResultParam.imp_url = valueFromJSON2;
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        if (str3.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        if (aPI_CpntrolParam.useragent.length() == 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(ZUCKS_URL + "?frameid=" + getValueFromJSON(str3, "frame_id"), logUtil, aPI_CpntrolParam.useragent, false);
        if (webAPIResultCallWebAPI.return_code == 200) {
            if (webAPIResultCallWebAPI.message.length() > 0) {
                setResultParam(aPI_ResultParam, webAPIResultCallWebAPI.message.trim(), aPI_CpntrolParam.idfa, i, logUtil);
                return;
            } else {
                aPI_ResultParam.err = -7;
                return;
            }
        }
        if (webAPIResultCallWebAPI.return_code == 204) {
            aPI_ResultParam.err = -4;
        } else {
            aPI_ResultParam.err = -7;
        }
    }
}
