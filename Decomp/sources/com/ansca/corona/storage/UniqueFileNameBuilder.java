package com.ansca.corona.storage;

import java.io.File;
import jp.stargarage.g2metrics.BuildConfig;

public class UniqueFileNameBuilder {
    private File fDirectory = null;
    private String fFileExtension = BuildConfig.FLAVOR;
    private String fFileNameFormat = "File %d";

    public File build() {
        boolean z = true;
        if (this.fDirectory == null || !this.fDirectory.exists()) {
            return null;
        }
        if (this.fFileNameFormat.indexOf(37) < 0) {
            z = false;
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= 10000; i++) {
                if (z) {
                    sb.append(String.format(this.fFileNameFormat, new Object[]{Integer.valueOf(i)}));
                } else {
                    sb.append(this.fFileNameFormat);
                    sb.append(Integer.toString(i));
                }
                if (this.fFileExtension.length() > 0) {
                    sb.append('.');
                    sb.append(this.fFileExtension);
                }
                File file = new File(this.fDirectory, sb.toString());
                if (!file.exists()) {
                    return file;
                }
                sb.setLength(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public File getDirectory() {
        return this.fDirectory;
    }

    public String getFileExtension() {
        return this.fFileExtension;
    }

    public String getFileNameFormat() {
        return this.fFileNameFormat;
    }

    public void setDirectory(File file) {
        this.fDirectory = file;
    }

    public void setFileExtension(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        this.fFileExtension = str;
    }

    public void setFileNameFormat(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fFileNameFormat = str;
    }
}
