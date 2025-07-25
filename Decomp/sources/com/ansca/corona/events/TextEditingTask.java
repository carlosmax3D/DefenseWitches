package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class TextEditingTask implements CoronaRuntimeTask {
    private int myEditTextId;
    private String newCharacters;
    private String newString;
    private int numDeleted;
    private String oldString;
    private int startPos;

    public TextEditingTask(int i, int i2, int i3, String str, String str2, String str3) {
        this.myEditTextId = i;
        this.startPos = i2;
        this.numDeleted = i3;
        this.newCharacters = str;
        this.oldString = str2;
        this.newString = str3;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.textEditingEvent(coronaRuntime, this.myEditTextId, this.startPos, this.numDeleted, this.newCharacters, this.oldString, this.newString);
    }
}
