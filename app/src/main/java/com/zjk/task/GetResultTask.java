package com.zjk.task;

import android.os.AsyncTask;

import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public abstract class GetResultTask<R extends Result> extends AsyncTask<Void, Void, R> {

    @Override
    protected R doInBackground(Void... ps) {
        return onBackground();
    }

    @Override
    protected void onPostExecute(R r) {
        onExecute(r);
    }

    public abstract R onBackground();

    public abstract void onExecute(R result);
}