package com.tapjoy.mraid.controller;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.google.android.gms.drive.DriveFile;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.controller.Defines;
import com.tapjoy.mraid.view.MraidView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(14)
public class Utility extends Abstract {
    private static final String TAG = "MRAID Utility";
    private Assets mAssetController;
    private Display mDisplayController;
    private MraidLocation mLocationController;
    private Network mNetworkController;
    private MraidSensor mSensorController;

    public Utility(MraidView mraidView, Context context) {
        super(mraidView, context);
        this.mAssetController = new Assets(mraidView, context);
        this.mDisplayController = new Display(mraidView, context);
        this.mLocationController = new MraidLocation(mraidView, context);
        this.mNetworkController = new Network(mraidView, context);
        this.mSensorController = new MraidSensor(mraidView, context);
        mraidView.addJavascriptInterface(this.mAssetController, "MRAIDAssetsControllerBridge");
        mraidView.addJavascriptInterface(this.mDisplayController, "MRAIDDisplayControllerBridge");
        mraidView.addJavascriptInterface(this.mLocationController, "MRAIDLocationControllerBridge");
        mraidView.addJavascriptInterface(this.mNetworkController, "MRAIDNetworkControllerBridge");
        mraidView.addJavascriptInterface(this.mSensorController, "MRAIDSensorControllerBridge");
    }

    /* access modifiers changed from: private */
    public void addCalendarEvent(int i, String str, String str2, String str3) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        long parseLong = Long.parseLong(str);
        ContentValues contentValues = new ContentValues();
        contentValues.put("calendar_id", Integer.valueOf(i));
        contentValues.put("title", str2);
        contentValues.put("description", str3);
        contentValues.put("dtstart", Long.valueOf(parseLong));
        contentValues.put("hasAlarm", 1);
        contentValues.put("dtend", Long.valueOf(parseLong + 3600000));
        Uri insert = Integer.parseInt(Build.VERSION.SDK) == 8 ? contentResolver.insert(Uri.parse("content://com.android.calendar/events"), contentValues) : contentResolver.insert(Uri.parse("content://calendar/events"), contentValues);
        if (insert != null) {
            long parseLong2 = Long.parseLong(insert.getLastPathSegment());
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("event_id", Long.valueOf(parseLong2));
            contentValues2.put(TJAdUnitConstants.String.METHOD, 1);
            contentValues2.put("minutes", 15);
            if (Integer.parseInt(Build.VERSION.SDK) == 8) {
                contentResolver.insert(Uri.parse("content://com.android.calendar/reminders"), contentValues2);
            } else {
                contentResolver.insert(Uri.parse("content://calendar/reminders"), contentValues2);
            }
        }
        Toast.makeText(this.mContext, "Event added to calendar", 0).show();
    }

    private String createTelUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return "tel:" + str;
    }

    private String getSupports() {
        String str = "supports: [ 'level-1', 'level-2', 'screen', 'orientation', 'network'";
        if (this.mLocationController.allowLocationServices() && (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0)) {
            str = str + ", 'location'";
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.SEND_SMS") == 0) {
            str = str + ", 'sms'";
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CALL_PHONE") == 0) {
            str = str + ", 'phone'";
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_CALENDAR") == 0 && this.mContext.checkCallingOrSelfPermission("android.permission.READ_CALENDAR") == 0 && Build.VERSION.SDK_INT >= 14) {
            str = str + ", 'calendar'";
        }
        String str2 = (((str + ", 'video'") + ", 'audio'") + ", 'map'") + ", 'email' ]";
        TapjoyLog.d(TAG, "getSupports: " + str2);
        return str2;
    }

    @JavascriptInterface
    public void activate(String str) {
        TapjoyLog.d(TAG, "activate: " + str);
        if (str.equalsIgnoreCase(Defines.Events.NETWORK_CHANGE)) {
            this.mNetworkController.startNetworkListener();
        } else if (this.mLocationController.allowLocationServices() && str.equalsIgnoreCase(Defines.Events.LOCATION_CHANGE)) {
            this.mLocationController.startLocationListener();
        } else if (str.equalsIgnoreCase(Defines.Events.SHAKE)) {
            this.mSensorController.startShakeListener();
        } else if (str.equalsIgnoreCase(Defines.Events.TILT_CHANGE)) {
            this.mSensorController.startTiltListener();
        } else if (str.equalsIgnoreCase(Defines.Events.HEADING_CHANGE)) {
            this.mSensorController.startHeadingListener();
        } else if (str.equalsIgnoreCase(Defines.Events.ORIENTATION_CHANGE)) {
            this.mDisplayController.startConfigurationListener();
        }
    }

    public String copyTextFromJarIntoAssetDir(String str, String str2) {
        return this.mAssetController.copyTextFromJarIntoAssetDir(str, str2);
    }

    @JavascriptInterface
    public void createEvent(String str, String str2, String str3) {
        TapjoyLog.d(TAG, "createEvent: date: " + str + " title: " + str2 + " body: " + str3);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String[] strArr = {"_id", "displayName", "_sync_account"};
        Cursor query = Integer.parseInt(Build.VERSION.SDK) == 8 ? contentResolver.query(Uri.parse("content://com.android.calendar/calendars"), strArr, (String) null, (String[]) null, (String) null) : contentResolver.query(Uri.parse("content://calendar/calendars"), strArr, (String) null, (String[]) null, (String) null);
        if (query == null || (query != null && !query.moveToFirst())) {
            Toast.makeText(this.mContext, "No calendar account found", 1).show();
            if (query != null) {
                query.close();
                return;
            }
            return;
        }
        if (query.getCount() == 1) {
            addCalendarEvent(query.getInt(0), str, str2, str3);
        } else {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < query.getCount(); i++) {
                HashMap hashMap = new HashMap();
                hashMap.put("ID", query.getString(0));
                hashMap.put("NAME", query.getString(1));
                hashMap.put("EMAILID", query.getString(2));
                arrayList.add(hashMap);
                query.moveToNext();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle("Choose Calendar to save event to");
            final ArrayList arrayList2 = arrayList;
            final String str4 = str;
            final String str5 = str2;
            final String str6 = str3;
            builder.setSingleChoiceItems(new SimpleAdapter(this.mContext, arrayList, 17367053, new String[]{"NAME", "EMAILID"}, new int[]{16908308, 16908309}), -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Utility.this.addCalendarEvent(Integer.parseInt((String) ((Map) arrayList2.get(i)).get("ID")), str4, str5, str6);
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }
        query.close();
    }

    @JavascriptInterface
    public void deactivate(String str) {
        TapjoyLog.d(TAG, "deactivate: " + str);
        if (str.equalsIgnoreCase(Defines.Events.NETWORK_CHANGE)) {
            this.mNetworkController.stopNetworkListener();
        } else if (str.equalsIgnoreCase(Defines.Events.LOCATION_CHANGE)) {
            this.mLocationController.stopAllListeners();
        } else if (str.equalsIgnoreCase(Defines.Events.SHAKE)) {
            this.mSensorController.stopShakeListener();
        } else if (str.equalsIgnoreCase(Defines.Events.TILT_CHANGE)) {
            this.mSensorController.stopTiltListener();
        } else if (str.equalsIgnoreCase(Defines.Events.HEADING_CHANGE)) {
            this.mSensorController.stopHeadingListener();
        } else if (str.equalsIgnoreCase(Defines.Events.ORIENTATION_CHANGE)) {
            this.mDisplayController.stopConfigurationListener();
        }
    }

    public void deleteOldAds() {
        this.mAssetController.deleteOldAds();
    }

    public void init(float f) {
        String str = "window.mraidview.fireChangeEvent({ state: 'default', network: '" + this.mNetworkController.getNetwork() + "'," + " size: " + this.mDisplayController.getSize() + "," + " placement: " + "'" + this.mMraidView.getPlacementType() + "'," + " maxSize: " + this.mDisplayController.getMaxSize() + ",expandProperties: " + this.mDisplayController.getMaxSize() + "," + " screenSize: " + this.mDisplayController.getScreenSize() + "," + " defaultPosition: { x:" + ((int) (((float) this.mMraidView.getLeft()) / f)) + ", y: " + ((int) (((float) this.mMraidView.getTop()) / f)) + ", width: " + ((int) (((float) this.mMraidView.getWidth()) / f)) + ", height: " + ((int) (((float) this.mMraidView.getHeight()) / f)) + " }," + " orientation:" + this.mDisplayController.getOrientation() + "," + getSupports() + ",viewable:true });";
        Log.d(TAG, "init: injection: " + str);
        this.mMraidView.injectMraidJavaScript(str);
        ready();
    }

    @JavascriptInterface
    public void makeCall(String str) {
        TapjoyLog.d(TAG, "makeCall: number: " + str);
        String createTelUrl = createTelUrl(str);
        if (createTelUrl == null) {
            this.mMraidView.raiseError("Bad Phone Number", "makeCall");
            return;
        }
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse(createTelUrl.toString()));
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        this.mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void mraidCreateEvent(String str) {
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        if (Build.VERSION.SDK_INT >= 14) {
            Calendar instance = Calendar.getInstance();
            Calendar instance2 = Calendar.getInstance();
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject(TJAdUnitConstants.String.VIDEO_START);
                JSONObject optJSONObject = jSONObject.optJSONObject("end");
                if (optJSONObject == null) {
                    optJSONObject = jSONObject2;
                }
                str2 = jSONObject.getString("description");
                str3 = jSONObject.optString("location");
                str4 = jSONObject.optString("summary");
                str5 = jSONObject.optString("status");
                instance.set(jSONObject2.getInt("year"), jSONObject2.getInt("month"), jSONObject2.getInt("day"), jSONObject2.getInt("hour"), jSONObject2.getInt("min"));
                instance2.set(optJSONObject.getInt("year"), optJSONObject.getInt("month"), optJSONObject.getInt("day"), optJSONObject.getInt("hour"), optJSONObject.getInt("min"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mMraidView.getContext().startActivity(new Intent("android.intent.action.INSERT").setData(CalendarContract.Events.CONTENT_URI).putExtra("beginTime", instance.getTimeInMillis()).putExtra("endTime", instance2.getTimeInMillis()).putExtra("title", str4).putExtra("description", str2).putExtra("eventLocation", str3).putExtra("eventStatus", str5));
        }
    }

    public void ready() {
        this.mMraidView.injectMraidJavaScript("mraid.setState(\"" + this.mMraidView.getState() + "\");");
        this.mMraidView.injectMraidJavaScript("mraidview.fireReadyEvent();");
    }

    @JavascriptInterface
    public void sendMail(String str, String str2, String str3) {
        TapjoyLog.d(TAG, "sendMail: recipient: " + str + " subject: " + str2 + " body: " + str3);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("plain/text");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{str});
        intent.putExtra("android.intent.extra.SUBJECT", str2);
        intent.putExtra("android.intent.extra.TEXT", str3);
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        this.mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void sendSMS(String str, String str2) {
        TapjoyLog.d(TAG, "sendSMS: recipient: " + str + " body: " + str2);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.putExtra("address", str);
        intent.putExtra("sms_body", str2);
        intent.setType("vnd.android-dir/mms-sms");
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        this.mContext.startActivity(intent);
    }

    public void setMaxSize(int i, int i2) {
        this.mDisplayController.setMaxSize(i, i2);
    }

    @JavascriptInterface
    public void showAlert(String str) {
        TapjoyLog.e(TAG, str);
    }

    public void stopAllListeners() {
        try {
            this.mAssetController.stopAllListeners();
            this.mDisplayController.stopAllListeners();
            this.mLocationController.stopAllListeners();
            this.mNetworkController.stopAllListeners();
            this.mSensorController.stopAllListeners();
        } catch (Exception e) {
        }
    }

    public String writeToDiskWrap(InputStream inputStream, String str, boolean z, String str2, String str3) throws IllegalStateException, IOException {
        return this.mAssetController.writeToDiskWrap(inputStream, str, z, str2, str3);
    }
}
