package com.facebook.share.model;

import android.os.Parcel;
import java.util.ArrayList;

public final class GameRequestContent implements ShareModel {
    private final ActionType actionType;
    private final String data;
    private final Filters filters;
    private final String message;
    private final String objectId;
    private final ArrayList<String> suggestions;
    private final String title;
    private final String to;

    public enum ActionType {
        SEND,
        ASKFOR,
        TURN
    }

    public static class Builder implements ShareModelBuilder<GameRequestContent, Builder> {
        /* access modifiers changed from: private */
        public ActionType actionType;
        /* access modifiers changed from: private */
        public String data;
        /* access modifiers changed from: private */
        public Filters filters;
        /* access modifiers changed from: private */
        public String message;
        /* access modifiers changed from: private */
        public String objectId;
        /* access modifiers changed from: private */
        public ArrayList<String> suggestions;
        /* access modifiers changed from: private */
        public String title;
        /* access modifiers changed from: private */
        public String to;

        public GameRequestContent build() {
            return new GameRequestContent(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((GameRequestContent) parcel.readParcelable(GameRequestContent.class.getClassLoader()));
        }

        public Builder readFrom(GameRequestContent gameRequestContent) {
            return gameRequestContent == null ? this : setMessage(gameRequestContent.getMessage()).setTo(gameRequestContent.getTo()).setTitle(gameRequestContent.getTitle()).setData(gameRequestContent.getData()).setActionType(gameRequestContent.getActionType()).setObjectId(gameRequestContent.getObjectId()).setFilters(gameRequestContent.getFilters()).setSuggestions(gameRequestContent.getSuggestions());
        }

        public Builder setActionType(ActionType actionType2) {
            this.actionType = actionType2;
            return this;
        }

        public Builder setData(String str) {
            this.data = str;
            return this;
        }

        public Builder setFilters(Filters filters2) {
            this.filters = filters2;
            return this;
        }

        public Builder setMessage(String str) {
            this.message = str;
            return this;
        }

        public Builder setObjectId(String str) {
            this.objectId = str;
            return this;
        }

        public Builder setSuggestions(ArrayList<String> arrayList) {
            this.suggestions = arrayList;
            return this;
        }

        public Builder setTitle(String str) {
            this.title = str;
            return this;
        }

        public Builder setTo(String str) {
            this.to = str;
            return this;
        }
    }

    public enum Filters {
        APP_USERS,
        APP_NON_USERS
    }

    GameRequestContent(Parcel parcel) {
        this.message = parcel.readString();
        this.to = parcel.readString();
        this.title = parcel.readString();
        this.data = parcel.readString();
        this.actionType = ActionType.valueOf(parcel.readString());
        this.objectId = parcel.readString();
        this.filters = Filters.valueOf(parcel.readString());
        this.suggestions = new ArrayList<>();
        parcel.readStringList(this.suggestions);
    }

    private GameRequestContent(Builder builder) {
        this.message = builder.message;
        this.to = builder.to;
        this.title = builder.title;
        this.data = builder.data;
        this.actionType = builder.actionType;
        this.objectId = builder.objectId;
        this.filters = builder.filters;
        this.suggestions = builder.suggestions;
    }

    public int describeContents() {
        return 0;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public String getData() {
        return this.data;
    }

    public Filters getFilters() {
        return this.filters;
    }

    public String getMessage() {
        return this.message;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public ArrayList<String> getSuggestions() {
        return this.suggestions;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTo() {
        return this.to;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.message);
        parcel.writeString(this.to);
        parcel.writeString(this.title);
        parcel.writeString(this.data);
        parcel.writeString(getActionType().toString());
        parcel.writeString(getObjectId());
        parcel.writeString(getFilters().toString());
        parcel.writeStringList(getSuggestions());
    }
}
