package com.tapjoy;

import java.util.LinkedHashMap;
import java.util.Map;

public class TJEventManager {
    private static Map<String, TJEvent> eventsTable = new LinkedHashMap<String, TJEvent>() {
        private static final long serialVersionUID = 5794666578643304105L;

        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Map.Entry<String, TJEvent> entry) {
            return size() > 20;
        }
    };

    public static TJEvent get(String str) {
        return eventsTable.get(str);
    }

    public static void put(String str, TJEvent tJEvent) {
        eventsTable.put(str, tJEvent);
    }

    public static void remove(String str) {
        eventsTable.remove(str);
    }
}
