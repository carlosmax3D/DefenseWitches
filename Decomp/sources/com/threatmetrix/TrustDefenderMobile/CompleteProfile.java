package com.threatmetrix.TrustDefenderMobile;

import java.util.concurrent.CountDownLatch;

class CompleteProfile implements Runnable {
    private CountDownLatch latch = null;
    private TrustDefenderMobile profile = null;

    public CompleteProfile(TrustDefenderMobile trustDefenderMobile, CountDownLatch countDownLatch) {
        this.profile = trustDefenderMobile;
        this.latch = countDownLatch;
    }

    public void run() {
        this.profile.completeProfileRequest();
        if (this.latch != null) {
            this.latch.countDown();
        }
    }
}
