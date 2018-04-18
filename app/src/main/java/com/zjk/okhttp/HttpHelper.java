package com.zjk.okhttp;

import android.text.TextUtils;

import com.zjk.param.Param;
import com.zjk.result.Result;
import com.zjk.util.DebugUtil;
import com.zjk.util.GsonUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by TZH on 2016/7/25.
 */
public class HttpHelper {

    private static final String TAG = "HttpHelper";

    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    private static final int HTTP_TIMEOUT = 15 * 1000;
    private static final int HTTP_FILE_TIMEOUT = 30 * 60 * 1000;

    private static HttpHelper instance;

    private final Object mHttpClientLock = new Object();
    private OkHttpClient mHttpClient;

    private final Object mFileHttpClientLock = new Object();
    private OkHttpClient mFileHttpClient;

    private HttpHelper() {
        initHttpClient();
        initFileHttpClient();
    }

    public static synchronized HttpHelper instance() {
        if (instance == null) {
            instance = new HttpHelper();
        }
        return instance;
    }

    private OkHttpClient initHttpClient() {
        synchronized (mHttpClientLock) {
            if (mHttpClient == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.readTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.writeTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
                mHttpClient = builder.build();
            }
            return mHttpClient;
        }
    }

    private OkHttpClient initFileHttpClient() {
        synchronized (mFileHttpClientLock) {
            if (mFileHttpClient == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(HTTP_FILE_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.readTimeout(HTTP_FILE_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.writeTimeout(HTTP_FILE_TIMEOUT, TimeUnit.MILLISECONDS);
                mFileHttpClient = builder.build();
            }
            return mFileHttpClient;
        }
    }

    public <P extends Param, R extends Result> R post(P param, Class<R> clazz) {
        String targetUrl = DefList.url + param.page;
        String json = GsonUtil.toJson(param);
        DebugUtil.debug(TAG, "url->" + targetUrl + "; json->" + json);
        String response;
        try {
            response = toServer(targetUrl, json, Charset.defaultCharset(), METHOD_POST);
        } catch (IOException e) {
            response = "{\"status\":0, \"errMsg\":\"服务器有点问题，请稍后重试！\"}";
        }
        return GsonUtil.toObj(response, clazz);
    }

    public <P extends Param, R extends Result> R get(P param, Class<R> clazz) {
        String targetUrl = DefList.url + param.page;
        String json = GsonUtil.toJson(param);
        DebugUtil.debug(TAG, "url->" + targetUrl + "; json->" + json);
        String response;
        try {
            response = toServer(targetUrl, json, Charset.defaultCharset(), METHOD_GET);
        } catch (IOException e) {
            response = "{\"errMsg\":\"服务器有点问题，请稍后重试！\",\"status\":0}";
        }
        return GsonUtil.toObj(response, clazz);
    }

    public <P extends Param, R extends Result> R put(P param, Class<R> clazz) {
        String targetUrl = DefList.url + param.page;
        String json = GsonUtil.toJson(param);
        DebugUtil.debug(TAG, "url->" + targetUrl + "; json->" + json);
        String response;
        try {
            response = toServer(targetUrl, json, Charset.defaultCharset(), METHOD_PUT);
        } catch (IOException e) {
            response = "{\"errMsg\":\"服务器有点问题，请稍后重试！\",\"status\":0}";
        }
        return GsonUtil.toObj(response, clazz);
    }

    public <P extends Param, R extends Result> R delete(P param, Class<R> clazz) {
        String targetUrl = DefList.url + param.page;
        String json = GsonUtil.toJson(param);
        DebugUtil.debug(TAG, "url->" + targetUrl + "; json->" + json);
        String response;
        try {
            response = toServer(targetUrl, json, Charset.defaultCharset(), METHOD_DELETE);
        } catch (IOException e) {
            response = "{\"errMsg\":\"服务器有点问题，请稍后重试！\",\"status\":0}";
        }
        return GsonUtil.toObj(response, clazz);
    }

    private String toServer(String url, String json, Charset charset, String method) throws IOException {
        String response = bytesToString(toServer(url, json, method), charset);
        if (response == null) {
            response = "{\"status\":0, \"errMsg\":\"链接可能有点问题，请升级到最新版本！\"}";
        }
        DebugUtil.debug(TAG, "response->" + response);
        return response;
    }

    private byte[] toServer(String url, String json, String method) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse(DefList.JSON_CONTENT_TYPE), json);
        Request request = new Request.Builder()
                .url(url)
                .method(method, body)
                .build();
        Response response = mHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().bytes();
        } else {
            return null;
        }
    }

    public <P extends Param, R extends Result> R postFile(P param, Class<R> clazz, String path) {
        String targetUrl = DefList.url + param.page;
        String json = GsonUtil.toJson(param);
        DebugUtil.debug(TAG, "url->" + targetUrl + "; json->" + json);
        String response;
        try {
            response = fileToServer(targetUrl, json, Charset.defaultCharset(), METHOD_POST, path);
        } catch (IOException e) {
            response = "{\"status\":0, \"errMsg\":\"服务器有点问题，请稍后重试！\"}";
        }
        return GsonUtil.toObj(response, clazz);
    }

    private String fileToServer(String url, String json, Charset charset, String method, String path) throws IOException {
        String response = bytesToString(fileFormsToServer(url, json, method, path), charset);
        if (response == null) {
            response = "{\"status\":0, \"errMsg\":\"链接可能有点问题，请升级到最新版本！\"}";
        }
        DebugUtil.debug(TAG, "response->" + response);
        return response;
    }

    private byte[] fileFormsToServer(String url, String json, String method, String path) throws IOException {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            DebugUtil.debug(TAG, "路径错误！");
            return null;
        }
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("json", json)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(DefList.FILE_CONTENT_TYPE), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method(method, body)
                .build();
        Response response = mFileHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().bytes();
        } else {
            return null;
        }
    }

    private String bytesToString(byte[] data, Charset charset) {
        if (data != null) {
            return new String(data, charset);
        } else {
            return null;
        }
    }
}
