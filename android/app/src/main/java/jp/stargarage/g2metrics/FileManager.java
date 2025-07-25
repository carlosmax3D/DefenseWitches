package jp.stargarage.g2metrics;

import android.app.Application;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class FileManager {
    FileManager() {
    }

    static void appendLog(Application application, String str, EntityLogData entityLogData) {
        try {
            String str2 = str + "apd";
            File fileStreamPath = application.getFileStreamPath(str);
            if (fileStreamPath.exists()) {
                if (fileStreamPath.length() >= 102400) {
                    application.deleteFile(str);
                }
                extract(application, str, str2);
                application.deleteFile(str);
            }
            String string = entityLogData.toJson().toString();
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(application.openFileOutput(str2, 32768), "UTF-8"));
            printWriter.append((CharSequence) string).append((CharSequence) "\r");
            printWriter.close();
            compress(application, str2, str);
            application.deleteFile(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void appendSendErrorSessionLog(Application application, String str, ArrayList<ArrayList<String>> arrayList) {
        try {
            String str2 = str + "ase";
            application.deleteFile(str);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(application.openFileOutput(str2, 32768), "UTF-8"));
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                printWriter.append((CharSequence) arrayList.get(size).get(1)).append((CharSequence) "\r");
            }
            printWriter.close();
            compress(application, str2, str);
            application.deleteFile(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compress(Application application, String str, String str2) throws IOException {
        byte[] bArr = new byte[1024];
        try {
            FileInputStream fileInputStreamOpenFileInput = application.openFileInput(str);
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(application.openFileOutput(str2, 32768));
            while (true) {
                int i = fileInputStreamOpenFileInput.read(bArr);
                if (i == -1) {
                    fileInputStreamOpenFileInput.close();
                    gZIPOutputStream.close();
                    return;
                }
                gZIPOutputStream.write(bArr, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteTopData(Application application, String str, Integer num) throws IOException {
        try {
            String str2 = str + "del";
            if (application.getFileStreamPath(str).exists()) {
                extract(application, str, str2);
                application.deleteFile(str);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(application.openFileInput(str2), "UTF-8"));
                Integer numValueOf = 0;
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    numValueOf = Integer.valueOf(numValueOf.intValue() + 1);
                    if (numValueOf.intValue() > num.intValue()) {
                        sb.append(String.format("%s\r", line));
                    }
                }
                bufferedReader.close();
                application.deleteFile(str2);
                if (sb.length() > 0) {
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(application.openFileOutput(str2, 32768), "UTF-8"));
                    printWriter.append((CharSequence) sb.toString().substring(0, sb.length() - 2));
                    printWriter.close();
                    compress(application, str2, str);
                    application.deleteFile(str2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extract(Application application, String str, String str2) throws IOException {
        byte[] bArr = new byte[1024];
        try {
            GZIPInputStream gZIPInputStream = new GZIPInputStream(application.openFileInput(str));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(application.openFileOutput(str2, 32768));
            while (true) {
                int i = gZIPInputStream.read(bArr);
                if (i == -1) {
                    gZIPInputStream.close();
                    bufferedOutputStream.close();
                    return;
                }
                bufferedOutputStream.write(bArr, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<String> getSendErrorSessionLog(Application application, String str, Integer num) throws IOException {
        ArrayList arrayList = new ArrayList();
        try {
            String str2 = str + "gse";
            if (application.getFileStreamPath(str).exists()) {
                extract(application, str, str2);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(application.openFileInput(str2), "UTF-8"));
                do {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    arrayList.add(line);
                } while (arrayList.size() < num.intValue());
                bufferedReader.close();
                application.deleteFile(str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    static List<EntityLogData> getTopData(Application application, String str, Integer num) throws IOException {
        ArrayList arrayList = new ArrayList();
        try {
            String str2 = str + "get";
            if (application.getFileStreamPath(str).exists()) {
                extract(application, str, str2);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(application.openFileInput(str2), "UTF-8"));
                do {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    EntityLogData entityLogData = new EntityLogData();
                    entityLogData.setDataByJsonStr(line);
                    arrayList.add(entityLogData);
                } while (arrayList.size() < num.intValue());
                bufferedReader.close();
                application.deleteFile(str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
