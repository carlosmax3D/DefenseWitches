package jp.stargarage.g2metrics;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresPermission;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.tapjoy.TapjoyConstants;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class DeviceInfoLoader implements Runnable {
    private final IAsyncInfoLoadCallBack callBack;
    private final Context context;

    DeviceInfoLoader(Context context, IAsyncInfoLoadCallBack iAsyncInfoLoadCallBack) {
        this.context = context;
        this.callBack = iAsyncInfoLoadCallBack;
    }

    private String getIPAddress(boolean useIPv4) throws SocketException {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface networkInterface : Collections.list(interfaces)) {
                for (InetAddress address : Collections.list(networkInterface.getInetAddresses())) {
                    if (!address.isLoopbackAddress()) {
                        String hostAddress = address.getHostAddress();

                        boolean isIPv4 = hostAddress.indexOf(':') < 0;

                        if (useIPv4 && isIPv4) {
                            return hostAddress;
                        } else if (!useIPv4 && !isIPv4) {
                            // drop IPv6 zone suffix if present (e.g., %wlan0)
                            int delim = hostAddress.indexOf('%');
                            return delim < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, delim).toUpperCase();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    private String getMACAddress() {
        return ((WifiManager) this.context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress(); //TapjoyConstants.TJC_CONNECTION_TYPE_WIFI
    }

    private String getParamFromTelManager(TelephonyManager telephonyManager, String str, String str2) {
        try {
            return String.valueOf(telephonyManager.getClass().getMethod(str, new Class[0]).invoke(telephonyManager, new Object[0]));
        } catch (Exception e) {
            return str2;
        }
    }

    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    @Override // java.lang.Runnable
    public void run() {
        DeviceInfo deviceInfo = new DeviceInfo();
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceInfo.careerName = getParamFromTelManager(telephonyManager, "getSimOperatorName", BuildConfig.FLAVOR);
        String paramFromTelManager = getParamFromTelManager(telephonyManager, "getSimOperator", BuildConfig.FLAVOR);
        if (paramFromTelManager.isEmpty() || paramFromTelManager.length() < 3) {
            deviceInfo.mcc = BuildConfig.FLAVOR;
        } else {
            deviceInfo.mcc = paramFromTelManager.substring(0, 3);
        }
        if (paramFromTelManager.isEmpty() || paramFromTelManager.length() < 5) {
            deviceInfo.mnc = BuildConfig.FLAVOR;
        } else {
            deviceInfo.mnc = paramFromTelManager.substring(3, 5);
        }
        deviceInfo.isoCountryCode = getParamFromTelManager(telephonyManager, "getSimCountryIso", BuildConfig.FLAVOR);
        deviceInfo.phoneNumber = getParamFromTelManager(telephonyManager, "getLine1Number", BuildConfig.FLAVOR);
        deviceInfo.simSerialNumber = getParamFromTelManager(telephonyManager, "getSimSerialNumber", BuildConfig.FLAVOR);
        deviceInfo.simState = getParamFromTelManager(telephonyManager, "getSimState", BuildConfig.FLAVOR);
        try {
            @SuppressLint("MissingPermission") String voiceMailNumber = telephonyManager.getVoiceMailNumber();
            if (voiceMailNumber == null) {
                voiceMailNumber = BuildConfig.FLAVOR;
            }
            deviceInfo.voiceMailNumber = voiceMailNumber;
        } catch (Exception e) {
            e.printStackTrace();
            deviceInfo.voiceMailNumber = BuildConfig.FLAVOR;
        }
        deviceInfo.systemVersion = String.valueOf(Build.VERSION.SDK_INT);
        deviceInfo.systemVersionRelease = Build.VERSION.RELEASE;
        deviceInfo.model = Build.MODEL;
        deviceInfo.serialNumber = Build.SERIAL;
        deviceInfo.androidId = Settings.Secure.getString(this.context.getContentResolver(), "android_id"); //TapjoyConstants.TJC_ANDROID_ID
        deviceInfo.language = Locale.getDefault().toString();
        deviceInfo.timeZone = Time.getCurrentTimezone();
        deviceInfo.macAddress = getMACAddress();
        try {
            deviceInfo.ipv4 = getIPAddress(true);
            deviceInfo.ipv6 = getIPAddress(false);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        try {
            deviceInfo.advertisingIdentifier = AdvertisingIdClient.getAdvertisingIdInfo(this.context).getId();
        } catch (Exception e2) {
            e2.printStackTrace();
            deviceInfo.advertisingIdentifier = BuildConfig.FLAVOR;
        }
        Display defaultDisplay = ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        deviceInfo.resolution = String.format("{%s,%s}", Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels));
        this.callBack.onLoadComplete(deviceInfo);
    }
}
