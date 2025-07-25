package com.ansca.corona.events;

import com.ansca.corona.Controller;

public class MediaEvent extends Event {
    Controller myController;
    Event myEvent;
    boolean myLooping = false;
    int myMediaId;
    String myMediaName;

    enum Event {
        LoadSound,
        PlaySound,
        StopSound,
        PauseSound,
        ResumeSound,
        PlayVideo,
        SoundEnded,
        VideoEnded
    }

    MediaEvent(Controller controller, int i, Event event) {
        this.myEvent = event;
        this.myMediaId = i;
        this.myController = controller;
    }

    MediaEvent(Controller controller, int i, String str, Event event) {
        this.myEvent = event;
        this.myMediaId = i;
        this.myMediaName = str;
        this.myController = controller;
    }

    MediaEvent(Controller controller, String str, Event event) {
        this.myEvent = event;
        this.myMediaName = str;
        this.myController = controller;
    }

    public void Send() {
        switch (this.myEvent) {
            case PlaySound:
                this.myController.getMediaManager().playMedia(this.myMediaId, this.myLooping);
                return;
            case StopSound:
                this.myController.getMediaManager().stopMedia(this.myMediaId);
                return;
            case PauseSound:
                this.myController.getMediaManager().pauseMedia(this.myMediaId);
                return;
            case ResumeSound:
                this.myController.getMediaManager().resumeMedia(this.myMediaId);
                return;
            case PlayVideo:
                this.myController.getMediaManager().playVideo(this.myMediaId, this.myMediaName, true);
                return;
            default:
                return;
        }
    }

    public void setLooping(boolean z) {
        this.myLooping = z;
    }
}
