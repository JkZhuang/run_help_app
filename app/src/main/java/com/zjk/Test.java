package com.zjk;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zjk.model.UserInfo;
import com.zjk.okhttp.HttpHelper;
import com.zjk.param.RegisteredParam;
import com.zjk.result.RegisteredResult;

import java.io.IOException;

/**
 * Created by pandengzhe on 2018/3/27.
 */

public class Test {

    public static void test() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                RegisteredParam param = new RegisteredParam();
                UserInfo userInfo = new UserInfo("18813295244", "123456", "攀登者",
                        "https://f12.baidu.com/it/u=2465775762,1509670197&fm=72", 170,
                        60, 24, 0, "18813295244");
                param.userInfo = userInfo;
                String json = new Gson().toJson(param);
                return HttpHelper.getInstance().postToServer("/register", json);
        }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // 处理结果
                RegisteredResult result = new Gson().fromJson(s, RegisteredResult.class);
//                Log.d("result:", "bool-> " + result.bool);
            }
        }.execute();


    }

    private void testPort() {
        RegisteredParam param = new RegisteredParam();
        UserInfo info = new UserInfo("18813295246", "123456", "jinkun",
                "", 178, 70, 23, 0, "18813295244");
        param.userInfo = info;
        String json = new Gson().toJson(param);
        String response = "";
        response = HttpHelper.getInstance().postToServer("/login_app", json);
        RegisteredResult result = new Gson().fromJson(response, RegisteredResult.class);
        Log.d("result:", "bool-> " + result.bool);
    }
}
