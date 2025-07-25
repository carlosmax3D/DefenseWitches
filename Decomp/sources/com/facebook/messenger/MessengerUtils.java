package com.facebook.messenger;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import bolts.AppLinks;
import com.facebook.FacebookSdk;
import com.facebook.internal.ServerProtocol;
import com.facebook.messenger.MessengerThreadParams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessengerUtils {
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String EXTRA_APP_ID = "com.facebook.orca.extra.APPLICATION_ID";
    public static final String EXTRA_EXTERNAL_URI = "com.facebook.orca.extra.EXTERNAL_URI";
    public static final String EXTRA_IS_COMPOSE = "com.facebook.orca.extra.IS_COMPOSE";
    public static final String EXTRA_IS_REPLY = "com.facebook.orca.extra.IS_REPLY";
    public static final String EXTRA_METADATA = "com.facebook.orca.extra.METADATA";
    public static final String EXTRA_PARTICIPANTS = "com.facebook.orca.extra.PARTICIPANTS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.orca.extra.PROTOCOL_VERSION";
    public static final String EXTRA_REPLY_TOKEN_KEY = "com.facebook.orca.extra.REPLY_TOKEN";
    public static final String EXTRA_THREAD_TOKEN_KEY = "com.facebook.orca.extra.THREAD_TOKEN";
    public static final String ORCA_THREAD_CATEGORY_20150314 = "com.facebook.orca.category.PLATFORM_THREAD_20150314";
    public static final String PACKAGE_NAME = "com.facebook.orca";
    public static final int PROTOCOL_VERSION_20150314 = 20150314;
    private static final String TAG = "MessengerUtils";

    public static void finishShareToMessenger(Activity activity, ShareToMessengerParams shareToMessengerParams) {
        Intent intent = activity.getIntent();
        Set<String> categories = intent.getCategories();
        if (categories == null) {
            activity.setResult(0, (Intent) null);
            activity.finish();
        } else if (categories.contains(ORCA_THREAD_CATEGORY_20150314)) {
            Bundle appLinkExtras = AppLinks.getAppLinkExtras(intent);
            Intent intent2 = new Intent();
            if (categories.contains(ORCA_THREAD_CATEGORY_20150314)) {
                intent2.putExtra(EXTRA_PROTOCOL_VERSION, PROTOCOL_VERSION_20150314);
                intent2.putExtra(EXTRA_THREAD_TOKEN_KEY, appLinkExtras.getString(EXTRA_THREAD_TOKEN_KEY));
                intent2.setDataAndType(shareToMessengerParams.uri, shareToMessengerParams.mimeType);
                intent2.setFlags(1);
                intent2.putExtra(EXTRA_APP_ID, FacebookSdk.getApplicationId());
                intent2.putExtra(EXTRA_METADATA, shareToMessengerParams.metaData);
                intent2.putExtra(EXTRA_EXTERNAL_URI, shareToMessengerParams.externalUri);
                activity.setResult(-1, intent2);
                activity.finish();
                return;
            }
            throw new RuntimeException();
        } else {
            activity.setResult(0, (Intent) null);
            activity.finish();
        }
    }

    private static Set<Integer> getAllAvailableProtocolVersions(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        HashSet hashSet = new HashSet();
        Cursor query = contentResolver.query(Uri.parse("content://com.facebook.orca.provider.MessengerPlatformProvider/versions"), new String[]{ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION}, (String) null, (String[]) null, (String) null);
        if (query != null) {
            try {
                int columnIndex = query.getColumnIndex(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION);
                while (query.moveToNext()) {
                    hashSet.add(Integer.valueOf(query.getInt(columnIndex)));
                }
            } finally {
                query.close();
            }
        }
        return hashSet;
    }

    public static MessengerThreadParams getMessengerThreadParamsForIntent(Intent intent) {
        Set<String> categories = intent.getCategories();
        if (categories == null || !categories.contains(ORCA_THREAD_CATEGORY_20150314)) {
            return null;
        }
        Bundle appLinkExtras = AppLinks.getAppLinkExtras(intent);
        String string = appLinkExtras.getString(EXTRA_THREAD_TOKEN_KEY);
        String string2 = appLinkExtras.getString(EXTRA_METADATA);
        String string3 = appLinkExtras.getString(EXTRA_PARTICIPANTS);
        boolean z = appLinkExtras.getBoolean(EXTRA_IS_REPLY);
        boolean z2 = appLinkExtras.getBoolean(EXTRA_IS_COMPOSE);
        MessengerThreadParams.Origin origin = MessengerThreadParams.Origin.UNKNOWN;
        if (z) {
            origin = MessengerThreadParams.Origin.REPLY_FLOW;
        } else if (z2) {
            origin = MessengerThreadParams.Origin.COMPOSE_FLOW;
        }
        return new MessengerThreadParams(origin, string, string2, parseParticipants(string3));
    }

    public static boolean hasMessengerInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void openMessengerInPlayStore(Context context) {
        try {
            startViewUri(context, "market://details?id=com.facebook.orca");
        } catch (ActivityNotFoundException e) {
            startViewUri(context, "http://play.google.com/store/apps/details?id=com.facebook.orca");
        }
    }

    private static List<String> parseParticipants(String str) {
        if (str == null || str.length() == 0) {
            return Collections.emptyList();
        }
        String[] split = str.split(",");
        ArrayList arrayList = new ArrayList();
        for (String trim : split) {
            arrayList.add(trim.trim());
        }
        return arrayList;
    }

    public static void shareToMessenger(Activity activity, int i, ShareToMessengerParams shareToMessengerParams) {
        if (!hasMessengerInstalled(activity)) {
            openMessengerInPlayStore(activity);
        } else if (getAllAvailableProtocolVersions(activity).contains(Integer.valueOf(PROTOCOL_VERSION_20150314))) {
            shareToMessenger20150314(activity, i, shareToMessengerParams);
        } else {
            openMessengerInPlayStore(activity);
        }
    }

    private static void shareToMessenger20150314(Activity activity, int i, ShareToMessengerParams shareToMessengerParams) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setFlags(1);
            intent.setPackage(PACKAGE_NAME);
            intent.putExtra("android.intent.extra.STREAM", shareToMessengerParams.uri);
            intent.setType(shareToMessengerParams.mimeType);
            String applicationId = FacebookSdk.getApplicationId();
            if (applicationId != null) {
                intent.putExtra(EXTRA_PROTOCOL_VERSION, PROTOCOL_VERSION_20150314);
                intent.putExtra(EXTRA_APP_ID, applicationId);
                intent.putExtra(EXTRA_METADATA, shareToMessengerParams.metaData);
                intent.putExtra(EXTRA_EXTERNAL_URI, shareToMessengerParams.externalUri);
            }
            activity.startActivityForResult(intent, i);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME));
        }
    }

    private static void startViewUri(Context context, String str) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }
}
