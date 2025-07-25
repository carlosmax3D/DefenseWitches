package com.facebook;

import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import twitter4j.HttpResponseCode;

public class GraphResponse {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
    private static final String RESPONSE_LOG_TAG = "Response";
    public static final String SUCCESS_KEY = "success";
    private final HttpURLConnection connection;
    private final FacebookRequestError error;
    private final JSONObject graphObject;
    private final JSONArray graphObjectArray;
    private final String rawResponse;
    private final GraphRequest request;

    public enum PagingDirection {
        NEXT,
        PREVIOUS
    }

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, FacebookRequestError facebookRequestError) {
        this(graphRequest, httpURLConnection, (String) null, (JSONObject) null, (JSONArray) null, facebookRequestError);
    }

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, String str, JSONArray jSONArray) {
        this(graphRequest, httpURLConnection, str, (JSONObject) null, jSONArray, (FacebookRequestError) null);
    }

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, String str, JSONObject jSONObject) {
        this(graphRequest, httpURLConnection, str, jSONObject, (JSONArray) null, (FacebookRequestError) null);
    }

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, String str, JSONObject jSONObject, JSONArray jSONArray, FacebookRequestError facebookRequestError) {
        this.request = graphRequest;
        this.connection = httpURLConnection;
        this.rawResponse = str;
        this.graphObject = jSONObject;
        this.graphObjectArray = jSONArray;
        this.error = facebookRequestError;
    }

    static List<GraphResponse> constructErrorResponses(List<GraphRequest> list, HttpURLConnection httpURLConnection, FacebookException facebookException) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new GraphResponse(list.get(i), httpURLConnection, new FacebookRequestError(httpURLConnection, facebookException)));
        }
        return arrayList;
    }

    private static GraphResponse createResponseFromObject(GraphRequest graphRequest, HttpURLConnection httpURLConnection, Object obj, Object obj2) throws JSONException {
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            FacebookRequestError checkResponseAndCreateError = FacebookRequestError.checkResponseAndCreateError(jSONObject, obj2, httpURLConnection);
            if (checkResponseAndCreateError != null) {
                if (checkResponseAndCreateError.getErrorCode() == 190 && Utility.isCurrentAccessToken(graphRequest.getAccessToken())) {
                    AccessToken.setCurrentAccessToken((AccessToken) null);
                }
                return new GraphResponse(graphRequest, httpURLConnection, checkResponseAndCreateError);
            }
            Object stringPropertyAsJSON = Utility.getStringPropertyAsJSON(jSONObject, BODY_KEY, NON_JSON_RESPONSE_PROPERTY);
            if (stringPropertyAsJSON instanceof JSONObject) {
                return new GraphResponse(graphRequest, httpURLConnection, stringPropertyAsJSON.toString(), (JSONObject) stringPropertyAsJSON);
            }
            if (stringPropertyAsJSON instanceof JSONArray) {
                return new GraphResponse(graphRequest, httpURLConnection, stringPropertyAsJSON.toString(), (JSONArray) stringPropertyAsJSON);
            }
            obj = JSONObject.NULL;
        }
        if (obj == JSONObject.NULL) {
            return new GraphResponse(graphRequest, httpURLConnection, obj.toString(), (JSONObject) null);
        }
        throw new FacebookException("Got unexpected object type in response, class: " + obj.getClass().getSimpleName());
    }

    private static List<GraphResponse> createResponsesFromObject(HttpURLConnection httpURLConnection, List<GraphRequest> list, Object obj) throws FacebookException, JSONException {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        Object obj2 = obj;
        if (size == 1) {
            GraphRequest graphRequest = list.get(0);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(BODY_KEY, obj);
                jSONObject.put(CODE_KEY, httpURLConnection != null ? httpURLConnection.getResponseCode() : HttpResponseCode.OK);
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(jSONObject);
                obj = jSONArray;
            } catch (JSONException e) {
                arrayList.add(new GraphResponse(graphRequest, httpURLConnection, new FacebookRequestError(httpURLConnection, e)));
            } catch (IOException e2) {
                arrayList.add(new GraphResponse(graphRequest, httpURLConnection, new FacebookRequestError(httpURLConnection, e2)));
            }
        }
        if (!(obj instanceof JSONArray) || ((JSONArray) obj).length() != size) {
            throw new FacebookException("Unexpected number of results");
        }
        JSONArray jSONArray2 = (JSONArray) obj;
        for (int i = 0; i < jSONArray2.length(); i++) {
            GraphRequest graphRequest2 = list.get(i);
            try {
                arrayList.add(createResponseFromObject(graphRequest2, httpURLConnection, jSONArray2.get(i), obj2));
            } catch (JSONException e3) {
                arrayList.add(new GraphResponse(graphRequest2, httpURLConnection, new FacebookRequestError(httpURLConnection, e3)));
            } catch (FacebookException e4) {
                arrayList.add(new GraphResponse(graphRequest2, httpURLConnection, new FacebookRequestError(httpURLConnection, e4)));
            }
        }
        return arrayList;
    }

    static List<GraphResponse> createResponsesFromStream(InputStream inputStream, HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) throws FacebookException, JSONException, IOException {
        String readStreamToString = Utility.readStreamToString(inputStream);
        Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, RESPONSE_LOG_TAG, "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf(readStreamToString.length()), readStreamToString);
        return createResponsesFromString(readStreamToString, httpURLConnection, graphRequestBatch);
    }

    static List<GraphResponse> createResponsesFromString(String str, HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) throws FacebookException, JSONException, IOException {
        List<GraphResponse> createResponsesFromObject = createResponsesFromObject(httpURLConnection, graphRequestBatch, new JSONTokener(str).nextValue());
        Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", graphRequestBatch.getId(), Integer.valueOf(str.length()), createResponsesFromObject);
        return createResponsesFromObject;
    }

    static List<GraphResponse> fromHttpConnection(HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        List<GraphResponse> constructErrorResponses;
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getResponseCode() >= 400 ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();
            constructErrorResponses = createResponsesFromStream(inputStream, httpURLConnection, graphRequestBatch);
        } catch (FacebookException e) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", e);
            constructErrorResponses = constructErrorResponses(graphRequestBatch, httpURLConnection, e);
        } catch (JSONException e2) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", e2);
            constructErrorResponses = constructErrorResponses(graphRequestBatch, httpURLConnection, new FacebookException((Throwable) e2));
        } catch (IOException e3) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", e3);
            constructErrorResponses = constructErrorResponses(graphRequestBatch, httpURLConnection, new FacebookException((Throwable) e3));
        } catch (SecurityException e4) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", e4);
            constructErrorResponses = constructErrorResponses(graphRequestBatch, httpURLConnection, new FacebookException((Throwable) e4));
        } finally {
            Utility.closeQuietly(inputStream);
        }
        return constructErrorResponses;
    }

    public final HttpURLConnection getConnection() {
        return this.connection;
    }

    public final FacebookRequestError getError() {
        return this.error;
    }

    public final JSONArray getJSONArray() {
        return this.graphObjectArray;
    }

    public final JSONObject getJSONObject() {
        return this.graphObject;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }

    public GraphRequest getRequest() {
        return this.request;
    }

    public GraphRequest getRequestForPagedResults(PagingDirection pagingDirection) {
        JSONObject optJSONObject;
        String str = null;
        if (!(this.graphObject == null || (optJSONObject = this.graphObject.optJSONObject("paging")) == null)) {
            str = pagingDirection == PagingDirection.NEXT ? optJSONObject.optString("next") : optJSONObject.optString("previous");
        }
        if (Utility.isNullOrEmpty(str)) {
            return null;
        }
        if (str != null && str.equals(this.request.getUrlForSingleRequest())) {
            return null;
        }
        try {
            return new GraphRequest(this.request.getAccessToken(), new URL(str));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public String toString() {
        String str;
        try {
            Locale locale = Locale.US;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.connection != null ? this.connection.getResponseCode() : HttpResponseCode.OK);
            str = String.format(locale, "%d", objArr);
        } catch (IOException e) {
            str = "unknown";
        }
        return "{Response: " + " responseCode: " + str + ", graphObject: " + this.graphObject + ", error: " + this.error + "}";
    }
}
