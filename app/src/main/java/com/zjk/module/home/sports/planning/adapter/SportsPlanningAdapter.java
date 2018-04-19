package com.zjk.module.home.sports.planning.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjk.model.SportsSuggestion;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;
import com.zjk.util.DateUtil;
import com.zjk.util.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class SportsPlanningAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SportsPlanningAdapter";

    private Context mContext;
    private List<SportsSuggestion> data;
    private OnItemClickListener listener;

    public SportsPlanningAdapter(Context context) {
        this.mContext = context;
        data = new ArrayList<>();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<SportsSuggestion> data) {
        this.data = data;
        DebugUtil.debug(TAG, data.size() + "");
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlanningViewHolder holder = new PlanningViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_sports_planning, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PlanningViewHolder) holder).bindData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PlanningViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRlContainer;
        private TextView mTvType;
        private TextView mTvTargetDistance;
        private TextView mTvUseTime;
        private TextView mTvStartTime;

        public PlanningViewHolder(View itemView) {
            super(itemView);
            mRlContainer = (RelativeLayout) itemView.findViewById(R.id.rl_container);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
            mTvTargetDistance = (TextView) itemView.findViewById(R.id.tv_target_distance);
            mTvUseTime = (TextView) itemView.findViewById(R.id.tv_use_time);
            mTvStartTime = (TextView) itemView.findViewById(R.id.tv_start_time);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(final int position) {
            mRlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
            SportsSuggestion suggestion = data.get(position);
            DebugUtil.debug(TAG, suggestion.toString());
            mTvType.setText(CommonsUtil.getSportsType(mContext, suggestion.getType()) +
                    mContext.getResources().getString(R.string.suggestion));
            mTvTargetDistance.setText(String.format("%.2f", suggestion.getDistance()) + mContext.getString(R.string.kilometers));
            mTvUseTime.setText(mContext.getResources().getString(R.string.used_time) +
                    String.valueOf(suggestion.getUsedTime()) + mContext.getResources().getString(R.string.min));
            mTvStartTime.setText(mContext.getResources().getString(R.string.start_time) +
                    DateUtil.dateToString(suggestion.getStartTime()));
        }
    }
}
