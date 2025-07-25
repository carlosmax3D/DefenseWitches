package com.tapjoy;

import java.util.Hashtable;

public class TapjoyConnectFlag {
    public static final Hashtable<String, Object> CONNECT_FLAG_DEFAULTS = new Hashtable<String, Object>() {
        {
            put(TapjoyConnectFlag.HOST_URL, TapjoyConfig.TJC_SERVICE_URL);
            put(TapjoyConnectFlag.EVENT_URL, TapjoyConfig.TJC_EVENT_SERVICE_URL);
        }
    };
    public static final String DEBUG_DEVICE_ID = "debug_device_id";
    public static final String DEBUG_HOST_URL = "debug_host_url";
    public static final String DISABLE_ADVERTISING_ID_CHECK = "disable_advertising_id_check";
    public static final String DISABLE_PERSISTENT_IDS = "disable_persistent_ids";
    public static final String DISABLE_VIDEOS = "disable_videos";
    public static final String ENABLE_LOGGING = "enable_logging";
    public static final String EVENT_URL = "TJC_EVENT_SERVICE_URL";
    public static final String[] FLAG_ARRAY = {DEBUG_DEVICE_ID, DEBUG_HOST_URL, SHA_2_UDID, "store_name", DISABLE_VIDEOS, VIDEO_CACHE_COUNT, ENABLE_LOGGING, "user_id", HOST_URL, EVENT_URL};
    public static final String HOST_URL = "TJC_SERVICE_URL";
    public static final String SEGMENTATION_PARAMS = "segmentation_params";
    public static final String SHA_2_UDID = "sha_2_udid";
    public static final String[] STORE_ARRAY = {STORE_GFAN, STORE_SKT};
    public static final String STORE_GFAN = "gfan";
    public static final String STORE_NAME = "store_name";
    public static final String STORE_SKT = "skt";
    public static final String USER_ID = "user_id";
    public static final String VIDEO_CACHE_COUNT = "video_cache_count";
}
