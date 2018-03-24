package com.zjk.logic;

import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/24
 */

public interface LogicHandler<R extends Result> {
    void onResult(R result, boolean onUIThread);
}
