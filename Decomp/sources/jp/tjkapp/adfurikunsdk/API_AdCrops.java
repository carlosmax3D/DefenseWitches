package jp.tjkapp.adfurikunsdk;

import com.facebook.appevents.AppEventsConstants;
import java.util.Locale;
import java.util.Random;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_AdCrops extends API_Base {
    private static final String ADCROPS_COUNT = "40";
    private static final String ADCROPS_EMPTY = "";
    private static final String ADCROPS_FORMAT = "json";
    private static final String ADCROPS_JS = "<!doctype html><html lang=\"ja\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><title>おすすめアプリ</title><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script><link rel=\"stylesheet\" href=\"./adf_wall_base.css\"><link rel=\"stylesheet\" href=\"./adf_wall_4.css\" id=\"wall_css\"><script type=\"text/javascript\"> var json_str = '[ADFR_CPIJS_JSONDATA]'; $(document) .ready( function() { try { var json_data = $.parseJSON(json_str); var wall = json_data.wall; var ads = json_data.ads; ads.sort( function(a,b) { var aReword = a.reward; var bReword = b.reward; if (aReword > bReword) return -1; if (aReword < bReword) return 1; return 0; } ); if (ads.length == 0 || ads[0].length == 0) { throw \"empty data\"; } var cnt = 0; for ( var i = 0; i < ads.length; i++) { if (cnt >= 14) { break; } var ad = ads[i]; if (ad.rest_count <= 50) { continue; } var link = ad.link_url; /* typeでリンクURLを変更してApp側でキャッチ。判別にしています。 */ if (ad.type == 1) { link = \"adfurikun_appurl:\" + link; } else if (ad.type == 2) { link = \"adfurikun_weburl:\" + link; } var ad_image = ad.image_icon_72; /* Androidは画像サイズ変える必要あり。 */ var html = '<article><a href=\"' + link + '\"><img src=\"' + ad_image + '\" alt=\"' + ad.title + '\" class=\"app_icon\"></a>'; html += '<h1>' + ad.title + '</h1></article>'; $(\".app_list\").append(html); cnt++; } for ( var j = cnt; j < 14; j++) { $(\".app_list\") .append( \"<article><a><img src='http://d1bqhgjuxdf1ml.cloudfront.net/spacer.png' class='app_icon spacer'></a><h1></h1></article>\"); } /* これが認識されるか確認, img src=\"\"で送ってる。 */ $(\"#wall_beacon\").attr(\"src\", wall.beacon_url); $(\"#wall_beacon\").hide(); } catch (e) { /* エラー発生 */ location.href = 'adfurikun_notfound:'; } });</script></head><body> <header class=\"site_header\"> <h1>おすすめアプリ</h1> </header> <div class='app_list'></div> <img src=\"\" width=\"1\" height=\"1\" id=\"wall_beacon\"></body></html>";
    private static final String ADCROPS_LIST_JS = "<!doctype html><html lang=\"ja\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><title>おすすめアプリ</title><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script><link rel=\"stylesheet\" href=\"./adf_list_base.css\"><link rel=\"stylesheet\" href=\"./adf_list_4.css\" id=\"wall_css\"><script type=\"text/javascript\"> var json_str = '[ADFR_CPIJS_JSONDATA]'; $(document) .ready( function() { try { var json_data = $.parseJSON(json_str); var wall = json_data.wall; var ads = json_data.ads; ads.sort( function(a,b) { var aReword = a.reward; var bReword = b.reward; if (aReword > bReword) return -1; if (aReword < bReword) return 1; return 0; } ); if (ads.length == 0 || ads[0].length == 0) { throw \"empty data\"; } var cnt = 0; for ( var i = 0; i < ads.length; i++) { var ad = ads[i]; if (ad.rest_count <= 50) { continue; } var link = ad.link_url; if (ad.type == 1) { link = \"adfurikun_appurl:\" + link; } else if (ad.type == 2) { link = \"adfurikun_weburl:\" + link; } link = '<a href=\"' + link + '\" '; var ad_image = ad.image_icon_72; var html = '<article>' + link + '><h1 class=\"app_name\"><span class=\"free\">無料</span>' + ad.title + '</h1></a>'; html += '<div class=\"app_box clearfix\">' + link + '><img src=\"' + ad_image + '\" alt=\"' +ad.title + '\" class=\"app_icon\"></a>'; html += '<div class=\"app_info\"><p>' + ad.description + '</p>'; if (ad.type == 2) { html += link + 'class=\"dl_button\">詳細へ</a></div>\\n</div>\\n</article>'; } else { html += link + 'class=\"dl_button\">インストール</a></div>\\n</div>\\n</article>'; } $(\".app_list\").append(html); cnt++; } $(\"#wall_beacon\").attr(\"src\", wall.beacon_url); $(\"#wall_beacon\").hide(); } catch (e) { location.href = 'adfurikun_notfound:'; } });</script></head><body> <header class=\"site_header\"> <h1>おすすめアプリ</h1> </header> <div class='app_list'></div> <img src=\"\" width=\"1\" height=\"1\" id=\"wall_beacon\"></body></html>";
    private static final String ADCROPS_LOC = "1";
    private static final String ADCROPS_PAGE = "1";
    private static final String ADCROPS_PARAM_COUNT = "_count=";
    private static final String ADCROPS_PARAM_FORMAT = "_format=";
    private static final String ADCROPS_PARAM_I = "_i=";
    private static final String ADCROPS_PARAM_LANG = "_lang=";
    private static final String ADCROPS_PARAM_LOC = "_loc=";
    private static final String ADCROPS_PARAM_LOCALE = "_locale=";
    private static final String ADCROPS_PARAM_PAGE = "_page=";
    private static final String ADCROPS_PARAM_PL = "_pl=";
    private static final String ADCROPS_PARAM_SITE = "_site=";
    private static final String ADCROPS_URL = "http://t.adcrops.net/ad/p/txt?";
    private static final String ADCROPS_USER_AGENT = "8CHK.A/Android/2.2.0/4.2.2/IS03/SHI03/ja/";

    API_AdCrops() {
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        if (str2 == null || str2.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(ADCROPS_URL + ADCROPS_PARAM_LANG + Locale.getDefault().getLanguage() + "&" + ADCROPS_PARAM_LOC + AppEventsConstants.EVENT_PARAM_VALUE_YES + "&" + ADCROPS_PARAM_SITE + str2 + "&" + ADCROPS_PARAM_PAGE + AppEventsConstants.EVENT_PARAM_VALUE_YES + "&" + ADCROPS_PARAM_COUNT + ADCROPS_COUNT + "&" + ADCROPS_PARAM_FORMAT + ADCROPS_FORMAT + "&" + ADCROPS_PARAM_LOCALE + Locale.getDefault().toString() + "&" + ADCROPS_PARAM_PL + "&" + ADCROPS_PARAM_I + "", logUtil, ADCROPS_USER_AGENT, false);
        if (webAPIResultCallWebAPI.return_code != 200) {
            aPI_ResultParam.err = webAPIResultCallWebAPI.return_code;
            return;
        }
        if (webAPIResultCallWebAPI.message.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        String strReplaceCode = replaceCode(webAPIResultCallWebAPI.message);
        if (new Random().nextInt(2) == 0) {
            aPI_ResultParam.html = ADCROPS_JS;
        } else {
            aPI_ResultParam.html = ADCROPS_LIST_JS;
        }
        aPI_ResultParam.html = aPI_ResultParam.html.replace("[ADFR_CPIJS_JSONDATA]", strReplaceCode);
    }
}
