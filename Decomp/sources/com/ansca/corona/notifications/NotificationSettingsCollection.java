package com.ansca.corona.notifications;

import com.ansca.corona.notifications.NotificationSettings;
import java.util.ArrayList;
import java.util.Iterator;

public class NotificationSettingsCollection<T extends NotificationSettings> implements Iterable<T> {
    private ArrayList<T> fCollection = new ArrayList<>();

    public void add(T t) {
        if (t != null && this.fCollection.indexOf(t) < 0) {
            this.fCollection.add(t);
        }
    }

    public void clear() {
        this.fCollection.clear();
    }

    public T getById(int i) {
        Iterator<T> it = this.fCollection.iterator();
        while (it.hasNext()) {
            T t = (NotificationSettings) it.next();
            if (t != null && t.getId() == i) {
                return t;
            }
        }
        return null;
    }

    public T getByIndex(int i) {
        if (i < 0 || i >= this.fCollection.size()) {
            return null;
        }
        return (NotificationSettings) this.fCollection.get(i);
    }

    public int indexOf(T t) {
        if (t == null) {
            return -1;
        }
        return this.fCollection.indexOf(t);
    }

    public Iterator<T> iterator() {
        return this.fCollection.iterator();
    }

    public boolean remove(T t) {
        if (t == null) {
            return false;
        }
        return this.fCollection.remove(t);
    }

    public boolean removeById(int i) {
        boolean z = false;
        while (remove(getById(i))) {
            z = true;
        }
        return z;
    }

    public int size() {
        return this.fCollection.size();
    }
}
