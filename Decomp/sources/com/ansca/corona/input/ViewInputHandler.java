package com.ansca.corona.input;

import android.os.Build;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.ansca.corona.Controller;

public class ViewInputHandler extends InputHandler {
    private EventHandler fEventHandler;
    private View fView;

    private static class ApiLevel12 {

        private static class EventHandler extends EventHandler implements View.OnGenericMotionListener {
            public EventHandler(ViewInputHandler viewInputHandler) {
                super(viewInputHandler);
            }

            public boolean onGenericMotion(View view, MotionEvent motionEvent) {
                return getInputHandler().handle(motionEvent);
            }

            /* access modifiers changed from: protected */
            public void setViewListenersTo(EventHandler eventHandler) {
                super.setViewListenersTo(eventHandler);
                View view = getInputHandler().getView();
                if (view != null) {
                    view.setOnGenericMotionListener((EventHandler) eventHandler);
                }
            }
        }

        private ApiLevel12() {
        }

        public static EventHandler createEventHandlerWith(ViewInputHandler viewInputHandler) {
            return new EventHandler(viewInputHandler);
        }
    }

    private static class EventHandler implements View.OnKeyListener, View.OnTouchListener {
        private ViewInputHandler fInputHandler;

        public EventHandler(ViewInputHandler viewInputHandler) {
            if (viewInputHandler == null) {
                throw new NullPointerException();
            }
            this.fInputHandler = viewInputHandler;
        }

        public ViewInputHandler getInputHandler() {
            return this.fInputHandler;
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            return this.fInputHandler.handle(keyEvent);
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return this.fInputHandler.handle(motionEvent);
        }

        /* access modifiers changed from: protected */
        public void setViewListenersTo(EventHandler eventHandler) {
            View view = this.fInputHandler.getView();
            if (view != null) {
                view.setOnKeyListener(eventHandler);
                view.setOnTouchListener(eventHandler);
            }
        }

        public void subscribe() {
            setViewListenersTo(this);
        }

        public void unsubscribe() {
            setViewListenersTo((EventHandler) null);
        }
    }

    public ViewInputHandler(Controller controller) {
        super(controller);
        if (Build.VERSION.SDK_INT >= 12) {
            this.fEventHandler = ApiLevel12.createEventHandlerWith(this);
        } else {
            this.fEventHandler = new EventHandler(this);
        }
        this.fView = null;
    }

    public View getView() {
        return this.fView;
    }

    public void setView(View view) {
        if (view != this.fView) {
            if (this.fView != null) {
                this.fEventHandler.unsubscribe();
            }
            this.fView = view;
            if (this.fView != null) {
                this.fEventHandler.subscribe();
            }
        }
    }
}
