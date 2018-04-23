package com.zjk.module.ranking.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjk.common.ui.BaseActivity;
import com.zjk.model.RankingVersion;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private static final String TAG = "RankingAdapter";

    private BaseActivity mContext;
    private List<RankingVersion> data = new ArrayList<>();

    public RankingAdapter(BaseActivity activity) {
        this.mContext = activity;
    }

    public void setData(List<RankingVersion> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RankingViewHolder holder = new RankingViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ranking, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RankingViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvRanking;
        private TextView mTvUserName;
        private ImageView mIvHeadPhoto;
        private TextView mTvDistance;

        public RankingViewHolder(View itemView) {
            super(itemView);
            mTvRanking = (TextView) itemView.findViewById(R.id.tv_ranking);
            mTvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            mIvHeadPhoto = (ImageView) itemView.findViewById(R.id.iv_head_photo);
            mTvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
        }

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        public void bindData(RankingVersion rankingVersion) {
            mTvRanking.setText(String.valueOf(rankingVersion.getRanking()));
            mTvUserName.setText(rankingVersion.getUserName());
            mTvDistance.setText(String.format("%.2f", rankingVersion.getDistance()) + mContext.getString(R.string.kilometers));
            Glide.with(mContext)
                    .load(CommonsUtil.getImageUrl(rankingVersion.getHeadUrl()))
                    .placeholder(R.drawable.head_photo_default)
                    .into(mIvHeadPhoto);
        }
    }
}
