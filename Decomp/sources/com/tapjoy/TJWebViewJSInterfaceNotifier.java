package com.tapjoy;

import org.json.JSONObject;

public interface TJWebViewJSInterfaceNotifier {
    void dispatchMethod(String str, JSONObject jSONObject);
}
