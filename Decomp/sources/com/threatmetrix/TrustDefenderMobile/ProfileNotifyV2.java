package com.threatmetrix.TrustDefenderMobile;

import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;

public interface ProfileNotifyV2 extends ProfileNotifyBase {
    void complete(TrustDefenderMobile.THMStatusCode tHMStatusCode);
}
