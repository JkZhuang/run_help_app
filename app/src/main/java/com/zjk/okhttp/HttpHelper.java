package com.zjk.okhttp;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by TZH on 2016/7/25.
 */
public class HttpHelper {

    private HttpHelper() {
        //no instance
    }

    private static final HttpHelper sInstance = new HttpHelper();

    public static HttpHelper getInstance() {
        return sInstance;
    }

    private final int TIMEOUT = 15 * 1000;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    public String postToServer(String url, String json) {
        Log.d("request->", url + json);
        mOkHttpClient.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS).build();
//        String ip = SharedPreferencesUtils.getInstance().getServerIP();
//        int port = SharedPreferencesUtils.getInstance().getServerPort();
        String ip = "192.168.43.4";
        int port = 8080;
        String r = null;
        try {
//            r = post("http://" + ip + ":" + port + url, json, Charset.defaultCharset());
            String string = "http://192.168.43.4:8080/user/register";
            r = post(string, json, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            r = "{\"msg\":\"服务器有点问题，请稍后再试！\",\"retcode\":0}";
        }
        return r;
    }

    public String post(String urlSpec, String json, Charset charset) throws IOException {
        String response = bytesToString(post(urlSpec, json), charset);
        if (response != null) {
            Log.d("response->", response);
        } else {
            Log.d("response->", "null");
        }
        return response;
    }

    public byte[] post(String urlSpec, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(urlSpec)
                .post(body)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().bytes();
        } else {
            return null;
        }
    }

    public String doGetFromServer(String url) throws IOException {
        Log.d("request->", url);
//        String ip = SharedPreferencesUtils.getInstance().getServerIP();
//        int port = SharedPreferencesUtils.getInstance().getServerPort();
        String ip = "192.168.43.4";
        int port = 8080;
        return doGet("http://" + ip + ":" + port + url, Charset.defaultCharset());
    }

    public String doGet(String urlSpec, Charset charset) throws IOException {
        String s = bytesToString(doGet(urlSpec), charset);
        if (s != null) {
            Log.d("response->", s);
        } else {
            Log.d("response->", "null");
        }
        return s;
    }

    public byte[] doGet(String urlSpec) throws IOException {
        Request request = new Request.Builder()
                .url(urlSpec)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
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
