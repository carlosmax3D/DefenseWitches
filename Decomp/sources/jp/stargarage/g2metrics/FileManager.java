package jp.stargarage.g2metrics;

import android.app.Application;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
            String jSONObject = entityLogData.toJson().toString();
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(application.openFileOutput(str2, 32768), "UTF-8"));
            printWriter.append(jSONObject).append("\r");
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
                printWriter.append((String) arrayList.get(size).get(1)).append("\r");
            }
            printWriter.close();
            compress(application, str2, str);
            application.deleteFile(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compress(Application application, String str, String str2) {
        byte[] bArr = new byte[1024];
        try {
            FileInputStream openFileInput = application.openFileInput(str);
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(application.openFileOutput(str2, 32768));
            while (true) {
                int read = openFileInput.read(bArr);
                if (read != -1) {
                    gZIPOutputStream.write(bArr, 0, read);
                } else {
                    openFileInput.close();
                    gZIPOutputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteTopData(Application application, String str, Integer num) {
        try {
            String str2 = str + "del";
            if (application.getFileStreamPath(str).exists()) {
                extract(application, str, str2);
                application.deleteFile(str);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(application.openFileInput(str2), "UTF-8"));
                Integer num2 = 0;
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    num2 = Integer.valueOf(num2.intValue() + 1);
                    if (num2.intValue() > num.intValue()) {
                        sb.append(String.format("%s\r", new Object[]{readLine}));
                    }
                }
                bufferedReader.close();
                application.deleteFile(str2);
                if (sb.length() > 0) {
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(application.openFileOutput(str2, 32768), "UTF-8"));
                    printWriter.append(sb.toString().substring(0, sb.length() - 2));
                    printWriter.close();
                    compress(application, str2, str);
                    application.deleteFile(str2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extract(Application application, String str, String str2) {
        byte[] bArr = new byte[1024];
        try {
            GZIPInputStream gZIPInputStream = new GZIPInputStream(application.openFileInput(str));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(application.openFileOutput(str2, 32768));
            while (true) {
                int read = gZIPInputStream.read(bArr);
                if (read != -1) {
                    bufferedOutputStream.write(bArr, 0, read);
                } else {
                    gZIPInputStream.close();
                    bufferedOutputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<String> getSendErrorSessionLog(Application application, String str, Integer num) {
        ArrayList arrayList = new ArrayList();
        try {
            String str2 = str + "gse";
            if (application.getFileStreamPath(str).exists()) {
                extract(application, str, str2);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(application.openFileInput(str2), "UTF-8"));
                do {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    arrayList.add(readLine);
                } while (arrayList.size() < num.intValue());
                bufferedReader.close();
                application.deleteFile(str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    static List<EntityLogData> getTopData(Application application, String str, Integer num) {
        ArrayList arrayList = new ArrayList();
        try {
            String str2 = str + "get";
            if (application.getFileStreamPath(str).exists()) {
                extract(application, str, str2);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(application.openFileInput(str2), "UTF-8"));
                do {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    EntityLogData entityLogData = new EntityLogData();
                    entityLogData.setDataByJsonStr(readLine);
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
