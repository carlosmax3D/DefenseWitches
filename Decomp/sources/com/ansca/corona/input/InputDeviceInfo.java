package com.ansca.corona.input;

import android.os.Build;
import android.os.Vibrator;
import android.view.InputDevice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jp.stargarage.g2metrics.BuildConfig;

public class InputDeviceInfo implements Cloneable {
    /* access modifiers changed from: private */
    public int fAndroidDeviceId;
    /* access modifiers changed from: private */
    public AxisInfoCollection fAxisCollection;
    /* access modifiers changed from: private */
    public boolean fCanVibrate;
    /* access modifiers changed from: private */
    public InputDeviceType fDeviceType;
    /* access modifiers changed from: private */
    public String fDisplayName;
    /* access modifiers changed from: private */
    public InputDeviceTypeSet fInputSources;
    /* access modifiers changed from: private */
    public String fPermanentStringId;
    /* access modifiers changed from: private */
    public int fPlayerNumber;
    /* access modifiers changed from: private */
    public String fProductName;
    private ReadOnlyAxisInfoCollection fReadOnlyAxisCollection;
    private ReadOnlyInputDeviceTypeSet fReadOnlyInputSources;

    private static class ApiLevel12 {
        private ApiLevel12() {
        }

        public static AxisInfoCollection getAxisInfoFrom(InputDevice inputDevice, InputDeviceType inputDeviceType) {
            AxisInfoCollection axisInfoCollection = new AxisInfoCollection();
            if (!(inputDevice == null || inputDeviceType == null)) {
                AxisSettings axisSettings = new AxisSettings();
                for (InputDevice.MotionRange next : inputDevice.getMotionRanges()) {
                    if (next.getSource() == inputDeviceType.toAndroidSourceId()) {
                        AxisType fromAndroidIntegerId = AxisType.fromAndroidIntegerId(next.getAxis());
                        boolean z = true;
                        if (fromAndroidIntegerId == AxisType.VERTICAL_SCROLL || fromAndroidIntegerId == AxisType.HORIZONTAL_SCROLL) {
                            z = false;
                        } else if (inputDeviceType == InputDeviceType.TRACKBALL && (fromAndroidIntegerId == AxisType.X || fromAndroidIntegerId == AxisType.Y)) {
                            z = false;
                        }
                        axisSettings.setType(AxisType.fromAndroidIntegerId(next.getAxis()));
                        axisSettings.setMinValue(next.getMin());
                        axisSettings.setMaxValue(next.getMax());
                        axisSettings.setAccuracy(next.getFuzz());
                        axisSettings.setIsProvidingAbsoluteValues(z);
                        axisInfoCollection.add(AxisInfo.from(axisSettings));
                    }
                }
            }
            return axisInfoCollection;
        }

        public static InputDeviceTypeSet getAxisSourcesFrom(InputDevice inputDevice) {
            InputDeviceTypeSet inputDeviceTypeSet = new InputDeviceTypeSet();
            if (inputDevice != null) {
                for (InputDevice.MotionRange source : inputDevice.getMotionRanges()) {
                    inputDeviceTypeSet.add(InputDeviceType.fromAndroidSourceId(source.getSource()));
                }
            }
            return inputDeviceTypeSet;
        }
    }

    private static class ApiLevel16 {
        private ApiLevel16() {
        }

        public static String getPermanentStringIdFrom(InputDevice inputDevice) {
            String str = null;
            if (inputDevice != null) {
                str = inputDevice.getDescriptor();
            }
            if (str == null || str.length() > 0) {
                return str;
            }
            return null;
        }

        public static boolean isVibrationSupportedFor(InputDevice inputDevice) {
            Vibrator vibrator;
            if (inputDevice == null || (vibrator = inputDevice.getVibrator()) == null) {
                return false;
            }
            return vibrator.hasVibrator();
        }
    }

    private static class ApiLevel19 {
        private ApiLevel19() {
        }

        public static int getControllerNumberFrom(InputDevice inputDevice) {
            if (inputDevice == null) {
                return 0;
            }
            return inputDevice.getControllerNumber();
        }
    }

    private static class ApiLevel9 {
        private ApiLevel9() {
        }

        private static InputDeviceInfo createDeviceInfoFor(InputDevice inputDevice, InputDeviceType inputDeviceType) {
            if (inputDevice == null || inputDeviceType == null) {
                return null;
            }
            InputDeviceInfo inputDeviceInfo = new InputDeviceInfo();
            int unused = inputDeviceInfo.fAndroidDeviceId = inputDevice.getId();
            InputDeviceType unused2 = inputDeviceInfo.fDeviceType = inputDeviceType;
            inputDeviceInfo.fInputSources.add(inputDeviceType);
            String unused3 = inputDeviceInfo.fProductName = inputDevice.getName();
            String unused4 = inputDeviceInfo.fDisplayName = inputDevice.getName();
            if (Build.VERSION.SDK_INT >= 16) {
                String unused5 = inputDeviceInfo.fPermanentStringId = ApiLevel16.getPermanentStringIdFrom(inputDevice);
                boolean unused6 = inputDeviceInfo.fCanVibrate = ApiLevel16.isVibrationSupportedFor(inputDevice);
            }
            if (Build.VERSION.SDK_INT < 19) {
                return inputDeviceInfo;
            }
            int unused7 = inputDeviceInfo.fPlayerNumber = ApiLevel19.getControllerNumberFrom(inputDevice);
            return inputDeviceInfo;
        }

        public static int[] fetchAndroidDeviceIds() {
            int[] deviceIds = InputDevice.getDeviceIds();
            return deviceIds == null ? new int[0] : deviceIds;
        }

        public static List<InputDeviceInfo> fetchDeviceInfoFromAndroidDeviceId(int i) {
            InputDevice device;
            InputDeviceTypeSet inputDeviceTypeSet;
            ArrayList arrayList = new ArrayList();
            if (i > 0 && (device = InputDevice.getDevice(i)) != null) {
                int sources = device.getSources();
                if (sources != 0) {
                    InputDeviceTypeSet collectionFromAndroidSourcesBitField = InputDeviceType.collectionFromAndroidSourcesBitField(sources);
                    if (Build.VERSION.SDK_INT >= 12) {
                        inputDeviceTypeSet = ApiLevel12.getAxisSourcesFrom(device);
                        collectionFromAndroidSourcesBitField.addAll(inputDeviceTypeSet);
                    } else {
                        inputDeviceTypeSet = new InputDeviceTypeSet();
                    }
                    InputDeviceType inputDeviceType = null;
                    if (inputDeviceTypeSet.size() > 1) {
                        inputDeviceType = InputDeviceType.fromAndroidSourceId(inputDeviceTypeSet.toAndroidSourcesBitField());
                        inputDeviceTypeSet.remove(inputDeviceType);
                    } else if (inputDeviceTypeSet.size() == 1) {
                        inputDeviceType = inputDeviceTypeSet.iterator().next();
                        inputDeviceTypeSet.clear();
                    }
                    if (inputDeviceType == null) {
                        inputDeviceType = InputDeviceType.fromAndroidSourceId(sources);
                    }
                    collectionFromAndroidSourcesBitField.removeAll(inputDeviceTypeSet);
                    if (collectionFromAndroidSourcesBitField.contains(InputDeviceType.KEYBOARD) && device.getKeyboardType() == 2) {
                        collectionFromAndroidSourcesBitField.remove(InputDeviceType.KEYBOARD);
                        arrayList.add(createDeviceInfoFor(device, InputDeviceType.KEYBOARD));
                    }
                    InputDeviceInfo createDeviceInfoFor = createDeviceInfoFor(device, inputDeviceType);
                    createDeviceInfoFor.fInputSources.addAll(collectionFromAndroidSourcesBitField);
                    if (Build.VERSION.SDK_INT >= 12) {
                        Iterator<InputDeviceType> it = collectionFromAndroidSourcesBitField.iterator();
                        while (it.hasNext()) {
                            createDeviceInfoFor.fAxisCollection.addAll(ApiLevel12.getAxisInfoFrom(device, it.next()));
                        }
                    }
                    arrayList.add(createDeviceInfoFor);
                    Iterator<InputDeviceType> it2 = inputDeviceTypeSet.iterator();
                    while (it2.hasNext()) {
                        InputDeviceType next = it2.next();
                        InputDeviceInfo createDeviceInfoFor2 = createDeviceInfoFor(device, next);
                        if (Build.VERSION.SDK_INT >= 12) {
                            createDeviceInfoFor2.fAxisCollection.addAll(ApiLevel12.getAxisInfoFrom(device, next));
                        }
                        arrayList.add(createDeviceInfoFor2);
                    }
                } else {
                    arrayList.add(createDeviceInfoFor(device, InputDeviceType.UNKNOWN));
                }
            }
            return arrayList;
        }
    }

    private InputDeviceInfo() {
        this.fDeviceType = InputDeviceType.UNKNOWN;
        this.fAndroidDeviceId = -1;
        this.fPermanentStringId = null;
        this.fProductName = BuildConfig.FLAVOR;
        this.fDisplayName = BuildConfig.FLAVOR;
        this.fCanVibrate = false;
        this.fPlayerNumber = 0;
        this.fInputSources = new InputDeviceTypeSet();
        this.fReadOnlyInputSources = new ReadOnlyInputDeviceTypeSet(this.fInputSources);
        this.fAxisCollection = new AxisInfoCollection();
        this.fReadOnlyAxisCollection = new ReadOnlyAxisInfoCollection(this.fAxisCollection);
    }

    static List<InputDeviceInfo> collectionFromAllAndroidDevices() {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 9) {
            for (int fetchDeviceInfoFromAndroidDeviceId : ApiLevel9.fetchAndroidDeviceIds()) {
                arrayList.addAll(ApiLevel9.fetchDeviceInfoFromAndroidDeviceId(fetchDeviceInfoFromAndroidDeviceId));
            }
        }
        return arrayList;
    }

    static List<InputDeviceInfo> collectionFromAndroidDeviceId(int i) {
        return Build.VERSION.SDK_INT >= 9 ? ApiLevel9.fetchDeviceInfoFromAndroidDeviceId(i) : new ArrayList();
    }

    public static InputDeviceInfo from(InputDeviceSettings inputDeviceSettings) {
        if (inputDeviceSettings == null) {
            return null;
        }
        InputDeviceInfo inputDeviceInfo = new InputDeviceInfo();
        inputDeviceInfo.fDeviceType = inputDeviceSettings.getType();
        inputDeviceInfo.fInputSources.add(inputDeviceInfo.fDeviceType);
        inputDeviceInfo.fPermanentStringId = inputDeviceSettings.getPermanentStringId();
        inputDeviceInfo.fProductName = inputDeviceSettings.getProductName();
        inputDeviceInfo.fDisplayName = inputDeviceSettings.getDisplayName();
        for (AxisSettings from : inputDeviceSettings.getAxes()) {
            inputDeviceInfo.fAxisCollection.add(AxisInfo.from(from));
        }
        return inputDeviceInfo;
    }

    public boolean canVibrate() {
        return this.fCanVibrate;
    }

    public InputDeviceInfo clone() {
        return this;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.ansca.corona.input.InputDeviceInfo} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ansca.corona.input.InputDeviceInfo cloneWithoutPlayerNumber() {
        /*
            r3 = this;
            r1 = r3
            boolean r2 = r3.hasPlayerNumber()
            if (r2 == 0) goto L_0x0012
            java.lang.Object r2 = super.clone()     // Catch:{ Exception -> 0x0013 }
            r0 = r2
            com.ansca.corona.input.InputDeviceInfo r0 = (com.ansca.corona.input.InputDeviceInfo) r0     // Catch:{ Exception -> 0x0013 }
            r1 = r0
            r2 = 0
            r1.fPlayerNumber = r2     // Catch:{ Exception -> 0x0013 }
        L_0x0012:
            return r1
        L_0x0013:
            r2 = move-exception
            goto L_0x0012
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.input.InputDeviceInfo.cloneWithoutPlayerNumber():com.ansca.corona.input.InputDeviceInfo");
    }

    public boolean equals(InputDeviceInfo inputDeviceInfo) {
        if (inputDeviceInfo == null) {
            return false;
        }
        if (this.fPermanentStringId == null && inputDeviceInfo.fPermanentStringId != null) {
            return false;
        }
        if (this.fPermanentStringId != null && inputDeviceInfo.fPermanentStringId == null) {
            return false;
        }
        if ((this.fPermanentStringId != null && !this.fPermanentStringId.equals(inputDeviceInfo.fPermanentStringId)) || inputDeviceInfo.fDeviceType != this.fDeviceType || inputDeviceInfo.fAndroidDeviceId != this.fAndroidDeviceId || !inputDeviceInfo.fProductName.equals(this.fProductName) || !inputDeviceInfo.fDisplayName.equals(this.fDisplayName) || inputDeviceInfo.fCanVibrate != this.fCanVibrate || inputDeviceInfo.fPlayerNumber != this.fPlayerNumber || !inputDeviceInfo.fInputSources.equals(this.fInputSources) || inputDeviceInfo.fAxisCollection.size() != this.fAxisCollection.size()) {
            return false;
        }
        for (int i = 0; i < this.fAxisCollection.size(); i++) {
            if (!inputDeviceInfo.fAxisCollection.getByIndex(i).equals(this.fAxisCollection.getByIndex(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InputDeviceInfo)) {
            return false;
        }
        return equals((InputDeviceInfo) obj);
    }

    public int getAndroidDeviceId() {
        return this.fAndroidDeviceId;
    }

    public ReadOnlyAxisInfoCollection getAxes() {
        return this.fReadOnlyAxisCollection;
    }

    public String getDisplayName() {
        return this.fDisplayName;
    }

    public ReadOnlyInputDeviceTypeSet getInputSources() {
        return this.fReadOnlyInputSources;
    }

    public String getPermanentStringId() {
        return this.fPermanentStringId;
    }

    public int getPlayerNumber() {
        return this.fPlayerNumber;
    }

    public String getProductName() {
        return this.fProductName;
    }

    public InputDeviceType getType() {
        return this.fDeviceType;
    }

    public boolean hasAndroidDeviceId() {
        return this.fAndroidDeviceId >= 0;
    }

    public boolean hasPermanentStringId() {
        return this.fPermanentStringId != null && this.fPermanentStringId.length() > 0;
    }

    public boolean hasPlayerNumber() {
        return this.fPlayerNumber > 0;
    }
}
