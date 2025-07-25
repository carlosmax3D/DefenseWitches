package com.facebook;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.facebook.GraphRequestBatch;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareConstants;
import com.tapjoy.TapjoyConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphRequest {
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private static final String ACCESS_TOKEN_PARAM = "access_token";
    private static final String ATTACHED_FILES_PARAM = "attached_files";
    private static final String ATTACHMENT_FILENAME_PREFIX = "file";
    private static final String BATCH_APP_ID_PARAM = "batch_app_id";
    private static final String BATCH_BODY_PARAM = "body";
    private static final String BATCH_ENTRY_DEPENDS_ON_PARAM = "depends_on";
    private static final String BATCH_ENTRY_NAME_PARAM = "name";
    private static final String BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM = "omit_response_on_success";
    private static final String BATCH_METHOD_PARAM = "method";
    private static final String BATCH_PARAM = "batch";
    private static final String BATCH_RELATIVE_URL_PARAM = "relative_url";
    private static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String DEBUG_KEY = "__debug__";
    private static final String DEBUG_MESSAGES_KEY = "messages";
    private static final String DEBUG_MESSAGE_KEY = "message";
    private static final String DEBUG_MESSAGE_LINK_KEY = "link";
    private static final String DEBUG_MESSAGE_TYPE_KEY = "type";
    private static final String DEBUG_PARAM = "debug";
    private static final String DEBUG_SEVERITY_INFO = "info";
    private static final String DEBUG_SEVERITY_WARNING = "warning";
    private static final String FORMAT_JSON = "json";
    private static final String FORMAT_PARAM = "format";
    private static final String ISO_8601_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final int MAXIMUM_BATCH_SIZE = 50;
    private static final String ME = "me";
    private static final String MIME_BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
    private static final String MY_FRIENDS = "me/friends";
    private static final String SDK_ANDROID = "android";
    private static final String SDK_PARAM = "sdk";
    private static final String SEARCH = "search";
    public static final String TAG = GraphRequest.class.getSimpleName();
    private static final String USER_AGENT_BASE = "FBAndroidSDK";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String VIDEOS_SUFFIX = "/videos";
    private static String defaultBatchApplicationId;
    private static volatile String userAgent;
    private static Pattern versionPattern = Pattern.compile("^/?v\\d+\\.\\d+/(.*)");
    private AccessToken accessToken;
    private String batchEntryDependsOn;
    private String batchEntryName;
    private boolean batchEntryOmitResultOnSuccess;
    private Callback callback;
    private JSONObject graphObject;
    private String graphPath;
    private HttpMethod httpMethod;
    private String overriddenURL;
    private Bundle parameters;
    private boolean skipClientToken;
    private Object tag;
    private String version;

    private static class Attachment {
        private final GraphRequest request;
        private final Object value;

        public Attachment(GraphRequest graphRequest, Object obj) {
            this.request = graphRequest;
            this.value = obj;
        }

        public GraphRequest getRequest() {
            return this.request;
        }

        public Object getValue() {
            return this.value;
        }
    }

    public interface Callback {
        void onCompleted(GraphResponse graphResponse);
    }

    public interface GraphJSONArrayCallback {
        void onCompleted(JSONArray jSONArray, GraphResponse graphResponse);
    }

    public interface GraphJSONObjectCallback {
        void onCompleted(JSONObject jSONObject, GraphResponse graphResponse);
    }

    private interface KeyValueSerializer {
        void writeString(String str, String str2) throws IOException;
    }

    public interface OnProgressCallback extends Callback {
        void onProgress(long j, long j2);
    }

    public static class ParcelableResourceWithMimeType<RESOURCE extends Parcelable> implements Parcelable {
        public static final Parcelable.Creator<ParcelableResourceWithMimeType> CREATOR = new Parcelable.Creator<ParcelableResourceWithMimeType>() {
            public ParcelableResourceWithMimeType createFromParcel(Parcel parcel) {
                return new ParcelableResourceWithMimeType(parcel);
            }

            public ParcelableResourceWithMimeType[] newArray(int i) {
                return new ParcelableResourceWithMimeType[i];
            }
        };
        private final String mimeType;
        private final RESOURCE resource;

        private ParcelableResourceWithMimeType(Parcel parcel) {
            this.mimeType = parcel.readString();
            this.resource = parcel.readParcelable(FacebookSdk.getApplicationContext().getClassLoader());
        }

        public ParcelableResourceWithMimeType(RESOURCE resource2, String str) {
            this.mimeType = str;
            this.resource = resource2;
        }

        public int describeContents() {
            return 1;
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public RESOURCE getResource() {
            return this.resource;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mimeType);
            parcel.writeParcelable(this.resource, i);
        }
    }

    private static class Serializer implements KeyValueSerializer {
        private boolean firstWrite = true;
        private final Logger logger;
        private final OutputStream outputStream;
        private boolean useUrlEncode = false;

        public Serializer(OutputStream outputStream2, Logger logger2, boolean z) {
            this.outputStream = outputStream2;
            this.logger = logger2;
            this.useUrlEncode = z;
        }

        private RuntimeException getInvalidTypeError() {
            return new IllegalArgumentException("value is not a supported type.");
        }

        public void write(String str, Object... objArr) throws IOException {
            if (!this.useUrlEncode) {
                if (this.firstWrite) {
                    this.outputStream.write("--".getBytes());
                    this.outputStream.write(GraphRequest.MIME_BOUNDARY.getBytes());
                    this.outputStream.write("\r\n".getBytes());
                    this.firstWrite = false;
                }
                this.outputStream.write(String.format(str, objArr).getBytes());
                return;
            }
            this.outputStream.write(URLEncoder.encode(String.format(Locale.US, str, objArr), "UTF-8").getBytes());
        }

        public void writeBitmap(String str, Bitmap bitmap) throws IOException {
            writeContentDisposition(str, str, "image/png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this.outputStream);
            writeLine(BuildConfig.FLAVOR, new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + str, "<Image>");
            }
        }

        public void writeBytes(String str, byte[] bArr) throws IOException {
            writeContentDisposition(str, str, "content/unknown");
            this.outputStream.write(bArr);
            writeLine(BuildConfig.FLAVOR, new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + str, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(bArr.length)}));
            }
        }

        public void writeContentDisposition(String str, String str2, String str3) throws IOException {
            if (!this.useUrlEncode) {
                write("Content-Disposition: form-data; name=\"%s\"", str);
                if (str2 != null) {
                    write("; filename=\"%s\"", str2);
                }
                writeLine(BuildConfig.FLAVOR, new Object[0]);
                if (str3 != null) {
                    writeLine("%s: %s", GraphRequest.CONTENT_TYPE_HEADER, str3);
                }
                writeLine(BuildConfig.FLAVOR, new Object[0]);
                return;
            }
            this.outputStream.write(String.format("%s=", new Object[]{str}).getBytes());
        }

        public void writeContentUri(String str, Uri uri, String str2) throws IOException {
            if (str2 == null) {
                str2 = "content/unknown";
            }
            writeContentDisposition(str, str, str2);
            InputStream openInputStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(uri);
            int i = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                Cursor query = FacebookSdk.getApplicationContext().getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
                int columnIndex = query.getColumnIndex("_size");
                query.moveToFirst();
                long j = query.getLong(columnIndex);
                query.close();
                ((ProgressNoopOutputStream) this.outputStream).addProgress(j);
            } else {
                i = 0 + Utility.copyAndCloseInputStream(openInputStream, this.outputStream);
            }
            writeLine(BuildConfig.FLAVOR, new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + str, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void writeFile(String str, ParcelFileDescriptor parcelFileDescriptor, String str2) throws IOException {
            if (str2 == null) {
                str2 = "content/unknown";
            }
            writeContentDisposition(str, str, str2);
            int i = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                ((ProgressNoopOutputStream) this.outputStream).addProgress(parcelFileDescriptor.getStatSize());
            } else {
                i = 0 + Utility.copyAndCloseInputStream(new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor), this.outputStream);
            }
            writeLine(BuildConfig.FLAVOR, new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + str, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void writeLine(String str, Object... objArr) throws IOException {
            write(str, objArr);
            if (!this.useUrlEncode) {
                write("\r\n", new Object[0]);
            }
        }

        public void writeObject(String str, Object obj, GraphRequest graphRequest) throws IOException {
            if (this.outputStream instanceof RequestOutputStream) {
                ((RequestOutputStream) this.outputStream).setCurrentRequest(graphRequest);
            }
            if (GraphRequest.isSupportedParameterType(obj)) {
                writeString(str, GraphRequest.parameterToString(obj));
            } else if (obj instanceof Bitmap) {
                writeBitmap(str, (Bitmap) obj);
            } else if (obj instanceof byte[]) {
                writeBytes(str, (byte[]) obj);
            } else if (obj instanceof Uri) {
                writeContentUri(str, (Uri) obj, (String) null);
            } else if (obj instanceof ParcelFileDescriptor) {
                writeFile(str, (ParcelFileDescriptor) obj, (String) null);
            } else if (obj instanceof ParcelableResourceWithMimeType) {
                ParcelableResourceWithMimeType parcelableResourceWithMimeType = (ParcelableResourceWithMimeType) obj;
                Parcelable resource = parcelableResourceWithMimeType.getResource();
                String mimeType = parcelableResourceWithMimeType.getMimeType();
                if (resource instanceof ParcelFileDescriptor) {
                    writeFile(str, (ParcelFileDescriptor) resource, mimeType);
                } else if (resource instanceof Uri) {
                    writeContentUri(str, (Uri) resource, mimeType);
                } else {
                    throw getInvalidTypeError();
                }
            } else {
                throw getInvalidTypeError();
            }
        }

        public void writeRecordBoundary() throws IOException {
            if (!this.useUrlEncode) {
                writeLine("--%s", GraphRequest.MIME_BOUNDARY);
                return;
            }
            this.outputStream.write("&".getBytes());
        }

        public void writeRequestsAsJson(String str, JSONArray jSONArray, Collection<GraphRequest> collection) throws IOException, JSONException {
            if (!(this.outputStream instanceof RequestOutputStream)) {
                writeString(str, jSONArray.toString());
                return;
            }
            RequestOutputStream requestOutputStream = (RequestOutputStream) this.outputStream;
            writeContentDisposition(str, (String) null, (String) null);
            write("[", new Object[0]);
            int i = 0;
            for (GraphRequest currentRequest : collection) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                requestOutputStream.setCurrentRequest(currentRequest);
                if (i > 0) {
                    write(",%s", jSONObject.toString());
                } else {
                    write("%s", jSONObject.toString());
                }
                i++;
            }
            write("]", new Object[0]);
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + str, jSONArray.toString());
            }
        }

        public void writeString(String str, String str2) throws IOException {
            writeContentDisposition(str, (String) null, (String) null);
            writeLine("%s", str2);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + str, str2);
            }
        }
    }

    public GraphRequest() {
        this((AccessToken) null, (String) null, (Bundle) null, (HttpMethod) null, (Callback) null);
    }

    public GraphRequest(AccessToken accessToken2, String str) {
        this(accessToken2, str, (Bundle) null, (HttpMethod) null, (Callback) null);
    }

    public GraphRequest(AccessToken accessToken2, String str, Bundle bundle, HttpMethod httpMethod2) {
        this(accessToken2, str, bundle, httpMethod2, (Callback) null);
    }

    public GraphRequest(AccessToken accessToken2, String str, Bundle bundle, HttpMethod httpMethod2, Callback callback2) {
        this(accessToken2, str, bundle, httpMethod2, callback2, (String) null);
    }

    public GraphRequest(AccessToken accessToken2, String str, Bundle bundle, HttpMethod httpMethod2, Callback callback2, String str2) {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.accessToken = accessToken2;
        this.graphPath = str;
        this.version = str2;
        setCallback(callback2);
        setHttpMethod(httpMethod2);
        if (bundle != null) {
            this.parameters = new Bundle(bundle);
        } else {
            this.parameters = new Bundle();
        }
        if (this.version == null) {
            this.version = ServerProtocol.getAPIVersion();
        }
    }

    GraphRequest(AccessToken accessToken2, URL url) {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.accessToken = accessToken2;
        this.overriddenURL = url.toString();
        setHttpMethod(HttpMethod.GET);
        this.parameters = new Bundle();
    }

    private void addCommonParameters() {
        if (this.accessToken != null) {
            if (!this.parameters.containsKey("access_token")) {
                String token = this.accessToken.getToken();
                Logger.registerAccessToken(token);
                this.parameters.putString("access_token", token);
            }
        } else if (!this.skipClientToken && !this.parameters.containsKey("access_token")) {
            String applicationId = FacebookSdk.getApplicationId();
            String clientToken = FacebookSdk.getClientToken();
            if (Utility.isNullOrEmpty(applicationId) || Utility.isNullOrEmpty(clientToken)) {
                Log.d(TAG, "Warning: Request without access token missing application ID or client token.");
            } else {
                this.parameters.putString("access_token", applicationId + "|" + clientToken);
            }
        }
        this.parameters.putString(SDK_PARAM, "android");
        this.parameters.putString(FORMAT_PARAM, FORMAT_JSON);
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO)) {
            this.parameters.putString(DEBUG_PARAM, DEBUG_SEVERITY_INFO);
        } else if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.parameters.putString(DEBUG_PARAM, DEBUG_SEVERITY_WARNING);
        }
    }

    private String appendParametersToBaseUrl(String str) {
        Uri.Builder encodedPath = new Uri.Builder().encodedPath(str);
        for (String str2 : this.parameters.keySet()) {
            Object obj = this.parameters.get(str2);
            if (obj == null) {
                obj = BuildConfig.FLAVOR;
            }
            if (isSupportedParameterType(obj)) {
                encodedPath.appendQueryParameter(str2, parameterToString(obj).toString());
            } else if (this.httpMethod == HttpMethod.GET) {
                throw new IllegalArgumentException(String.format(Locale.US, "Unsupported parameter type for GET request: %s", new Object[]{obj.getClass().getSimpleName()}));
            }
        }
        return encodedPath.toString();
    }

    private static HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty(USER_AGENT_HEADER, getUserAgent());
        httpURLConnection.setRequestProperty(ACCEPT_LANGUAGE_HEADER, Locale.getDefault().toString());
        httpURLConnection.setChunkedStreamingMode(0);
        return httpURLConnection;
    }

    public static GraphResponse executeAndWait(GraphRequest graphRequest) {
        List<GraphResponse> executeBatchAndWait = executeBatchAndWait(graphRequest);
        if (executeBatchAndWait != null && executeBatchAndWait.size() == 1) {
            return executeBatchAndWait.get(0);
        }
        throw new FacebookException("invalid state: expected a single response");
    }

    public static List<GraphResponse> executeBatchAndWait(GraphRequestBatch graphRequestBatch) {
        Validate.notEmptyAndContainsNoNulls(graphRequestBatch, "requests");
        try {
            return executeConnectionAndWait(toHttpConnection(graphRequestBatch), graphRequestBatch);
        } catch (Exception e) {
            List<GraphResponse> constructErrorResponses = GraphResponse.constructErrorResponses(graphRequestBatch.getRequests(), (HttpURLConnection) null, new FacebookException((Throwable) e));
            runCallbacks(graphRequestBatch, constructErrorResponses);
            return constructErrorResponses;
        }
    }

    public static List<GraphResponse> executeBatchAndWait(Collection<GraphRequest> collection) {
        return executeBatchAndWait(new GraphRequestBatch(collection));
    }

    public static List<GraphResponse> executeBatchAndWait(GraphRequest... graphRequestArr) {
        Validate.notNull(graphRequestArr, "requests");
        return executeBatchAndWait((Collection<GraphRequest>) Arrays.asList(graphRequestArr));
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequestBatch graphRequestBatch) {
        Validate.notEmptyAndContainsNoNulls(graphRequestBatch, "requests");
        GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequestAsyncTask(graphRequestBatch);
        graphRequestAsyncTask.executeOnSettingsExecutor();
        return graphRequestAsyncTask;
    }

    public static GraphRequestAsyncTask executeBatchAsync(Collection<GraphRequest> collection) {
        return executeBatchAsync(new GraphRequestBatch(collection));
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequest... graphRequestArr) {
        Validate.notNull(graphRequestArr, "requests");
        return executeBatchAsync((Collection<GraphRequest>) Arrays.asList(graphRequestArr));
    }

    public static List<GraphResponse> executeConnectionAndWait(HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        List<GraphResponse> fromHttpConnection = GraphResponse.fromHttpConnection(httpURLConnection, graphRequestBatch);
        Utility.disconnectQuietly(httpURLConnection);
        int size = graphRequestBatch.size();
        if (size != fromHttpConnection.size()) {
            throw new FacebookException(String.format(Locale.US, "Received %d responses while expecting %d", new Object[]{Integer.valueOf(fromHttpConnection.size()), Integer.valueOf(size)}));
        }
        runCallbacks(graphRequestBatch, fromHttpConnection);
        AccessTokenManager.getInstance().extendAccessTokenIfNeeded();
        return fromHttpConnection;
    }

    public static List<GraphResponse> executeConnectionAndWait(HttpURLConnection httpURLConnection, Collection<GraphRequest> collection) {
        return executeConnectionAndWait(httpURLConnection, new GraphRequestBatch(collection));
    }

    public static GraphRequestAsyncTask executeConnectionAsync(Handler handler, HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        Validate.notNull(httpURLConnection, "connection");
        GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequestAsyncTask(httpURLConnection, graphRequestBatch);
        graphRequestBatch.setCallbackHandler(handler);
        graphRequestAsyncTask.executeOnSettingsExecutor();
        return graphRequestAsyncTask;
    }

    public static GraphRequestAsyncTask executeConnectionAsync(HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        return executeConnectionAsync((Handler) null, httpURLConnection, graphRequestBatch);
    }

    private static String getBatchAppId(GraphRequestBatch graphRequestBatch) {
        String applicationId;
        if (!Utility.isNullOrEmpty(graphRequestBatch.getBatchApplicationId())) {
            return graphRequestBatch.getBatchApplicationId();
        }
        Iterator it = graphRequestBatch.iterator();
        while (it.hasNext()) {
            AccessToken accessToken2 = ((GraphRequest) it.next()).accessToken;
            if (accessToken2 != null && (applicationId = accessToken2.getApplicationId()) != null) {
                return applicationId;
            }
        }
        return !Utility.isNullOrEmpty(defaultBatchApplicationId) ? defaultBatchApplicationId : FacebookSdk.getApplicationId();
    }

    public static final String getDefaultBatchApplicationId() {
        return defaultBatchApplicationId;
    }

    private String getGraphPathWithVersion() {
        if (versionPattern.matcher(this.graphPath).matches()) {
            return this.graphPath;
        }
        return String.format("%s/%s", new Object[]{this.version, this.graphPath});
    }

    private static String getMimeContentType() {
        return String.format("multipart/form-data; boundary=%s", new Object[]{MIME_BOUNDARY});
    }

    private static String getUserAgent() {
        if (userAgent == null) {
            userAgent = String.format("%s.%s", new Object[]{USER_AGENT_BASE, FacebookSdkVersion.BUILD});
        }
        return userAgent;
    }

    private static boolean hasOnProgressCallbacks(GraphRequestBatch graphRequestBatch) {
        for (GraphRequestBatch.Callback callback2 : graphRequestBatch.getCallbacks()) {
            if (callback2 instanceof GraphRequestBatch.OnProgressCallback) {
                return true;
            }
        }
        Iterator it = graphRequestBatch.iterator();
        while (it.hasNext()) {
            if (((GraphRequest) it.next()).getCallback() instanceof OnProgressCallback) {
                return true;
            }
        }
        return false;
    }

    private static boolean isGzipCompressible(GraphRequestBatch graphRequestBatch) {
        Iterator it = graphRequestBatch.iterator();
        while (it.hasNext()) {
            GraphRequest graphRequest = (GraphRequest) it.next();
            Iterator it2 = graphRequest.parameters.keySet().iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (isSupportedAttachmentType(graphRequest.parameters.get((String) it2.next()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isMeRequest(String str) {
        Matcher matcher = versionPattern.matcher(str);
        if (matcher.matches()) {
            str = matcher.group(1);
        }
        return str.startsWith("me/") || str.startsWith("/me/");
    }

    private static boolean isSupportedAttachmentType(Object obj) {
        return (obj instanceof Bitmap) || (obj instanceof byte[]) || (obj instanceof Uri) || (obj instanceof ParcelFileDescriptor) || (obj instanceof ParcelableResourceWithMimeType);
    }

    /* access modifiers changed from: private */
    public static boolean isSupportedParameterType(Object obj) {
        return (obj instanceof String) || (obj instanceof Boolean) || (obj instanceof Number) || (obj instanceof Date);
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken accessToken2, Context context, Callback callback2) {
        return newCustomAudienceThirdPartyIdRequest(accessToken2, context, (String) null, callback2);
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken accessToken2, Context context, String str, Callback callback2) {
        if (str == null && accessToken2 != null) {
            str = accessToken2.getApplicationId();
        }
        if (str == null) {
            str = Utility.getMetadataApplicationId(context);
        }
        if (str == null) {
            throw new FacebookException("Facebook App ID cannot be determined");
        }
        String str2 = str + "/custom_audience_third_party_id";
        AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        Bundle bundle = new Bundle();
        if (accessToken2 == null) {
            String attributionId = attributionIdentifiers.getAttributionId() != null ? attributionIdentifiers.getAttributionId() : attributionIdentifiers.getAndroidAdvertiserId();
            if (attributionIdentifiers.getAttributionId() != null) {
                bundle.putString(TapjoyConstants.TJC_DEVICE_ID_NAME, attributionId);
            }
        }
        if (FacebookSdk.getLimitEventAndDataUsage(context) || attributionIdentifiers.isTrackingLimited()) {
            bundle.putString("limit_event_usage", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        return new GraphRequest(accessToken2, str2, bundle, HttpMethod.GET, callback2);
    }

    public static GraphRequest newDeleteObjectRequest(AccessToken accessToken2, String str, Callback callback2) {
        return new GraphRequest(accessToken2, str, (Bundle) null, HttpMethod.DELETE, callback2);
    }

    public static GraphRequest newGraphPathRequest(AccessToken accessToken2, String str, Callback callback2) {
        return new GraphRequest(accessToken2, str, (Bundle) null, (HttpMethod) null, callback2);
    }

    public static GraphRequest newMeRequest(AccessToken accessToken2, final GraphJSONObjectCallback graphJSONObjectCallback) {
        return new GraphRequest(accessToken2, ME, (Bundle) null, (HttpMethod) null, new Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                if (graphJSONObjectCallback != null) {
                    graphJSONObjectCallback.onCompleted(graphResponse.getJSONObject(), graphResponse);
                }
            }
        });
    }

    public static GraphRequest newMyFriendsRequest(AccessToken accessToken2, final GraphJSONArrayCallback graphJSONArrayCallback) {
        return new GraphRequest(accessToken2, MY_FRIENDS, (Bundle) null, (HttpMethod) null, new Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                if (graphJSONArrayCallback != null) {
                    JSONObject jSONObject = graphResponse.getJSONObject();
                    graphJSONArrayCallback.onCompleted(jSONObject != null ? jSONObject.optJSONArray("data") : null, graphResponse);
                }
            }
        });
    }

    public static GraphRequest newPlacesSearchRequest(AccessToken accessToken2, Location location, int i, int i2, String str, final GraphJSONArrayCallback graphJSONArrayCallback) {
        if (location != null || !Utility.isNullOrEmpty(str)) {
            Bundle bundle = new Bundle(5);
            bundle.putString("type", "place");
            bundle.putInt("limit", i2);
            if (location != null) {
                bundle.putString("center", String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())}));
                bundle.putInt("distance", i);
            }
            if (!Utility.isNullOrEmpty(str)) {
                bundle.putString("q", str);
            }
            return new GraphRequest(accessToken2, SEARCH, bundle, HttpMethod.GET, new Callback() {
                public void onCompleted(GraphResponse graphResponse) {
                    if (graphJSONArrayCallback != null) {
                        JSONObject jSONObject = graphResponse.getJSONObject();
                        graphJSONArrayCallback.onCompleted(jSONObject != null ? jSONObject.optJSONArray("data") : null, graphResponse);
                    }
                }
            });
        }
        throw new FacebookException("Either location or searchText must be specified.");
    }

    public static GraphRequest newPostRequest(AccessToken accessToken2, String str, JSONObject jSONObject, Callback callback2) {
        GraphRequest graphRequest = new GraphRequest(accessToken2, str, (Bundle) null, HttpMethod.POST, callback2);
        graphRequest.setGraphObject(jSONObject);
        return graphRequest;
    }

    /* access modifiers changed from: private */
    public static String parameterToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if ((obj instanceof Boolean) || (obj instanceof Number)) {
            return obj.toString();
        }
        if (obj instanceof Date) {
            return new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format(obj);
        }
        throw new IllegalArgumentException("Unsupported parameter type.");
    }

    private static void processGraphObject(JSONObject jSONObject, String str, KeyValueSerializer keyValueSerializer) throws IOException {
        boolean z = false;
        if (isMeRequest(str)) {
            int indexOf = str.indexOf(":");
            int indexOf2 = str.indexOf("?");
            z = indexOf > 3 && (indexOf2 == -1 || indexOf < indexOf2);
        }
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            processGraphObjectProperty(next, jSONObject.opt(next), keyValueSerializer, z && next.equalsIgnoreCase("image"));
        }
    }

    private static void processGraphObjectProperty(String str, Object obj, KeyValueSerializer keyValueSerializer, boolean z) throws IOException {
        Class<?> cls = obj.getClass();
        if (JSONObject.class.isAssignableFrom(cls)) {
            JSONObject jSONObject = (JSONObject) obj;
            if (z) {
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    processGraphObjectProperty(String.format("%s[%s]", new Object[]{str, next}), jSONObject.opt(next), keyValueSerializer, z);
                }
            } else if (jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_ID)) {
                processGraphObjectProperty(str, jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID), keyValueSerializer, z);
            } else if (jSONObject.has("url")) {
                processGraphObjectProperty(str, jSONObject.optString("url"), keyValueSerializer, z);
            } else if (jSONObject.has(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY)) {
                processGraphObjectProperty(str, jSONObject.toString(), keyValueSerializer, z);
            }
        } else if (JSONArray.class.isAssignableFrom(cls)) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                processGraphObjectProperty(String.format(Locale.ROOT, "%s[%d]", new Object[]{str, Integer.valueOf(i)}), jSONArray.opt(i), keyValueSerializer, z);
            }
        } else if (String.class.isAssignableFrom(cls) || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls)) {
            keyValueSerializer.writeString(str, obj.toString());
        } else if (Date.class.isAssignableFrom(cls)) {
            KeyValueSerializer keyValueSerializer2 = keyValueSerializer;
            String str2 = str;
            keyValueSerializer2.writeString(str2, new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format((Date) obj));
        }
    }

    private static void processRequest(GraphRequestBatch graphRequestBatch, Logger logger, int i, URL url, OutputStream outputStream, boolean z) throws IOException, JSONException {
        Serializer serializer = new Serializer(outputStream, logger, z);
        if (i == 1) {
            GraphRequest graphRequest = graphRequestBatch.get(0);
            HashMap hashMap = new HashMap();
            for (String str : graphRequest.parameters.keySet()) {
                Object obj = graphRequest.parameters.get(str);
                if (isSupportedAttachmentType(obj)) {
                    hashMap.put(str, new Attachment(graphRequest, obj));
                }
            }
            if (logger != null) {
                logger.append("  Parameters:\n");
            }
            serializeParameters(graphRequest.parameters, serializer, graphRequest);
            if (logger != null) {
                logger.append("  Attachments:\n");
            }
            serializeAttachments(hashMap, serializer);
            if (graphRequest.graphObject != null) {
                processGraphObject(graphRequest.graphObject, url.getPath(), serializer);
                return;
            }
            return;
        }
        String batchAppId = getBatchAppId(graphRequestBatch);
        if (Utility.isNullOrEmpty(batchAppId)) {
            throw new FacebookException("App ID was not specified at the request or Settings.");
        }
        serializer.writeString(BATCH_APP_ID_PARAM, batchAppId);
        HashMap hashMap2 = new HashMap();
        serializeRequestsAsJSON(serializer, graphRequestBatch, hashMap2);
        if (logger != null) {
            logger.append("  Attachments:\n");
        }
        serializeAttachments(hashMap2, serializer);
    }

    static void runCallbacks(final GraphRequestBatch graphRequestBatch, List<GraphResponse> list) {
        int size = graphRequestBatch.size();
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            GraphRequest graphRequest = graphRequestBatch.get(i);
            if (graphRequest.callback != null) {
                arrayList.add(new Pair(graphRequest.callback, list.get(i)));
            }
        }
        if (arrayList.size() > 0) {
            AnonymousClass5 r5 = new Runnable() {
                public void run() {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        ((Callback) pair.first).onCompleted((GraphResponse) pair.second);
                    }
                    for (GraphRequestBatch.Callback onBatchCompleted : graphRequestBatch.getCallbacks()) {
                        onBatchCompleted.onBatchCompleted(graphRequestBatch);
                    }
                }
            };
            Handler callbackHandler = graphRequestBatch.getCallbackHandler();
            if (callbackHandler == null) {
                r5.run();
            } else {
                callbackHandler.post(r5);
            }
        }
    }

    private static void serializeAttachments(Map<String, Attachment> map, Serializer serializer) throws IOException {
        for (String next : map.keySet()) {
            Attachment attachment = map.get(next);
            if (isSupportedAttachmentType(attachment.getValue())) {
                serializer.writeObject(next, attachment.getValue(), attachment.getRequest());
            }
        }
    }

    private static void serializeParameters(Bundle bundle, Serializer serializer, GraphRequest graphRequest) throws IOException {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (isSupportedParameterType(obj)) {
                serializer.writeObject(str, obj, graphRequest);
            }
        }
    }

    private static void serializeRequestsAsJSON(Serializer serializer, Collection<GraphRequest> collection, Map<String, Attachment> map) throws JSONException, IOException {
        JSONArray jSONArray = new JSONArray();
        for (GraphRequest serializeToBatch : collection) {
            serializeToBatch.serializeToBatch(jSONArray, map);
        }
        serializer.writeRequestsAsJson(BATCH_PARAM, jSONArray, collection);
    }

    private void serializeToBatch(JSONArray jSONArray, Map<String, Attachment> map) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject();
        if (this.batchEntryName != null) {
            jSONObject.put("name", this.batchEntryName);
            jSONObject.put(BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM, this.batchEntryOmitResultOnSuccess);
        }
        if (this.batchEntryDependsOn != null) {
            jSONObject.put(BATCH_ENTRY_DEPENDS_ON_PARAM, this.batchEntryDependsOn);
        }
        String urlForBatchedRequest = getUrlForBatchedRequest();
        jSONObject.put(BATCH_RELATIVE_URL_PARAM, urlForBatchedRequest);
        jSONObject.put("method", this.httpMethod);
        if (this.accessToken != null) {
            Logger.registerAccessToken(this.accessToken.getToken());
        }
        ArrayList arrayList = new ArrayList();
        for (String str : this.parameters.keySet()) {
            Object obj = this.parameters.get(str);
            if (isSupportedAttachmentType(obj)) {
                String format = String.format(Locale.ROOT, "%s%d", new Object[]{ATTACHMENT_FILENAME_PREFIX, Integer.valueOf(map.size())});
                arrayList.add(format);
                map.put(format, new Attachment(this, obj));
            }
        }
        if (!arrayList.isEmpty()) {
            jSONObject.put(ATTACHED_FILES_PARAM, TextUtils.join(",", arrayList));
        }
        if (this.graphObject != null) {
            final ArrayList arrayList2 = new ArrayList();
            processGraphObject(this.graphObject, urlForBatchedRequest, new KeyValueSerializer() {
                public void writeString(String str, String str2) throws IOException {
                    arrayList2.add(String.format(Locale.US, "%s=%s", new Object[]{str, URLEncoder.encode(str2, "UTF-8")}));
                }
            });
            jSONObject.put(BATCH_BODY_PARAM, TextUtils.join("&", arrayList2));
        }
        jSONArray.put(jSONObject);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00f4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final void serializeToUrlConnection(com.facebook.GraphRequestBatch r22, java.net.HttpURLConnection r23) throws java.io.IOException, org.json.JSONException {
        /*
            com.facebook.internal.Logger r20 = new com.facebook.internal.Logger
            com.facebook.LoggingBehavior r2 = com.facebook.LoggingBehavior.REQUESTS
            java.lang.String r3 = "Request"
            r0 = r20
            r0.<init>(r2, r3)
            int r4 = r22.size()
            boolean r7 = isGzipCompressible(r22)
            r2 = 1
            if (r4 != r2) goto L_0x0095
            r2 = 0
            r0 = r22
            com.facebook.GraphRequest r2 = r0.get((int) r2)
            com.facebook.HttpMethod r0 = r2.httpMethod
            r18 = r0
        L_0x0021:
            java.lang.String r2 = r18.name()
            r0 = r23
            r0.setRequestMethod(r2)
            r0 = r23
            setConnectionContentType(r0, r7)
            java.net.URL r5 = r23.getURL()
            java.lang.String r2 = "Request:\n"
            r0 = r20
            r0.append((java.lang.String) r2)
            java.lang.String r2 = "Id"
            java.lang.String r3 = r22.getId()
            r0 = r20
            r0.appendKeyValue(r2, r3)
            java.lang.String r2 = "URL"
            r0 = r20
            r0.appendKeyValue(r2, r5)
            java.lang.String r2 = "Method"
            java.lang.String r3 = r23.getRequestMethod()
            r0 = r20
            r0.appendKeyValue(r2, r3)
            java.lang.String r2 = "User-Agent"
            java.lang.String r3 = "User-Agent"
            r0 = r23
            java.lang.String r3 = r0.getRequestProperty(r3)
            r0 = r20
            r0.appendKeyValue(r2, r3)
            java.lang.String r2 = "Content-Type"
            java.lang.String r3 = "Content-Type"
            r0 = r23
            java.lang.String r3 = r0.getRequestProperty(r3)
            r0 = r20
            r0.appendKeyValue(r2, r3)
            int r2 = r22.getTimeout()
            r0 = r23
            r0.setConnectTimeout(r2)
            int r2 = r22.getTimeout()
            r0 = r23
            r0.setReadTimeout(r2)
            com.facebook.HttpMethod r2 = com.facebook.HttpMethod.POST
            r0 = r18
            if (r0 != r2) goto L_0x0098
            r19 = 1
        L_0x008f:
            if (r19 != 0) goto L_0x009b
            r20.log()
        L_0x0094:
            return
        L_0x0095:
            com.facebook.HttpMethod r18 = com.facebook.HttpMethod.POST
            goto L_0x0021
        L_0x0098:
            r19 = 0
            goto L_0x008f
        L_0x009b:
            r2 = 1
            r0 = r23
            r0.setDoOutput(r2)
            r8 = 0
            java.io.BufferedOutputStream r9 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00f1 }
            java.io.OutputStream r2 = r23.getOutputStream()     // Catch:{ all -> 0x00f1 }
            r9.<init>(r2)     // Catch:{ all -> 0x00f1 }
            if (r7 == 0) goto L_0x00b3
            java.util.zip.GZIPOutputStream r8 = new java.util.zip.GZIPOutputStream     // Catch:{ all -> 0x00f8 }
            r8.<init>(r9)     // Catch:{ all -> 0x00f8 }
            r9 = r8
        L_0x00b3:
            boolean r2 = hasOnProgressCallbacks(r22)     // Catch:{ all -> 0x00f8 }
            if (r2 == 0) goto L_0x00fb
            r6 = 0
            com.facebook.ProgressNoopOutputStream r6 = new com.facebook.ProgressNoopOutputStream     // Catch:{ all -> 0x00f8 }
            android.os.Handler r2 = r22.getCallbackHandler()     // Catch:{ all -> 0x00f8 }
            r6.<init>(r2)     // Catch:{ all -> 0x00f8 }
            r3 = 0
            r2 = r22
            processRequest(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00f8 }
            int r21 = r6.getMaxProgress()     // Catch:{ all -> 0x00f8 }
            java.util.Map r11 = r6.getProgressMap()     // Catch:{ all -> 0x00f8 }
            com.facebook.ProgressOutputStream r8 = new com.facebook.ProgressOutputStream     // Catch:{ all -> 0x00f8 }
            r0 = r21
            long r12 = (long) r0     // Catch:{ all -> 0x00f8 }
            r10 = r22
            r8.<init>(r9, r10, r11, r12)     // Catch:{ all -> 0x00f8 }
        L_0x00db:
            r12 = r22
            r13 = r20
            r14 = r4
            r15 = r5
            r16 = r8
            r17 = r7
            processRequest(r12, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x00f1 }
            if (r8 == 0) goto L_0x00ed
            r8.close()
        L_0x00ed:
            r20.log()
            goto L_0x0094
        L_0x00f1:
            r2 = move-exception
        L_0x00f2:
            if (r8 == 0) goto L_0x00f7
            r8.close()
        L_0x00f7:
            throw r2
        L_0x00f8:
            r2 = move-exception
            r8 = r9
            goto L_0x00f2
        L_0x00fb:
            r8 = r9
            goto L_0x00db
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.GraphRequest.serializeToUrlConnection(com.facebook.GraphRequestBatch, java.net.HttpURLConnection):void");
    }

    private static void setConnectionContentType(HttpURLConnection httpURLConnection, boolean z) {
        if (z) {
            httpURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty(CONTENT_ENCODING_HEADER, "gzip");
            return;
        }
        httpURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, getMimeContentType());
    }

    public static final void setDefaultBatchApplicationId(String str) {
        defaultBatchApplicationId = str;
    }

    public static HttpURLConnection toHttpConnection(GraphRequestBatch graphRequestBatch) {
        try {
            try {
                HttpURLConnection createConnection = createConnection(graphRequestBatch.size() == 1 ? new URL(graphRequestBatch.get(0).getUrlForSingleRequest()) : new URL(ServerProtocol.getGraphUrlBase()));
                serializeToUrlConnection(graphRequestBatch, createConnection);
                return createConnection;
            } catch (IOException e) {
                throw new FacebookException("could not construct request body", (Throwable) e);
            } catch (JSONException e2) {
                throw new FacebookException("could not construct request body", (Throwable) e2);
            }
        } catch (MalformedURLException e3) {
            throw new FacebookException("could not construct URL for request", (Throwable) e3);
        }
    }

    public static HttpURLConnection toHttpConnection(Collection<GraphRequest> collection) {
        Validate.notEmptyAndContainsNoNulls(collection, "requests");
        return toHttpConnection(new GraphRequestBatch(collection));
    }

    public static HttpURLConnection toHttpConnection(GraphRequest... graphRequestArr) {
        return toHttpConnection((Collection<GraphRequest>) Arrays.asList(graphRequestArr));
    }

    public final GraphResponse executeAndWait() {
        return executeAndWait(this);
    }

    public final GraphRequestAsyncTask executeAsync() {
        return executeBatchAsync(this);
    }

    public final AccessToken getAccessToken() {
        return this.accessToken;
    }

    public final String getBatchEntryDependsOn() {
        return this.batchEntryDependsOn;
    }

    public final String getBatchEntryName() {
        return this.batchEntryName;
    }

    public final boolean getBatchEntryOmitResultOnSuccess() {
        return this.batchEntryOmitResultOnSuccess;
    }

    public final Callback getCallback() {
        return this.callback;
    }

    public final JSONObject getGraphObject() {
        return this.graphObject;
    }

    public final String getGraphPath() {
        return this.graphPath;
    }

    public final HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public final Bundle getParameters() {
        return this.parameters;
    }

    public final Object getTag() {
        return this.tag;
    }

    /* access modifiers changed from: package-private */
    public final String getUrlForBatchedRequest() {
        if (this.overriddenURL != null) {
            throw new FacebookException("Can't override URL for a batch request");
        }
        String graphPathWithVersion = getGraphPathWithVersion();
        addCommonParameters();
        return appendParametersToBaseUrl(graphPathWithVersion);
    }

    /* access modifiers changed from: package-private */
    public final String getUrlForSingleRequest() {
        if (this.overriddenURL != null) {
            return this.overriddenURL.toString();
        }
        String format = String.format("%s/%s", new Object[]{(getHttpMethod() != HttpMethod.POST || this.graphPath == null || !this.graphPath.endsWith(VIDEOS_SUFFIX)) ? ServerProtocol.getGraphUrlBase() : ServerProtocol.getGraphVideoUrlBase(), getGraphPathWithVersion()});
        addCommonParameters();
        return appendParametersToBaseUrl(format);
    }

    public final String getVersion() {
        return this.version;
    }

    public final void setAccessToken(AccessToken accessToken2) {
        this.accessToken = accessToken2;
    }

    public final void setBatchEntryDependsOn(String str) {
        this.batchEntryDependsOn = str;
    }

    public final void setBatchEntryName(String str) {
        this.batchEntryName = str;
    }

    public final void setBatchEntryOmitResultOnSuccess(boolean z) {
        this.batchEntryOmitResultOnSuccess = z;
    }

    public final void setCallback(final Callback callback2) {
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO) || FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.callback = new Callback() {
                public void onCompleted(GraphResponse graphResponse) {
                    JSONObject jSONObject = graphResponse.getJSONObject();
                    JSONObject optJSONObject = jSONObject != null ? jSONObject.optJSONObject(GraphRequest.DEBUG_KEY) : null;
                    JSONArray optJSONArray = optJSONObject != null ? optJSONObject.optJSONArray(GraphRequest.DEBUG_MESSAGES_KEY) : null;
                    if (optJSONArray != null) {
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                            String optString = optJSONObject2 != null ? optJSONObject2.optString("message") : null;
                            String optString2 = optJSONObject2 != null ? optJSONObject2.optString("type") : null;
                            String optString3 = optJSONObject2 != null ? optJSONObject2.optString("link") : null;
                            if (!(optString == null || optString2 == null)) {
                                LoggingBehavior loggingBehavior = LoggingBehavior.GRAPH_API_DEBUG_INFO;
                                if (optString2.equals(GraphRequest.DEBUG_SEVERITY_WARNING)) {
                                    loggingBehavior = LoggingBehavior.GRAPH_API_DEBUG_WARNING;
                                }
                                if (!Utility.isNullOrEmpty(optString3)) {
                                    optString = optString + " Link: " + optString3;
                                }
                                Logger.log(loggingBehavior, GraphRequest.TAG, optString);
                            }
                        }
                    }
                    if (callback2 != null) {
                        callback2.onCompleted(graphResponse);
                    }
                }
            };
        } else {
            this.callback = callback2;
        }
    }

    public final void setGraphObject(JSONObject jSONObject) {
        this.graphObject = jSONObject;
    }

    public final void setGraphPath(String str) {
        this.graphPath = str;
    }

    public final void setHttpMethod(HttpMethod httpMethod2) {
        if (this.overriddenURL == null || httpMethod2 == HttpMethod.GET) {
            if (httpMethod2 == null) {
                httpMethod2 = HttpMethod.GET;
            }
            this.httpMethod = httpMethod2;
            return;
        }
        throw new FacebookException("Can't change HTTP method on request with overridden URL.");
    }

    public final void setParameters(Bundle bundle) {
        this.parameters = bundle;
    }

    public final void setSkipClientToken(boolean z) {
        this.skipClientToken = z;
    }

    public final void setTag(Object obj) {
        this.tag = obj;
    }

    public final void setVersion(String str) {
        this.version = str;
    }

    public String toString() {
        return "{Request: " + " accessToken: " + (this.accessToken == null ? "null" : this.accessToken) + ", graphPath: " + this.graphPath + ", graphObject: " + this.graphObject + ", httpMethod: " + this.httpMethod + ", parameters: " + this.parameters + "}";
    }
}
