package com.ansca.corona;

import com.ansca.corona.events.Event;
import com.ansca.corona.events.EventManager;
import com.naef.jnlua.LuaState;

public class CoronaRuntimeTaskDispatcher {
    private CoronaRuntime fRuntime;

    private static class TaskEvent extends Event {
        private CoronaRuntime fCoronaRuntime;
        private CoronaRuntimeTask fTask;

        public TaskEvent(CoronaRuntimeTask coronaRuntimeTask, CoronaRuntime coronaRuntime) {
            if (coronaRuntimeTask == null) {
                throw new NullPointerException();
            }
            this.fTask = coronaRuntimeTask;
            this.fCoronaRuntime = coronaRuntime;
        }

        public void Send() {
            if (!this.fCoronaRuntime.wasDisposed()) {
                this.fTask.executeUsing(this.fCoronaRuntime);
            }
        }
    }

    public CoronaRuntimeTaskDispatcher(CoronaRuntime coronaRuntime) {
        this.fRuntime = coronaRuntime;
    }

    public CoronaRuntimeTaskDispatcher(LuaState luaState) {
        this.fRuntime = null;
        if (luaState != null) {
            this.fRuntime = CoronaRuntimeProvider.getRuntimeByLuaState(luaState);
        }
    }

    public boolean isRuntimeAvailable() {
        if (this.fRuntime == null) {
            return false;
        }
        return this.fRuntime.wasNotDisposed();
    }

    public boolean isRuntimeUnavailable() {
        return !isRuntimeAvailable();
    }

    public void send(CoronaRuntimeTask coronaRuntimeTask) {
        EventManager eventManager;
        if (coronaRuntimeTask == null) {
            throw new NullPointerException();
        } else if (!isRuntimeUnavailable() && (eventManager = this.fRuntime.getController().getEventManager()) != null) {
            eventManager.addEvent(new TaskEvent(coronaRuntimeTask, this.fRuntime));
        }
    }
}
