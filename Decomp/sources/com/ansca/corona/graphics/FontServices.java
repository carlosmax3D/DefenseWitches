package com.ansca.corona.graphics;

import android.content.Context;
import android.graphics.Typeface;
import com.ansca.corona.ApplicationContextProvider;
import com.ansca.corona.storage.FileServices;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class FontServices extends ApplicationContextProvider {
    private static HashMap<TypefaceInfo, Typeface> sTypefaceCollection = new HashMap<>();

    public FontServices(Context context) {
        super(context);
    }

    public String[] fetchAllSystemFontNames() {
        AnonymousClass1 r0 = new FilenameFilter() {
            public boolean accept(File file, String str) {
                return str.endsWith(".ttf");
            }
        };
        ArrayList arrayList = new ArrayList();
        for (File name : new File("/system/fonts/").listFiles(r0)) {
            String name2 = name.getName();
            arrayList.add(name2.subSequence(0, name2.lastIndexOf(".ttf")).toString());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public Typeface fetchTypefaceFor(TypefaceInfo typefaceInfo) {
        Typeface typeface;
        Typeface typeface2;
        File extractAssetFile;
        if (typefaceInfo == null) {
            return null;
        }
        synchronized (sTypefaceCollection) {
            typeface = sTypefaceCollection.get(typefaceInfo);
        }
        if (typeface != null) {
            return typeface;
        }
        if (typefaceInfo.getName() != null && typefaceInfo.getName().length() > 0) {
            try {
                String name = typefaceInfo.getName();
                FileServices fileServices = new FileServices(getApplicationContext());
                if (!fileServices.doesAssetFileExist(name)) {
                    name = typefaceInfo.getName() + ".ttf";
                    if (!fileServices.doesAssetFileExist(name)) {
                        name = typefaceInfo.getName() + ".otf";
                        if (!fileServices.doesAssetFileExist(name)) {
                            name = null;
                        }
                    }
                }
                if (!(name == null || (extractAssetFile = fileServices.extractAssetFile(name)) == null)) {
                    typeface = Typeface.createFromFile(extractAssetFile);
                }
            } catch (Exception e) {
            }
            if (typeface == null) {
                try {
                    File file = new File("/system/fonts/" + typefaceInfo.getName() + ".ttf");
                    if (file.exists()) {
                        typeface = Typeface.createFromFile(file);
                    }
                } catch (Exception e2) {
                }
            }
            if (typeface == null) {
                System.out.println("WARNING: Could not load font " + typefaceInfo.getName() + ". Using default.");
            }
        }
        if (typeface2 == null) {
            typeface2 = typefaceInfo.isBold() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT;
            if (typeface2 == null) {
                typeface2 = Typeface.create((String) null, typefaceInfo.getAndroidTypefaceStyle());
            }
        }
        if (typeface2 == null) {
            return typeface2;
        }
        synchronized (sTypefaceCollection) {
            sTypefaceCollection.put(typefaceInfo, typeface2);
        }
        return typeface2;
    }

    public Typeface fetchTypefaceFor(TypefaceSettings typefaceSettings) {
        if (typefaceSettings == null) {
            return null;
        }
        return fetchTypefaceFor(new TypefaceInfo(typefaceSettings));
    }
}
