package jp.co.voyagegroup.android.fluct.jar.sdk;

import android.content.Context;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.IOException;
import jp.co.voyagegroup.android.fluct.jar.util.Log;
import jp.stargarage.g2metrics.BuildConfig;

public class FluctPreferences {
    private static final String TAG = "FluctPreferences";
    private static String mAdid = null;
    private static Boolean mInitialized = false;
    private static FluctPreferences mInstance = new FluctPreferences();
    private static String mNotAdTrackingParam = null;

    private FluctPreferences() {
        Log.d(TAG, TAG);
    }

    public static FluctPreferences getInstance() {
        return mInstance;
    }

    public void dispose() {
        Log.d(TAG, "dispose : ");
        mInitialized = false;
    }

    public String getAdid() {
        Log.d(TAG, "getAdid : mAdid is " + mAdid);
        return mAdid;
    }

    public String getNotAdTrackingParam() {
        Log.d(TAG, "getNotAdTrackingParam : mNotAdTrackingParam is " + mNotAdTrackingParam);
        return mNotAdTrackingParam;
    }

    public void makeFluctPreferences(Context context) {
        Log.d(TAG, "makeFluctPreferences : mInitialized? " + mInitialized);
        if (!mInitialized.booleanValue()) {
            AdvertisingIdClient.Info info = null;
            int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
            switch (isGooglePlayServicesAvailable) {
                case 0:
                    try {
                        info = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    } catch (IOException e) {
                        mAdid = BuildConfig.FLAVOR;
                        mNotAdTrackingParam = BuildConfig.FLAVOR;
                    } catch (GooglePlayServicesRepairableException e2) {
                        mAdid = BuildConfig.FLAVOR;
                        mNotAdTrackingParam = BuildConfig.FLAVOR;
                    } catch (GooglePlayServicesNotAvailableException e3) {
                        mAdid = BuildConfig.FLAVOR;
                        mNotAdTrackingParam = BuildConfig.FLAVOR;
                    }
                    if (info == null) {
                        mAdid = BuildConfig.FLAVOR;
                        mNotAdTrackingParam = BuildConfig.FLAVOR;
                        break;
                    } else {
                        mNotAdTrackingParam = info.isLimitAdTrackingEnabled() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO;
                        if (!mNotAdTrackingParam.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                            mAdid = BuildConfig.FLAVOR;
                            break;
                        } else {
                            mAdid = info.getId();
                            break;
                        }
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 1500:
                    Log.v(TAG, "makeFluctPreferences : Google Play Services connection error occerred. resultCode:" + isGooglePlayServicesAvailable);
                    mAdid = BuildConfig.FLAVOR;
                    mNotAdTrackingParam = BuildConfig.FLAVOR;
                    break;
                default:
                    Log.v(TAG, "makeFluctPreferences : Google Play Services unknown error occerred. resultCode:" + isGooglePlayServicesAvailable);
                    mAdid = BuildConfig.FLAVOR;
                    mNotAdTrackingParam = BuildConfig.FLAVOR;
                    break;
            }
            Log.v(TAG, "makeFluctPreferences : ADID is " + mAdid + " mNotAdTrackingParam is " + mNotAdTrackingParam);
            mInitialized = true;
        }
    }
}
