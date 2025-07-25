package com.ansca.corona.events;

import com.ansca.corona.Controller;
import com.ansca.corona.events.MediaEvent;
import java.util.Iterator;
import java.util.LinkedList;

public class EventManager {
    private Controller myController;
    private LinkedList<Event> myEvents = new LinkedList<>();

    public EventManager(Controller controller) {
        this.myController = controller;
    }

    public void addEvent(Event event) {
        synchronized (this) {
            this.myEvents.add(event);
        }
        this.myController.requestEventRender();
    }

    public void loadEventSound(int i, String str) {
        this.myController.getMediaManager().loadEventSound(i, str);
    }

    public void loadSound(int i, String str) {
        this.myController.getMediaManager().loadSound(i, str);
    }

    public void pauseSound(int i) {
        addEvent(new MediaEvent(this.myController, i, MediaEvent.Event.PauseSound));
    }

    public void playSound(int i, String str, boolean z) {
        MediaEvent mediaEvent = new MediaEvent(this.myController, i, str, MediaEvent.Event.PlaySound);
        mediaEvent.setLooping(z);
        addEvent(mediaEvent);
    }

    public void playVideo(int i, String str) {
        addEvent(new MediaEvent(this.myController, i, str, MediaEvent.Event.PlayVideo));
    }

    public synchronized void removeAllEvents() {
        this.myEvents.clear();
    }

    public void resumeSound(int i) {
        addEvent(new MediaEvent(this.myController, i, MediaEvent.Event.ResumeSound));
    }

    public void sendEvents() {
        LinkedList linkedList;
        synchronized (this) {
            linkedList = (LinkedList) this.myEvents.clone();
            this.myEvents.clear();
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ((Event) it.next()).Send();
        }
    }

    public void stopSound(int i) {
        addEvent(new MediaEvent(this.myController, i, MediaEvent.Event.StopSound));
    }
}
