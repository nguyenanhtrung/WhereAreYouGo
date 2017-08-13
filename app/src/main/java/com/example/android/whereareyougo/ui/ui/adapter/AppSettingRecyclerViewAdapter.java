package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.Setting;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class AppSettingRecyclerViewAdapter extends UltimateViewAdapter<AppSettingRecyclerViewAdapter.AppSettingViewHolder> {
    private Context context;
    private ArrayList<Setting> settings;
    private AppSettingOnClickItem appSettingOnClickItem;

    public AppSettingRecyclerViewAdapter(Context context, ArrayList<Setting> settings, AppSettingOnClickItem appSettingOnClickItem) {
        this.context = context;
        this.settings = settings;
        this.appSettingOnClickItem = appSettingOnClickItem;
    }

    public interface AppSettingOnClickItem{
        void onClickItem(View v, int position);
    }

    @Override
    public AppSettingViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public AppSettingViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public AppSettingViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_app_settings_row, parent, false);

        return new AppSettingViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return settings.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(AppSettingViewHolder holder, int position) {
        Setting currentSetting = settings.get(position);
        if (currentSetting != null){
            //load image icon for setting
            Glide.with(context)
                 .load(R.drawable.ic_my_location)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .override(48,48)
                 .into(holder.imageSetting);

            //set SettingName
            holder.textSettingName.setText(currentSetting.getSettingName());

            //set SettingContent
            holder.textSettingContent.setText(currentSetting.getSettingContent());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class AppSettingViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener {
        ImageView imageSetting;
        TextView textSettingName;
        TextView textSettingContent;

        public AppSettingViewHolder(View itemView) {
            super(itemView);
            imageSetting = (ImageView) itemView.findViewById(R.id.image_setting);
            textSettingName = (TextView) itemView.findViewById(R.id.text_setting_name);
            textSettingContent = (TextView) itemView.findViewById(R.id.text_setting_content);
            //
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            appSettingOnClickItem.onClickItem(v,getAdapterPosition());
        }
    }
}
