package jp.co.voyagegroup.android.fluct.jar.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jp.co.voyagegroup.android.fluct.jar.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FluctHttpAccess {
    private static final String TAG = "FluctHttpAccess";

    private boolean doGetRequest(String str) {
        Log.d(TAG, "doGetRequest : url is " + str);
        boolean z = false;
        HttpGet httpGet = new HttpGet(str);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpParams params = defaultHttpClient.getParams();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        try {
            if (defaultHttpClient.execute(httpGet).getStatusLine().getStatusCode() == 200) {
                z = true;
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doGetRequest : IOException is " + e.getLocalizedMessage());
                }
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e2) {
            Log.e(TAG, "doGetRequest : Exception is " + e2.getLocalizedMessage());
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e3) {
                    Log.e(TAG, "doGetRequest : IOException is " + e3.getLocalizedMessage());
                }
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Throwable th) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    Log.e(TAG, "doGetRequest : IOException is " + e4.getLocalizedMessage());
                    throw th;
                }
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
        return z;
    }

    public static Document getDocument(String str) {
        Log.d(TAG, "getDocument : ");
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            HttpGet httpGet = new HttpGet(str);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpParams params = defaultHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                return newDocumentBuilder.parse(execute.getEntity().getContent());
            }
            return null;
        } catch (ClientProtocolException e) {
            Log.e(TAG, "getDocument : ClientProtocolException is " + e.getLocalizedMessage());
            return null;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "getDocument : IllegalStateException is " + e2.getLocalizedMessage());
            return null;
        } catch (ParserConfigurationException e3) {
            Log.e(TAG, "getDocument : ParserConfigurationException is " + e3.getLocalizedMessage());
            return null;
        } catch (IOException e4) {
            Log.e(TAG, "getDocument : IOException is " + e4.getLocalizedMessage());
            return null;
        } catch (SAXException e5) {
            Log.e(TAG, "getDocument : SAXException is " + e5.getLocalizedMessage());
            return null;
        }
    }

    public boolean executeUrls(List<String> list) {
        Log.d(TAG, "executeUrls : ");
        boolean z = true;
        for (int i = 0; i < list.size(); i++) {
            Log.v(TAG, "executeUrls : url is " + list.get(i));
            if (!doGetRequest(list.get(i))) {
                z = false;
            }
        }
        return z;
    }
}
