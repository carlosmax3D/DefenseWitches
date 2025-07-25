package com.tapjoy;

public interface TapjoySpendPointsNotifier {
    void getSpendPointsResponse(String str, int i);

    void getSpendPointsResponseFailed(String str);
}
