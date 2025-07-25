package com.tapjoy.mraid.controller;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.tapjoy.mraid.util.NavigationStringEnum;
import com.tapjoy.mraid.util.TransitionStringEnum;
import com.tapjoy.mraid.view.MraidView;
import java.lang.reflect.Field;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Abstract {
    private static final String BOOLEAN_TYPE = "boolean";
    public static final String EXIT = "exit";
    private static final String FLOAT_TYPE = "float";
    public static final String FULL_SCREEN = "fullscreen";
    private static final String INT_TYPE = "int";
    private static final String NAVIGATION_TYPE = "class com.tapjoy.mraid.util.NavigationStringEnum";
    private static final String STRING_TYPE = "class java.lang.String";
    public static final String STYLE_NORMAL = "normal";
    private static final String TRANSITION_TYPE = "class com.tapjoy.mraid.util.TransitionStringEnum";
    protected Context mContext;
    protected MraidView mMraidView;

    public static class Dimensions extends ReflectedParcelable {
        public static final Parcelable.Creator<Dimensions> CREATOR = new Parcelable.Creator<Dimensions>() {
            public Dimensions createFromParcel(Parcel parcel) {
                return new Dimensions(parcel);
            }

            public Dimensions[] newArray(int i) {
                return new Dimensions[i];
            }
        };
        public int height;
        public int width;
        public int x;
        public int y;

        public Dimensions() {
            this.x = -1;
            this.y = -1;
            this.width = -1;
            this.height = -1;
        }

        protected Dimensions(Parcel parcel) {
            super(parcel);
        }
    }

    public static class PlayerProperties extends ReflectedParcelable {
        public static final Parcelable.Creator<PlayerProperties> CREATOR = new Parcelable.Creator<PlayerProperties>() {
            public PlayerProperties createFromParcel(Parcel parcel) {
                return new PlayerProperties(parcel);
            }

            public PlayerProperties[] newArray(int i) {
                return new PlayerProperties[i];
            }
        };
        public boolean audioMuted;
        public boolean autoPlay;
        public boolean doLoop;
        public boolean inline;
        public boolean showControl;
        public String startStyle;
        public String stopStyle;

        public PlayerProperties() {
            this.showControl = true;
            this.autoPlay = true;
            this.audioMuted = false;
            this.doLoop = false;
            this.stopStyle = Abstract.STYLE_NORMAL;
            this.startStyle = Abstract.STYLE_NORMAL;
            this.inline = false;
        }

        public PlayerProperties(Parcel parcel) {
            super(parcel);
        }

        public boolean doLoop() {
            return this.doLoop;
        }

        public boolean doMute() {
            return this.audioMuted;
        }

        public boolean exitOnComplete() {
            return this.stopStyle.equalsIgnoreCase(Abstract.EXIT);
        }

        public boolean isAutoPlay() {
            return this.autoPlay;
        }

        public boolean isFullScreen() {
            return this.startStyle.equalsIgnoreCase(Abstract.FULL_SCREEN);
        }

        public void muteAudio() {
            this.audioMuted = true;
        }

        public void setProperties(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, String str, String str2) {
            this.autoPlay = z2;
            this.showControl = z3;
            this.doLoop = z5;
            this.audioMuted = z;
            this.startStyle = str;
            this.stopStyle = str2;
            this.inline = z4;
        }

        public void setStopStyle(String str) {
            this.stopStyle = str;
        }

        public boolean showControl() {
            return this.showControl;
        }
    }

    public static class Properties extends ReflectedParcelable {
        public static final Parcelable.Creator<Properties> CREATOR = new Parcelable.Creator<Properties>() {
            public Properties createFromParcel(Parcel parcel) {
                return new Properties(parcel);
            }

            public Properties[] newArray(int i) {
                return new Properties[i];
            }
        };
        public int backgroundColor;
        public float backgroundOpacity;
        public boolean isModal;
        public boolean lockOrientation;
        public boolean useBackground;
        public boolean useCustomClose;

        public Properties() {
            this.useBackground = false;
            this.backgroundColor = 0;
            this.backgroundOpacity = 0.0f;
            this.isModal = false;
            this.lockOrientation = false;
            this.useCustomClose = false;
        }

        protected Properties(Parcel parcel) {
            super(parcel);
        }
    }

    public static class ReflectedParcelable implements Parcelable {
        public ReflectedParcelable() {
        }

        protected ReflectedParcelable(Parcel parcel) {
            Field[] declaredFields = getClass().getDeclaredFields();
            int i = 0;
            while (i < declaredFields.length) {
                try {
                    Field field = declaredFields[i];
                    Class<?> type = field.getType();
                    if (type.isEnum()) {
                        String cls = type.toString();
                        if (cls.equals(Abstract.NAVIGATION_TYPE)) {
                            field.set(this, NavigationStringEnum.fromString(parcel.readString()));
                        } else if (cls.equals(Abstract.TRANSITION_TYPE)) {
                            field.set(this, TransitionStringEnum.fromString(parcel.readString()));
                        }
                    } else if (!(field.get(this) instanceof Parcelable.Creator)) {
                        field.set(this, parcel.readValue((ClassLoader) null));
                    }
                    i++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return;
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            Field[] declaredFields = getClass().getDeclaredFields();
            int i2 = 0;
            while (i2 < declaredFields.length) {
                try {
                    Field field = declaredFields[i2];
                    Class<?> type = field.getType();
                    if (type.isEnum()) {
                        String cls = type.toString();
                        if (cls.equals(Abstract.NAVIGATION_TYPE)) {
                            parcel.writeString(((NavigationStringEnum) field.get(this)).getText());
                        } else if (cls.equals(Abstract.TRANSITION_TYPE)) {
                            parcel.writeString(((TransitionStringEnum) field.get(this)).getText());
                        }
                    } else {
                        Object obj = field.get(this);
                        if (!(obj instanceof Parcelable.Creator)) {
                            parcel.writeValue(obj);
                        }
                    }
                    i2++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return;
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }
    }

    public Abstract(MraidView mraidView, Context context) {
        this.mMraidView = mraidView;
        this.mContext = context;
    }

    protected static Object getFromJSON(JSONObject jSONObject, Class<?> cls) throws IllegalAccessException, InstantiationException, NumberFormatException, NullPointerException {
        int parseInt;
        Field[] declaredFields = cls.getDeclaredFields();
        Object newInstance = cls.newInstance();
        int i = 0;
        while (i < declaredFields.length) {
            Field field = declaredFields[i];
            String replace = field.getName().replace('_', '-');
            String obj = field.getType().toString();
            try {
                if (obj.equals(INT_TYPE)) {
                    String lowerCase = jSONObject.getString(replace).toLowerCase();
                    if (lowerCase.startsWith("#")) {
                        parseInt = -1;
                        try {
                            parseInt = lowerCase.startsWith("#0x") ? Integer.decode(lowerCase.substring(1)).intValue() : Integer.parseInt(lowerCase.substring(1), 16);
                        } catch (NumberFormatException e) {
                        }
                    } else {
                        parseInt = Integer.parseInt(lowerCase);
                    }
                    field.set(newInstance, Integer.valueOf(parseInt));
                    i++;
                } else {
                    if (obj.equals(STRING_TYPE)) {
                        field.set(newInstance, jSONObject.getString(replace));
                    } else if (obj.equals(BOOLEAN_TYPE)) {
                        field.set(newInstance, Boolean.valueOf(jSONObject.getBoolean(replace)));
                    } else if (obj.equals(FLOAT_TYPE)) {
                        field.set(newInstance, Float.valueOf(Float.parseFloat(jSONObject.getString(replace))));
                    } else if (obj.equals(NAVIGATION_TYPE)) {
                        field.set(newInstance, NavigationStringEnum.fromString(jSONObject.getString(replace)));
                    } else if (obj.equals(TRANSITION_TYPE)) {
                        field.set(newInstance, TransitionStringEnum.fromString(jSONObject.getString(replace)));
                    }
                    i++;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return newInstance;
    }

    public abstract void stopAllListeners();
}
