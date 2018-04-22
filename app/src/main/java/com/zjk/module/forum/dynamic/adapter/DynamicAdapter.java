package com.zjk.module.forum.dynamic.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjk.common.app.App;
import com.zjk.common.ui.BaseActivity;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.module.forum.dynamic.present.DynamicPresenter;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;
import com.zjk.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public class DynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DynamicAdapter";

    private BaseActivity mContext;
    private DynamicPresenter mPresenter;
    private List<ForumInfo> data;

    public DynamicAdapter(BaseActivity context, DynamicPresenter presenter) {
        this.mContext = context;
        mPresenter = presenter;
        data = new ArrayList<>();
    }

    public void setData(List<ForumInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<ForumInfo> getData() {
        return data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DynamicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dynamic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DynamicViewHolder) holder).bindData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 是否点过赞
     * @param lFList
     * @param uId
     * @return
     */
    private boolean checkHasLike(ArrayList<LikeForumInfo> lFList, int uId) {
        if (lFList == null) {
            return false;
        }
        boolean bool = false;
        for (LikeForumInfo info : lFList) {
            if (info.getuId() == uId) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    private String getLikeNickName(ArrayList<LikeForumInfo> lFList) {
        if (lFList == null || lFList.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int i;
        for (i = 0; i < lFList.size() - 1; i++) {
            LikeForumInfo info = lFList.get(i);
            builder.append(info.getUserName());
            builder.append(",");
        }
        LikeForumInfo info = lFList.get(i);        builder.append(info.getUserName());
        return builder.toString();
    }

    private LikeForumInfo createLikeForum(ForumInfo info) {
        ArrayList<LikeForumInfo> list = info.getlFList();
        LikeForumInfo likeForumInfo;
        if (list != null) {
            for (LikeForumInfo lInfo : list) {
                if (lInfo.getuId() == mContext.getUserInfo().getuId()) {
                    likeForumInfo = lInfo;
                    return likeForumInfo;
                }
            }
        }
        likeForumInfo = new LikeForumInfo();
        likeForumInfo.setfId(info.getfId());
        likeForumInfo.setuId(mContext.getUserInfo().getuId());
        likeForumInfo.setUserName(mContext.getUserInfo().getUserName());
        likeForumInfo.setTime(new Date());
        return likeForumInfo;
    }

    class DynamicViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvHeadPhoto;
        private TextView mTvNickName;
        private TextView mTvContent;
        private ImageView mIvContent;
        private TextView mTvTime;
        private ImageView mIvLike;
        private ImageView mIvComment;

        private LinearLayout mLlLikeContainer;
        private TextView mTvLikeNickName;

        private View mViewDivider;

        private RecyclerView mRvComment;
        private CommentAdapter mCommentAdapter;

        public DynamicViewHolder(View view) {
            super(view);
            mIvHeadPhoto = (ImageView) view.findViewById(R.id.iv_head_photo);
            mTvNickName = (TextView) view.findViewById(R.id.tv_nick_name);
            mTvContent = (TextView) view.findViewById(R.id.tv_content_text);
            mIvContent = (ImageView) view.findViewById(R.id.iv_content_image);
            mTvTime = (TextView) view.findViewById(R.id.tv_time);
            mIvLike = (ImageView) view.findViewById(R.id.iv_like);
            mIvComment = (ImageView) view.findViewById(R.id.iv_chat);

            mLlLikeContainer = (LinearLayout) view.findViewById(R.id.ll_like_container);
            mTvLikeNickName = (TextView) view.findViewById(R.id.tv_like_name);

            mViewDivider = (View) view.findViewById(R.id.view_divider);

            mRvComment = (RecyclerView) view.findViewById(R.id.rv_comment);
        }

        public void bindData(int position) {
            final ForumInfo info = data.get(position);
            Glide.with(mContext)
                    .load(CommonsUtil.getImageUrl(info.getHeadPhotoUrl()))
                    .placeholder(R.drawable.head_photo_default)
                    .into(mIvHeadPhoto);
            mTvNickName.setText(info.getUserName());
            mTvContent.setText(info.getContent());
            if (!TextUtils.isEmpty(info.getPhotoUrl())) {
                mIvContent.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(CommonsUtil.getImageUrl(info.getPhotoUrl()))
                        .asBitmap()
                        .placeholder(R.drawable.photo_default)
                        .into(mIvContent);
            } else {
                mIvContent.setVisibility(View.GONE);
            }
            mTvTime.setText(DateUtil.dateToString(info.getTime()));

            if (checkHasLike(info.getlFList(), mContext.getUserInfo().getuId())) {
                mIvLike.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_liked));
            } else {
                mIvLike.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_like_normal));
            }
            mIvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.likeForum(createLikeForum(info));
                }
            });
            mIvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.showCommentWidget(info, null);
                }
            });

            if (info.getlFList() == null || info.getlFList().size() == 0) {
                mLlLikeContainer.setVisibility(View.GONE);
            } else {
                mLlLikeContainer.setVisibility(View.VISIBLE);
                mTvLikeNickName.setText(getLikeNickName(info.getlFList()));
            }

            if (info.getlFList() == null || info.getlFList().size() == 0 ||
                    info.getcFList() == null || info.getcFList().size() == 0) {
                mViewDivider.setVisibility(View.GONE);
            } else {
                mViewDivider.setVisibility(View.VISIBLE);
            }

            if (info.getcFList() == null || info.getcFList().size() == 0) {
                mRvComment.setVisibility(View.GONE);
            } else {
                mCommentAdapter = new CommentAdapter(mContext, info, mPresenter);
                mCommentAdapter.setData(info.getcFList());
                mRvComment.setLayoutManager(new LinearLayoutManager(mContext));
                mRvComment.setAdapter(mCommentAdapter);
            }

        }
    }
}
