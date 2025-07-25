package com.tapjoy;

public interface TJEventCallback {
    void contentDidDisappear(TJEvent tJEvent);

    void contentDidShow(TJEvent tJEvent);

    void contentIsReady(TJEvent tJEvent, int i);

    void didRequestAction(TJEvent tJEvent, TJEventRequest tJEventRequest);

    void sendEventCompleted(TJEvent tJEvent, boolean z);

    void sendEventFail(TJEvent tJEvent, TJError tJError);
}
