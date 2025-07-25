package com.ansca.corona.notifications;

import java.lang.reflect.Field;

public class NotificationType {
    public static final NotificationType LOCAL = new NotificationType("local");
    public static final NotificationType REMOTE = new NotificationType("remote");
    public static final NotificationType REMOTE_REGISTRATION = new NotificationType("remoteRegistration");
    private String fInvariantName;

    private NotificationType(String str) {
        this.fInvariantName = str;
    }

    public static NotificationType fromInvariantString(String str) {
        try {
            for (Field field : NotificationType.class.getDeclaredFields()) {
                if (field.getType().equals(NotificationType.class)) {
                    NotificationType notificationType = (NotificationType) field.get((Object) null);
                    if (notificationType.fInvariantName.equals(str)) {
                        return notificationType;
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
