package com.zjk.logic;

import android.annotation.SuppressLint;

import com.zjk.okhttp.HttpHelper;
import com.zjk.param.ChangeUserInfoParam;
import com.zjk.param.CommentForumParam;
import com.zjk.param.GetFeedbackParam;
import com.zjk.param.GetForumParam;
import com.zjk.param.GetRankingVersionParam;
import com.zjk.param.GetSportsSuggestionParam;
import com.zjk.param.GetUserSportsDataParam;
import com.zjk.param.GetUserSportsSuggestedDataParam;
import com.zjk.param.LikeForumParam;
import com.zjk.param.LoginParam;
import com.zjk.param.Param;
import com.zjk.param.PublishForumParam;
import com.zjk.param.RegisteredParam;
import com.zjk.param.SetFeedbackParam;
import com.zjk.param.UpdateFallThresholdParam;
import com.zjk.param.UploadImageParam;
import com.zjk.param.UploadSportsDataParam;
import com.zjk.result.ChangeUserInfoResult;
import com.zjk.result.CommentForumResult;
import com.zjk.result.GetFeedbackResult;
import com.zjk.result.GetForumResult;
import com.zjk.result.GetRankingVersionResult;
import com.zjk.result.GetSportsSuggestionResult;
import com.zjk.result.GetUserSportsDataResult;
import com.zjk.result.GetUserSportsSuggestedDataResult;
import com.zjk.result.LikeForumResult;
import com.zjk.result.LoginResult;
import com.zjk.result.PublishForumResult;
import com.zjk.result.RegisteredResult;
import com.zjk.result.Result;
import com.zjk.result.SetFeedbackResult;
import com.zjk.result.UpdateFallThresholdResult;
import com.zjk.result.UploadImageResult;
import com.zjk.task.GetResultTask;
import com.zjk.util.CommonsUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/24
 */

public class LogicImpl implements Logic {

    private static final String TAG = "LogicImpl";

    private Executor executor = Executors.newFixedThreadPool(CommonsUtil.getCoreNum() * 2);

    private static LogicImpl instance;

    private LogicImpl() {

    }

    public static LogicImpl getInstance() {
        if (instance == null) {
            synchronized (LogicImpl.class) {
                if (instance == null) {
                    instance = new LogicImpl();
                }
            }
        }
        return instance;
    }

    private <P extends Param, R extends Result> void getResult(final P param, final LogicHandler<R> handler, final Class<R> clazz) {
        @SuppressLint("StaticFieldLeak")
        GetResultTask<R> task = new GetResultTask<R>() {
            @Override
            public R onBackground() {
                R result = HttpHelper.instance().post(param, clazz);
                handler.onResult(result, false);
                return result;
            }

            @Override
            public void onExecute(R result) {
                handler.onResult(result, true);
            }
        };
        task.executeOnExecutor(executor);
    }

    @Override
    public void register(RegisteredParam param, LogicHandler<RegisteredResult> handler) {
        getUploadResult(param, handler, RegisteredResult.class, param.userInfo.getHeadUrl());
    }

    @Override
    public void login(LoginParam param, LogicHandler<LoginResult> handler) {
        getResult(param, handler, LoginResult.class);
    }

    @Override
    public void changeUserInfo(ChangeUserInfoParam param, LogicHandler<ChangeUserInfoResult> handler) {
        getUploadResult(param, handler, ChangeUserInfoResult.class, param.userInfo.getHeadUrl());
    }

    @Override
    public void uploadSportsData(UploadSportsDataParam param, LogicHandler<UpdateFallThresholdResult> handler) {
        getResult(param, handler, UpdateFallThresholdResult.class);
    }

    @Override
    public void getUserSportsData(GetUserSportsDataParam param, LogicHandler<GetUserSportsDataResult> handler) {
        getResult(param, handler, GetUserSportsDataResult.class);
    }

    @Override
    public void getRankingVersion(GetRankingVersionParam param, LogicHandler<GetRankingVersionResult> handler) {
        getResult(param, handler, GetRankingVersionResult.class);
    }

    @Override
    public void getUserSportsSuggestedData(GetUserSportsSuggestedDataParam param, LogicHandler<GetUserSportsSuggestedDataResult> handler) {
        getResult(param, handler, GetUserSportsSuggestedDataResult.class);
    }

    @Override
    public void getSportsSuggestion(GetSportsSuggestionParam param, LogicHandler<GetSportsSuggestionResult> handler) {
        getResult(param, handler, GetSportsSuggestionResult.class);
    }

    @Override
    public void updateFallThreshold(UpdateFallThresholdParam param, LogicHandler<UpdateFallThresholdResult> handler) {
        getResult(param, handler, UpdateFallThresholdResult.class);
    }

    @Override
    public void publishForum(PublishForumParam param, LogicHandler<PublishForumResult> handler) {
        getUploadResult(param, handler, PublishForumResult.class, param.forumInfo.getPhotoUrl());
    }

    @Override
    public void commentForum(CommentForumParam param, LogicHandler<CommentForumResult> handler) {
        getResult(param, handler, CommentForumResult.class);
    }

    @Override
    public void likeForum(LikeForumParam param, LogicHandler<LikeForumResult> handler) {
        getResult(param, handler, LikeForumResult.class);
    }

    @Override
    public void getForum(GetForumParam param, LogicHandler<GetForumResult> handler) {
        getResult(param, handler, GetForumResult.class);
    }

    @Override
    public void setFeedback(SetFeedbackParam param, LogicHandler<SetFeedbackResult> handler) {
        getResult(param, handler, SetFeedbackResult.class);
    }

    @Override
    public void getFeedback(GetFeedbackParam param, LogicHandler<GetFeedbackResult> handler) {
        getResult(param, handler, GetFeedbackResult.class);
    }

    @Override
    public void uploadImage(UploadImageParam param, LogicHandler<UploadImageResult> handler) {
        getUploadResult(param, handler, UploadImageResult.class, param.path);
    }

    private <P extends Param, R extends Result> void getUploadResult(final P param, final LogicHandler<R> handler, final Class<R> clazz, final String path) {
        @SuppressLint("StaticFieldLeak")
        GetResultTask<R> task = new GetResultTask<R>() {
            @Override
                public R onBackground() {
                R result = HttpHelper.instance().postFile(param, clazz, path);
                handler.onResult(result, false);
                return result;
            }

            @Override
            public void onExecute(R result) {
                handler.onResult(result, true);
            }
        };
        task.executeOnExecutor(executor);
    }
}
