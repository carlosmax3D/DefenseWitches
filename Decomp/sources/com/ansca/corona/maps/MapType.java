package com.ansca.corona.maps;

import java.lang.reflect.Field;

public class MapType {
    public static final MapType HYBRID = new MapType("hybrid");
    public static final MapType SATELLITE = new MapType("satellite");
    public static final MapType STANDARD = new MapType("standard");
    private String fInvariantName;

    private MapType(String str) {
        this.fInvariantName = str;
    }

    public static MapType fromInvariantString(String str) {
        try {
            for (Field field : MapType.class.getDeclaredFields()) {
                if (field.getType().equals(MapType.class)) {
                    MapType mapType = (MapType) field.get((Object) null);
                    if (mapType.fInvariantName.equals(str)) {
                        return mapType;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String toInvariantString() {
        return this.fInvariantName;
    }
}
