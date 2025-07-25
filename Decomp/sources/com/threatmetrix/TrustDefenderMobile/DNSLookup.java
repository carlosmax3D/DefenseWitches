package com.threatmetrix.TrustDefenderMobile;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSLookup implements Runnable {
    private static final String TAG = DNSLookup.class.getSimpleName();
    private String domain;
    private InetAddress inetAddr;

    public DNSLookup(String str) {
        this.domain = str;
    }

    public synchronized InetAddress get() {
        return this.inetAddr;
    }

    public void run() {
        try {
            String str = TAG;
            InetAddress byName = InetAddress.getByName(this.domain);
            String str2 = TAG;
            set(byName);
        } catch (UnknownHostException e) {
            String str3 = TAG;
        }
    }

    public synchronized void set(InetAddress inetAddress) {
        this.inetAddr = inetAddress;
    }
}
