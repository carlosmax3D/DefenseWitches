package jp.stargarage.g2metrics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class ApiClient extends AsyncTask<Void, Void, Void> {
    private final IAsyncApiRequestCallBack callBack;
    private final Context context;
    private final ParamBase param;

    ApiClient(Context context, ParamBase paramBase, IAsyncApiRequestCallBack iAsyncApiRequestCallBack) {
        this.context = context;
        this.param = paramBase;
        this.callBack = iAsyncApiRequestCallBack;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    public Void doInBackground(Void... voidArr) {
        if (Constant.devModel) {
            try {
                if ("GET".equals(param.getMethodType())) {
                    Log.d("G2Metrics", "GET: " + param.getUrl());
                } else {
                    Log.d("G2Metrics", "POST: " + param.getUrl() + "\rBODY:" + param.toJson().toString());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            callBack.onFailure(0);
            return null;
        }

        OkHttpClient client = new OkHttpClient();
        Request request;

        try {
            if ("GET".equals(param.getMethodType())) {
                request = new Request.Builder()
                        .url(param.getUrl())
                        .header("Accept-Encoding", "gzip")
                        .build();
            } else {
                // GZIP compress the JSON body
                String json = param.toJson().toString();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (GZIPOutputStream gzipOut = new GZIPOutputStream(baos)) {
                    gzipOut.write(json.getBytes("UTF-8"));
                }

                RequestBody requestBody = RequestBody.create(
                        baos.toByteArray(),
                        MediaType.parse("application/json") // Content-Type
                );

                request = new Request.Builder()
                        .url(param.getUrl())
                        .header("Content-Encoding", "gzip")
                        .header("Accept-Encoding", "gzip")
                        .post(requestBody)
                        .build();
            }

            Response response = client.newCall(request).execute();
            int statusCode = response.code();

            if (statusCode == 200) {
                InputStream responseStream = response.body().byteStream();
                String encoding = response.header("Content-Encoding", "");

                if ("gzip".equalsIgnoreCase(encoding)) {
                    responseStream = new GZIPInputStream(responseStream);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
                responseStream.close();

                String responseStr = sb.toString();

                if (Constant.devModel) {
                    Log.d("G2Metrics", "RESPONSE: " + responseStr);
                }

                if (param.getResponseClass() == null) {
                    callBack.onSuccess(null);
                } else {
                    ApiEntityBase entity = (ApiEntityBase) param.getResponseClass().newInstance();
                    entity.setDataByJsonStr(responseStr);
                    callBack.onSuccess(entity);
                }
            } else {
                if (Constant.devModel) {
                    Log.d("G2Metrics", "RESPONSE HTTP CODE: " + statusCode);
                }
                callBack.onFailure(statusCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFailure(0);
            if (Constant.devModel) {
                Log.d("G2Metrics", "RESPONSE HTTP CODE: 0");
            }
        }

        return null;
    }


    void executeSync() throws Throwable {
        doInBackground(new Void[0]);
    }
}
