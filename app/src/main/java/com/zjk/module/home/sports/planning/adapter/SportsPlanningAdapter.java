package com.zjk.module.home.sports.planning.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjk.model.SportsSuggestion;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;
import com.zjk.util.DateUtil;
import com.zjk.util.DebugUtil;

import java.util.ArrayList;
import java.util.Date;
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

    public SportsPlanningAdapter(Context context) {
        this.mContext = context;
        data = new ArrayList<>();

//        test();
    }

    private void test() {
        SportsSuggestion suggestion = new SportsSuggestion();
        suggestion.setsSId(1);
        suggestion.setuId(1);
        suggestion.setStartTime(new Date());
        suggestion.setType(0);
        suggestion.setUsedTime(100);
        data.add(suggestion);

        SportsSuggestion suggestion1 = new SportsSuggestion();
        suggestion1.setsSId(1);
        suggestion1.setuId(1);
        suggestion1.setStartTime(new Date());
        suggestion1.setType(2);
        suggestion1.setUsedTime(200);
        data.add(suggestion1);

        SportsSuggestion suggestion2 = new SportsSuggestion();
        suggestion2.setsSId(1);
        suggestion2.setuId(1);
        suggestion2.setStartTime(new Date());
        suggestion2.setType(1);
        suggestion2.setUsedTime(120);
        data.add(suggestion2);
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

        private TextView mTvType;
        private TextView mTvUseTime;
        private TextView mTvStartTime;

        public PlanningViewHolder(View itemView) {
            super(itemView);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
            mTvUseTime = (TextView) itemView.findViewById(R.id.tv_use_time);
            mTvStartTime = (TextView) itemView.findViewById(R.id.tv_start_time);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(int position) {
            SportsSuggestion suggestion = data.get(position);
            DebugUtil.debug(TAG, suggestion.toString());
            mTvType.setText(CommonsUtil.getSportsType(mContext, suggestion.getType()) +
                    mContext.getResources().getString(R.string.suggestion));
            mTvUseTime.setText(mContext.getResources().getString(R.string.used_time) +
                    String.valueOf(suggestion.getUsedTime()) + mContext.getResources().getString(R.string.min));
            mTvStartTime.setText(mContext.getResources().getString(R.string.start_time) +
                    DateUtil.dateToString(suggestion.getStartTime()));
        }
    }
}
