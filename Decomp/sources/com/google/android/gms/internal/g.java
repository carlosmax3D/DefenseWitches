package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

public class g extends f {

    class a {
        private String dH;
        private boolean dI;

        public a(String str, boolean z) {
            this.dH = str;
            this.dI = z;
        }

        public String getId() {
            return this.dH;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.dI;
        }
    }

    private g(Context context, j jVar, k kVar) {
        super(context, jVar, kVar);
    }

    public static g a(String str, Context context) {
        a aVar = new a();
        a(str, context, aVar);
        return new g(context, aVar, new m(239));
    }

    /* access modifiers changed from: protected */
    public void b(Context context) {
        long j = 1;
        super.b(context);
        try {
            a f = f(context);
            try {
                if (!f.isLimitAdTrackingEnabled()) {
                    j = 0;
                }
                a(28, j);
                String id = f.getId();
                if (id != null) {
                    a(30, id);
                }
            } catch (IOException e) {
            }
        } catch (GooglePlayServicesNotAvailableException e2) {
        } catch (IOException e3) {
            a(28, 1);
        }
    }

    /* access modifiers changed from: package-private */
    public a f(Context context) throws IOException, GooglePlayServicesNotAvailableException {
        String str;
        int i = 0;
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            String id = advertisingIdInfo.getId();
            if (id == null || !id.matches("^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")) {
                str = id;
            } else {
                byte[] bArr = new byte[16];
                int i2 = 0;
                while (i < id.length()) {
                    if (i == 8 || i == 13 || i == 18 || i == 23) {
                        i++;
                    }
                    bArr[i2] = (byte) ((Character.digit(id.charAt(i), 16) << 4) + Character.digit(id.charAt(i + 1), 16));
                    i2++;
                    i += 2;
                }
                str = this.dw.a(bArr, true);
            }
            return new a(str, advertisingIdInfo.isLimitAdTrackingEnabled());
        } catch (GooglePlayServicesRepairableException e) {
            throw new IOException(e);
        }
    }
}
