package com.zjk.module.forum.dynamic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.module.forum.dynamic.present.DynamicPresenter;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommentAdapter";

    private Context mContext;
    private ForumInfo mForumInfo;
    private DynamicPresenter mPresenter;

    public CommentAdapter(Context context, ForumInfo forumInfo, DynamicPresenter presenter) {
        this.mContext = context;
        this.mForumInfo = forumInfo;
        this.mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentViewHolder holder = new CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommentViewHolder) holder).bindData(position);
    }

    @Override
    public int getItemCount() {
        return mForumInfo.getcFList().size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mTvComment = (TextView) itemView.findViewById(R.id.tv_dynamic_comment);
        }

        public void bindData(int position) {
            final CommentForumInfo info = mForumInfo.getcFList().get(position);
            String text = info.getUserName() + mContext.getResources().getString(R.string.reply) +
                    info.gettUserName() + ": " + info.getContent();
            mTvComment.setText(text);
            mTvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.showCommentWidget(mForumInfo, info);
                }
            });
        }
    }
}
