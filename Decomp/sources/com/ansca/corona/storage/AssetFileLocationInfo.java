package com.ansca.corona.storage;

import java.io.File;

public class AssetFileLocationInfo {
    private Settings fSettings;

    public static class Settings implements Cloneable {
        private String fAssetFilePath = null;
        private long fByteCountInPackage = 0;
        private long fByteOffsetInPackage = 0;
        private boolean fIsCompressed = false;
        private File fPackageFile = null;
        private String fZipEntryName = null;

        public Settings clone() {
            try {
                return (Settings) super.clone();
            } catch (Exception e) {
                return null;
            }
        }

        public String getAssetFilePath() {
            return this.fAssetFilePath;
        }

        public long getByteCountInPackage() {
            return this.fByteCountInPackage;
        }

        public long getByteOffsetInPackage() {
            return this.fByteOffsetInPackage;
        }

        public File getPackageFile() {
            return this.fPackageFile;
        }

        public String getZipEntryName() {
            return this.fZipEntryName;
        }

        public boolean isCompressed() {
            return this.fIsCompressed;
        }

        public void setAssetFilePath(String str) {
            this.fAssetFilePath = str;
        }

        public void setByteCountInPackage(long j) {
            if (j <= 0) {
                throw new IllegalArgumentException();
            }
            this.fByteCountInPackage = j;
        }

        public void setByteOffsetInPackage(long j) {
            if (j < 0) {
                throw new IllegalArgumentException();
            }
            this.fByteOffsetInPackage = j;
        }

        public void setIsCompressed(boolean z) {
            this.fIsCompressed = z;
        }

        public void setPackageFile(File file) {
            this.fPackageFile = file;
        }

        public void setZipEntryName(String str) {
            this.fZipEntryName = str;
        }
    }

    public AssetFileLocationInfo(Settings settings) {
        if (settings == null) {
            throw new NullPointerException();
        } else if (settings.getPackageFile() == null || settings.getAssetFilePath() == null || settings.getAssetFilePath().length() <= 0 || settings.getZipEntryName() == null || settings.getZipEntryName().length() <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.fSettings = settings.clone();
        }
    }

    public String getAssetFilePath() {
        return this.fSettings.getAssetFilePath();
    }

    public long getByteCountInPackage() {
        return this.fSettings.getByteCountInPackage();
    }

    public long getByteOffsetInPackage() {
        return this.fSettings.getByteOffsetInPackage();
    }

    public File getPackageFile() {
        return this.fSettings.getPackageFile();
    }

    public String getZipEntryName() {
        return this.fSettings.getZipEntryName();
    }

    public boolean isCompressed() {
        return this.fSettings.isCompressed();
    }
}
