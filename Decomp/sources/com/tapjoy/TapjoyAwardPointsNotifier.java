package com.tapjoy;

public interface TapjoyAwardPointsNotifier {
    void getAwardPointsResponse(String str, int i);

    void getAwardPointsResponseFailed(String str);
}
