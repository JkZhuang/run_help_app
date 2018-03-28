package com.zjk;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

/**
 * Created by pandengzhe on 2018/3/27.
 */

public class Test {

    @SuppressLint("StaticFieldLeak")
    public static void test() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute();
    }
}
