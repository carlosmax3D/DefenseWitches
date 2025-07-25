package com.tapjoy;

public interface TapjoyViewNotifier {
    void viewDidClose(int i);

    void viewDidOpen(int i);

    void viewWillClose(int i);

    void viewWillOpen(int i);
}
