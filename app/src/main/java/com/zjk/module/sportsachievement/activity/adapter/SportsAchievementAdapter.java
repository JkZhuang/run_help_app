package com.zjk.module.sportsachievement.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjk.model.SportsData;
import com.zjk.module.sportsachievement.detail.view.SportsDetailActivity;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;
import com.zjk.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public class SportsAchievementAdapter extends RecyclerView.Adapter<SportsAchievementAdapter.SAViewHolder> {

    private static final String TAG = "SportsAchievementAdapter";

    private Context mContext;
    private List<SportsData> data = new ArrayList<>();

    public SportsAchievementAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<SportsData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public SAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SAViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sports_achievement, parent, false));
    }

    @Override
    public void onBindViewHolder(SAViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SAViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRlContainer;
        private TextView mTvContent;

        public SAViewHolder(View itemView) {
            super(itemView);
            mRlContainer = (RelativeLayout) itemView.findViewById(R.id.rl_container);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

        @SuppressLint("DefaultLocale")
        public void bindData(final SportsData sportsData) {
            mRlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SportsDetailActivity.start(mContext, sportsData);
                }
            });
            mTvContent.setText(
                    mContext.getString(R.string.sports_a_content,
                            DateUtil.dateToString(sportsData.getStartTime()),
                            CommonsUtil.getSportsType(mContext, sportsData.getType()),
                            String.format("%.2f", sportsData.getDistance()),
                            String.valueOf(sportsData.getUsedTime()),
                            String.format("%.2f", sportsData.getMaxSpeed())
                    )
            );
        }
    }
}
