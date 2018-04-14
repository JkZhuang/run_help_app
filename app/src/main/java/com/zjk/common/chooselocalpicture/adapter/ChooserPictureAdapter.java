package com.zjk.common.chooselocalpicture.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zjk.common.ui.BaseActivity;
import com.zjk.module.user.register.view.RegisterActivity;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public class ChooserPictureAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ChooserPictureAdapter";

    private BaseActivity mContext;
    private ArrayList<String> photoFileNameList = new ArrayList<>();

    public ChooserPictureAdapter(BaseActivity context) {
        this.mContext = context;
    }

    public void setData(ArrayList<String> list) {
        this.photoFileNameList = list;
        notifyDataSetChanged();
    }

    @Override
    public ChoosePictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChoosePictureHolder holder = new ChoosePictureHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_choose_picture, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChoosePictureHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return photoFileNameList.size();
    }

    class ChoosePictureHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPicture;

        public ChoosePictureHolder(View itemView) {
            super(itemView);
            mIvPicture = itemView.findViewById(R.id.iv_picture);
        }

        public void bindView(final int position) {
            File file = new File(photoFileNameList.get(position));
            DebugUtil.debug(TAG, photoFileNameList.get(position));
            Glide.with(mContext)
                    .load(file)
                    .placeholder(R.drawable.photo_default)
                    .into(mIvPicture);
            mIvPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra(RegisterActivity.KEY_IMAGE, photoFileNameList.get(position));
                    mContext.setResult(Activity.RESULT_OK, intent);
                    mContext.finish();
                }
            });
        }
    }
}
