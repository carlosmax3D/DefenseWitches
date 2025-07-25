package com.ansca.corona.notifications;

public abstract class NotificationSettings implements Cloneable {
    public NotificationSettings clone() {
        try {
            return (NotificationSettings) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public abstract int getId();

    public abstract void setId(int i);
}
