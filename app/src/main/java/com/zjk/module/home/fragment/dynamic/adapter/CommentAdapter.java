package com.zjk.module.home.fragment.dynamic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjk.model.CommentForumInfo;
import com.zjk.run_help.R;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommentAdapter";

    private Context mContext;
    private List<CommentForumInfo> data;

    public CommentAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<CommentForumInfo> data) {
        this.data = data;
        notifyDataSetChanged();
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
        return data.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mTvComment = (TextView) itemView.findViewById(R.id.tv_dynamic_comment);
        }

        public void bindData(int position) {
            CommentForumInfo info = data.get(position);
            String text = info.getUserName() + ": " + info.getContent();
            mTvComment.setText(text);
        }
    }
}
