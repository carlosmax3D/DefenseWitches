package jp.tjkapp.adfurikunsdk;

import CoronaProvider.ads.admob.AdMobAd;
import com.facebook.share.internal.ShareConstants;
import com.tapjoy.TapjoyConstants;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.API_Controller2;
import jp.tjkapp.adfurikunsdk.ApiAccessUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class API_GameLogic extends API_Base {
    private static final String GAMELOGIC_HTML1 = "<html><head><base target=\"_blank\"><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body style='text-align:center;padding:0;margin:0;overflow:hidden;'><div style=\"width:";
    private static final String GAMELOGIC_HTML2 = "px;margin:0 auto;\">";
    private static final String GAMELOGIC_HTML3 = "</div></body></html>";
    private static final String GAMELOGIC_URL = "http://cast-bid27-j.adtdp.com/bid/adfurikun";
    private static final String GETINFO_GAMELOGIC_PARAM_BADV = "badv";
    private static final String GETINFO_GAMELOGIC_PARAM_BANNER_HEIGHT = "banner_h";
    private static final String GETINFO_GAMELOGIC_PARAM_BANNER_WIDTH = "banner_w";
    private static final String GETINFO_GAMELOGIC_PARAM_FLOOR_PRICE = "floor_price";
    private static final String GETINFO_GAMELOGIC_PARAM_GENDER = "user_gender";
    private static final String[] PRICE_LIST = {"yw==", "yg==", "yQ==", "yA==", "zw==", "zg==", "zQ==", "zA==", "ww==", "wg==", "ym4=", "ym8=", "ymw=", "ym0=", "ymo=", "yms=", "ymg=", "ymk=", "ymY=", "ymc=", "yXQ=", "yXU=", "yXY=", "yXc=", "yXA=", "yXE=", "yXI=", "yXM=", "yXw=", "yX0=", "yGU=", "yGQ=", "yGc=", "yGY=", "yGE=", "yGA=", "yGM=", "yGI=", "yG0=", "yGw=", "z0Y=", "z0c=", "z0Q=", "z0U=", "z0I=", "z0M=", "z0A=", "z0E=", "z04=", "z08=", "zhY=", "zhc=", "zhQ=", "zhU=", "zhI=", "zhM=", "zhA=", "zhE=", "zh4=", "zh8=", "zRM=", "zRI=", "zRE=", "zRA=", "zRc=", "zRY=", "zRU=", "zRQ=", "zRs=", "zRo=", "zNs=", "zNo=", "zNk=", "zNg=", "zN8=", "zN4=", "zN0=", "zNw=", "zNM=", "zNI=", "wzc=", "wzY=", "wzU=", "wzQ=", "wzM=", "wzI=", "wzE=", "wzA=", "wz8=", "wz4=", "wgM=", "wgI=", "wgE=", "wgA=", "wgc=", "wgY=", "wgU=", "wgQ=", "wgs=", "wgo=", "ym7t", "ym7s", "ym7v", "ym7u", "ym7p", "ym7o", "ym7r", "ym7q", "ym7l", "ym7k", "ym9I", "ym9J", "ym9K", "ym9L", "ym9M", "ym9N", "ym9O", "ym9P", "ym9A", "ym9B", "ymzF", "ymzE", "ymzH", "ymzG", "ymzB", "ymzA", "ymzD", "ymzC", "ymzN", "ymzM", "ym0B", "ym0A", "ym0D", "ym0C", "ym0F", "ym0E", "ym0H", "ym0G", "ym0J", "ym0I", "ymr1", "ymr0", "ymr3", "ymr2", "ymrx", "ymrw", "ymrz", "ymry", "ymr9", "ymr8", "ymu8", "ymu9", "ymu+", "ymu/", "ymu4", "ymu5", "ymu6", "ymu7", "ymu0", "ymu1", "ymim", "ymin", "ymik", "ymil", "ymii", "ymij", "ymig", "ymih", "ymiu", "ymiv", "ymkk", "ymkl", "ymkm", "ymkn", "ymkg", "ymkh", "ymki", "ymkj", "ymks", "ymkt", "ymYH", "ymYG", "ymYF", "ymYE", "ymYD", "ymYC", "ymYB", "ymYP", "ymYO", "ymcZ", "ymcY", "ymcb", "ymca", "ymcd", "ymcc", "ymcf", "ymce", "ymcR", "ymcQ", "yXQ4", "yXQ5", "yXQ6", "yXQ7", "yXQ8", "yXQ9", "yXQ+", "yXQ/", "yXQw", "yXQx", "yXU7", "yXU6", "yXU5", "yXU4", "yXU/", "yXU+", "yXU9", "yXU8", "yXUz", "yXUy", "yXZG", "yXZH", "yXZE", "yXZF", "yXZC", "yXZD", "yXZA", "yXZB", "yXZO", "yXZP", "yXdg", "yXdh", "yXdi", "yXdj", "yXdk", "yXdl", "yXdm", "yXdn", "yXdo", "yXdp", "yXDw", "yXDx", "yXDy", "yXDz", "yXD0", "yXD1", "yXD2", "yXD3", "yXD4", "yXD5", "yXHe", "yXHf", "yXHc", "yXHd", "yXHa", "yXHb", "yXHY", "yXHZ", "yXHW", "yXHX", "yXJS", "yXJT", "yXJQ", "yXJR", "yXJW", "yXJX", "yXJU", "yXJV", "yXJa", "yXJb", "yXOZ", "yXOY", "yXOb", "yXOa", "yXOd", "yXOc", "yXOf", "yXOe", "yXOR", "yXOQ", "yXyU", "yXyV", "yXyW", "yXyX", "yXyQ", "yXyR", "yXyS", "yXyT", "yXyc", "yXyd", "yX1F", "yX1E", "yX1H", "yX1G", "yX1B", "yX1A", "yX1D", "yX1C", "yX1N", "yX1M", "yGUT", "yGUS", "yGUR", "yGUQ", "yGUX", "yGUW", "yGUV", "yGUU", "yGUb", "yGUa", "yGQH", "yGQG", "yGQF", "yGQE", "yGQD", "yGQC", "yGQB", "yGQA", "yGQP", "yGQO", "yGdY", "yGdZ", "yGda", "yGdb", "yGdc", "yGdd", "yGde", "yGdf", "yGdQ", "yGdR", "yGYt", "yGYs", "yGYv", "yGYu", "yGYp", "yGYo", "yGYr", "yGYq", "yGYl", "yGYk", "yGHj", "yGHi", "yGHh", "yGHg", "yGHn", "yGHm", "yGHl", "yGHk", "yGHr", "yGHq", "yGC0", "yGC1", "yGC2", "yGC3", "yGCw", "yGCx", "yGCy", "yGCz", "yGC8", "yGC9", "yGND", "yGNC", "yGNB", "yGNA", "yGNH", "yGNG", "yGNF", "yGNE", "yGNL", "yGNK", "yGJY", "yGJZ", "yGJa", "yGJb", "yGJc", "yGJd", "yGJe", "yGJf", "yGJQ", "yGJR", "yG18", "yG19", "yG1+", "yG1/", "yG14", "yG15", "yG16", "yG17", "yG10", "yG11", "yGx9", "yGx8", "yGx/", "yGx+", "yGx5", "yGx4", "yGx7", "yGx6", "yGx1", "yGx0", "z0Yb", "z0Ya", "z0YZ", "z0YY", "z0Yf", "z0Ye", "z0Yd", "z0Yc", "z0YT", "z0YS", "z0eV", "z0eU", "z0eX", "z0eW", "z0eR", "z0eQ", "z0eT", "z0eS", "z0ed", "z0ec", "z0Ti", "z0Tj", "z0Tg", "z0Th", "z0Tm", "z0Tn", "z0Tk", "z0Tl", "z0Tq", "z0Tr", "z0W8", "z0W9", "z0W+", "z0W/", "z0W4", "z0W5", "z0W6", "z0W7", "z0W0", "z0W1", "z0Ld", "z0Lc", "z0Lf", "z0Le", "z0LZ", "z0LY", "z0Lb", "z0La", "z0LV", "z0LU", "z0MR", "z0MQ", "z0MT", "z0MS", "z0MV", "z0MU", "z0MX", "z0MW", "z0MZ", "z0MY", "z0Cz", "z0Cy", "z0Cx", "z0Cw", "z0C3", "z0C2", "z0C1", "z0C0", "z0C7", "z0C6", "z0HP", "z0HO", "z0HN", "z0HM", "z0HL", "z0HK", "z0HJ", "z0HI", "z0HH", "z0HG", "z05q", "z05r", "z05o", "z05p", "z05u", "z05v", "z05s", "z05t", "z05i", "z05j", "z0/C", "z0/D", "z0/A", "z0/B", "z0/G", "z0/H", "z0/E", "z0/F", "z0/K", "z0/L", "zhYl", "zhYk", "zhYn", "zhYm", "zhYh", "zhYg", "zhYj", "zhYi", "zhYt", "zhYs", "zhe9", "zhe8", "zhe/", "zhe+", "zhe5", "zhe4", "zhe7", "zhe6", "zhe1", "zhe0", "zhQF", "zhQE", "zhQH", "zhQG", "zhQB", "zhQA", "zhQD", "zhQC", "zhQN", "zhQM", "zhV1", "zhV0", "zhV3", "zhV2", "zhVx", "zhVw", "zhVz", "zhVy", "zhV9", "zhV8", "zhJh", "zhJg", "zhJj", "zhJi", "zhJl", "zhJk", "zhJn", "zhJm", "zhJp", "zhJo", "zhOW", "zhOX", "zhOU", "zhOV", "zhOS", "zhOT", "zhOQ", "zhOR", "zhOe", "zhOf", "zhB3", "zhB2", "zhB1", "zhB0", "zhBz", "zhBy", "zhBx", "zhBw", "zhB/", "zhB+", "zhG+", "zhG/", "zhG8", "zhG9", "zhG6", "zhG7", "zhG4", "zhG5", "zhG2", "zhG3", "zh4i", "zh4j", "zh4g", "zh4h", "zh4m", "zh4n", "zh4k", "zh4l", "zh4q", "zh4r", "zh90", "zh91", "zh92", "zh93", "zh9w", "zh9x", "zh9y", "zh9z", "zh98", "zh99", "zRN8", "zRN9", "zRN+", "zRN/", "zRN4", "zRN5", "zRN6", "zRN7", "zRN0", "zRN1", "zRL+", "zRL/", "zRL8", "zRL9", "zRL6", "zRL7", "zRL4", "zRL5", "zRL2", "zRL3", "zRGn", "zRGm", "zRGl", "zRGk", "zRGj", "zRGi", "zRGh", "zRGg", "zRGv", "zRGu", "zRDm", "zRDn", "zRDk", "zRDl", "zRDi", "zRDj", "zRDg", "zRDh", "zRDu", "zRDv", "zRfD", "zRfC", "zRfB", "zRfA", "zRfH", "zRfG", "zRfF", "zRfE", "zRfL", "zRfK", "zRZQ", "zRZR", "zRZS", "zRZT", "zRZU", "zRZV", "zRZW", "zRZX", "zRZY", "zRZZ", "zRVZ", "zRVY", "zRVb", "zRVa", "zRVd", "zRVc", "zRVf", "zRVe", "zRVR", "zRVQ", "zRQ3", "zRQ2", "zRQ1", "zRQ0", "zRQz", "zRQy", "zRQx", "zRQw", "zRQ/", "zRQ+", "zRuV", "zRuU", "zRuX", "zRuW", "zRuR", "zRuQ", "zRuT", "zRuS", "zRud", "zRuc", "zRri", "zRrj", "zRrg", "zRrh", "zRrm", "zRrn", "zRrk", "zRrl", "zRrq", "zRrr", "zNuo", "zNup", "zNuq", "zNur", "zNus", "zNut", "zNuu", "zNuv", "zNug", "zNuh", "zNqX", "zNqW", "zNqV", "zNqU", "zNqT", "zNqS", "zNqR", "zNqQ", "zNqf", "zNqe", "zNkd", "zNkc", "zNkf", "zNke", "zNkZ", "zNkY", "zNkb", "zNka", "zNkV", "zNkU", "zNgk", "zNgl", "zNgm", "zNgn", "zNgg", "zNgh", "zNgi", "zNgj", "zNgs", "zNgt", "zN+Z", "zN+Y", "zN+b", "zN+a", "zN+d", "zN+c", "zN+f", "zN+e", "zN+R", "zN+Q", "zN5U", "zN5V", "zN5W", "zN5X", "zN5Q", "zN5R", "zN5S", "zN5T", "zN5c", "zN5d", "zN3u", "zN3v", "zN3s", "zN3t", "zN3q", "zN3r", "zN3o", "zN3p", "zN3m", "zN3n", "zNym", "zNyn", "zNyk", "zNyl", "zNyi", "zNyj", "zNyg", "zNyh", "zNyu", "zNyv", "zNN2", "zNN3", "zNN0", "zNN1", "zNNy", "zNNz", "zNNw", "zNNx", "zNN+", "zNN/", "zNKV", "zNKU", "zNKX", "zNKW", "zNKR", "zNKQ", "zNKT", "zNKS", "zNKd", "zNKc", "wzcU", "wzcV", "wzcW", "wzcX", "wzcQ", "wzcR", "wzcS", "wzcT", "wzcc", "wzcd", "wzZX", "wzZW", "wzZV", "wzZU", "wzZT", "wzZS", "wzZR", "wzZQ", "wzZf", "wzZe", "wzWn", "wzWm", "wzWl", "wzWk", "wzWj", "wzWi", "wzWh", "wzWg", "wzWv", "wzWu", "wzSm", "wzSn", "wzSk", "wzSl", "wzSi", "wzSj", "wzSg", "wzSh", "wzSu", "wzSv", "wzOC", "wzOD", "wzOA", "wzOB", "wzOG", "wzOH", "wzOE", "wzOF", "wzOK", "wzOL", "wzLg", "wzLh", "wzLi", "wzLj", "wzLk", "wzLl", "wzLm", "wzLn", "wzLo", "wzLp", "wzEH", "wzEG", "wzEF", "wzEE", "wzED", "wzEC", "wzEB", "wzEA", "wzEP", "wzEO", "wzAz", "wzAy", "wzAx", "wzAw", "wzA3", "wzA2", "wzA1", "wzA0", "wzA7", "wzA6", "wz/0", "wz/1", "wz/2", "wz/3", "wz/w", "wz/x", "wz/y", "wz/z", "wz/8", "wz/9", "wz6X", "wz6W", "wz6V", "wz6U", "wz6T", "wz6S", "wz6R", "wz6Q", "wz6f", "wz6e", "wgNl", "wgNk", "wgNn", "wgNm", "wgNh", "wgNg", "wgNj", "wgNi", "wgNt", "wgNs", "wgIp", "wgIo", "wgIr", "wgIq", "wgIt", "wgIs", "wgIv", "wgIu", "wgIh", "wgIg", "wgHR", "wgHQ", "wgHT", "wgHS", "wgHV", "wgHU", "wgHX", "wgHW", "wgHZ", "wgHY", "wgDF", "wgDE", "wgDH", "wgDG", "wgDB", "wgDA", "wgDD", "wgDC", "wgDN", "wgDM", "wgej", "wgei", "wgeh", "wgeg", "wgen", "wgem", "wgel", "wgek", "wger", "wgeq", "wgYA", "wgYB", "wgYC", "wgYD", "wgYE", "wgYF", "wgYG", "wgYH", "wgYI", "wgYJ", "wgVN", "wgVM", "wgVP", "wgVO", "wgVJ", "wgVI", "wgVL", "wgVK", "wgVF", "wgVE", "wgRS", "wgRT", "wgRQ", "wgRR", "wgRW", "wgRX", "wgRU", "wgRV", "wgRa", "wgRb", "wgsU", "wgsV", "wgsW", "wgsX", "wgsQ", "wgsR", "wgsS", "wgsT", "wgsc", "wgsd", "wgpW", "wgpX", "wgpU", "wgpV", "wgpS", "wgpT", "wgpQ", "wgpR", "wgpe", "wgpf", "ym7tJQ==", 
    "ym7tJA==", "ym7tJw==", "ym7tJg==", "ym7tIQ==", "ym7tIA==", "ym7tIw==", "ym7tIg==", "ym7tLQ==", "ym7tLA==", "ym7seQ==", "ym7seA==", "ym7sew==", "ym7seg==", "ym7sfQ==", "ym7sfA==", "ym7sfw==", "ym7sfg==", "ym7scQ==", "ym7scA==", "ym7vzA==", "ym7vzQ==", "ym7vzg==", "ym7vzw==", "ym7vyA==", "ym7vyQ==", "ym7vyg==", "ym7vyw==", "ym7vxA==", "ym7vxQ==", "ym7ulQ==", "ym7ulA==", "ym7ulw==", "ym7ulg==", "ym7ukQ==", "ym7ukA==", "ym7ukw==", "ym7ukg==", "ym7unQ==", "ym7unA==", "ym7pZw==", "ym7pZg==", "ym7pZQ==", "ym7pZA==", "ym7pYw==", "ym7pYg==", "ym7pYQ==", "ym7pYA==", "ym7pbw==", "ym7pbg==", "ym7oKw==", "ym7oKg==", "ym7oKQ==", "ym7oKA==", "ym7oLw==", "ym7oLg==", "ym7oLQ==", "ym7oLA==", "ym7oIw==", "ym7oIg==", "ym7rgw==", "ym7rgg==", "ym7rgQ==", "ym7rgA==", "ym7rhw==", "ym7rhg==", "ym7rhQ==", "ym7rhA==", "ym7riw==", "ym7rig==", "ym7qkw==", "ym7qkg==", "ym7qkQ==", "ym7qkA==", "ym7qlw==", "ym7qlg==", "ym7qlQ==", "ym7qlA==", "ym7qmw==", "ym7qmg==", "ym7l9Q==", "ym7l9A==", "ym7l9w==", "ym7l9g==", "ym7l8Q==", "ym7l8A==", "ym7l8w==", "ym7l8g==", "ym7l/Q==", "ym7l/A==", "ym7kBw==", "ym7kBg==", "ym7kBQ==", "ym7kBA==", "ym7kAw==", "ym7kAg==", "ym7kAQ==", "ym7kAA==", "ym7kDw==", "ym7kDg==", "ym9IKw==", "ym9IKg==", "ym9IKQ==", "ym9IKA==", "ym9ILw==", "ym9ILg==", "ym9ILQ==", "ym9ILA==", "ym9IIw==", "ym9IIg==", "ym9JwQ==", "ym9JwA==", "ym9Jww==", "ym9Jwg==", "ym9JxQ==", "ym9JxA==", "ym9Jxw==", "ym9Jxg==", "ym9JyQ==", "ym9JyA==", "ym9KUg==", "ym9KUw==", "ym9KUA==", "ym9KUQ==", "ym9KVg==", "ym9KVw==", "ym9KVA==", "ym9KVQ==", "ym9KWg==", "ym9KWw==", "ym9LJw==", "ym9LJg==", "ym9LJQ==", "ym9LJA==", "ym9LIw==", "ym9LIg==", "ym9LIQ==", "ym9LIA==", "ym9LLw==", "ym9LLg==", "ym9MDg==", "ym9MDw==", "ym9MDA==", "ym9MDQ==", "ym9MCg==", "ym9MCw==", "ym9MCA==", "ym9MCQ==", "ym9MBg==", "ym9MBw==", "ym9N1w==", "ym9N1g==", "ym9N1Q==", "ym9N1A==", "ym9N0w==", "ym9N0g==", "ym9N0Q==", "ym9N0A==", "ym9N3w==", "ym9N3g==", "ym9OUw==", "ym9OUg==", "ym9OUQ==", "ym9OUA==", "ym9OVw==", "ym9OVg==", "ym9OVQ==", "ym9OVA==", "ym9OWw==", "ym9OWg==", "ym9PDw==", "ym9PDg==", "ym9PDQ==", "ym9PDA==", "ym9PCw==", "ym9PCg==", "ym9PCQ==", "ym9PCA==", "ym9PBw==", "ym9PBg==", "ym9AdQ==", "ym9AdA==", "ym9Adw==", "ym9Adg==", "ym9AcQ==", "ym9AcA==", "ym9Acw==", "ym9Acg==", "ym9AfQ==", "ym9AfA==", "ym9BRg==", "ym9BRw==", "ym9BRA==", "ym9BRQ==", "ym9BQg==", "ym9BQw==", "ym9BQA==", "ym9BQQ==", "ym9BTg==", "ym9BTw==", "ymzFgA==", "ymzFgQ==", "ymzFgg==", "ymzFgw==", "ymzFhA==", "ymzFhQ==", "ymzFhg==", "ymzFhw==", "ymzFiA==", "ymzFiQ==", "ymzEtA==", "ymzEtQ==", "ymzEtg==", "ymzEtw==", "ymzEsA==", "ymzEsQ==", "ymzEsg==", "ymzEsw==", "ymzEvA==", "ymzEvQ==", "ymzHyg==", "ymzHyw==", "ymzHyA==", "ymzHyQ==", "ymzHzg==", "ymzHzw==", "ymzHzA==", "ymzHzQ==", "ymzHwg==", "ymzHww==", "ymzGCA==", "ymzGCQ==", "ymzGCg==", "ymzGCw==", "ymzGDA==", "ymzGDQ==", "ymzGDg==", "ymzGDw==", "ymzGAA==", "ymzGAQ==", "ymzBKg==", "ymzBKw==", "ymzBKA==", "ymzBKQ==", "ymzBLg==", "ymzBLw==", "ymzBLA==", "ymzBLQ==", "ymzBIg==", "ymzBIw==", "ymzAAQ==", "ymzAAA==", "ymzAAw==", "ymzAAg==", "ymzABQ==", "ymzABA==", "ymzABw==", "ymzABg==", "ymzACQ==", "ymzACA==", "ymzDfA==", "ymzDfQ==", "ymzDfg==", "ymzDfw==", "ymzDeA==", "ymzDeQ==", "ymzDeg==", "ymzDew==", "ymzDdA==", "ymzDdQ==", "ymzCxw==", "ymzCxg==", "ymzCxQ==", "ymzCxA==", "ymzCww==", "ymzCwg==", "ymzCwQ==", "ymzCwA==", "ymzCzw==", "ymzCzg==", "ymzN3w==", "ymzN3g==", "ymzN3Q==", "ymzN3A==", "ymzN2w==", "ymzN2g==", "ymzN2Q==", "ymzN2A==", "ymzN1w==", "ymzN1g==", "ymzMOg==", "ymzMOw==", "ymzMOA==", "ymzMOQ==", "ymzMPg==", "ymzMPw==", "ymzMPA==", "ymzMPQ==", "ymzMMg==", "ymzMMw==", "ym0B6Q==", "ym0B6A==", "ym0B6w==", "ym0B6g==", "ym0B7Q==", "ym0B7A==", "ym0B7w==", "ym0B7g==", "ym0B4Q==", "ym0B4A==", "ym0A1A==", "ym0A1Q==", "ym0A1g==", "ym0A1w==", "ym0A0A==", "ym0A0Q==", "ym0A0g==", "ym0A0w==", "ym0A3A==", "ym0A3Q==", "ym0DQw==", "ym0DQg==", "ym0DQQ==", "ym0DQA==", "ym0DRw==", "ym0DRg==", "ym0DRQ==", "ym0DRA==", "ym0DSw==", "ym0DSg==", "ym0Ccw==", "ym0Ccg==", "ym0CcQ==", "ym0CcA==", "ym0Cdw==", "ym0Cdg==", "ym0CdQ==", "ym0CdA==", "ym0Cew==", "ym0Ceg==", "ym0FwA==", "ym0FwQ==", "ym0Fwg==", "ym0Fww==", "ym0FxA==", "ym0FxQ==", "ym0Fxg==", "ym0Fxw==", "ym0FyA==", "ym0FyQ==", "ym0EPw==", "ym0EPg==", "ym0EPQ==", "ym0EPA==", "ym0EOw==", "ym0EOg==", "ym0EOQ==", "ym0EOA==", "ym0ENw==", "ym0ENg==", "ym0HKA==", "ym0HKQ==", "ym0HKg==", "ym0HKw==", "ym0HLA==", "ym0HLQ==", "ym0HLg==", "ym0HLw==", "ym0HIA==", "ym0HIQ==", "ym0GcQ==", "ym0GcA==", "ym0Gcw==", "ym0Gcg==", "ym0GdQ==", "ym0GdA==", "ym0Gdw==", "ym0Gdg==", "ym0GeQ==", "ym0GeA==", "ym0Jeg==", "ym0Jew==", "ym0JeA==", "ym0JeQ==", "ym0Jfg==", "ym0Jfw==", "ym0JfA==", "ym0JfQ==", "ym0Jcg==", "ym0Jcw==", "ym0Iuw==", "ym0Iug==", "ym0IuQ==", "ym0IuA==", "ym0Ivw==", "ym0Ivg==", "ym0IvQ==", "ym0IvA==", "ym0Isw==", "ym0Isg==", "ymr1hQ==", "ymr1hA==", "ymr1hw==", "ymr1hg==", "ymr1gQ==", "ymr1gA==", "ymr1gw==", "ymr1gg==", "ymr1jQ==", "ymr1jA==", "ymr0lg==", "ymr0lw==", "ymr0lA==", "ymr0lQ==", "ymr0kg==", "ymr0kw==", "ymr0kA==", "ymr0kQ==", "ymr0ng==", "ymr0nw==", "ymr3nw==", "ymr3ng==", "ymr3nQ==", "ymr3nA==", "ymr3mw==", "ymr3mg==", "ymr3mQ==", "ymr3mA==", "ymr3lw==", "ymr3lg==", "ymr2tQ==", "ymr2tA==", "ymr2tw==", "ymr2tg==", "ymr2sQ==", "ymr2sA==", "ymr2sw==", "ymr2sg==", "ymr2vQ==", "ymr2vA==", "ymrxsA==", "ymrxsQ==", "ymrxsg==", "ymrxsw==", "ymrxtA==", "ymrxtQ==", "ymrxtg==", "ymrxtw==", "ymrxuA==", "ymrxuQ==", "ymrw2Q==", "ymrw2A==", "ymrw2w==", "ymrw2g==", "ymrw3Q==", "ymrw3A==", "ymrw3w==", "ymrw3g==", "ymrw0Q==", "ymrw0A==", "ymrzoQ==", "ymrzoA==", "ymrzow==", "ymrzog==", "ymrzpQ==", "ymrzpA==", "ymrzpw==", "ymrzpg==", "ymrzqQ==", "ymrzqA==", "ymrysA==", "ymrysQ==", "ymrysg==", "ymrysw==", "ymrytA==", "ymrytQ==", "ymrytg==", "ymrytw==", "ymryuA==", "ymryuQ==", "ymr9hA==", "ymr9hQ==", "ymr9hg==", "ymr9hw==", "ymr9gA==", "ymr9gQ==", "ymr9gg==", "ymr9gw==", "ymr9jA==", "ymr9jQ==", "ymr8FA==", "ymr8FQ==", "ymr8Fg==", "ymr8Fw==", "ymr8EA==", "ymr8EQ==", "ymr8Eg==", "ymr8Ew==", "ymr8HA==", "ymr8HQ==", "ymu8zA==", "ymu8zQ==", "ymu8zg==", "ymu8zw==", "ymu8yA==", "ymu8yQ==", "ymu8yg==", "ymu8yw==", "ymu8xA==", "ymu8xQ==", "ymu9Qg==", "ymu9Qw==", "ymu9QA==", "ymu9QQ==", "ymu9Rg==", "ymu9Rw==", "ymu9RA==", "ymu9RQ==", "ymu9Sg==", "ymu9Sw==", "ymu+JA==", "ymu+JQ==", "ymu+Jg==", "ymu+Jw==", "ymu+IA==", "ymu+IQ==", "ymu+Ig==", "ymu+Iw==", "ymu+LA==", "ymu+LQ==", "ymu/4w==", "ymu/4g==", "ymu/4Q==", "ymu/4A==", "ymu/5w==", "ymu/5g==", "ymu/5Q==", "ymu/5A==", "ymu/6w==", "ymu/6g==", "ymu4pw==", "ymu4pg==", "ymu4pQ==", "ymu4pA==", "ymu4ow==", "ymu4og==", "ymu4oQ==", "ymu4oA==", "ymu4rw==", "ymu4rg==", "ymu5SA==", "ymu5SQ==", "ymu5Sg==", "ymu5Sw==", "ymu5TA==", "ymu5TQ==", "ymu5Tg==", "ymu5Tw==", "ymu5QA==", "ymu5QQ==", "ymu6KQ==", "ymu6KA==", "ymu6Kw==", "ymu6Kg==", "ymu6LQ==", "ymu6LA==", "ymu6Lw==", "ymu6Lg==", "ymu6IQ==", "ymu6IA==", "ymu7pQ==", "ymu7pA==", "ymu7pw==", "ymu7pg==", "ymu7oQ==", "ymu7oA==", "ymu7ow==", "ymu7og==", "ymu7rQ==", "ymu7rA==", "ymu0xA==", "ymu0xQ==", "ymu0xg==", "ymu0xw==", "ymu0wA==", "ymu0wQ==", "ymu0wg==", "ymu0ww==", "ymu0zA==", "ymu0zQ==", "ymu1pw==", "ymu1pg==", "ymu1pQ==", "ymu1pA==", "ymu1ow==", "ymu1og==", "ymu1oQ==", "ymu1oA==", "ymu1rw==", "ymu1rg==", "ymimGA==", "ymimGQ==", "ymimGg==", "ymimGw==", "ymimHA==", "ymimHQ==", "ymimHg==", "ymimHw==", "ymimEA==", "ymimEQ==", "yminsg==", "yminsw==", "yminsA==", "yminsQ==", "ymintg==", "ymintw==", "ymintA==", "ymintQ==", "yminug==", "yminuw==", "ymikCQ==", "ymikCA==", "ymikCw==", "ymikCg==", "ymikDQ==", "ymikDA==", "ymikDw==", "ymikDg==", "ymikAQ==", "ymikAA==", "ymilZA==", "ymilZQ==", "ymilZg==", "ymilZw==", "ymilYA==", "ymilYQ==", "ymilYg==", "ymilYw==", "ymilbA==", "ymilbQ==", "ymii3w==", "ymii3g==", "ymii3Q==", "ymii3A==", "ymii2w==", "ymii2g==", "ymii2Q==", "ymii2A==", "ymii1w==", "ymii1g==", "ymijgw==", "ymijgg==", "ymijgQ==", "ymijgA==", "ymijhw==", "ymijhg==", "ymijhQ==", "ymijhA==", "ymijiw==", "ymijig==", "ymigCA==", "ymigCQ==", "ymigCg==", "ymigCw==", "ymigDA==", "ymigDQ==", "ymigDg==", "ymigDw==", "ymigAA==", "ymigAQ==", "ymihzw==", "ymihzg==", "ymihzQ==", "ymihzA==", "ymihyw==", "ymihyg==", "ymihyQ==", "ymihyA==", "ymihxw==", "ymihxg==", "ymiurQ==", "ymiurA==", "ymiurw==", "ymiurg==", "ymiuqQ==", "ymiuqA==", "ymiuqw==", "ymiuqg==", "ymiupQ==", "ymiupA==", "ymivYA==", "ymivYQ==", "ymivYg==", "ymivYw==", "ymivZA==", "ymivZQ==", "ymivZg==", "ymivZw==", "ymivaA==", "ymivaQ==", "ymkkyg==", "ymkkyw==", "ymkkyA==", "ymkkyQ==", "ymkkzg==", "ymkkzw==", "ymkkzA==", "ymkkzQ==", "ymkkwg==", "ymkkww==", "ymklTw==", "ymklTg==", "ymklTQ==", "ymklTA==", "ymklSw==", "ymklSg==", "ymklSQ==", "ymklSA==", "ymklRw==", "ymklRg==", "ymkmlA==", "ymkmlQ==", "ymkmlg==", "ymkmlw==", "ymkmkA==", "ymkmkQ==", "ymkmkg==", "ymkmkw==", "ymkmnA==", "ymkmnQ==", "ymknDw==", "ymknDg==", "ymknDQ==", "ymknDA==", "ymknCw==", "ymknCg==", "ymknCQ==", "ymknCA==", "ymknBw==", "ymknBg==", "ymkgRQ==", "ymkgRA==", "ymkgRw==", "ymkgRg==", "ymkgQQ==", "ymkgQA==", "ymkgQw==", "ymkgQg==", "ymkgTQ==", "ymkgTA==", "ymkhHA==", "ymkhHQ==", "ymkhHg==", "ymkhHw==", "ymkhGA==", "ymkhGQ==", "ymkhGg==", "ymkhGw==", "ymkhFA==", "ymkhFQ==", "ymking==", "ymkinw==", "ymkinA==", "ymkinQ==", "ymkimg==", "ymkimw==", "ymkimA==", "ymkimQ==", "ymkilg==", "ymkilw==", "ymkjpg==", "ymkjpw==", "ymkjpA==", "ymkjpQ==", "ymkjog==", "ymkjow==", "ymkjoA==", "ymkjoQ==", "ymkjrg==", "ymkjrw==", "ymksQA==", "ymksQQ==", "ymksQg==", "ymksQw==", "ymksRA==", "ymksRQ==", "ymksRg==", "ymksRw==", "ymksSA==", "ymksSQ==", "ymktlw==", "ymktlg==", "ymktlQ==", "ymktlA==", "ymktkw==", "ymktkg==", "ymktkQ==", "ymktkA==", "ymktnw==", "ymktng==", "ymYHHA==", "ymYHHQ==", "ymYHHg==", "ymYHHw==", "ymYHGA==", "ymYHGQ==", "ymYHGg==", "ymYHGw==", "ymYHFA==", "ymYHFQ==", "ymYGzw==", "ymYGzg==", "ymYGzQ==", "ymYGzA==", "ymYGyw==", "ymYGyg==", "ymYGyQ==", "ymYGyA==", "ymYGxw==", "ymYGxg==", "ymYF7w==", "ymYF7g==", "ymYF7Q==", "ymYF7A==", "ymYF6w==", "ymYF6g==", "ymYF6Q==", "ymYF6A==", "ymYF5w==", "ymYF5g==", "ymYEyg==", "ymYEyw==", "ymYEyA==", "ymYEyQ==", "ymYEzg==", "ymYEzw==", "ymYEzA==", "ymYEzQ==", "ymYEwg==", "ymYEww==", "ymYDbg==", "ymYDbw==", "ymYDbA==", "ymYDbQ==", "ymYDag==", "ymYDaw==", "ymYDaA==", "ymYDaQ==", "ymYDZg==", "ymYDZw==", "ymYChg==", "ymYChw==", "ymYChA==", "ymYChQ==", "ymYCgg==", "ymYCgw==", "ymYCgA==", "ymYCgQ==", "ymYCjg==", "ymYCjw==", "ymYBgw==", "ymYBgg==", "ymYBgQ==", "ymYBgA==", "ymYBhw==", "ymYBhg==", "ymYBhQ==", "ymYBhA==", "ymYBiw==", "ymYBig==", "ymYA7Q==", "ymYA7A==", "ymYA7w==", "ymYA7g==", "ymYA6Q==", "ymYA6A==", "ymYA6w==", "ymYA6g==", "ymYA5Q==", "ymYA5A==", "ymYPHg==", "ymYPHw==", "ymYPHA==", "ymYPHQ==", "ymYPGg==", "ymYPGw==", "ymYPGA==", "ymYPGQ==", "ymYPFg==", "ymYPFw==", "ymYOeA==", "ymYOeQ==", "ymYOeg==", "ymYOew==", "ymYOfA==", "ymYOfQ==", "ymYOfg==", "ymYOfw==", "ymYOcA==", "ymYOcQ==", "ymcZHw==", "ymcZHg==", "ymcZHQ==", "ymcZHA==", "ymcZGw==", "ymcZGg==", "ymcZGQ==", "ymcZGA==", "ymcZFw==", "ymcZFg==", "ymcYww==", "ymcYwg==", "ymcYwQ==", "ymcYwA==", "ymcYxw==", "ymcYxg==", "ymcYxQ==", "ymcYxA==", "ymcYyw==", "ymcYyg==", "ymcb6g==", "ymcb6w==", "ymcb6A==", "ymcb6Q==", "ymcb7g==", "ymcb7w==", "ymcb7A==", "ymcb7Q==", "ymcb4g==", "ymcb4w==", "ymcaPw==", "ymcaPg==", "ymcaPQ==", "ymcaPA==", "ymcaOw==", "ymcaOg==", "ymcaOQ==", "ymcaOA==", "ymcaNw==", "ymcaNg==", "ymcdiw==", "ymcdig==", "ymcdiQ==", "ymcdiA==", "ymcdjw==", "ymcdjg==", "ymcdjQ==", "ymcdjA==", "ymcdgw==", "ymcdgg==", "ymccZQ==", "ymccZA==", "ymccZw==", "ymccZg==", "ymccYQ==", "ymccYA==", "ymccYw==", "ymccYg==", "ymccbQ==", "ymccbA==", "ymcf8w==", "ymcf8g==", "ymcf8Q==", "ymcf8A==", "ymcf9w==", "ymcf9g==", "ymcf9Q==", "ymcf9A==", "ymcf+w==", "ymcf+g==", "ymcerw==", "ymcerg==", "ymcerQ==", "ymcerA==", "ymceqw==", "ymceqg==", "ymceqQ==", "ymceqA==", "ymcepw==", "ymcepg==", "ymcR7A==", "ymcR7Q==", "ymcR7g==", "ymcR7w==", "ymcR6A==", "ymcR6Q==", "ymcR6g==", "ymcR6w==", "ymcR5A==", "ymcR5Q==", "ymcQMQ==", "ymcQMA==", "ymcQMw==", "ymcQMg==", "ymcQNQ==", "ymcQNA==", "ymcQNw==", "ymcQNg==", "ymcQOQ==", "ymcQOA==", "yXQ4xw=="
    };
    private String mAppID;

    API_GameLogic() {
    }

    private String createGameLogicJson(String str, String str2, int i, int i2, int i3, String str3, String[] strArr, String str4, String str5, String str6, LogUtil logUtil) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, str);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(ShareConstants.WEB_DIALOG_PARAM_ID, str2);
            jSONObject2.put("bidfloor", i);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("w", i2);
            jSONObject3.put("h", i3);
            jSONObject3.put("pos", 0);
            jSONObject2.put(AdMobAd.BANNER, jSONObject3);
            jSONArray.put(jSONObject2);
            jSONObject.put("imp", jSONArray);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("gender", str3);
            jSONObject.put("user", jSONObject4);
            if (strArr != null && strArr.length > 0) {
                JSONArray jSONArray2 = new JSONArray();
                for (String str7 : strArr) {
                    jSONArray2.put(str7);
                }
                if (jSONArray2.length() > 0) {
                    jSONObject.put(GETINFO_GAMELOGIC_PARAM_BADV, jSONArray2);
                }
            }
            jSONObject.put("tmax", 2000);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("idfa", str6);
            jSONObject5.put("ip", str4);
            jSONObject5.put("ua", str5);
            jSONObject.put(TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX, jSONObject5);
            JSONObject jSONObject6 = new JSONObject();
            jSONObject6.put(ShareConstants.WEB_DIALOG_PARAM_ID, this.mAppID);
            jSONObject.put("app", jSONObject6);
            return jSONObject.toString();
        } catch (JSONException e) {
            logUtil.debug_e(Constants.TAG_NAME, "JSONException");
            logUtil.debug_e(Constants.TAG_NAME, e);
            return BuildConfig.FLAVOR;
        }
    }

    private String getPriceCode(String str, String str2, LogUtil logUtil) {
        return ApiAccessUtil.getPriceCode_old(str, logUtil, str2).message;
    }

    private String searchJSONArrayString(String str, String str2) throws JSONException {
        JSONArray jSONArray = new JSONArray(str);
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = new JSONObject(jSONArray.getString(i));
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                if (str2.equals(itKeys.next())) {
                    return jSONObject.getString(str2);
                }
            }
        }
        return null;
    }

    @Override // jp.tjkapp.adfurikunsdk.API_Base
    public void getContent(API_Controller2.API_ResultParam aPI_ResultParam, String str, String str2, String str3, API_Controller2.API_CpntrolParam aPI_CpntrolParam, LogUtil logUtil, int i) throws Exception {
        String priceCode;
        if (AdInfo.getAdType(i) == 3) {
            aPI_ResultParam.err = -2;
            return;
        }
        if (aPI_CpntrolParam.idfa == null || aPI_CpntrolParam.idfa.length() <= 0) {
            aPI_ResultParam.err = -4;
            return;
        }
        this.mAppID = str;
        String strCreateUniqueID = ApiAccessUtil.createUniqueID(aPI_CpntrolParam.idfa);
        String strCreateUniqueID2 = ApiAccessUtil.createUniqueID(aPI_CpntrolParam.idfa);
        if (strCreateUniqueID.length() <= 0 || strCreateUniqueID2.length() <= 0) {
            aPI_ResultParam.err = -4;
            return;
        }
        if (str3 == null || str3.length() <= 0) {
            aPI_ResultParam.err = -7;
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str3);
            Iterator<String> itKeys = jSONObject.keys();
            int i2 = 10;
            int i3 = 320;
            int i4 = 50;
            String string = "O";
            String[] strArrSplit = null;
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (GETINFO_GAMELOGIC_PARAM_FLOOR_PRICE.equals(next)) {
                    i2 = Integer.parseInt(jSONObject.getString(next));
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "floor_price[" + i2 + "]");
                    }
                } else if (GETINFO_GAMELOGIC_PARAM_BANNER_WIDTH.equals(next)) {
                    i3 = Integer.parseInt(jSONObject.getString(next));
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "banner_w[" + i3 + "]");
                    }
                } else if (GETINFO_GAMELOGIC_PARAM_BANNER_HEIGHT.equals(next)) {
                    i4 = Integer.parseInt(jSONObject.getString(next));
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "banner_h[" + i4 + "]");
                    }
                } else if (GETINFO_GAMELOGIC_PARAM_GENDER.equals(next)) {
                    string = jSONObject.getString(next);
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "user_gender[" + string + "]");
                    }
                } else if (GETINFO_GAMELOGIC_PARAM_BADV.equals(next)) {
                    String string2 = jSONObject.getString(next);
                    if (Constants.DETAIL_LOG) {
                        logUtil.debug_i(Constants.TAG_NAME, "badv[" + string2 + "]");
                    }
                    if (string2 != null && string2.length() > 0) {
                        strArrSplit = string2.split(",");
                    }
                }
            }
            if (!"F".equals(string) && !"M".equals(string)) {
                string = "O";
            }
            ApiAccessUtil.WebAPIResult webAPIResultCallWebAPI = ApiAccessUtil.callWebAPI(GAMELOGIC_URL, logUtil, aPI_CpntrolParam.useragent, createGameLogicJson(strCreateUniqueID, strCreateUniqueID2, i2, i3, i4, string, strArrSplit, aPI_CpntrolParam.ipua.f3207ip, aPI_CpntrolParam.useragent, aPI_CpntrolParam.idfa, logUtil), BuildConfig.FLAVOR, false);
            if (webAPIResultCallWebAPI.return_code == 200) {
                if (webAPIResultCallWebAPI.message.length() > 0) {
                    try {
                        String strSearchJSONArrayString = searchJSONArrayString(new JSONObject(webAPIResultCallWebAPI.message).getString("seatbid"), "bid");
                        String strSearchJSONArrayString2 = searchJSONArrayString(strSearchJSONArrayString, "adm");
                        String strSearchJSONArrayString3 = searchJSONArrayString(strSearchJSONArrayString, TapjoyConstants.TJC_EVENT_IAP_PRICE);
                        if (strSearchJSONArrayString2 == null || strSearchJSONArrayString2.length() <= 0 || strSearchJSONArrayString3 == null || strSearchJSONArrayString3.length() <= 0) {
                            aPI_ResultParam.err = -7;
                        } else {
                            if (strSearchJSONArrayString3.indexOf(".") != -1) {
                                priceCode = getPriceCode(strSearchJSONArrayString3, aPI_CpntrolParam.useragent, logUtil);
                            } else {
                                int i5 = Integer.parseInt(strSearchJSONArrayString3);
                                priceCode = i5 < PRICE_LIST.length ? PRICE_LIST[i5] : getPriceCode(strSearchJSONArrayString3, aPI_CpntrolParam.useragent, logUtil);
                            }
                            if (priceCode == null || priceCode.length() <= 0) {
                                aPI_ResultParam.err = -4;
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append(GAMELOGIC_HTML1);
                                sb.append(i3);
                                sb.append(GAMELOGIC_HTML2);
                                sb.append(strSearchJSONArrayString2.replace("${AUCTION_PRICE}", priceCode));
                                sb.append(GAMELOGIC_HTML3);
                                aPI_ResultParam.html = sb.toString();
                                aPI_ResultParam.imp_price = strSearchJSONArrayString3;
                            }
                        }
                    } catch (JSONException e) {
                        logUtil.debug_e(Constants.TAG_NAME, "JSONException");
                        logUtil.debug_e(Constants.TAG_NAME, e);
                        aPI_ResultParam.err = -7;
                    }
                } else {
                    aPI_ResultParam.err = -7;
                }
            } else if (webAPIResultCallWebAPI.return_code == 204) {
                aPI_ResultParam.err = -4;
            }
            if (aPI_ResultParam.html.length() > 0 || aPI_ResultParam.err == -4) {
                return;
            }
            aPI_ResultParam.err = -7;
        } catch (JSONException e2) {
            logUtil.debug_e(Constants.TAG_NAME, "JSONException");
            logUtil.debug_e(Constants.TAG_NAME, e2);
            aPI_ResultParam.err = -7;
        }
    }
}
