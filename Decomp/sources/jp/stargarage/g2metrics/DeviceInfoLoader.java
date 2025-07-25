package jp.stargarage.g2metrics;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.tapjoy.TapjoyConstants;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;
import org.apache.http.conn.util.InetAddressUtils;

final class DeviceInfoLoader implements Runnable {
    private final IAsyncInfoLoadCallBack callBack;
    private final Context context;

    DeviceInfoLoader(Context context2, IAsyncInfoLoadCallBack iAsyncInfoLoadCallBack) {
        this.context = context2;
        this.callBack = iAsyncInfoLoadCallBack;
    }

    private String getIPAddress(boolean z) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress()) {
                            String hostAddress = nextElement.getHostAddress();
                            if (InetAddressUtils.isIPv4Address(nextElement.getHostAddress())) {
                                if (z) {
                                    return hostAddress;
                                }
                            } else if (!z) {
                                int indexOf = hostAddress.indexOf(37);
                                return indexOf >= 0 ? hostAddress.substring(0, indexOf) : hostAddress;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BuildConfig.FLAVOR;
    }

    private String getMACAddress() {
        return ((WifiManager) this.context.getSystemService(TapjoyConstants.TJC_CONNECTION_TYPE_WIFI)).getConnectionInfo().getMacAddress();
    }

    private String getParamFromTelManager(TelephonyManager telephonyManager, String str, String str2) {
        try {
            return String.valueOf(telephonyManager.getClass().getMethod(str, new Class[0]).invoke(telephonyManager, new Object[0]));
        } catch (Exception e) {
            return str2;
        }
    }

    public void run() {
        DeviceInfo deviceInfo = new DeviceInfo();
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService("phone");
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
            String voiceMailNumber = telephonyManager.getVoiceMailNumber();
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
        deviceInfo.androidId = Settings.Secure.getString(this.context.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
        deviceInfo.language = Locale.getDefault().toString();
        deviceInfo.timeZone = Time.getCurrentTimezone();
        deviceInfo.macAddress = getMACAddress();
        deviceInfo.ipv4 = getIPAddress(true);
        deviceInfo.ipv6 = getIPAddress(false);
        try {
            deviceInfo.advertisingIdentifier = AdvertisingIdClient.getAdvertisingIdInfo(this.context).getId();
        } catch (Exception e2) {
            e2.printStackTrace();
            deviceInfo.advertisingIdentifier = BuildConfig.FLAVOR;
        }
        Display defaultDisplay = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        deviceInfo.resolution = String.format("{%s,%s}", new Object[]{Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels)});
        this.callBack.onLoadComplete(deviceInfo);
    }
}
