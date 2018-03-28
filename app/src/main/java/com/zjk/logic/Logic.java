package com.zjk.logic;

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
import com.zjk.param.PublishForumParam;
import com.zjk.param.RegisteredParam;
import com.zjk.param.SetFeedbackParam;
import com.zjk.param.UpdateFallThresholdParam;
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
import com.zjk.result.SetFeedbackResult;
import com.zjk.result.UpdateFallThresholdResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/24
 */

public interface Logic {

    void register(RegisteredParam param, LogicHandler<RegisteredResult> handler);

    void login(LoginParam param, LogicHandler<LoginResult> handler);

    void changeUserInfo(ChangeUserInfoParam param, LogicHandler<ChangeUserInfoResult> handler);

    void uploadSportsData(UploadSportsDataParam param, LogicHandler<UpdateFallThresholdResult> handler);

    void getUserSportsData(GetUserSportsDataParam param, LogicHandler<GetUserSportsDataResult> handler);

    void getRankingVersion(GetRankingVersionParam param, LogicHandler<GetRankingVersionResult> handler);

    void getUserSportsSuggestedData(GetUserSportsSuggestedDataParam param, LogicHandler<GetUserSportsSuggestedDataResult> handler);

    void getSportsSuggestion(GetSportsSuggestionParam param, LogicHandler<GetSportsSuggestionResult> handler);

    void updateFallThreshold(UpdateFallThresholdParam param, LogicHandler<UpdateFallThresholdResult> handler);

    void publishForum(PublishForumParam param, LogicHandler<PublishForumResult> handler);

    void commentForum(CommentForumParam param, LogicHandler<CommentForumResult> handler);

    void likeForum(LikeForumParam param, LogicHandler<LikeForumResult> handler);

    void getForum(GetForumParam param, LogicHandler<GetForumResult> handler);

    void setFeedback(SetFeedbackParam param, LogicHandler<SetFeedbackResult> handler);

    void getFeedback(GetFeedbackParam param, LogicHandler<GetFeedbackResult> handler);
}
