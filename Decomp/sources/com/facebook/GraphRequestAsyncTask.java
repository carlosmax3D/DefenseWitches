package com.facebook;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

public class GraphRequestAsyncTask extends AsyncTask<Void, Void, List<GraphResponse>> {
    private static final String TAG = GraphRequestAsyncTask.class.getCanonicalName();
    private static Method executeOnExecutorMethod;
    private final HttpURLConnection connection;
    private Exception exception;
    private final GraphRequestBatch requests;

    static {
        for (Method method : AsyncTask.class.getMethods()) {
            if ("executeOnExecutor".equals(method.getName())) {
                Class<Executor>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 2 && parameterTypes[0] == Executor.class && parameterTypes[1].isArray()) {
                    executeOnExecutorMethod = method;
                    return;
                }
            }
        }
    }

    public GraphRequestAsyncTask(GraphRequestBatch graphRequestBatch) {
        this((HttpURLConnection) null, graphRequestBatch);
    }

    public GraphRequestAsyncTask(HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        this.requests = graphRequestBatch;
        this.connection = httpURLConnection;
    }

    public GraphRequestAsyncTask(HttpURLConnection httpURLConnection, Collection<GraphRequest> collection) {
        this(httpURLConnection, new GraphRequestBatch(collection));
    }

    public GraphRequestAsyncTask(HttpURLConnection httpURLConnection, GraphRequest... graphRequestArr) {
        this(httpURLConnection, new GraphRequestBatch(graphRequestArr));
    }

    public GraphRequestAsyncTask(Collection<GraphRequest> collection) {
        this((HttpURLConnection) null, new GraphRequestBatch(collection));
    }

    public GraphRequestAsyncTask(GraphRequest... graphRequestArr) {
        this((HttpURLConnection) null, new GraphRequestBatch(graphRequestArr));
    }

    /* access modifiers changed from: protected */
    public List<GraphResponse> doInBackground(Void... voidArr) {
        try {
            return this.connection == null ? this.requests.executeAndWait() : GraphRequest.executeConnectionAndWait(this.connection, this.requests);
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public GraphRequestAsyncTask executeOnSettingsExecutor() {
        if (executeOnExecutorMethod != null) {
            try {
                executeOnExecutorMethod.invoke(this, new Object[]{FacebookSdk.getExecutor(), null});
            } catch (IllegalAccessException | InvocationTargetException e) {
            }
        } else {
            execute(new Void[0]);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public final Exception getException() {
        return this.exception;
    }

    /* access modifiers changed from: protected */
    public final GraphRequestBatch getRequests() {
        return this.requests;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(List<GraphResponse> list) {
        super.onPostExecute(list);
        if (this.exception != null) {
            Log.d(TAG, String.format("onPostExecute: exception encountered during request: %s", new Object[]{this.exception.getMessage()}));
        }
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        if (this.requests.getCallbackHandler() == null) {
            this.requests.setCallbackHandler(new Handler());
        }
    }

    public String toString() {
        return "{RequestAsyncTask: " + " connection: " + this.connection + ", requests: " + this.requests + "}";
    }
}
