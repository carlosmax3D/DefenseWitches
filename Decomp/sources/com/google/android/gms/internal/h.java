package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;

public class h {
    private String dK = "googleads.g.doubleclick.net";
    private String dL = "/pagead/ads";
    private String[] dM = {".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private d dN;
    private final c dO = new c();

    public h(d dVar) {
        this.dN = dVar;
    }

    private Uri a(Uri uri, Context context, String str, boolean z) throws i {
        try {
            if (uri.getQueryParameter("ms") != null) {
                throw new i("Query parameter already exists: ms");
            }
            return a(uri, "ms", z ? this.dN.a(context, str) : this.dN.a(context));
        } catch (UnsupportedOperationException e) {
            throw new i("Provided Uri is not in a valid state");
        }
    }

    private Uri a(Uri uri, String str, String str2) throws UnsupportedOperationException {
        String uri2 = uri.toString();
        int indexOf = uri2.indexOf("&adurl");
        if (indexOf == -1) {
            indexOf = uri2.indexOf("?adurl");
        }
        return indexOf != -1 ? Uri.parse(uri2.substring(0, indexOf + 1) + str + "=" + str2 + "&" + uri2.substring(indexOf + 1)) : uri.buildUpon().appendQueryParameter(str, str2).build();
    }

    public Uri a(Uri uri, Context context) throws i {
        try {
            return a(uri, context, uri.getQueryParameter("ai"), true);
        } catch (UnsupportedOperationException e) {
            throw new i("Provided Uri is not in a valid state");
        }
    }

    public void a(MotionEvent motionEvent) {
        this.dN.a(motionEvent);
    }

    public boolean a(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            String host = uri.getHost();
            for (String endsWith : this.dM) {
                if (host.endsWith(endsWith)) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public d g() {
        return this.dN;
    }
}
